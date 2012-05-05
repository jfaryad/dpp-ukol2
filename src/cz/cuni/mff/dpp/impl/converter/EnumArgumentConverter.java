package cz.cuni.mff.dpp.impl.converter;

import java.lang.annotation.AnnotationFormatError;

import cz.cuni.mff.dpp.api.ArgumentConverter;

public class EnumArgumentConverter<T extends Enum<T>> implements ArgumentConverter<T> {

    private final Class<T> targetClass;

    public EnumArgumentConverter(Class<T> targetClass) {
        super();
        this.targetClass = targetClass;
    }

    @Override
    public T parse(String argument) {
        try {
            return Enum.valueOf(targetClass, argument);
        } catch (Exception e) {
            throw new AnnotationFormatError(e);
        }
    }

    @Override
    public Class<T> getTargetClass() {
        return targetClass;
    }
}
