package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.convertor.DummyArgumentConverter;

/**
 * Metodě nesoucí toto označení budou předány obyčejné parametry
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonArgument {

    /**
     * Určí zda se daná metoda může volat v případě výskytu více argumentů vícekrát (sémantika add)
     * 
     * @return
     */
    boolean multipleInvocation() default true;

    String description() default "";

    //todo raplace DummyArgumentConverter by ArgumentConverter
    Class<? extends ArgumentConverter<?>> argumentConverter() default DummyArgumentConverter.class;

}
