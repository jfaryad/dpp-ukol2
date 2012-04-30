package cz.cuni.mff.dpp.api;

public interface ArgumentConverter<T> {

    T parse(String argument);
    
    Class<T> getTargetClass();

}
