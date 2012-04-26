package cz.cuni.mff.dpp.impl.option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.OptionSetter;
import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Basic implementation of {@link SingleOption}
 * 
 * @author jfaryad
 * 
 */
public class SingleOptionBuilder implements SingleOption {

    private final Set<String> names = new TreeSet<String>();
    private boolean required = false;
    private final List<String> dependentOn = new ArrayList<String>();
    private final List<String> incompatibleWith = new ArrayList<String>();
    private OptionArgumentObligation argumentObligation = OptionArgumentObligation.FORBIDDEN;
    private Class<?> argumentClass;
    private String argumentName;
    private String description = "";
    private ArgumentConverter<?> argumentConverter;
    private Object defaultValue;
    private OptionSetter<?> optionSetter;

    SingleOptionBuilder(String... names) {
        for (String name : names) {
            addName(name);
        }
    }

    @Override
    public Collection<String> getNames() {
        return names;
    }

    @Override
    public Collection<String> getShortNames() {
        Set<String> shortNames = new TreeSet<String>();
        for (String name : names) {
            if (name.length() < 2) {
                shortNames.add(name);
            }
        }
        return shortNames;
    }

    @Override
    public Collection<String> getLongNames() {
        Set<String> longNames = new TreeSet<String>();
        for (String name : names) {
            if (name.length() > 1) {
                longNames.add(name);
            }
        }
        return longNames;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public Collection<String> getDependendentList() {
        return dependentOn;
    }

    @Override
    public Collection<String> getIncompatibleList() {
        return incompatibleWith;
    }

    @Override
    public boolean hasArgument() {
        return argumentObligation != OptionArgumentObligation.FORBIDDEN;
    }

    @Override
    public Class<?> getArgumentClass() {
        return argumentClass;
    }

    @Override
    public String getArgumentName() {
        return argumentName;
    }

    @Override
    public boolean isArgumentRequired() {
        return argumentObligation == OptionArgumentObligation.REQUIRED;
    }

    @Override
    public OptionArgumentObligation getArgumentObligation() {
        return argumentObligation;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ArgumentConverter<?> getArgumentConverter() {
        return argumentConverter;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public OptionSetter<?> getOptionSetter() {
        return optionSetter;
    }

    public SingleOptionBuilder addName(String name) {
        checkArgumentNotEmpty(name);
        names.add(name);
        return this;
    }

    public SingleOptionBuilder setArgumentObligation(OptionArgumentObligation argumentObligation) {
        this.argumentObligation = argumentObligation;
        return this;
    }

    public SingleOptionBuilder setArgumentClass(Class<?> argumentClass) {
        this.argumentClass = argumentClass;
        return this;
    }

    public SingleOptionBuilder setArgumentName(String argumentName) {
        this.argumentName = argumentName;
        return this;
    }

    public SingleOptionBuilder setRequired(boolean required) {
        this.required = required;
        return this;
    }

    // todo totez jako dole
    public SingleOptionBuilder dependentOn(String... optionName) {
        dependentOn.addAll(Arrays.asList(optionName));
        return this;
    }

    // todo tohle by se mozna melo prohnat pres treeset kvuli duplicitam
    public SingleOptionBuilder incompatibleWith(String... optionName) {
        incompatibleWith.addAll(Arrays.asList(optionName));
        return this;
    }

    public SingleOptionBuilder setDescription(String descpription) {
        this.description = descpription;
        return this;
    }

    private static void checkArgumentNotEmpty(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Cannot accept empty value as parameter");
        }
    }

    public void setArgumentConverter(ArgumentConverter<?> argumentConverter) {
        this.argumentConverter = argumentConverter;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setOptionSetter(OptionSetter<?> optionSetter) {
        this.optionSetter = optionSetter;
    }

    @Override
    public String toString() {
        return "SingleOptionBuilder [names=" + names + ", required=" + required + ", dependentOn=" + dependentOn
                + ", incompatibleWith=" + incompatibleWith + ", argumentObligation=" + argumentObligation
                + ", argumentClass=" + argumentClass + ", argumentName=" + argumentName + ", description="
                + description + "]";
    }
}
