package cz.cuni.mff.dpp.api;

/**
 * Class used for converting option parameters and common arguments from {@link String} to the desired type.
 * 
 * @author Tom
 * 
 * @param <T>
 *            the type to convert to
 */
public interface ArgumentConverter<T> {

    String CONVERT_METHOD_NAME = "convert";

    /**
     * Converts {@link String} value to desired type.
     * 
     * @param argument
     *            - value to be converted
     * @return converted value
     * @throws ArgumentFormatException
     *             if format is invalid
     */
    T convert(String argument);

    /**
     * returns type of the result
     * 
     * @return type of the result
     */
    Class<T> getTargetClass();
}
