package cz.cuni.mff.dpp.api.parser.exception;

/**
 * Indicates no option parameter for option with obligatory option parameter
 * 
 * @author Tom
 * 
 */
public class RequiredOptionParameterException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "There is no option parameter on command line for option with name: '%s'";

    private final String optionName;

    public RequiredOptionParameterException(String optionName) {
        super(String.format(MESSAGE, optionName));
        this.optionName = optionName;
    }

    /**
     * Option name for which is unspecified option parameter
     * 
     * @return
     */
    public String getOptionName() {
        return optionName;
    }
}
