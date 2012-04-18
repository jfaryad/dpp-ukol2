package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Metodì nesoucí toto oznaèení budou pøedány obyèejné parametry 
 * 
 * @author Tom
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {
	
	
	/**
	 * Urèí zda se daná metoda mùže volat v pøípadì výskytu více argumentù vícekrát
	 * (sémantika add)
	 * @return
	 */
	boolean multipleInvocation() default true;
	
	
	String description() default "";
	
	
	Class<?> argumentConverter() default void.class;

}
