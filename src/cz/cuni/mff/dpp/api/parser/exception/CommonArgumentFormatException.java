package cz.cuni.mff.dpp.api.parser.exception;

import cz.cuni.mff.dpp.api.ArgumentFormatException;

/**
 * Indicates invalid common argument format for argument converter.
 * @author Tom
 *
 */
public class CommonArgumentFormatException extends AbstractArgumentConverterException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Value: '%s' cannot  be formatted with converter: %s.";

    public CommonArgumentFormatException(ArgumentFormatException exception) {

        super(String.format(MESSAGE, exception.getArgument(), exception.getArgumentConverterClass().getName()), exception,
                exception.getArgument(), exception.getArgumentConverterClass());
    }

}
