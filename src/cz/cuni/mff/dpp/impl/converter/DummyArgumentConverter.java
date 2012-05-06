package cz.cuni.mff.dpp.impl.converter;

import cz.cuni.mff.dpp.api.ArgumentConverter;


/**
 * Dummy converter for interval use
 * @author Tom
 *
 */
public final class DummyArgumentConverter implements ArgumentConverter<Object> {

    private DummyArgumentConverter() {
    }

    @Override
    public Object convert(String argument) {
        throw new IllegalStateException("This method shouldn't be called.");
    }

    @Override
    public Class<Object> getTargetClass() {
        throw new IllegalStateException("This method shouldn't be called.");
    }

}
