package cz.cuni.mff.dpp.api.parser.exception;

public class RequiredOptionParameterException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "There is no option parameter on command line for option with name: '%s'";

    private final String optionName;

    public RequiredOptionParameterException(String optionName) {
        super(String.format(MESSAGE, optionName));
        this.optionName = optionName;
    }

    public String getOptionName() {
        return optionName;
    }
}
