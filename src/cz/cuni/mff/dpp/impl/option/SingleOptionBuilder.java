package cz.cuni.mff.dpp.impl.option;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.ArgumentValidator;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.OptionSetter;
import cz.cuni.mff.dpp.api.RequiredCountInterval;
import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Basic implementation of {@link SingleOption}
 * 
 * @author jfaryad
 * 
 */
public class SingleOptionBuilder implements SingleOption {

    private final Set<String> names = new TreeSet<String>();
    private final boolean required = false;
    private final Set<String> dependentOn = new TreeSet<String>();
    private final Set<String> incompatibleWith = new TreeSet<String>();
    private OptionArgumentObligation argumentObligation = OptionArgumentObligation.FORBIDDEN;
    private Class<?> argumentClass;
    private String argumentName;
    private String description = "";
    private ArgumentConverter<?> argumentConverter;
    private Object defaultValue;
    private OptionSetter optionSetter;
    private final Set<ArgumentValidator<?>> validators = new HashSet<ArgumentValidator<?>>();
    private RequiredCountInterval requiredCountInterval;

    SingleOptionBuilder(final String... names) {
        for (final String name : names) {
            addName(name);
        }
    }

    @Override
    public Collection<String> getNames() {
        return names;
    }

    @Override
    public Collection<String> getShortNames() {
        final Set<String> shortNames = new TreeSet<String>();
        for (final String name : names) {
            if (name.length() < 2) {
                shortNames.add(name);
            }
        }
        return shortNames;
    }

    @Override
    public Collection<String> getLongNames() {
        final Set<String> longNames = new TreeSet<String>();
        for (final String name : names) {
            if (name.length() > 1) {
                longNames.add(name);
            }
        }
        return longNames;
    }

    @Override
    public Collection<String> getDependentList() {
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

    public SingleOptionBuilder addName(final String name) {
        checkArgumentNotEmpty(name);
        names.add(name);
        return this;
    }

    public SingleOptionBuilder setArgumentObligation(OptionArgumentObligation argumentObligation) {
        this.argumentObligation = argumentObligation;
        return this;
    }

    /**
     * Sets the argument class and if the argument was forbidden until now (it is by default) it will be set to
     * REQUIRED. If it already is REQUIRED or OPTIONAL, the argument obligation will not change.
     * 
     */
    public SingleOptionBuilder setArgumentClass(final Class<?> argumentClass) {
        this.argumentClass = argumentClass;
        if (argumentObligation == OptionArgumentObligation.FORBIDDEN) {
            argumentObligation = OptionArgumentObligation.REQUIRED;
        }
        return this;
    }

    /**
     * Sets the argument name and if the argument was forbidden until now (it is by default) it will be set to REQUIRED.
     * If it already is REQUIRED or OPTIONAL, the argument obligation will not change.
     * 
     */
    public SingleOptionBuilder setArgumentName(final String argumentName) {
        this.argumentName = argumentName;
        if (argumentObligation == OptionArgumentObligation.FORBIDDEN) {
            argumentObligation = OptionArgumentObligation.REQUIRED;
        }
        return this;
    }

    public SingleOptionBuilder dependentOn(final String... optionName) {
        dependentOn.addAll(Arrays.asList(optionName));
        return this;
    }

    public SingleOptionBuilder incompatibleWith(final String... optionName) {
        incompatibleWith.addAll(Arrays.asList(optionName));
        return this;
    }

    public SingleOptionBuilder setDescription(final String descpription) {
        this.description = descpription;
        return this;
    }

    /**
     * Sets the argumentConverter and if the argument was forbidden until now (it is by default) it will be set to
     * REQUIRED. If it already is REQUIRED or OPTIONAL, the argument obligation will not change.
     * 
     */
    public SingleOptionBuilder setArgumentConverter(final ArgumentConverter<?> argumentConverter) {
        this.argumentConverter = argumentConverter;
        if (argumentObligation == OptionArgumentObligation.FORBIDDEN) {
            argumentObligation = OptionArgumentObligation.REQUIRED;
        }
        return this;
    }

    public SingleOptionBuilder setDefaultValue(final Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public SingleOptionBuilder setOptionSetter(final OptionSetter optionSetter) {
        this.optionSetter = optionSetter;
        return this;
    }

    public SingleOptionBuilder addValidator(final ArgumentValidator<?> validator) {
        this.validators.add(validator);
        return this;
    }

    public SingleOptionBuilder setValidators(final ArgumentValidator<?>... validators) {
        for (final ArgumentValidator<?> validator : validators) {
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

    private static void checkArgumentNotEmpty(final String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Cannot accept empty value as parameter");
        }
    }

    @Override
    public RequiredCountInterval getRequiredCountInterval() {
        return requiredCountInterval;
    }

    public SingleOptionBuilder setRequiredCountInterval(final RequiredCountInterval requiredCountInterval) {
        this.requiredCountInterval = requiredCountInterval;
        return this;
    }

    @Override
    public String getFirstOptionName() {
        return names.iterator().next();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((names == null) ? 0 : names.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SingleOptionBuilder other = (SingleOptionBuilder) obj;
        if (names == null) {
            if (other.names != null) {
                return false;
            }
        } else if (!names.equals(other.names)) {
            return false;
        }
        return true;
    }
}
