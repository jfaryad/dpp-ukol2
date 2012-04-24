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
public class OptionsBuilder implements Options {

    private final Map<String, SingleOption> options = new HashMap<String, SingleOption>();
    private boolean nonOptionArgumentsAllowed = false;

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

    @Override
    public boolean nonOptionArgumentsAllowed() {
        return nonOptionArgumentsAllowed;
    }

    public void setNonOptionArgumentsAllowed(boolean nonOptionArgumentsAllowed) {
        this.nonOptionArgumentsAllowed = nonOptionArgumentsAllowed;
    }

}
