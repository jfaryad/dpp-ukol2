package cz.cuni.mff.dpp.api.parser.exception;

import java.util.Collection;

import cz.cuni.mff.dpp.api.SingleOption;

public class IncompatibleOptionsException extends AbstractOptionsCompatibilityException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option with name: %s is incompatible with options which are specified on the command line.";

    public IncompatibleOptionsException(SingleOption incompatibleSingleOption,
            Collection<SingleOption> specifiedIncompatibleOptions) {
        super(MESSAGE, incompatibleSingleOption, specifiedIncompatibleOptions);
    }

    public SingleOption getIncompatibleSingleOption() {
        return getSingleOption();
    }

    public Collection<SingleOption> getSpecifiedIncompatibleOptions() {
        return getSingleOptionList();
    }
}
