package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.converter.DummyArgumentConverter;

/**
 * Metodě nesoucí toto označení budou předány obyčejné parametry
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface CommonArgument {
    
    String description() default "";

    Class<? extends ArgumentConverter<?>> argumentConverter() default DummyArgumentConverter.class;

    int minRequiredCount() default 0;

    int maxRequiredCount() default 1;

}
