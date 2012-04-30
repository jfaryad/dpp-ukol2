package cz.cuni.mff.dpp.api;

public interface CommandLineParser {
    
    String COMMON_ARGUMENT_DELIMITER="--";
    
    String SHORT_OPTION_DELIMITER="-";
    
    String LONG_OPTION_DELIMITER="-";

    Object parse(String[] commnadLine);

    Options getOptions();

}
