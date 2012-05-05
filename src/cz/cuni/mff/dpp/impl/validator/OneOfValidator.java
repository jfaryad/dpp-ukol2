package cz.cuni.mff.dpp.impl.validator;

/**
 * Validates that the argument is equal to one of the enumerated values. The values can contain {@code null}.
 * 
 * @author jakub
 * 
 * @param <T>
 *            the type of the argument to validate
 */
public class OneOfValidator extends AbstractValidator<Object> {

    private final Object[] allowedValues;

    /**
     * Creates a validator, that validates that the argument is equal to one of the enumerated values. The values can
     * contain {@code null}.
     * 
     * @param targetClass
     *            the class of the argument to validate
     * @param allowedValues
     *            An array of allowed values.
     */
    public OneOfValidator(final Class<Object> targetClass, final Object[] allowedValues) {
        super(targetClass);
        checkEmptyArray(allowedValues);
        this.allowedValues = allowedValues;
    }

    @Override
    public boolean isValid(final Object argument) {
        for (final Object allowedValue : allowedValues) {
            if (bothNullOrEqual(argument, allowedValue)) {
                return true;
            }
        }
        return false;
    }

}
