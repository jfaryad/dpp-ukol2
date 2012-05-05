package cz.cuni.mff.dpp.impl.parser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.ArgumentFormatException;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.OptionSetter;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.SingleOption;
import cz.cuni.mff.dpp.api.parser.CommandLineParser;
import cz.cuni.mff.dpp.api.parser.exception.CommandLineParserException;
import cz.cuni.mff.dpp.api.parser.exception.CommonArgumentFormatException;
import cz.cuni.mff.dpp.api.parser.exception.OptionParameterFormatException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionParameterException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionParameterException;

public class DefaultCommandLineParser implements CommandLineParser {

    private final Options options;

    public DefaultCommandLineParser(Options options) {
        super();
        this.options = options;
    }

    @Override
    public Object parse(String[] commnadLine) {
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

        public ParserImpl(String[] commandLine) {
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
                Constructor<?> constructor = options.getTargetBeanClass().getConstructor();
                targetBean = constructor.newInstance();
            } catch (Exception e) {
                throw new UnexceptedException(e);
            }

        }

        private void check() {

            Collection<SingleOption> requiredOptions = new HashSet<SingleOption>(options.getRequiredOptions());

            for (ParsedOption parsedOption : parsedOptionList) {

                String optionName = parsedOption.getOptionName();

                SingleOption singleOption = parsedOption.getSingleOptionConfig();
                requiredOptions.remove(singleOption);

                if (singleOption == null) {
                    processException(new UnexceptedOptionException(optionName, parsedOption.isShortOption()));
                    continue;
                }

                checkUnexceptedParameterOption(parsedOption);
                checkExceptedParameterOption(parsedOption);

                checkDependentOption(singleOption);

            }

            checkReqiuredOptions(requiredOptions);

        }

        private void checkExceptedParameterOption(ParsedOption parsedOption) {
            if (!parsedOption.hasOptionParameter()
                    && parsedOption.getSingleOptionConfig().getArgumentObligation() == OptionArgumentObligation.REQUIRED) {
                processException(new RequiredOptionParameterException(parsedOption.getOptionName()));
            }
        }

        private void checkUnexceptedParameterOption(ParsedOption parsedOption) {
            if (parsedOption.hasOptionParameter()
                    && parsedOption.getSingleOptionConfig().getArgumentObligation() == OptionArgumentObligation.FORBIDDEN) {
                processException(new UnexceptedOptionParameterException(parsedOption.getOptionName(),
                        parsedOption.getOptionParameter()));
            }
        }

        private void checkReqiuredOptions(Collection<SingleOption> unprovidedRequiredOptions) {
            if (!unprovidedRequiredOptions.isEmpty()) {
                List<String> optionNames = new ArrayList<String>(unprovidedRequiredOptions.size());
                for (SingleOption singleOption : unprovidedRequiredOptions) {
                    // only first name for each option
                    optionNames.add(singleOption.getNames().iterator().next());
                }
                processException(new RequiredOptionException(optionNames));
            }
        }

        private void checkDependentOption(SingleOption singleOption) {
            // todo - budeme to opravdu podporovat???
        }

        private void checkCommonArguments() {
            // todo spolecne s poctama
        }

        private void processException(CommandLineParserException exception) {
            throw exception;
        }

        private void processOptions() {

            for (ParsedOption parsedOption : parsedOptionList) {
                SingleOption singleOption = options.getOption(parsedOption.getOptionName());

                Object convertedOptionParameter;
                if (parsedOption.hasOptionParameter()) {

                    try {
                        convertedOptionParameter = singleOption.getArgumentConverter().parse(
                                parsedOption.getOptionParameter());
                    } catch (ArgumentFormatException afe) {
                        processException(new OptionParameterFormatException(afe, parsedOption.getOptionName()));
                        continue;
                    } catch (Exception e) {
                        throw new UnexceptedException(e);
                    }

                } else {
                    convertedOptionParameter = singleOption.getDefaultValue();
                }

                setOptionParameter(singleOption.getOptionSetter(), convertedOptionParameter);
            }
        }

        private void setOptionParameter(OptionSetter optionSetter, Object convertedOptionParameter) {

            try {
                optionSetter.setOption(targetBean, convertedOptionParameter);
            } catch (Exception e) {
                throw new UnexceptedException(e);
            }

        }

        private void processCommonArguments() {

            OptionSetter commonArgumentSetter = options.getCommonArgumentSetter();
            ArgumentConverter<?> commonArgumentConverter = options.getCommonArgumentConverter();
            for (String commonArgument : commonArgumetList) {
                Object convertedObject;

                // todo...
                if (commonArgumentConverter == null) {
                    convertedObject = commonArgument;
                } else {

                    try {
                        convertedObject = commonArgumentConverter.parse(commonArgument);
                    } catch (ArgumentFormatException afe) {
                        processException(new CommonArgumentFormatException(afe));
                        continue;
                    } catch (Exception e) {
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

            for (String element : commandLine) {

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

        private boolean hasOptionParameter(String optionName) {
            SingleOption singleOption = options.getOption(optionName);
            return singleOption != null && singleOption.getArgumentObligation() == OptionArgumentObligation.REQUIRED;
        }

        private ParsedOption parseOption(String value) {

            ParsedOption result = new ParsedOption();

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

            
            //todo parsovani -abc
            int delimIndex = token.indexOf("=");

            if (delimIndex == -1) {
                result.setOptionName(token);
            } else {
                String optionName = token.substring(0, delimIndex);
                result.setOptionName(optionName);
                String optionParam = token.substring(delimIndex + 1);
                result.setOptionParameter(optionParam);
            }

            return result;
        }

        private boolean isOption(String value) {
            return isLongOption(value) || isShortOption(value);
        }

        private boolean isShortOption(String value) {
            return value.startsWith(CommandLineParser.SHORT_OPTION_PREFIX)
                    && value.length() > CommandLineParser.SHORT_OPTION_PREFIX.length();
        }

        private boolean isLongOption(String value) {
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

        public void setOptionName(String optionName) {
            this.optionName = optionName;
            this.singleOption = options.getOption(optionName);
        }

        public void setOptionParameter(String argument) {
            this.optionParameter = argument;
        }

        public boolean isShortOption() {
            return isShortOption;
        }

        public void setShortOption(boolean isShortOption) {
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
