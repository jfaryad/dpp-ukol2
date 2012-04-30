package cz.cuni.mff.dpp.parser;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.dpp.api.CommandLineParser;
import cz.cuni.mff.dpp.api.Options;

public class DefaultCommandLineParser implements CommandLineParser {

    private final Options options;

    public DefaultCommandLineParser(Options options) {
        super();
        this.options = options;
    }

    @Override
    public Object parse(String[] commnadLine) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Options getOptions() {
        return options;
    }

    private class DefaultCommandLineParserImpl {

        private final String[] commandLine;

        private final List<String> argumetList = new ArrayList<String>();

        private final List<OptionWithParameter> optionsList = new ArrayList<DefaultCommandLineParser.OptionWithParameter>();

        private Object bean;

        public DefaultCommandLineParserImpl(String[] commandLine) {
            super();
            this.commandLine = commandLine;

            parse();

        }

        private void parse() {
            
            boolean isNextCommonArgument=false;
            boolean isNextOptionParameter=false;
            OptionWithParameter option=null;
            
            for (String element : commandLine) {
                
                if (isNextCommonArgument) {
                  argumetList.add(element);  
                } else if (isNextOptionParameter) {
                    option.setArgument(element);
                    isNextOptionParameter=false;
                } else if (CommandLineParser.COMMON_ARGUMENT_DELIMITER.equals(element)) {
                    isNextCommonArgument=true;
                } else if (isOption(element)) {
                    
                    //todotodotodo
                    
                }
                
            }
        }

        private boolean isOption(String value) {
            return isLongOption(value) || isShortOption(value);
        }
        
        private boolean isLongOption(String value) {
            return value.startsWith(CommandLineParser.SHORT_OPTION_DELIMITER)
                    && value.length() > CommandLineParser.SHORT_OPTION_DELIMITER.length();
        }
        
        private boolean isShortOption(String value) {
            return value.startsWith(CommandLineParser.LONG_OPTION_DELIMITER) &&
                    value.length() > CommandLineParser.LONG_OPTION_DELIMITER.length();
        }
        
        
        
        

        public Object getBean() {
            return bean;
        }

    }

    private static class OptionWithParameter {

        private String optionName;

        private String argument;

        public OptionWithParameter(String optionName, String argument) {
            super();
            this.optionName = optionName;
            this.argument = argument;
        }

        public String getOptionName() {
            return optionName;
        }

        public String getArgument() {
            return argument;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public void setArgument(String argument) {
            this.argument = argument;
        }

    }

}
