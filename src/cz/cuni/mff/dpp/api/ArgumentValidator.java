package cz.cuni.mff.dpp.api;

/**
 * ArgumentValidator takes the value of an argument and checks if it adheres to the expected constraints.
 * 
 * @author jakub
 * 
 * @param <T>
 *            the type of the argument to validate
 */
public interface ArgumentValidator<T> {

    /**
     * Checks whether the argument is valid according to this validator.
     * 
     * @param argument
     *            the argument to validate
     * @return true, if the value respects the constraint represented by this validator, else false
     */
    public boolean isValid(final Object argument);

    /**
     * Returns the class of the argument to be validated by this validator.
     * 
     */
    public Class<T> getTargetClass();

}
