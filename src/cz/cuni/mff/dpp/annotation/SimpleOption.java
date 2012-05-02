package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface SimpleOption {

    String[] names();

    String description() default "";

    String[] dependentOn() default {};

    String[] incompatibleWith() default {};

}
