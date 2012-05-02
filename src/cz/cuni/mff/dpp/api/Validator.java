package cz.cuni.mff.dpp.api;

/**
 * Validator takes the value of an argument and checks if it adheres to the expected constraints.
 * 
 * @author jakub
 * 
 * @param <T>
 *            the type of the argument to validate
 */
public interface Validator<T> {

    /**
     * Invokes the validation. If the value doesn't pass the validation, an {@link IllegalArgumentException} is thrown.
     * 
     * @param value
     * @throws IllegalArgumentException
     *             if the value doesn't respect the constraint represented by this validator.
     */
    public void validate(final T value) throws IllegalArgumentException;

    /**
     * Returns the class of the argument to be validated by this validator.
     * 
     */
    public Class<?> getTargetClass();

}
