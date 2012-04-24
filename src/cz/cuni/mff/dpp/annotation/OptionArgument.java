package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Takto označená metoda bude příjímat argument volby
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionArgument {

	String optionId();

	String description() default "";

	Class<?> argumentConverter() default void.class;

}
