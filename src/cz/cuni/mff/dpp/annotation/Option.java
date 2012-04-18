package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cz.cuni.mff.dpp.OptionArgumentObligation;

/**
 * Anotace pro oznaèkování metody, která se zavolá pokud bude pøítomná na CLI.
 * Bude jednat o metody s jedním parametrem boolean
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Option {

	String id() default "";

	/**
	 * Jména volby (krátká i dlouhá)
	 * 
	 * @return
	 */
	String[] names();

	/**
	 * Dlouhá jména volby
	 * 
	 * @return
	 */
	String[] longNames() default {};

	/**
	 * Krátká jména volby
	 * 
	 * @return
	 */
	String[] shortNames() default {};

	String description() default "";

	boolean required() default false;

	String[] dependentIds() default {};

	String[] incompatibleIds() default {};
	

	/**
	 * Urèí zda má volba argument
	 * 
	 * @return
	 */
	OptionArgumentObligation argumentObligation() default OptionArgumentObligation.OPTIONAL;

	/**
	 * To be displayed in generated help screen.
	 * 
	 * @return
	 */
	String argumentName() default "";

}
