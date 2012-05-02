package cz.cuni.mff.dpp.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.convertor.DummyArgumentConverter;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface ParameterOption {

    public static final Class<DummyArgumentConverter> DEFAULT_ARGUMENT_CONVERTER_CLASS = DummyArgumentConverter.class;

    /**
     * Jména volby (krátká i dlouhá)
     * 
     * @return
     */
    String[] names();

    String description() default "";

    /**
     * To be displayed in generated help screen.
     * 
     * @return
     */
    String argumentName() default "";

    boolean required() default false;

    String[] dependentOn() default {};

    String[] incompatibleWith() default {};

    boolean optionRequired() default false;

    boolean parameterRequired() default true;

    Class<? extends ArgumentConverter<?>> argumentConverter() default DummyArgumentConverter.class;

    Class<? extends Serializable> test() default Serializable.class;

    String[] defaultParameter() default {};

    boolean multipleInvocation() default true;

}
