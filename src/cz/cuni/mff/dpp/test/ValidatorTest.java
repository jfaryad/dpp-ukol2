package cz.cuni.mff.dpp.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.parser.exception.ValidationException;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;
import cz.cuni.mff.dpp.impl.parser.DefaultCommandLineParser;

/**
 * This is an example of how validators can be used.
 * 
 * @author jakub
 * 
 */
public class ValidatorTest {

    final static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final Options options = OptionsFactory.createOptions(ValidatedPerson.class);
    private final DefaultCommandLineParser parser = new DefaultCommandLineParser(options);

    @Test
    public void testValidCommandLineValidation() {
        parser.parse(new String[] { "--a", "55", "-c", "blue", "-b", "30-05-2012" });
    }

    @Test(expected = ValidationException.class)
    public void testInvalidDateGreaterThanValidation() {
        parser.parse(new String[] { "--a", "55", "-c", "blue", "-b", "30-03-2012" });
    }

    @Test(expected = ValidationException.class)
    public void testInvalidBetweenInclusiveValidation() {
        parser.parse(new String[] { "--a", "125", "-c", "blue", "-b", "30-03-2012" });
    }

    @Test(expected = ValidationException.class)
    public void testInvalidOneOfValidation() {
        parser.parse(new String[] { "--a", "55", "-c", "purple", "-b", "30-05-2012" });
    }

    public class DateArgumentConverter implements ArgumentConverter<Date> {

        @Override
        public Date parse(final String argument) {
            try {
                return ValidatorTest.dateFormat.parse(argument);
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
