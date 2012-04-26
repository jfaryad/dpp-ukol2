package cz.cuni.mff.dpp.impl.convertor;

import cz.cuni.mff.dpp.api.ArgumentConverter;

public final class DummyArgumentConverter implements ArgumentConverter<Object> {

    private DummyArgumentConverter() {

    }

    @Override
    public Object parse(String argument) {
        throw new IllegalStateException("This method shouldn't be called.");
    }

}
