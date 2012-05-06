package cz.cuni.mff.dpp.api;

public interface OptionSetter {

    /**
     * Implementation of this interface are used for setting options
     * 
     * @param context - some context (e.g. field or method)
     * @param argument - value to be setted
     */
    void setOption(Object context, Object argument);

}
