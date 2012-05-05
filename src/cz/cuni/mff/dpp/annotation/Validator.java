package cz.cuni.mff.dpp.annotation;

public @interface Validator {
    
    
    Class<? extends cz.cuni.mff.dpp.api.Validator<?>> validatorClass();
    
    String[] constructorParams() default {};

}
