package cz.cuni.mff.dpp.api.parser.exception;

public class UnexceptedOptionException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Unexcepted option with name: '%s' on command line.";

    private final String optionName;

    private final boolean isShortOption;

    public UnexceptedOptionException(String optionName, boolean isShortOption) {
        super(String.format(MESSAGE, optionName));
        this.optionName = optionName;
        this.isShortOption = isShortOption;
    }

    public String getOptionName() {
        return optionName;
    }

    public boolean isShortOption() {
        return isShortOption;
    }

}
