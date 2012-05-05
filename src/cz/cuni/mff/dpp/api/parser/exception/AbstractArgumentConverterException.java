package cz.cuni.mff.dpp.api.parser.exception;

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

    public String getValue() {
        return value;
    }

    public Class<?> getArgumentConverterClass() {
        return argumentConverterClass;
    }

}
