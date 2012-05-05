package cz.cuni.mff.dpp.api.parser.exception;

import java.util.Collection;
import java.util.Collections;

import cz.cuni.mff.dpp.api.SingleOption;

public abstract class AbstractOptionsCompatibilityException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private final SingleOption singleOption;

    private final Collection<SingleOption> singleOptionList;

    public AbstractOptionsCompatibilityException(String formattedMessage, SingleOption singleOption,
            Collection<SingleOption> singleOptionList) {
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
