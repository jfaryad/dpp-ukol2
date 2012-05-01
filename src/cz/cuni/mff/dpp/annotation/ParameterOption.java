package cz.cuni.mff.dpp.annotation;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.convertor.DummyArgumentConverter;

@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterOption {

    Class<DummyArgumentConverter> DEFAULT_ARGUMENT_CONVERTER_CLASS = DummyArgumentConverter.class;

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

    //todo raplace DummyArgumentConverter by ArgumentConverter
    Class<? extends ArgumentConverter<?>> argumentConverter() default DummyArgumentConverter.class;
    
    Class<? extends Serializable> test() default Serializable.class;

    String[] defaultParameter() default {};

    boolean multipleInvocation() default true;

}
