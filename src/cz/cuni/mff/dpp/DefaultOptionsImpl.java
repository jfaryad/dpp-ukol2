package cz.cuni.mff.dpp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic implementation of {@link Options}
 * 
 * @author jfaryad
 * 
 */
public class DefaultOptionsImpl implements Options {

    private final Map<String, SingleOption> options = new HashMap<String, SingleOption>();
    private boolean nonOptionArgumentsAllowed = false;

    @Override
    public void addOption(SingleOption option) {
        for (String optionName : option.getNames()) {
            options.put(optionName, option);
        }
    }

    @Override
    public Collection<SingleOption> getOptions() {
        return options.values();
    }

    @Override
    public SingleOption getOption(String optionName) {
        return options.get(optionName);
    }

    @Override
    public boolean nonOptionArgumentsAllowed() {
        return nonOptionArgumentsAllowed;
    }

    public void setNonOptionArgumentsAllowed(boolean nonOptionArgumentsAllowed) {
        this.nonOptionArgumentsAllowed = nonOptionArgumentsAllowed;
    }

}
