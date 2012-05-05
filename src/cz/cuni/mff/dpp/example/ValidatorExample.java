package cz.cuni.mff.dpp.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.cuni.mff.dpp.annotation.ParameterOption;
import cz.cuni.mff.dpp.annotation.Validator;
import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;
import cz.cuni.mff.dpp.validator.BetweenInclusiveValidator;
import cz.cuni.mff.dpp.validator.GreaterThenValidator;
import cz.cuni.mff.dpp.validator.OneOfValidator;

/**
 * This is an example of how validators can be used.
 * 
 * @author jakub
 * 
 */
public class ValidatorExample {

    private final static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final static String TODAY = dateFormat.format(Calendar.getInstance().getTime());

    // private final static ArgumentValidator<Integer> ageValidator = ValidatorFactory.createValidator(
    // BetweenInclusiveValidator.class,
    // ArgumentConverterFactory.getDefaultConverter(Integer.class),
    // "0", "120");
    //
    // private final static ArgumentValidator<String> colorValidator = ValidatorFactory.createValidator(
    // OneOfValidator.class,
    // ArgumentConverterFactory.getDefaultConverter(String.class),
    // "red", "blue", "green");
    //
    // private final static ArgumentValidator<Date> birthdayDateValidator = ValidatorFactory.createValidator(
    // GreaterThenValidator.class,
    // new DateArgumentConverter(),
    // dateFormat.format(Calendar.getInstance().getTime()));

    public static void main(final String[] args) {
        final Options createOptions = OptionsFactory.createOptions(ValidatedPerson.class);
        System.out.println(createOptions);
    }

    // private static void manuallyCreateBuilder() {
    // final OptionsBuilder builder = new OptionsBuilder();
    // builder.addOption("a", "age")
    // .setArgumentName("AGE")
    // .setArgumentClass(Integer.class)
    // .setValidators(ageValidator)
    // .setDescription("The age of the person.");
    // builder.addOption("c", "color")
    // .setArgumentName("COLOR")
    // .setValidators(colorValidator)
    // .setDescription("Favorite color.");
    // builder.addOption("b", "next-brithday")
    // .setArgumentConverter(new DateArgumentConverter())
    // .setValidators(birthdayDateValidator)
    // .setDescription("Date of the next birthday.");
    // builder.setNonOptionArgumentsAllowed(false);
    // }

    public static class DateArgumentConverter implements ArgumentConverter<Date> {

        public DateArgumentConverter() {

        }

        @Override
        public Date parse(final String argument) {
            try {
                return dateFormat.parse(argument);
            } catch (final ParseException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Class<Date> getTargetClass() {
            return Date.class;
        }

    }

    private static class ValidatedPerson {

        @ParameterOption(
                names = { "a", "age" },
                argumentName = "AGE",
                validators = {
                        @Validator(
                                validatorClass = BetweenInclusiveValidator.class,
                                constructorParams = { "0", "120" })
                },
                description = "The age of the person.")
        private int age;

        @ParameterOption(
                names = { "c", "color" },
                argumentName = "COLOR",
                validators = {
                        @Validator(
                                validatorClass = OneOfValidator.class,
                                constructorParams = { "red", "blue", "green" })
                },
                description = "Favourite color")
        private String favouriteColor;

        @ParameterOption(
                names = { "b", "next-birthday" },
                argumentConverter = DateArgumentConverter.class,
                validators = {
                        @Validator(
                                validatorClass = GreaterThenValidator.class,
                                constructorParams = { "30-04-2012" })
                },
                description = "Date of the next birthday")
        private Date nextBirthday;
    }
}
