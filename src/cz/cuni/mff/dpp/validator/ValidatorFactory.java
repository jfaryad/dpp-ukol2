package cz.cuni.mff.dpp.validator;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.ArgumentValidator;
import cz.cuni.mff.dpp.impl.converter.ArgumentConverterFactory;

/**
 * Factory used to create {@link ArgumentValidator} instances.
 * 
 * @author jakub
 * 
 */
public class ValidatorFactory {

    /**
     * Creates a {@link ArgumentValidator} specified by the given validatorClass.
     * 
     * @param validatorClass
     *            the class of the validator to instantiate
     * @param argumentConverter
     *            converter used to convert the validator constructor parameters, which come as strings, into the
     *            required type
     * @param constructorParams
     *            parameters passed to the validator constructor (first they will be converter using the
     *            argumentConverter)
     * @return a new {@link ArgumentValidator} instance
     */
    @SuppressWarnings("rawtypes")
    public static <T> ArgumentValidator<T> createValidator(final Class<? extends ArgumentValidator> validatorClass,
            final ArgumentConverter<T> argumentConverter, final String... constructorParams) {

        final T[] convertedConstructorParams = convertConstructorParams(argumentConverter, constructorParams);
        return createValidatorInstance(validatorClass, argumentConverter.getTargetClass(), convertedConstructorParams);
    }

    @SuppressWarnings({"rawtypes" })
    private static <T> ArgumentValidator<T> createValidatorInstance(
            final Class<? extends ArgumentValidator> validatorClass,
            final Class<T> targetClass, final T... constructorParams) {

        final Constructor<? extends ArgumentValidator> constructor = getValidatorConstructor(validatorClass);
        return getValidatorInstanceFromConstructor(constructor, targetClass, constructorParams);

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Constructor<? extends ArgumentValidator> getValidatorConstructor(
            final Class<? extends ArgumentValidator> validatorClass) {
        try {
            return (Constructor<? extends ArgumentValidator>) validatorClass.getConstructors()[0];
        } catch (final Exception e) {
            throw new IllegalStateException("Unable to find a suiteable constructor for " + validatorClass);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <T> ArgumentValidator<T> getValidatorInstanceFromConstructor(
            final Constructor<? extends ArgumentValidator> constructor,
            final Class<T> targetClass, final T[] constructorParams) {
        try {
            return constructor.newInstance(targetClass, constructorParams);
        } catch (final Exception e) {
            throw new IllegalStateException("Unable to instanciate " + constructor.getDeclaringClass(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] convertConstructorParams(final ArgumentConverter<T> argumentConverter,
            final String... constructorParams) {
        final T[] convertedParams = (T[]) Array.newInstance(argumentConverter.getTargetClass(),
                constructorParams.length);
        for (int i = 0; i < constructorParams.length; i++) {
            convertedParams[i] = argumentConverter.parse(constructorParams[i]);
        }
        return convertedParams;
    }

    // TODO jenom pro testovani, presunout do testovaci tridy
    public static void main(final String[] args) {

        System.out.println("BetweenValidator test:");
        final ArgumentValidator<Integer> validator = createValidator(BetweenValidator.class,
                ArgumentConverterFactory.getDefaultConverter(Integer.class), "0", "5");
        System.out.println("Validating 3, should be ok");
        boolean isValid = validator.isValid(3);
        if (isValid) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }
        System.out.println("Validating 5, should be wrong");
        isValid = validator.isValid(3);
        if (!isValid) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }

        System.out.println("BetweenInclusiveValidator test");
        final ArgumentValidator<Integer> validatorInclusive = createValidator(BetweenInclusiveValidator.class,
                ArgumentConverterFactory.getDefaultConverter(Integer.class), new String[] { "0", "120" });
        System.out.println("Validating 120, should be ok");
        isValid = validatorInclusive.isValid(120);
        if (isValid) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }
        System.out.println("Validating 121, should be wrong");
        isValid = validatorInclusive.isValid(121);
        if (!isValid) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }
    }
}
