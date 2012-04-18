package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Metod� nesouc� toto ozna�en� budou p�ed�ny oby�ejn� parametry 
 * 
 * @author Tom
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {
	
	
	/**
	 * Ur�� zda se dan� metoda m��e volat v p��pad� v�skytu v�ce argument� v�cekr�t
	 * (s�mantika add)
	 * @return
	 */
	boolean multipleInvocation() default true;
	
	
	String description() default "";
	
	
	Class<?> argumentConverter() default void.class;

}
