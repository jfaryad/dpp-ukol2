package cz.cuni.mff.dpp.impl.validator;

/**
 * Validates that the argument is less or equal to the given constraint.
 * 
 * @author jakub
 * 
 * @param <T>
 *            the type of the argument to validate
 */
public class LessOrEqualThenValidator<T extends Comparable<T>> extends AbstractValidator<T> {

    private final T constraint;

    /**
     * Creates a validator, that validates that the argument is less or equal to the given constraint.
     * 
     * @param targetClass
     *            the class of the argument to validate
     * @param constraint
     *            the argument must be less or equal to this value, or else the validation fails.<br>
     *            The array must contain exactly one value
     */
    public LessOrEqualThenValidator(final Class<T> targetClass, final T[] constraint) {
        super(targetClass);
        checkArraySize(constraint, 1);
        checkNullConstraint(constraint[0]);
        this.constraint = constraint[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(final Object argument) {
        checkNullValue(argument);
        final T value = (T) argument;
        if (value.compareTo(constraint) < 0) {
            return false;
        }
        return true;
    }

}
