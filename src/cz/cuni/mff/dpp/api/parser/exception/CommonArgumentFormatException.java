package cz.cuni.mff.dpp.api.parser.exception;

import cz.cuni.mff.dpp.api.ArgumentFormatException;

public class CommonArgumentFormatException extends AbstractArgumentConverterException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Value: '%s' cannot  be formatted with converter: %s.";

    public CommonArgumentFormatException(ArgumentFormatException exception) {

        super(String.format(MESSAGE, exception.getValue(), exception.getArgumentConverterClass().getName()), exception,
                exception.getValue(), exception.getArgumentConverterClass());
    }

}
