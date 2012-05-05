package cz.cuni.mff.dpp.api.parser.exception;

import java.util.Collection;
import java.util.Collections;

public class RequiredOptionException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "This required options: %s weren't provided";

    private final Collection<String> unprovidedRequiredOptionNames;

    public RequiredOptionException(Collection<String> unprovidedRequiredOptionNames) {
        super(String.format(MESSAGE, unprovidedRequiredOptionNames));
        this.unprovidedRequiredOptionNames = Collections.unmodifiableCollection(unprovidedRequiredOptionNames);
    }

    public Collection<String> getUnProvidedRequiredOptionNames() {
        return unprovidedRequiredOptionNames;
    }

}
