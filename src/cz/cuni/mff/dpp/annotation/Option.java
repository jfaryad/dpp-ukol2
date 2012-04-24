package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cz.cuni.mff.dpp.OptionArgumentObligation;

/**
 * Anotace pro ozna�kov�n� metody, kter� se zavol� pokud bude p��tomn� na CLI. Bude jednat o metody s jedn�m parametrem
 * boolean
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Option {

    /**
     * Jm�na volby (kr�tk� i dlouh�)
     * 
     * @return
     */
    String[] names();

    /**
     * Dlouh� jm�na volby
     * 
     * @return
     */
    String[] longNames() default {};

    /**
     * Kr�tk� jm�na volby
     * 
     * @return
     */
    String[] shortNames() default {};

    String description() default "";

    boolean required() default false;

    String[] dependentOn() default {};

    String[] incompatibleWith() default {};

    /**
     * Ur�� zda m� volba argument
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
