package cz.cuni.mff.dpp.api.parser.exception;

/**
 * Indicates unconfigured option occurrence on the command line.
 * @author Tom
 *
 */
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

    /**
     * Unconfigured option name
     * @return
     */
    public String getOptionName() {
        return optionName;
    }

    /**
     * Return true if option is short
     * @return
     */
    public boolean isShortOption() {
        return isShortOption;
    }

}
