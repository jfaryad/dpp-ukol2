package cz.cuni.mff.dpp.api.parser.exception;

import cz.cuni.mff.dpp.api.ArgumentValidator;

/**
 * Indicates, that validator cannot validate an object (option parameter)
 * 
 * @author Tom
 * 
 */
public class ValidationException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Argument: %s cannot be validated by validator with class: %s.";

    private final ArgumentValidator<?> validator;

    private final Object invalidArgument;

    public ValidationException(final ArgumentValidator<?> validator, final Object invalidArgument) {
        super(String.format(MESSAGE, invalidArgument, validator.getClass().getName()));
        this.validator = validator;
        this.invalidArgument = invalidArgument;
    }

    /**
     * Returns the validator
     * 
     * @return
     */
    public ArgumentValidator<?> getValidator() {
        return validator;
    }

    /**
     * Returns the object, that was not validated by validator
     * 
     * @return
     */
    public Object getInvalidArgument() {
        return invalidArgument;
    }
}
