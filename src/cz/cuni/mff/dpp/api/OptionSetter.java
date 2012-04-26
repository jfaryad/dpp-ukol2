package cz.cuni.mff.dpp.api;

public interface OptionSetter<T> {

    /**
     * Nastaví argument opsny
     * 
     * @param target
     * @param argument
     */
    void setOption(Object context, Object argument);

}
