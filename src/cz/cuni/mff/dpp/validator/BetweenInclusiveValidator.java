package cz.cuni.mff.dpp.validator;

/**
 * Validates that the argument is between (inclusive) the given bounds.
 * 
 * @author jakub
 * 
 * @param <T>
 *            the type of the argument to validate
 */
public class BetweenInclusiveValidator<T extends Comparable<T>> extends AbstractValidator<T> {

    private final T lowerBound;
    private final T upperBound;

    /**
     * Creates a validator, that validates that the argument is between (inclusive) the given bounds.
     * 
     * @param targetClass
     *            the class of the argument to validate
     * @param constraints
     *            an array of exactly two values representing the lower and the upper bound.<br>
     *            lowerBound: the argument must be greater or equal than this value, or else the validation fails.<br>
     *            upperBound: the argument must be less or equal than this value, or else the validation fails.
     */
    public BetweenInclusiveValidator(final Class<T> targetClass, final T[] constraints) {
        super(targetClass);
        checkArraySize(constraints, 2);
        this.lowerBound = constraints[0];
        this.upperBound = constraints[1];
        checkNullConstraint(lowerBound);
        checkNullConstraint(upperBound);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(final Object argument) {
        checkNullValue(argument);
        final T value = (T) argument;
        if (value.compareTo(lowerBound) < 0 || value.compareTo(upperBound) > 0) {
            return false;
        }
        return true;
    }

}
