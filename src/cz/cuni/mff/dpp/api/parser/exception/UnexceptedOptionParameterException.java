package cz.cuni.mff.dpp.api.parser.exception;

/**
 * Indicates that a parameter has bees specified for an option, that does not permit option parameters
 * 
 * @author Tom
 * 
 */
public class UnexceptedOptionParameterException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option parameter with name %s has unexcepted parameter: \"%s\"";

    private final String optionName;
    private final String unexceptedOptionParameter;

    public UnexceptedOptionParameterException(final String optionName, final String unexceptedOptionParameter) {
        super(String.format(MESSAGE, optionName, unexceptedOptionParameter));
        this.optionName = optionName;
        this.unexceptedOptionParameter = unexceptedOptionParameter;
    }

    /**
     * Returns option parameter name
     * 
     * @return
     */
    public String getOptionName() {
        return optionName;
    }

    /**
     * Return not permitted option parameter
     * 
     * @return
     */
    public String getUnexceptedOptionParameter() {
        return unexceptedOptionParameter;
    }

}
