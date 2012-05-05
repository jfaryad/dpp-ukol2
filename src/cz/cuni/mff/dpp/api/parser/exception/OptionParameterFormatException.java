package cz.cuni.mff.dpp.api.parser.exception;

import cz.cuni.mff.dpp.api.ArgumentFormatException;

public class OptionParameterFormatException extends AbstractArgumentConverterException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Value: '%s' cannot  be formated with converter: %s.";

    private final String optionName;

    public OptionParameterFormatException(ArgumentFormatException exception, String optioName) {

        super(String.format(MESSAGE, exception.getValue(), exception.getArgumentConverterClass().getName()), exception,
                exception.getValue(), exception.getArgumentConverterClass());
        this.optionName = optioName;
    }

    public String getOptionName() {
        return optionName;
    }

}
