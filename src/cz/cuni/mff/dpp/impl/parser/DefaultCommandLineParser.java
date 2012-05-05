package cz.cuni.mff.dpp.impl.parser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cz.cuni.mff.dpp.api.parser.exception.OptionParameterFormatException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredCommonArgumentCountException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionCountException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionParameterException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionParameterException;

public class DefaultCommandLineParser implements CommandLineParser {

    private final Options options;

    public DefaultCommandLineParser(final Options options) {
        super();
        this.options = options;
    }

    @Override
    public Object parse(final String[] commnadLine) {
        return new ParserImpl(commnadLine).getTargetBean();
    }

    @Override
    public Options getOptions() {
        return options;
    }

    private class ParserImpl {

        private final String[] commandLine;

        private final List<String> commonArgumetList = new ArrayList<String>();

        private final List<ParsedOption> parsedOptionList = new ArrayList<DefaultCommandLineParser.ParsedOption>();

        // private Set<SingleOption> singleOptionsOnCommandLine;

        private Object targetBean;

        public ParserImpl(final String[] commandLine) {
            super();
            this.commandLine = commandLine;

            createTargetBean();
            parse();
            // fillSingleOptionsOnCommandLine();
            check();
            process();
        }

        // private void fillSingleOptionsOnCommandLine() {
        // for (ParsedOption parsedOption : parsedOptionList) {
        // singleOptionsOnCommandLine.add(parsedOption.getSingleOptionConfig());
        // }
        // }

        private void process() {
            processOptions();
            processCommonArguments();
        }

        private void createTargetBean() {
            try {
                final Constructor<?> constructor = options.getTargetBeanClass().getConstructor();
                targetBean = constructor.newInstance();
            } catch (final Exception e) {
                throw new UnexceptedException(e);
            }

        }

        private void check() {

            Map<SingleOption, Integer> optionCounts = new HashMap<SingleOption, Integer>();

            for (final ParsedOption parsedOption : parsedOptionList) {

                final String optionName = parsedOption.getOptionName();

                final SingleOption singleOption = parsedOption.getSingleOptionConfig();
                addSingleOptionCount(optionCounts, singleOption);

                if (singleOption == null) {
                    processException(new UnexceptedOptionException(optionName, parsedOption.isShortOption()));
                    continue;
                }

                checkUnexceptedParameterOption(parsedOption);
                checkExceptedParameterOption(parsedOption);

                checkDependentOption(singleOption);

            }

            checkOptionCounts(optionCounts);
            checkCommonArgumentsCounts();

        }

        private void addSingleOptionCount(final Map<SingleOption, Integer> optionCounts, final SingleOption singleOption) {
            final int count = getSingleOptionCount(optionCounts, singleOption);
            optionCounts.put(singleOption, count + 1);
        }

        private int getSingleOptionCount(final Map<SingleOption, Integer> optionCounts, final SingleOption singleOption) {
            return optionCounts.containsKey(singleOption) ? optionCounts.get(singleOption) : 0;
        }

        private void checkExceptedParameterOption(final ParsedOption parsedOption) {
            if (!parsedOption.hasOptionParameter()
                    && parsedOption.getSingleOptionConfig().getArgumentObligation() == OptionArgumentObligation.REQUIRED) {
                processException(new RequiredOptionParameterException(parsedOption.getOptionName()));
            }
        }

        private void checkUnexceptedParameterOption(final ParsedOption parsedOption) {
            if (parsedOption.hasOptionParameter()
                    && parsedOption.getSingleOptionConfig().getArgumentObligation() == OptionArgumentObligation.FORBIDDEN) {
                processException(new UnexceptedOptionParameterException(parsedOption.getOptionName(),
                        parsedOption.getOptionParameter()));
            }
        }

        private void checkOptionCounts(final Map<SingleOption, Integer> optionCounts) {

            for (final SingleOption singleOption : options.getOptions()) {
                final int count = getSingleOptionCount(optionCounts, singleOption);
                if (!singleOption.getRequiredCountInterval().isInside(count)) {
                    processException(new RequiredOptionCountException(singleOption.getFirstOptionName(), count));
                }
            }
        }

        private void checkDependentOption(final SingleOption singleOption) {
            // todo - budeme to opravdu podporovat???
        }

        public void checkCommonArgumentsCounts() {
            if (!options.getCommonArgumentRequiredCountInterval().isInside(commonArgumetList.size())) {
                processException(new RequiredCommonArgumentCountException(commonArgumetList.size()));
            }
        }

        private void processException(final CommandLineParserException exception) {
            throw exception;
        }

        private void processOptions() {

            for (final ParsedOption parsedOption : parsedOptionList) {
                final SingleOption singleOption = options.getOption(parsedOption.getOptionName());

                Object convertedOptionParameter;
                if (parsedOption.hasOptionParameter()) {

                    try {
                        convertedOptionParameter = singleOption.getArgumentConverter().parse(
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

        // TODO mozna presunout nekam jinam podle potreby a zmenit vyjimku
        private <T> void applyParameterValidations(final SingleOption option,
                final T parameter) {
            for (final ArgumentValidator<?> validator : option.getValidators()) {
                if (!validator.isValid(parameter)) {
                    throw new RuntimeException("Validation failed for the option parameter" + option.getArgumentName()
                            + " with value " + parameter);
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

                // todo...
                if (commonArgumentConverter == null) {
                    convertedObject = commonArgument;
                } else {

                    try {
                        convertedObject = commonArgumentConverter.parse(commonArgument);
                    } catch (final ArgumentFormatException afe) {
                        processException(new CommonArgumentFormatException(afe));
                        continue;
                    } catch (final Exception e) {
                        throw new UnexceptedException(e);
                    }
                }

                setOptionParameter(commonArgumentSetter, convertedObject);
            }

        }

        private void parse() {

            boolean isNextCommonArgument = false;
            boolean isNextOptionParameter = false;
            ParsedOption option = null;

            for (final String element : commandLine) {

                if (isNextCommonArgument) {
                    commonArgumetList.add(element);
                } else if (isNextOptionParameter) {
                    option.setOptionParameter(element);
                    isNextOptionParameter = false;
                } else if (CommandLineParser.COMMON_ARGUMENT_DELIMITER.equals(element)) {
                    isNextCommonArgument = true;
                } else if (isOption(element)) {
                    option = parseOption(element);
                    parsedOptionList.add(option);
                    if (option.getOptionParameter() == null && hasOptionParameter(option.getOptionName())) {
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

        private ParsedOption parseOption(final String value) {

            final ParsedOption result = new ParsedOption();

            String token;
            if (isLongOption(value)) {
                token = value.substring(CommandLineParser.LONG_OPTION_PREFIX.length());
                result.setShortOption(false);
            } else if (isShortOption(value)) {
                token = value.substring(CommandLineParser.SHORT_OPTION_PREFIX.length());
                result.setShortOption(true);
            } else {
                throw new IllegalStateException("This should never happen");
            }

            // todo parsovani -abc
            final int delimIndex = token.indexOf("=");

            if (delimIndex == -1) {
                result.setOptionName(token);
            } else {
                final String optionName = token.substring(0, delimIndex);
                result.setOptionName(optionName);
                final String optionParam = token.substring(delimIndex + 1);
                result.setOptionParameter(optionParam);
            }

            return result;
        }

        private boolean isOption(final String value) {
            return isLongOption(value) || isShortOption(value);
        }

        private boolean isShortOption(final String value) {
            return value.startsWith(CommandLineParser.SHORT_OPTION_PREFIX)
                    && value.length() > CommandLineParser.SHORT_OPTION_PREFIX.length();
        }

        private boolean isLongOption(final String value) {
            return value.startsWith(CommandLineParser.LONG_OPTION_PREFIX) &&
                    value.length() > CommandLineParser.LONG_OPTION_PREFIX.length();
        }

        public Object getTargetBean() {
            return targetBean;
        }

    }

    private class ParsedOption {

        private boolean isShortOption;

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
            return isShortOption;
        }

        public void setShortOption(final boolean isShortOption) {
            this.isShortOption = isShortOption;
        }

        public boolean hasOptionParameter() {
            return optionParameter != null;
        }

        public SingleOption getSingleOptionConfig() {
            return singleOption;
        }
    }
}
