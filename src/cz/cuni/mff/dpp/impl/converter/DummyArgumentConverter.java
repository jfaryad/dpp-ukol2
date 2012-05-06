package cz.cuni.mff.dpp.impl.converter;

import cz.cuni.mff.dpp.api.ArgumentConverter;

/**
 * Dummy converter for internal use. It indicates that no explicit converter has been specified
 * 
 * @author Tom
 * 
 */
public final class DummyArgumentConverter implements ArgumentConverter<Object> {

    private DummyArgumentConverter() {
    }

    @Override
    public Object convert(final String argument) {
        throw new IllegalStateException("This method shouldn't be called.");
    }

    @Override
    public Class<Object> getTargetClass() {
        throw new IllegalStateException("This method shouldn't be called.");
    }

}
