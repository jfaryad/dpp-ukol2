package cz.cuni.mff.dpp.impl.convertor;

import cz.cuni.mff.dpp.api.ArgumentConverter;

public class EnumArgumentConverter<T extends Enum<T>> implements ArgumentConverter<T> {

    private final Class<T> targetClass;

    public EnumArgumentConverter(Class<T> targetClass) {
        super();
        this.targetClass = targetClass;
    }

    @Override
    public T parse(String argument) {
        return Enum.valueOf(targetClass, argument);
    }

    @Override
    public Class<T> getTargetClass() {
        return targetClass;
    }

}
