package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.converter.DummyArgumentConverter;

/**
 * Metodě nesoucí toto označení budou předány obyčejné parametry
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface CommonArgument {

    /**
     * Určí zda se daná metoda může volat v případě výskytu více argumentů vícekrát (sémantika add)
     * 
     * @return
     */
    boolean multipleInvocation() default true;

    String description() default "";

    Class<? extends ArgumentConverter<?>> argumentConverter() default DummyArgumentConverter.class;

}
