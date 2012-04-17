package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cz.cuni.mff.dpp.OptionArgumentObligation;

@Retention(RetentionPolicy.RUNTIME)
public @interface Option {

    String id() default "";

    String[] names();
    
    String[] longNames() default {};
    
    String[] shortNames() default {};

    String description() default "";

    boolean required() default false;

    String[] dependentIds() default {};

    String[] incompatibleIds() default {};
    
    OptionArgumentObligation argumentObligation() default OptionArgumentObligation.OPTIONAL;
    
    Class<?> parser() default void.class;

    /**
     * To be displayed in generated help screen.
     * 
     * @return
     */
    String argumentName() default "";

}
