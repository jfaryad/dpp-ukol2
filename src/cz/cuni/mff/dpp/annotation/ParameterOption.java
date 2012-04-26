package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.convertor.DummyArgumentConverter;

@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterOption {

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

    String[] defaultParameter() default {};

    boolean multipleInvocation() default true;

}
