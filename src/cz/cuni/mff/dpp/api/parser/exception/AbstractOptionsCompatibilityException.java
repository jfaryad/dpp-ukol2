package cz.cuni.mff.dpp.api.parser.exception;

import java.util.Collection;
import java.util.Collections;

import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Parent class for dependency/incompatibility exceptions
 * 
 * @author jakub
 * 
 */
public abstract class AbstractOptionsCompatibilityException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private final SingleOption singleOption;

    private final Collection<SingleOption> singleOptionList;

    /**
     * 
     * @param formattedMessage
     *            message of the exception
     * @param singleOption
     *            the invalid option
     * @param singleOptionList
     *            the dependencies/incompatibilities that cause the invalidity
     */
    public AbstractOptionsCompatibilityException(final String formattedMessage, final SingleOption singleOption,
            final Collection<SingleOption> singleOptionList) {
        super(String.format(formattedMessage, singleOption.getFirstOptionName()));
        this.singleOptionList = Collections.unmodifiableCollection(singleOptionList);
        this.singleOption = singleOption;
    }

    public SingleOption getSingleOption() {
        return singleOption;
    }

    public Collection<SingleOption> getSingleOptionList() {
        return singleOptionList;
    }
}
