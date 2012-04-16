package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Option {

    String id() default "";

    String[] names();

    String description() default "";

    boolean required() default false;

    String[] dependentIds() default {};

    String[] incompatibleIds() default {};

    /**
     * To be displayed in generated help screen.
     * 
     * @return
     */
    String argumentName() default "";

}
