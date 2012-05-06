package cz.cuni.mff.dpp.api.parser.exception;

/**
 * Base exception to extended by command line exceptions.
 * 
 * @author Tom
 * 
 */
public abstract class CommandLineParserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommandLineParserException() {
        super();
    }

    public CommandLineParserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CommandLineParserException(final String message) {
        super(message);
    }

    public CommandLineParserException(final Throwable cause) {
        super(cause);
    }

}
