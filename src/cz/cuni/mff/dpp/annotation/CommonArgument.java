package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.converter.DummyArgumentConverter;

/**
 * This annotation is used for tagging fields or methods, that are designed for common arguments of the program. This
 * annotation can be used at most once in the annotated bean.<br>
 * 
 * Field contract is: non-static, non-final field.<br>
 * Method contract is: non-static, one-parameter method, that returns void.
 * 
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface CommonArgument {

    /**
     * Description of the program's common argument used in help screen
     * 
     */
    String description() default "";

    /**
     * {@link Class} literal of the class implementing {@link ArgumentConverter} interface. This implementation must
     * have a parameterless public constructor, that is used for instantiation. The return type of the method
     * {@link ArgumentConverter#convert(String)} has to be assignable to the field or method tagged with this
     * annotation. This parameter can be omitted if the target class is a primitive type, wrapper type, {@link String}
     * type or enum type.
     * 
     */
    Class<? extends ArgumentConverter<?>> argumentConverter() default DummyArgumentConverter.class;

    /**
     * Lower bound of the common argument occurrence count on the command line
     * 
     */
    int minRequiredCount() default 0;

    /**
     * Upper bound of the common argument occurrence count on the command line
     * 
     */
    int maxRequiredCount() default Integer.MAX_VALUE;

}
