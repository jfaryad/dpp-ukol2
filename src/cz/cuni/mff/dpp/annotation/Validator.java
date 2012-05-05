package cz.cuni.mff.dpp.annotation;

import cz.cuni.mff.dpp.api.ArgumentValidator;

public @interface Validator {

    @SuppressWarnings("rawtypes")
    Class<? extends ArgumentValidator> validatorClass();

    String[] constructorParams() default {};

}
