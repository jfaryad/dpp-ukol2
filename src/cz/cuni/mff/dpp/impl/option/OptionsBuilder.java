package cz.cuni.mff.dpp.impl.option;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.OptionSetter;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.RequiredCountInterval;
import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Basic implementation of {@link Options}
 * 
 * @author jfaryad
 * 
 */
public class OptionsBuilder implements Options {

    private static final RequiredCountInterval DEFAULT_REQUIRED_COUNT_INTERVAL = new RequiredCountInterval(0, 0);

    private final Map<String, SingleOption> options = new HashMap<String, SingleOption>();
    private Class<?> targetBeanClass;

    private ArgumentConverter<?> commonArgumentConverter;
    private OptionSetter commonArgumentSetter;
    private RequiredCountInterval commonArgumentRequiredCountInterval = DEFAULT_REQUIRED_COUNT_INTERVAL;

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
    public Class<?> getTargetBeanClass() {
        return targetBeanClass;
    }

    public void setTargetBeanClass(Class<?> targetBeanClass) {
        this.targetBeanClass = targetBeanClass;
    }

    @Override
    public ArgumentConverter<?> getCommonArgumentConverter() {
        return commonArgumentConverter;
    }

    public void setCommonArgumentConverter(ArgumentConverter<?> commonArgumentConverter) {
        this.commonArgumentConverter = commonArgumentConverter;
    }

    @Override
    public OptionSetter getCommonArgumentSetter() {
        return commonArgumentSetter;
    }

    public void setCommonArgumentSetter(OptionSetter commonArgumentSetter) {
        this.commonArgumentSetter = commonArgumentSetter;
    }

    @Override
    public String toString() {
        return "OptionsBuilder [options=" + options + ", targetBeanClass=" + targetBeanClass
                + ", commonArgumentConverter=" + commonArgumentConverter + ", commonArgumentSetter="
                + commonArgumentSetter + ", commonArgumentRequiredCountInterval=" + commonArgumentRequiredCountInterval
                + "]";
    }

    @Override
    public RequiredCountInterval getCommonArgumentRequiredCountInterval() {
        return commonArgumentRequiredCountInterval;
    }

    public void setCommonArgumentRequiredCountInterval(RequiredCountInterval commonArgumentRequiredCountInterval) {
        this.commonArgumentRequiredCountInterval = commonArgumentRequiredCountInterval;
    }

    @Override
    public Collection<SingleOption> getDependentSingleOptionList(String optionName) {
        return getSingleOptionsByOptionNames(getOption(optionName).getDependentList());
    }

    @Override
    public Collection<SingleOption> getIncompatibleSingleOptionList(String optionName) {
        return getSingleOptionsByOptionNames(getOption(optionName).getIncompatibleList());
    }

    private final Collection<SingleOption> getSingleOptionsByOptionNames(Collection<String> optionNames) {
        Collection<SingleOption> result = new HashSet<SingleOption>();
        for (String dependentOptionName : optionNames) {
            result.add(options.get(dependentOptionName));
        }
        return result;
    }
}
