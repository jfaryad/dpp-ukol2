package cz.cuni.mff.dpp.api.parser.exception;

public class RequiredOptionCountException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option: %s appears in bad quantity: %d on the command line.";

    private final String optionName;

    private final int badCount;

    public RequiredOptionCountException(String optionName, int badCount) {
        super(String.format(MESSAGE, optionName, badCount));
        this.optionName = optionName;
        this.badCount = badCount;
    }

    public String getOptionName() {
        return optionName;
    }

    public int getBadCount() {
        return badCount;
    }

}
