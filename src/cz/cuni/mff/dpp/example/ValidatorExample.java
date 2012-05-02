package cz.cuni.mff.dpp.example;

import static cz.cuni.mff.dpp.validator.ValidatorFactory.betweenInclusive;
import static cz.cuni.mff.dpp.validator.ValidatorFactory.greaterThen;
import static cz.cuni.mff.dpp.validator.ValidatorFactory.oneOf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.impl.option.OptionsBuilder;

/**
 * This is an example of how validators can be used.
 * 
 * @author jakub
 * 
 */
public class ValidatorExample {

    public static void main(String[] args) {
        OptionsBuilder builder = new OptionsBuilder();
        builder.addOption("a", "age")
                .setArgumentName("AGE")
                .setArgumentClass(Integer.class)
                .setValidators(betweenInclusive(0, 120))
                .setDescription("The age of the person.");
        builder.addOption("c", "color")
                .setArgumentName("COLOR")
                .setValidators(oneOf(new String[] { "red", "blue", "green" }))
                .setDescription("Favorite color.");
        builder.addOption("b", "next-brithday")
                .dependentOn("output")
                .setArgumentConverter(new DateArgumentConverter())
                .setValidators(greaterThen(Calendar.getInstance().getTime()))
                .setDescription("Date of the next birthday.");
        builder.setNonOptionArgumentsAllowed(false);
    }

    private static class DateArgumentConverter implements ArgumentConverter<Date> {

        @Override
        public Date parse(String argument) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            try {
                return dateFormat.parse(argument);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Class<Date> getTargetClass() {
            return Date.class;
        }

    }
}
