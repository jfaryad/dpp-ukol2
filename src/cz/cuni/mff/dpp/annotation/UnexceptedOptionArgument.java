package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Takto tagovan� metoda se vyvol� pro neo�ek�van� argumenty voleb.
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface UnexceptedOptionArgument {

	Class<?> argumentConverter() default void.class;

}
