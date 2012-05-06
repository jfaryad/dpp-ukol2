package cz.cuni.mff.dpp.api.parser.exception;

/**
 * Indicates that no option parameter has been specified for an option with obligatory option parameter
 * 
 * @author Tom
 * 
 */
public class RequiredOptionParameterException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "There is no option parameter on command line for option with name: '%s'";

    private final String optionName;

    public RequiredOptionParameterException(final String optionName) {
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
