package cz.cuni.mff.dpp.api.parser.exception;

/**
 * Indicates, that argument converter cannot convert value because of bad value format
 * @author Tom
 *
 */
public class AbstractArgumentConverterException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private final String value;

    private final Class<?> argumentConverterClass;

    public AbstractArgumentConverterException(String message, Throwable cause, String value,
            Class<?> argumentConverterClass) {
        super(message, cause);
        this.value = value;
        this.argumentConverterClass = argumentConverterClass;
    }

    /**
     * Returns invalid value
     * @return invalid value
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns argument converter class
     * @return argument converter class
     */
    public Class<?> getArgumentConverterClass() {
        return argumentConverterClass;
    }

}
