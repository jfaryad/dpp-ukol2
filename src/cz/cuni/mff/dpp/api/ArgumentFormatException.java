package cz.cuni.mff.dpp.api;

/**
 * This exception is used for indication of an invalid value in {@link ArgumentConverter}
 * 
 * @author Tom
 * 
 */
public class ArgumentFormatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final static String MESSAGE = "Argument: '%s' has bad format for conversion to the type: %s.";

    private final Class<? extends ArgumentConverter<?>> argumentConverterClass;

    private final String argument;

    public ArgumentFormatException(final String argument,
            final Class<? extends ArgumentConverter<?>> argumentConverterClass) {
        super(String.format(MESSAGE, argument, argumentConverterClass.getName()));
        this.argumentConverterClass = argumentConverterClass;
        this.argument = argument;
    }

    public ArgumentFormatException(final Throwable cause, final String argument,
            final Class<? extends ArgumentConverter<?>> argumentConverterClass) {
        super(String.format(MESSAGE, argument, argumentConverterClass.getName()), cause);
        this.argumentConverterClass = argumentConverterClass;
        this.argument = argument;
    }

    /**
     * Class of the argument converter, that throws this exception.
     * 
     * @return class of the argument converter, that throws this exception.
     */
    public Class<? extends ArgumentConverter<?>> getArgumentConverterClass() {
        return argumentConverterClass;
    }

    /**
     * Returns invalid value
     * 
     * @return invalid value
     */
    public String getArgument() {
        return argument;
    }

}
