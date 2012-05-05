package cz.cuni.mff.dpp.api.parser.exception;

public class UnexceptedOptionParameterException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option parameter with name %s has unexcepted parameter: \"%s\"";

    private final String optionName;
    private final String unexceptedOptionParameter;

    public UnexceptedOptionParameterException(String optionName, String unexceptedOptionParameter) {
        super(String.format(MESSAGE, optionName, unexceptedOptionParameter));
        this.optionName = optionName;
        this.unexceptedOptionParameter = unexceptedOptionParameter;
    }

    public String getOptionName() {
        return optionName;
    }

    public String getUnexceptedOptionParameter() {
        return unexceptedOptionParameter;
    }

}
