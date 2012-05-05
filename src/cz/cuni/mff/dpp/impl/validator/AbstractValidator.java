package cz.cuni.mff.dpp.impl.validator;

import cz.cuni.mff.dpp.api.ArgumentValidator;

/**
 * Default implementation of a parent class for validators. Subclasses have to implement the
 * {@link ArgumentValidator#validate(Object)} method.
 * 
 * @author jakub
 * 
 * @param <T>
 *            the type of the argument to validate
 */
public abstract class AbstractValidator<T> implements ArgumentValidator<T> {

    private final Class<T> targetClass;

    /**
     * Creates an instance.
     * 
     * @param targetClass
     *            the class of the argument to validate
     */
    
    public AbstractValidator(final Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public Class<T> getTargetClass() {
        return targetClass;
    }

    protected static boolean bothNullOrEqual(final Object a, final Object b) {
        return (a == null && b == null) || a.equals(b);
    }

    protected static void checkNullConstraint(final Object constraint) {
        if (constraint == null) {
            throw new IllegalArgumentException("A validator doesn't accept a null constraint.");
        }
    }

    protected static void checkEmptyArray(final Object[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("A validator doesn't accept a null or empty array of allowedValues.");
        }
    }

    protected static void checkArraySize(final Object[] array, final int size) {
        if (array == null || array.length != size) {
            throw new IllegalArgumentException(
                    "A validator doesn't accept a null array in it's constructor and the expected number of items in the constructor parameter array is "
                            + size);
        }
    }

    protected static void checkNullValue(final Object value) {
        if (value == null) {
            throw new IllegalArgumentException("A validator doesn't accept a null value in the method 'validate'.");
        }
    }

    protected static void checkSameClass(final Object argument, final Object constraint) {
        if (argument.getClass() != constraint.getClass()) {
            throw new IllegalStateException("The validated argument is not of same class as a constraint");
        }
    }

}
