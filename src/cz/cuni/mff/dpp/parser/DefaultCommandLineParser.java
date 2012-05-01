package cz.cuni.mff.dpp.parser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.CommandLineParser;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.OptionSetter;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.SingleOption;

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

        private Object targetBean;

        public ParserImpl(String[] commandLine) {
            super();
            this.commandLine = commandLine;

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
                Constructor<?> constructor = options.getTargetBeanClass().getConstructor();
                targetBean = constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
                // todo translate exception
            }

        }

        private void check() {

            // todo check parsed command line against configuration

        }

        private void processOptions() {

            for (ParsedOption parsedOption : parsedOptionList) {
                SingleOption singleOption = options.getOption(parsedOption.getOptionName());
                Object convertedOptionParameter;
                if (parsedOption.hasOptionParameter()) {
                    String optionParameter = parsedOption.getOptionParameter();
                    convertedOptionParameter = singleOption.getArgumentConverter().parse(optionParameter);
                    // todo catch and translate exception
                } else {
                    convertedOptionParameter = singleOption.getDefaultValue();
                }
                singleOption.getOptionSetter().setOption(targetBean, convertedOptionParameter);
            }
        }

        private void processCommonArguments() {

            OptionSetter commonArgumentSetter = options.getCommonArgumentSetter();
            ArgumentConverter<?> commonArgumentConverter = options.getCommonArgumentConverter();
            for (String commonArgument : commonArgumetList) {
                Object convertedObject;
                if (commonArgumentConverter == null) {
                    convertedObject = commonArgument;
                } else {
                    convertedObject = commonArgumentConverter.parse(commonArgument);
                }
                commonArgumentSetter.setOption(targetBean, convertedObject);
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
                token = value.substring(CommandLineParser.LONG_OPTION_DELIMITER.length());
                result.setShortOption(false);
            } else if (isShortOption(value)) {
                token = value.substring(CommandLineParser.SHORT_OPTION_DELIMITER.length());
                result.setShortOption(true);
            } else {
                throw new IllegalStateException("This should never happen");
            }

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
            // todo shrink option names
            return value.startsWith(CommandLineParser.SHORT_OPTION_DELIMITER)
                    && value.length() > CommandLineParser.SHORT_OPTION_DELIMITER.length();
        }

        private boolean isLongOption(String value) {
            return value.startsWith(CommandLineParser.LONG_OPTION_DELIMITER) &&
                    value.length() > CommandLineParser.LONG_OPTION_DELIMITER.length();
        }

        public Object getTargetBean() {
            return targetBean;
        }

    }

    private static class ParsedOption {

        private boolean isShortOption;

        private String optionName;

        private String optionParameter;

        public String getOptionName() {
            return optionName;
        }

        public String getOptionParameter() {
            return optionParameter;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
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
    }

}
