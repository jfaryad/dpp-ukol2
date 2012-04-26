package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Metodě nesoucí toto označení budou předány obyčejné parametry
 * 
 * @author Tom
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {

    /**
     * Určí zda se daná metoda může volat v případě výskytu více argumentů vícekrát (sémantika add)
     * 
     * @return
     */
    boolean multipleInvocation() default true;

    String description() default "";

    Class<?> argumentConverter() default void.class;

}
