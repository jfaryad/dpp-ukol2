package cz.cuni.mff.dpp.api.parser.exception;

import java.util.Collection;

import cz.cuni.mff.dpp.api.SingleOption;

public class DependentOptionsException extends AbstractOptionsCompatibilityException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option with name: %s depends on options which aren't specified on the command line.";

    public DependentOptionsException(SingleOption dependentSingleOption, Collection<SingleOption> unspecifiedDependentOptions) {
        super(MESSAGE, dependentSingleOption, unspecifiedDependentOptions);
    }

    public SingleOption getDependentSingleOption() {
        return getSingleOption();
    }

    public Collection<SingleOption> getUnspecifiedDendentOptions() {
        return getSingleOptionList();
    }

}
