package cz.cuni.mff.dpp.annotation;

import cz.cuni.mff.dpp.api.ArgumentValidator;

/**
 * Determines a validator of the parameter of a @ParameterOption. The validation will be executed when parsing the
 * command line options.
 * 
 * @author jakub
 * 
 */
public @interface Validator {

    /**
     * Returns the class of the validator.<br>
     * When using the default OptionsFactory, the validator class must have a constructor that takes the class of the
     * argument and a string array as parameters.
     */
    @SuppressWarnings("rawtypes")
    Class<? extends ArgumentValidator> validatorClass();

    /**
     * The parameters passed to the constructor of the validator. They represent the constraints and will be converted
     * to the type of the argument.
     */
    String[] constructorParams() default {};

}
