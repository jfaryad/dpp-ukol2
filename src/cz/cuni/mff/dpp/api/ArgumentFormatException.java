package cz.cuni.mff.dpp.api;

public class ArgumentFormatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final static String MESSAGE = "Argument: '%s' has bad format for converson to the type: %s.";

    private final Class<?> argumentConverterClass;

    private final String value;

    public ArgumentFormatException(String value, Class<?> argumentConverterClass) {
        super(String.format(MESSAGE, value, argumentConverterClass.getName()));
        this.argumentConverterClass = argumentConverterClass;
        this.value = value;
    }

    public ArgumentFormatException(Throwable cause, String value, Class<?> targetClass) {
        super(String.format(MESSAGE, value, targetClass.getName()), cause);
        this.argumentConverterClass = targetClass;
        this.value = value;
    }

    public Class<?> getArgumentConverterClass() {
        return argumentConverterClass;
    }

    public String getValue() {
        return value;
    }

}
