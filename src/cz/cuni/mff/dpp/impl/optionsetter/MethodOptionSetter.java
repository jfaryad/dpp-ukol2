package cz.cuni.mff.dpp.impl.optionsetter;

import java.lang.reflect.Method;

import cz.cuni.mff.dpp.api.OptionSetter;


/**
 * Setter used for method invoking
 * 
 * @author Tom
 *
 */
public class MethodOptionSetter implements OptionSetter {

    private final Method method;

    public MethodOptionSetter(Method method) {
        super();
        this.method = method;
    }

    @Override
    public void setOption(Object context, Object argument) {

        try {
            boolean accessible = method.isAccessible();
            method.setAccessible(true);
            method.invoke(context, argument);
            method.setAccessible(accessible);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
