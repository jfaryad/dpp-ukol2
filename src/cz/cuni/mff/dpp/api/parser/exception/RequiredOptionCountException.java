package cz.cuni.mff.dpp.api.parser.exception;

import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Indicates bad occurrences count of the option parameter
 * 
 * @author Tom
 * 
 */
public class RequiredOptionCountException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option: %s appears in bad quantity: %d on the command line.";

    private final SingleOption singleOption;

    private final int badCount;

    public RequiredOptionCountException(final SingleOption singleOption, final int badCount) {
        super(String.format(MESSAGE, singleOption.getFirstOptionName(), badCount));
        this.singleOption = singleOption;
        this.badCount = badCount;
    }

    /**
     * Return single option configuration object
     * @return
     */
    public SingleOption getSingleOption() {
        return singleOption;
    }

    /**
     * Return count of the common argument occurrence
     * @return
     */
    public int getBadCount() {
        return badCount;
    }

}
