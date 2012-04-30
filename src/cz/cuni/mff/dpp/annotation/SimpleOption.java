package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.sun.xml.internal.ws.model.ParameterImpl;

@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleOption {

    String[] names();
  
    String description() default "";

    String[] dependentOn() default {};

    String[] incompatibleWith() default {};

}
