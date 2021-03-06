package cz.cuni.mff.dpp.api.parser.exception;

/**
 * Indicates bad occurrences count of the common argument
 * @author Tom
 *
 */
public class RequiredCommonArgumentCountException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Common arguments appear in bad quantity: %d on the command line.";

    private final int badCount;

    public RequiredCommonArgumentCountException(int badCount) {
        super(String.format(MESSAGE, badCount));
        this.badCount = badCount;
    }

    /**
     * Return count of the common argument occurrence
     * @return
     */
    public int getBadCount() {
        return badCount;
    };

}
