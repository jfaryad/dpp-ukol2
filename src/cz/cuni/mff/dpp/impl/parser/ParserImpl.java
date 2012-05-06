package cz.cuni.mff.dpp.impl.parser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.ArgumentFormatException;
import cz.cuni.mff.dpp.api.ArgumentValidator;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.OptionSetter;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.SingleOption;
import cz.cuni.mff.dpp.api.parser.CommandLineParser;
import cz.cuni.mff.dpp.api.parser.exception.CommandLineParserException;
import cz.cuni.mff.dpp.api.parser.exception.CommonArgumentFormatException;
import cz.cuni.mff.dpp.api.parser.exception.DependentOptionsException;
import cz.cuni.mff.dpp.api.parser.exception.IncompatibleOptionsException;
import cz.cuni.mff.dpp.api.parser.exception.LongOptionException;
import cz.cuni.mff.dpp.api.parser.exception.OptionParameterFormatException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredCommonArgumentCountException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionCountException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionParameterException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionParameterException;
import cz.cuni.mff.dpp.api.parser.exception.ValidationException;

/**
 * {@code DefaultCommandLineParser} strategy. Is not meant for public usage.
 * 
 * @author Tom
 * 
 * @param <T>
 */
/* package */class ParserImpl<T> {

    private final String EMPTY_OPTION_PARAMETER = "";

    private final String[] commandLine;

    private final List<String> commonArgumetList = new ArrayList<String>();

    private final List<ParsedOption> parsedOptionList = new ArrayList<ParsedOption>();

    private final Options<T> options;

    private T targetBean;

    /* package */ParserImpl(final Options<T> options, final String[] commandLine) {
        super();
        this.commandLine = commandLine;
        this.options = options;

        createTargetBean();
        parse();
        check();
        process();
    }

    private void process() {
        processOptions();
        processCommonArguments();
    }

    private void createTargetBean() {
        try {
            final Constructor<T> constructor = options.getTargetBeanClass().getConstructor();
            targetBean = constructor.newInstance();
        } catch (final Exception e) {
            throw new UnexceptedException(e);
        }

    }

    private void check() {

        final Map<SingleOption, Integer> optionCounts = new HashMap<SingleOption, Integer>();

        for (final ParsedOption parsedOption : parsedOptionList) {

            final String optionName = parsedOption.getOptionName();

            checkLongOption(parsedOption);

            final SingleOption singleOption = parsedOption.getSingleOptionConfig();
            addSingleOptionCount(optionCounts, singleOption);

            if (singleOption == null) {
                processException(new UnexceptedOptionException(optionName, parsedOption.isShortOption()));
                continue;
            }

            checkUnexceptedParameterOption(parsedOption);
            checkExceptedParameterOption(parsedOption);
        }

        checkOptionsCompatibility(optionCounts);
        checkOptionCounts(optionCounts);
        checkCommonArgumentsCounts();

    }

    private void checkLongOption(final ParsedOption parsedOption) {
        if (parsedOption.isShortOption() && !parsedOption.isPrefixedWithShortOptionPrefix()) {
            processException(new LongOptionException(parsedOption.getOptionName()));
        } else if (!parsedOption.isShortOption() && parsedOption.isPrefixedWithShortOptionPrefix()) {
            throw new IllegalStateException("This should never happen");
        } else {
            // all is ok
        }
    }

    private void addSingleOptionCount(final Map<SingleOption, Integer> optionCounts, final SingleOption singleOption) {
        final int count = getSingleOptionCount(optionCounts, singleOption);
        optionCounts.put(singleOption, count + 1);
    }

    private int getSingleOptionCount(final Map<SingleOption, Integer> optionCounts, final SingleOption singleOption) {
        return optionCounts.containsKey(singleOption) ? optionCounts.get(singleOption) : 0;
    }

    private void checkExceptedParameterOption(final ParsedOption parsedOption) {
        if (!parsedOption.isOptionParameterSpecified()
                && parsedOption.getSingleOptionConfig().getArgumentObligation() == OptionArgumentObligation.REQUIRED) {
            processException(new RequiredOptionParameterException(parsedOption.getOptionName()));
        }
    }

    private void checkUnexceptedParameterOption(final ParsedOption parsedOption) {
        if (parsedOption.isOptionParameterSpecified()
                && parsedOption.getSingleOptionConfig().getArgumentObligation() == OptionArgumentObligation.FORBIDDEN) {
            processException(new UnexceptedOptionParameterException(parsedOption.getOptionName(),
                    parsedOption.getOptionParameter()));
        }
    }

    private void checkOptionCounts(final Map<SingleOption, Integer> optionCounts) {

        for (final SingleOption singleOption : options.getOptions()) {
            final int count = getSingleOptionCount(optionCounts, singleOption);
            if (!singleOption.getRequiredCountInterval().isInside(count)) {
                processException(new RequiredOptionCountException(singleOption, count));
            }
        }
    }

    private void checkOptionsCompatibility(final Map<SingleOption, Integer> optionCounts) {

        for (final SingleOption singleOption : optionCounts.keySet()) {
            checkDependentOptions(singleOption, new HashSet<SingleOption>(optionCounts.keySet()));
            checkIncompatibleOptions(singleOption, new HashSet<SingleOption>(optionCounts.keySet()));
        }
    }

    private void checkDependentOptions(final SingleOption singleOption, final Set<SingleOption> optionsOnCommandLine) {
        final Collection<SingleOption> dependentSingleOptionList =
                options.getDependentSingleOptionList(singleOption.getFirstOptionName());
        if (!optionsOnCommandLine.containsAll(dependentSingleOptionList)) {
            optionsOnCommandLine.removeAll(dependentSingleOptionList);
            processException(new DependentOptionsException(singleOption, optionsOnCommandLine));
        }
    }

    private void checkIncompatibleOptions(final SingleOption singleOption, final Set<SingleOption> optionsOnCommandLine) {
        final Collection<SingleOption> incompatibleSingleOptionList =
                options.getIncompatibleSingleOptionList(singleOption.getFirstOptionName());
        optionsOnCommandLine.retainAll(incompatibleSingleOptionList);
        if (!optionsOnCommandLine.isEmpty()) {
            processException(new IncompatibleOptionsException(singleOption, optionsOnCommandLine));
        }
    }

    private void checkCommonArgumentsCounts() {
        if (!options.getCommonArgumentRequiredCountInterval().isInside(commonArgumetList.size())) {
            processException(new RequiredCommonArgumentCountException(commonArgumetList.size()));
        }
    }

    private static void processException(final CommandLineParserException exception) {
        throw exception;
    }

    private void processOptions() {

        for (final ParsedOption parsedOption : parsedOptionList) {
            final SingleOption singleOption = options.getOption(parsedOption.getOptionName());

            Object convertedOptionParameter;
            if (parsedOption.isOptionParameterSpecified()) {

                try {
                    convertedOptionParameter = singleOption.getArgumentConverter().convert(
                            parsedOption.getOptionParameter());
                } catch (final ArgumentFormatException afe) {
                    processException(new OptionParameterFormatException(afe, parsedOption.getOptionName()));
                    continue;
                } catch (final Exception e) {
                    throw new UnexceptedException(e);
                }

            } else {
                convertedOptionParameter = singleOption.getDefaultValue();
            }
            applyParameterValidations(singleOption, convertedOptionParameter);
            setOptionParameter(singleOption.getOptionSetter(), convertedOptionParameter);
        }
    }

    private <P> void applyParameterValidations(final SingleOption option,
            final P parameter) {
        for (final ArgumentValidator<?> validator : option.getValidators()) {
            if (!validator.isValid(parameter)) {
                processException(new ValidationException(validator, parameter));
            }
        }
    }

    private void setOptionParameter(final OptionSetter optionSetter, final Object convertedOptionParameter) {

        try {
            optionSetter.setOption(targetBean, convertedOptionParameter);
        } catch (final Exception e) {
            throw new UnexceptedException(e);
        }

    }

    private void processCommonArguments() {

        final OptionSetter commonArgumentSetter = options.getCommonArgumentSetter();
        final ArgumentConverter<?> commonArgumentConverter = options.getCommonArgumentConverter();
        for (final String commonArgument : commonArgumetList) {

            Object convertedObject;
            try {
                convertedObject = commonArgumentConverter.convert(commonArgument);
            } catch (final ArgumentFormatException afe) {
                processException(new CommonArgumentFormatException(afe));
                continue;
            } catch (final Exception e) {
                throw new UnexceptedException(e);
            }

            setOptionParameter(commonArgumentSetter, convertedObject);
        }

    }

    private void parse() {

        boolean isNextCommonArgument = false;
        boolean isNextOptionParameter = false;
        ParsedOption lastOption = null;

        for (final String element : commandLine) {

            if (isNextCommonArgument) {
                commonArgumetList.add(element);
            } else if (isNextOptionParameter) {
                lastOption.setOptionParameter(element);
                isNextOptionParameter = false;
            } else if (CommandLineParser.COMMON_ARGUMENT_DELIMITER.equals(element)) {
                isNextCommonArgument = true;
            } else if (isOption(element)) {
                lastOption = addOptions(element);
                if (!lastOption.isOptionParameterSpecified() && hasOptionParameter(lastOption.getOptionName())) {
                    isNextOptionParameter = true;
                }
            } else {
                commonArgumetList.add(element);
            }

        }
    }

    private boolean hasOptionParameter(final String optionName) {
        final SingleOption singleOption = options.getOption(optionName);
        return singleOption != null && singleOption.getArgumentObligation() == OptionArgumentObligation.REQUIRED;
    }

    private ParsedOption addOptions(final String value) {

        String token;
        boolean isShortOption;
        if (isLongOption(value)) {
            token = value.substring(CommandLineParser.LONG_OPTION_PREFIX.length());
            isShortOption = false;
        } else if (isShortOption(value)) {
            token = value.substring(CommandLineParser.SHORT_OPTION_PREFIX.length());
            isShortOption = true;
        } else {
            throw new IllegalStateException("This should never happen");
        }

        final int delimIndex = token.indexOf(CommandLineParser.OPTION_VALUE_DELIMITER);

        String tokenName;
        String tokenParameter;
        if (delimIndex == -1) {
            tokenName = token;
            tokenParameter = null;
        } else {
            tokenName = token.substring(0, delimIndex);
            if (token.length() == delimIndex + 1) {
                tokenParameter = EMPTY_OPTION_PARAMETER;
            } else {
                tokenParameter = token.substring(delimIndex + 1);
            }
        }

        if (isShortOption) {
            addShortOptions(tokenName, isShortOption);
        } else {
            addParsedOption(tokenName, isShortOption);
        }

        final ParsedOption lastParsedOption = parsedOptionList.get(parsedOptionList.size() - 1);
        if (tokenParameter != null) {
            lastParsedOption.setOptionParameter(tokenParameter);
        }

        return lastParsedOption;
    }

    private void addShortOptions(final String tokenName, final boolean prefixedWithShortOption) {
        final char[] optionNames = tokenName.toCharArray();
        for (final char optionName : optionNames) {
            addParsedOption(Character.toString(optionName), prefixedWithShortOption);
        }
    }

    private void addParsedOption(final String optionName, final boolean prefixedWithShortOption) {
        final ParsedOption parsedOption = new ParsedOption();
        parsedOption.setOptionName(optionName);
        parsedOption.setPrefixedWithShortOptionPrefix(prefixedWithShortOption);
        parsedOptionList.add(parsedOption);
    }

    private static boolean isOption(final String value) {
        return isLongOption(value) || isShortOption(value);
    }

    private static boolean isShortOption(final String value) {
        return value.startsWith(CommandLineParser.SHORT_OPTION_PREFIX)
                && value.length() > CommandLineParser.SHORT_OPTION_PREFIX.length();
    }

    private static boolean isLongOption(final String value) {
        return value.startsWith(CommandLineParser.LONG_OPTION_PREFIX) &&
                value.length() > CommandLineParser.LONG_OPTION_PREFIX.length();
    }

    public T getTargetBean() {
        return targetBean;
    }

    private class ParsedOption {

        private boolean prefixedWithShortOptionPrefix;

        private String optionName;

        private String optionParameter;

        private SingleOption singleOption;

        public String getOptionName() {
            return optionName;
        }

        public String getOptionParameter() {
            return optionParameter;
        }

        public void setOptionName(final String optionName) {
            this.optionName = optionName;
            this.singleOption = options.getOption(optionName);
        }

        public void setOptionParameter(final String argument) {
            this.optionParameter = argument;
        }

        public boolean isShortOption() {
            return optionName.length() == CommandLineParser.SHORT_OPTION_NAME_LENGTH;
        }

        public boolean isOptionParameterSpecified() {
            return optionParameter != null;
        }

        public SingleOption getSingleOptionConfig() {
            return singleOption;
        }

        public boolean isPrefixedWithShortOptionPrefix() {
            return prefixedWithShortOptionPrefix;
        }

        public void setPrefixedWithShortOptionPrefix(final boolean prefixedShortOptionPrefix) {
            this.prefixedWithShortOptionPrefix = prefixedShortOptionPrefix;
        }
    }

}
