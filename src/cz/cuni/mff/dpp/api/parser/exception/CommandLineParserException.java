package cz.cuni.mff.dpp.api.parser.exception;

/**
 * Base exception to inherited from it by command line exceptions.
 * @author Tom
 *
 */
public abstract class CommandLineParserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommandLineParserException() {
        super();
    }

    public CommandLineParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandLineParserException(String message) {
        super(message);
    }

    public CommandLineParserException(Throwable cause) {
        super(cause);
    }

}
