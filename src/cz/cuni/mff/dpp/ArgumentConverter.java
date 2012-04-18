package cz.cuni.mff.dpp;

public interface ArgumentConverter<T> {

	T parse(String optionParam);

}
