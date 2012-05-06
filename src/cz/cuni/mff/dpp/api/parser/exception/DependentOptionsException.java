package cz.cuni.mff.dpp.api.parser.exception;

import java.util.Collection;

import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Indicates that an option has is dependent on another option that was not specified on the command line
 * 
 * @author Tom
 * 
 */
public class DependentOptionsException extends AbstractOptionsCompatibilityException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option with name: %s depends on options which aren't specified on the command line.";

    /**
     * 
     * @param dependentSingleOption
     *            the option that misses dependencies
     * @param unspecifiedDependentOptions
     *            the options that this option is dependent on
     */
    public DependentOptionsException(final SingleOption dependentSingleOption,
            final Collection<SingleOption> unspecifiedDependentOptions) {
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
