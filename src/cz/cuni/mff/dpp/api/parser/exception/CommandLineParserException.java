package cz.cuni.mff.dpp.api.parser.exception;

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
