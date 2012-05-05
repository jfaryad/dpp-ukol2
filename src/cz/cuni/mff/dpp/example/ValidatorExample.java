package cz.cuni.mff.dpp.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;
import cz.cuni.mff.dpp.impl.parser.DefaultCommandLineParser;

/**
 * This is an example of how validators can be used.
 * 
 * @author jakub
 * 
 */
public class ValidatorExample {

    private final static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    // private final static String TODAY = dateFormat.format(Calendar.getInstance().getTime());

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
        final Options<ValidatedPerson> options = OptionsFactory.createOptions(ValidatedPerson.class);
        System.out.println(options);

        final DefaultCommandLineParser<ValidatedPerson> parser = new DefaultCommandLineParser<ValidatedPerson>(options);

        try {

            System.out.println("Testing valid command line");
            final ValidatedPerson person = parser.parse(new String[] { "--a", "55", "-c", "blue",
                    "-b",
                    "30-05-2012" });
            System.out.println(person.toString());
            System.out.println("Passed");
        } catch (final Exception e) {
            System.out.println("Failed");
        }

        try {
            System.out.println("Testing invalid nextBirthday");
            final ValidatedPerson person = parser.parse(new String[] { "--a", "55", "-c", "blue",
                    "-b",
                    "30-03-2012" });
            System.out.println(person.toString());
            System.out.println("Failed");
        } catch (final Exception e) {
            System.out.println("Passed");
        }

        try {
            System.out.println("Testing invalid color");
            final ValidatedPerson person = parser.parse(new String[] { "--a", "55", "-c", "purple",
                    "-b",
                    "30-05-2012" });
            System.out.println(person.toString());
            System.out.println("Failed");
        } catch (final Exception e) {
            System.out.println("Passed");
        }

        try {
            System.out.println("Testing invalid age");
            final ValidatedPerson person = parser.parse(new String[] { "--a", "125", "-c", "blue",
                    "-b",
                    "30-06-2012" });
            System.out.println(person.toString());
            System.out.println("Failed");
        } catch (final Exception e) {
            System.out.println("Passed");
        }
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
}
