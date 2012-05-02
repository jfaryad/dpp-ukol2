package cz.cuni.mff.dpp.validator;

import cz.cuni.mff.dpp.api.Validator;

/**
 * Default implementation of a parent class for validators. Subclasses have to implement the
 * {@link Validator#validate(Object)} method.
 * 
 * @author jakub
 * 
 * @param <T>
 *            the type of the argument to validate
 */
public abstract class AbstractValidator<T> implements Validator<T> {

    private Class<?> targetClass;

    /**
     * Creates an instance.
     * 
     * @param targetClass
     *            the class of the argument to validate
     */
    public AbstractValidator(final Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public Class<?> getTargetClass() {
        return targetClass;
    }

}
