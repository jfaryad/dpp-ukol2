package cz.cuni.mff.dpp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Takto tagovaná metoda se vyvolá pro neočekávané volby. Jako argumnt dostavaji
 * jmeno volby
 * 
 * @author Tom
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface UnexceptedOption {

}
