package cz.cuni.mff.dpp;

public final class DummyArgumentConverter implements ArgumentConverter<Object> {

	private DummyArgumentConverter() {

	}

	@Override
	public Object parse(String argument) {
		throw new IllegalStateException("This method shouldn't be called.");
	}

}
