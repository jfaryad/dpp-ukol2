package cz.cuni.mff.dpp.api;

/**
 * Class used for converting of the option parameters and common arguments from {@link String} to desired type.
 * 
 * @author Tom
 * 
 * @param <T>
 */
public interface ArgumentConverter<T> {

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
