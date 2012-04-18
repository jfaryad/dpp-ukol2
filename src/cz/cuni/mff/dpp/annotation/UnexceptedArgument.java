package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Takto tagovan� metoda se vyvol� pro neo�ek�van� argumenty (pokud argumenty
 * nejsou povoleny)
 * 
 * @author Tom
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface UnexceptedArgument {

	Class<?> argumentConverter() default void.class;

}
