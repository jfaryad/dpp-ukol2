package cz.cuni.mff.dpp.api.parser.exception;

import cz.cuni.mff.dpp.api.ArgumentValidator;

public class ValidationException extends CommandLineParserException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Argument: %s cannot be validated by validator with class: %s.";

    private final ArgumentValidator<?> validator;

    private final Object invalidArgument;

    public ValidationException(ArgumentValidator<?> validator, Object invalidArgument) {
        super(String.format(MESSAGE, invalidArgument, validator.getClass().getName()));
        this.validator = validator;
        this.invalidArgument = invalidArgument;
    }

    public ArgumentValidator<?> getValidator() {
        return validator;
    }

    public Object getInvalidArgument() {
        return invalidArgument;
    }
}
