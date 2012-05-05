package cz.cuni.mff.dpp.api.parser.exception;

import java.util.Collection;
import java.util.Collections;


//todo bude se pouzivat...
public class DependentOptionsException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option with name: %s depends on this options: which aren't provided on command line.";

    private final Collection<String> unprovidedOptionNames;

    private final String optionName;

    public DependentOptionsException(String optionName, Collection<String> unprovidedOptionNames) {
        super(String.format(MESSAGE, optionName, unprovidedOptionNames));
        this.unprovidedOptionNames = Collections.unmodifiableCollection(unprovidedOptionNames);
        this.optionName = optionName;
    }

    public Collection<String> getUnprovidedOptionNames() {
        return unprovidedOptionNames;
    }

    public String getOptionName() {
        return optionName;
    }

}
