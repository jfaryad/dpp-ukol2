package cz.cuni.mff.dpp.validator;

import cz.cuni.mff.dpp.api.Validator;

/**
 * Factory providing implementations of basic validators.
 * 
 * @author jakub
 * 
 */
public class ValidatorFactory {

    /**
     * Validates that the argument is greater than the given constraint.
     * 
     * @param constraint
     *            the argument must be greater than this value, or else the validation fails.
     */
    public static <T extends Comparable<T>> Validator<T> greaterThen(final T constraint) {
        checkNullConstraint(constraint);
        return new AbstractValidator<T>(constraint.getClass()) {

            @Override
            public void validate(final T value) throws IllegalArgumentException {
                checkNullValue(value);
                if (value.compareTo(constraint) <= 0) {
                    throw new IllegalArgumentException("The value '" + value + "' is not greater then '" + constraint
                            + "'");
                }
            }
        };
    }

    /**
     * Validates that the argument is greater or equal than the given constraint.
     * 
     * @param constraint
     *            the argument must be greater or equal than this value, or else the validation fails.
     */
    public static <T extends Comparable<T>> Validator<T> greaterOrEqualThen(final T constraint) {
        checkNullConstraint(constraint);
        return new AbstractValidator<T>(constraint.getClass()) {

            @Override
            public void validate(final T value) throws IllegalArgumentException {
                checkNullValue(value);
                if (value.compareTo(constraint) < 0) {
                    throw new IllegalArgumentException("The value '" + value + "' is not greater or equal then '"
                            + constraint
                            + "'");
                }
            }
        };
    }

    /**
     * Validates that the argument is less than the given constraint.
     * 
     * @param constraint
     *            the argument must be less than this value, or else the validation fails.
     */
    public static <T extends Comparable<T>> Validator<T> lessThen(final T constraint) {
        checkNullConstraint(constraint);
        return new AbstractValidator<T>(constraint.getClass()) {

            @Override
            public void validate(final T value) throws IllegalArgumentException {
                checkNullValue(value);
                if (value.compareTo(constraint) >= 0) {
                    throw new IllegalArgumentException("The value '" + value + "' is not less then '" + constraint
                            + "'");
                }
            }
        };
    }

    /**
     * Validates that the argument is less or equal than the given constraint.
     * 
     * @param constraint
     *            the argument must be less or equal than this value, or else the validation fails.
     */
    public static <T extends Comparable<T>> Validator<T> lessOrEqualThen(final T constraint) {
        checkNullConstraint(constraint);
        return new AbstractValidator<T>(constraint.getClass()) {

            @Override
            public void validate(final T value) throws IllegalArgumentException {
                checkNullValue(value);
                if (value.compareTo(constraint) > 0) {
                    throw new IllegalArgumentException("The value '" + value + "' is not less or equal then '"
                            + constraint
                            + "'");
                }
            }
        };
    }

    /**
     * Validates that the argument is between (exclusively) the given bounds
     * 
     * @param lowerBound
     *            the argument must be greater than this value, or else the validation fails.
     * @param upperBound
     *            the argument must be less than this value, or else the validation fails.
     */
    public static <T extends Comparable<T>> Validator<T> between(final T lowerBound, final T upperBound) {
        checkNullConstraint(lowerBound);
        checkNullConstraint(upperBound);
        return new AbstractValidator<T>(lowerBound.getClass()) {

            @Override
            public void validate(final T value) throws IllegalArgumentException {
                checkNullValue(value);
                if (value.compareTo(lowerBound) <= 0 || value.compareTo(upperBound) >= 0) {
                    throw new IllegalArgumentException("The value '" + value + "' is not between '" + lowerBound
                            + "' and '" + upperBound + "'");
                }
            }
        };
    }

    /**
     * Validates that the argument is between (inclusive) the given bounds
     * 
     * @param lowerBound
     *            the argument must be greater or equal than this value, or else the validation fails.
     * @param upperBound
     *            the argument must be less or equal than this value, or else the validation fails.
     */
    public static <T extends Comparable<T>> Validator<T> betweenInclusive(final T lowerBound, final T upperBound) {
        checkNullConstraint(lowerBound);
        checkNullConstraint(upperBound);
        return new AbstractValidator<T>(lowerBound.getClass()) {

            @Override
            public void validate(final T value) throws IllegalArgumentException {
                checkNullValue(value);
                if (value.compareTo(lowerBound) < 0 || value.compareTo(upperBound) > 0) {
                    throw new IllegalArgumentException("The value '" + value + "' is not between '" + lowerBound
                            + "' and '" + upperBound + "'");
                }
            }
        };
    }

    /**
     * Validates that the argument is equal to one of the enumerated values. The values can contain {@code null}.
     * 
     * @param allowedValues
     *            An array of allowed values.
     */
    public static Validator<Object> oneOf(final Object[] allowedValues) {
        checkEmptyArray(allowedValues);
        return new AbstractValidator<Object>(allowedValues[0].getClass()) {

            @Override
            public void validate(final Object value) throws IllegalArgumentException {
                for (Object allowedValue : allowedValues) {
                    if (bothNullOrEqual(value, allowedValue)) {
                        return;
                    }
                }
                throw new IllegalArgumentException("The value '" + value + "' doesn't match any of the allowed values");
            }
        };
    }

    private static boolean bothNullOrEqual(final Object a, final Object b) {
        return (a == null && b == null) || a.equals(b);
    }

    private static void checkNullConstraint(final Object constraint) {
        if (constraint == null) {
            throw new IllegalArgumentException("A validator doesn't accept a null constraint.");
        }
    }

    private static void checkEmptyArray(final Object[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("A validator doesn't accept a null or empty array of allowedValues.");
        }
    }

    private static void checkNullValue(final Object value) {
        if (value == null) {
            throw new IllegalArgumentException("A validator doesn't accept a null value in the method 'validate'.");
        }
    }

}
