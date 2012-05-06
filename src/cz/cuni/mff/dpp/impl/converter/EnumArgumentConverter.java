package cz.cuni.mff.dpp.impl.converter;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.ArgumentFormatException;

/**
 * Enum argument converter
 * 
 * @author Tom
 * 
 * @param <T>
 */
public class EnumArgumentConverter<T extends Enum<T>> implements ArgumentConverter<T> {

    private final Class<T> targetClass;

    public EnumArgumentConverter(Class<T> targetClass) {
        super();
        this.targetClass = targetClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T convert(String argument) {
        try {
            return Enum.valueOf(targetClass, argument);
        } catch (Exception e) {
            throw new ArgumentFormatException(e, argument, (Class<? extends ArgumentConverter<?>>) this.getClass());
        }
    }

    @Override
    public Class<T> getTargetClass() {
        return targetClass;
    }
}
