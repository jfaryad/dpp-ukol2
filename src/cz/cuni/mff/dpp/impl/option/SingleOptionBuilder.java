package cz.cuni.mff.dpp.impl.option;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.OptionSetter;
import cz.cuni.mff.dpp.api.SingleOption;
import cz.cuni.mff.dpp.api.ArgumentValidator;

/**
 * Basic implementation of {@link SingleOption}
 * 
 * @author jfaryad
 * 
 */
public class SingleOptionBuilder implements SingleOption {

    private final Set<String> names = new TreeSet<String>();
    private boolean required = false;
    private final Set<String> dependentOn = new TreeSet<String>();
    private final Set<String> incompatibleWith = new TreeSet<String>();
    private OptionArgumentObligation argumentObligation = OptionArgumentObligation.FORBIDDEN;
    private Class<?> argumentClass;
    private String argumentName;
    private String description = "";
    private ArgumentConverter<?> argumentConverter;
    private Object defaultValue;
    private OptionSetter optionSetter;
    private Set<ArgumentValidator<?>> validators = new HashSet<ArgumentValidator<?>>();

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
    public OptionSetter getOptionSetter() {
        return optionSetter;
    }

    @Override
    public Collection<ArgumentValidator<?>> getValidators() {
        return validators;
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

    public SingleOptionBuilder dependentOn(String... optionName) {
        dependentOn.addAll(Arrays.asList(optionName));
        return this;
    }

    public SingleOptionBuilder incompatibleWith(String... optionName) {
        incompatibleWith.addAll(Arrays.asList(optionName));
        return this;
    }

    public SingleOptionBuilder setDescription(String descpription) {
        this.description = descpription;
        return this;
    }

    public SingleOptionBuilder setArgumentConverter(ArgumentConverter<?> argumentConverter) {
        this.argumentConverter = argumentConverter;
        return this;
    }

    public SingleOptionBuilder setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public SingleOptionBuilder setOptionSetter(OptionSetter optionSetter) {
        this.optionSetter = optionSetter;
        return this;
    }

    public SingleOptionBuilder addValidator(ArgumentValidator<?> validator) {
        this.validators.add(validator);
        return this;
    }

    public SingleOptionBuilder setValidators(ArgumentValidator<?>... validators) {
        for (ArgumentValidator<?> validator : validators) {
            addValidator(validator);
        }
        return this;
    }

    @Override
    public String toString() {
        return "SingleOptionBuilder [names=" + names + ", required=" + required + ", dependentOn=" + dependentOn
                + ", incompatibleWith=" + incompatibleWith + ", argumentObligation=" + argumentObligation
                + ", argumentClass=" + argumentClass + ", argumentName=" + argumentName + ", description="
                + description + "]";
    }

    private static void checkArgumentNotEmpty(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Cannot accept empty value as parameter");
        }
    }
}
