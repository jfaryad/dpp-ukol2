package cz.cuni.mff.dpp.api.parser.exception;

/**
 * Indicates unexcpectedd exception during parsing (e.g. during conversion or argument setting)
 * 
 * @author Tom
 * 
 */
public class UnexceptedException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    public UnexceptedException(final Throwable cause) {
        super(cause);
    }
}
