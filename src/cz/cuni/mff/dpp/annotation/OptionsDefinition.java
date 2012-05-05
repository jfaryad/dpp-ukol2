package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the name and the description for the command line program whose options are described by the annotated bean.
 * This description will be used for help screen generation.
 * 
 * @author jakub
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface OptionsDefinition {

    /**
     * The name of the program.
     * 
     */
    String name() default "";

    /**
     * The description of the program.
     * 
     */
    String description() default "";

    /**
     * The "usage" line in the help screen
     */
    String usage() default "";

}
