package cz.cuni.mff.dpp.api.parser.exception;

import cz.cuni.mff.dpp.api.parser.CommandLineParser;

/**
 * Indicates short option (one-character option) prefixed with long option prefix
 * 
 * @author Tom
 * 
 */
public class LongOptionException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Short option: %s prefixed with long option prefix: "
            + CommandLineParser.LONG_OPTION_PREFIX + ".";

    private final String optionName;

    public LongOptionException(String optionName) {
        super(String.format(MESSAGE, optionName));
        this.optionName = optionName;
    }

    /**
     * Returns bad prefixed short option name
     * 
     * @return
     */
    public String getOptionName() {
        return optionName;
    }

}
