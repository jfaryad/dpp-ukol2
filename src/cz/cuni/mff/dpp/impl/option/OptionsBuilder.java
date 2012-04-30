package cz.cuni.mff.dpp.impl.option;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Basic implementation of {@link Options}
 * 
 * @author jfaryad
 * 
 */
public class OptionsBuilder implements Options {

    private final Map<String, SingleOption> options = new HashMap<String, SingleOption>();
    private boolean nonOptionArgumentsAllowed = false;
    private Class<?> targetBeanClass;

    public SingleOptionBuilder addOption(String... optionNames) {
        SingleOptionBuilder builder = new SingleOptionBuilder(optionNames);
        for (String optionName : optionNames) {
            options.put(optionName, builder);
        }
        return builder;
    }

    @Override
    public Collection<SingleOption> getOptions() {
        return options.values();
    }

    @Override
    public SingleOption getOption(String optionName) {
        return options.get(optionName);
    }

    /**
     * Returns {@code true} if exists some configuration for optionName, otherwise {@code false}
     * 
     * @param optionName
     * @return
     */
    public boolean isExistsOption(String optionName) {
        return options.containsKey(optionName);
    }

    @Override
    public boolean nonOptionArgumentsAllowed() {
        return nonOptionArgumentsAllowed;
    }

    public void setNonOptionArgumentsAllowed(boolean nonOptionArgumentsAllowed) {
        this.nonOptionArgumentsAllowed = nonOptionArgumentsAllowed;
    }

    @Override
    public Class<?> getTargetBeanClass() {
        return targetBeanClass;
    }

    public void setTargetBeanClass(Class<?> targetBeanClass) {
        this.targetBeanClass = targetBeanClass;
    }

    @Override
    public String toString() {
        return "OptionsBuilder [options=" + options + ", nonOptionArgumentsAllowed=" + nonOptionArgumentsAllowed
                + ", targetBeanClass=" + targetBeanClass + "]";
    }
}
