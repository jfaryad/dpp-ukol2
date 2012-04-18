package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Takto tagovaná metoda se vyvolá pro neoèekávané argumenty voleb.
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface UnexceptedOptionArgument {

	Class<?> argumentConverter() default void.class;

}
