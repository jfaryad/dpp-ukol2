package cz.cuni.mff.dpp.impl.optionsetter;

import java.lang.reflect.Field;

import cz.cuni.mff.dpp.api.OptionSetter;

/**
 * Setter used for fields setting
 * @author Tom
 *
 */
public class FieldOptionSetter implements OptionSetter {

    private final Field field;

    public FieldOptionSetter(final Field field) {
        this.field = field;
    }

    @Override
    public void setOption(Object context, Object argument) {

        try {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(context, argument);
            field.setAccessible(accessible);
        } catch (IllegalAccessException e) {
            // I hate checked exceptions...
            // In this case is really nothing better, than rethrow this stupid exception like unchecked exception
            throw new RuntimeException(e);
        }

    }
}
