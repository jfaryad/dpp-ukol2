package cz.cuni.mff.dpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Basic implementation of {@link SingleOption}
 * 
 * @author jfaryad
 * 
 */
public class DefaultSingleOptionImpl implements SingleOption {

    private String id = "";
    private final Set<String> names = new TreeSet<String>();
    private boolean required = false;
    private final List<String> dependentIds = new ArrayList<String>();
    private final List<String> incompatibleIds = new ArrayList<String>();
    private OptionArgumentObligation argumentObligation = OptionArgumentObligation.FORBIDDEN;
    private Class<?> argumentClass;
    private String argumentName;
    private String description = "";

    @Override
    public String getId() {
        // TODO check if this is really necessary
        if (!id.isEmpty()) {
            return id;
        }
        // if id is empty, try to return the first long name
        Collection<String> longNames = getLongNames();
        if (!longNames.isEmpty()) {
            return longNames.iterator().next();
        }

        // if there are no long names, take a short one
        return names.iterator().next();
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
    public Collection<String> getDependendentIds() {
        return dependentIds;
    }

    @Override
    public Collection<String> getIncompatibleIds() {
        return incompatibleIds;
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

    public void setId(String id) {
        this.id = id;
    }

    public void addName(String name) {
        checkArgumentNotEmpty(name);
        names.add(name);
    }

    public void setArgumentObligation(OptionArgumentObligation argumentObligation) {
        this.argumentObligation = argumentObligation;
    }

    public void setArgumentClass(Class<?> argumentClass) {
        this.argumentClass = argumentClass;
    }

    public void setArgumentName(String argumentName) {
        this.argumentName = argumentName;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void addDependentOptionId(String id) {
        dependentIds.add(id);
    }

    public void addIncompatibleOptionId(String id) {
        incompatibleIds.add(id);
    }

    public void setDescription(String descpription) {
        this.description = descpription;
    }

    private static void checkArgumentNotEmpty(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Cannot accept empty value as parameter");
        }
    }
}
