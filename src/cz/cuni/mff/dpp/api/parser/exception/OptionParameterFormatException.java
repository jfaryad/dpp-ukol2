package cz.cuni.mff.dpp.api.parser.exception;

import cz.cuni.mff.dpp.api.ArgumentFormatException;

/**
 * Indicates an invalid option parameter format for an argument converter.
 * 
 * @author Tom
 * 
 */
public class OptionParameterFormatException extends AbstractArgumentConverterException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Value: '%s' cannot  be converted with converter: %s.";

    private final String optionName;

    public OptionParameterFormatException(final ArgumentFormatException exception, final String optioName) {

        super(String.format(MESSAGE, exception.getArgument(), exception.getArgumentConverterClass().getName()),
                exception,
                exception.getArgument(), exception.getArgumentConverterClass());
        this.optionName = optioName;
    }

    /**
     * Option name with invalid option parameter
     * 
     * @return
     */
    public String getOptionName() {
        return optionName;
    }

}
