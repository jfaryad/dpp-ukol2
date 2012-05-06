package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.converter.DummyArgumentConverter;

/**
 * This annotation is used for tagging fields or methods, that are designed for common arguments of the program. This
 * annotation can be used at most one time in the annotated bean.
 * 
 * Field contract is: non-static, non-final field. Method contract is: non-static, one-parameter method, that returns
 * void.
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
     * @return
     */
    String description() default "";

    /**
     * {@link Class} literal of the class implementing {@link ArgumentConverter} interface. This implementation must
     * have parameterless public constructor, that is used for instantiation. Returns type of the method
     * {@link ArgumentConverter#convert(String)} have to be assignable to the field or method tagged with this annotation.
     * This parameter can be omitted if the target class is primitive type, wrapper type, {@link String} type or enum
     * type.
     * 
     * @return
     */
    Class<? extends ArgumentConverter<?>> argumentConverter() default DummyArgumentConverter.class;

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

}
