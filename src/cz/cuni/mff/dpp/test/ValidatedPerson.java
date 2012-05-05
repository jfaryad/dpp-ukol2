package cz.cuni.mff.dpp.test;

import java.util.Date;

import cz.cuni.mff.dpp.annotation.OptionsDefinition;
import cz.cuni.mff.dpp.annotation.ParameterOption;
import cz.cuni.mff.dpp.annotation.Validator;
import cz.cuni.mff.dpp.example.ValidatorExample.DateArgumentConverter;
import cz.cuni.mff.dpp.validator.BetweenInclusiveValidator;
import cz.cuni.mff.dpp.validator.GreaterThenValidator;
import cz.cuni.mff.dpp.validator.OneOfValidator;

/**
 * Annotated class used for some tests
 * 
 * @author jakub
 * 
 */
@OptionsDefinition(
        name = "person",
        description = "saves information about a person")
public class ValidatedPerson {

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

    public Integer getAge() {
        return age;
    }

    public String getFavouriteColor() {
        return favouriteColor;
    }

    public Date getNextBirthday() {
        return nextBirthday;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public void setFavouriteColor(final String favouriteColor) {
        this.favouriteColor = favouriteColor;
    }

    public void setNextBirthday(final Date nextBirthday) {
        this.nextBirthday = nextBirthday;
    }

    @Override
    public String toString() {
        return "ValidatedPerson[age=" + age + ", favouriteColor='" + favouriteColor + "', nextBirthday='"
                + nextBirthday + "]";
    }

}