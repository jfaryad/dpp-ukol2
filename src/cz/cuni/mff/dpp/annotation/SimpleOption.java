package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SimpleOption is used for parameterless options.
 * This annotation can be used for fields or methods.
 * If this option appears on the command line, {@code true} value is setted into field or tagged method is invoked with parameter {@code true}.
 * 
 * Field have to fulfill this contract: non-static, non-final with type {@code boolean} or {@link Boolean}
 * Method have to fulfill this contract: non-static, with one {@code boolean} or {@link Boolean} parameter, returning void.
 * 
 * 
 * @author Tom
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface SimpleOption {

    /**
     * All names (long or short) that represent this option. Short names have one character length. 
     * Option name cannot start with character '-' and cannot contain character '='
     * @return
     */
    String[] names();

    /**
     * Description of this option. It will be used for help-page generation.
     * @return
     */
    String description() default "";

    /**
     * Names of options that must be specified together with this option.
     * @return
     */
    String[] dependentOn() default {};

    /**
     * Names of options that must <b>not<b> be specified when this option is specified.
     * @return
     */
    String[] incompatibleWith() default {};

}
