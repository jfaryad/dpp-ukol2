package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.converter.DummyArgumentConverter;

/**
 * ParameterOption is used for options with parameters.
 * 
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface ParameterOption {

    /**
     * All names (long or short) that represent this option. Short names have one character length. Option name cannot
     * start with character '-' and cannot contain character '='
     * 
     * @return
     */
    String[] names();

    /**
     * Description of this option. It will be used for help-page generation.
     * 
     * @return
     */
    String description() default "";

    /**
     * To be displayed in generated help screen.
     * 
     * @return
     */
    String argumentName() default "";

    /**
     * Names of options that must be specified together with this option.
     * 
     * @return
     */
    String[] dependentOn() default {};

    /**
     * Names of options that must <b>not<b> be specified when this option is specified.
     * 
     * @return
     */
    String[] incompatibleWith() default {};

    /**
     * If true must be option parameter always specified, otherwise is option parameter optional
     * 
     * @return
     */
    boolean parameterRequired() default true;

    /**
     * {@link Class} literal of the class implementing {@link ArgumentConverter} interface. This implementation must
     * have parameterless public constructor, that is used for instantiation. Returns type of the method
     * {@link ArgumentConverter#convert(String)} have to be assignable to the field or method tagged with this annotation.
     * This parameter can be omitted if the target class is primitive type, wrapper type, {@link String} or enum type.
     * 
     * @return
     */
    Class<? extends ArgumentConverter<?>> argumentConverter() default DummyArgumentConverter.class;

    /**
     * Default parameter used for options without specified option parameter. If not specified, the default value is
     * automatically determined in similar way like in uninitialized field in Java classes.
     * 
     * @return
     */
    String[] defaultParameter() default {};

    /**
     * Lower bound of the common argument occurrence on the command line
     * 
     * @return
     */
    int minRequiredCount() default 0;

    /**
     * Upper bound of the common argument occurrence on the command line
     * 
     * @return
     */
    int maxRequiredCount() default 1;

    /**
     * Validators used for option parameter validation
     * 
     * @return
     */
    Validator[] validators() default {};

}
