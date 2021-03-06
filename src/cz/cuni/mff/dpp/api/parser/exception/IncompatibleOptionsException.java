package cz.cuni.mff.dpp.api.parser.exception;

import java.util.Collection;

import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Indicates that an option has been specified on the command line together with other options, that are incompatible
 * with this option.
 * 
 * @author Tom
 * 
 */
public class IncompatibleOptionsException extends AbstractOptionsCompatibilityException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Option with name: %s is incompatible with options which are specified on the command line.";

    /**
     * 
     * @param incompatibleSingleOption
     *            the invalid option
     * @param specifiedIncompatibleOptions
     *            the options that this option is incombatible with
     */
    public IncompatibleOptionsException(final SingleOption incompatibleSingleOption,
            final Collection<SingleOption> specifiedIncompatibleOptions) {
        super(MESSAGE, incompatibleSingleOption, specifiedIncompatibleOptions);
    }

    /**
     * return option, for which incompatible options were found
     * 
     * @return
     */
    public SingleOption getIncompatibleSingleOption() {
        return getSingleOption();
    }

    /**
     * return specified incompatible options.
     * 
     * @return
     */
    public Collection<SingleOption> getSpecifiedIncompatibleOptions() {
        return getSingleOptionList();
    }
}
