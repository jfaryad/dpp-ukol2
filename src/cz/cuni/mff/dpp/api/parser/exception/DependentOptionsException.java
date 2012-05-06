package cz.cuni.mff.dpp.api.parser.exception;

import java.util.Collection;

import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Indicates unspecified options on which is option specified on the command line dependent.
 * 
 * @author Tom
 * 
 */
public class DependentOptionsException extends AbstractOptionsCompatibilityException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option with name: %s depends on options which aren't specified on the command line.";

    public DependentOptionsException(SingleOption dependentSingleOption,
            Collection<SingleOption> unspecifiedDependentOptions) {
        super(MESSAGE, dependentSingleOption, unspecifiedDependentOptions);
    }

    /**
     * Option config object of the option speciied on the command line
     * 
     * @return
     */
    public SingleOption getDependentSingleOption() {
        return getSingleOption();
    }

    /**
     * Dependent options which are not specified on the command line
     * 
     * @return
     */
    public Collection<SingleOption> getUnspecifiedDendentOptions() {
        return getSingleOptionList();
    }

}
