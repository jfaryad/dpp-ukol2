package cz.cuni.mff.dpp.api;

import java.util.Collection;

/**
 * 
 * Object holding information about all specified options as well as some other information like whether non-option
 * arguments are allowed etc.<br>
 * This object will be passed to a command line parser.
 * 
 * @author jakub
 * 
 */
public interface Options<T> {

    /**
     * Returns a collection of all defined options. Returns an empty list if no options are specified.
     * 
     */
    public Collection<SingleOption> getOptions();

    /**
     * Returns the option with the specified name (long or short). If no such option exists, null is returned.
     * 
     * @param optionName
     *            the name of the option to return.
     * @return option with the given name or null
     */
    public SingleOption getOption(String optionName);

    /**
     * Returns class of the target bean.
     * 
     * @return
     */
    public Class<T> getTargetBeanClass();

    /**
     * Returns converter for common argument, this converter is used to convert value from {@link String} to desired
     * type.
     * 
     * @return
     */
    public ArgumentConverter<?> getCommonArgumentConverter();

    /**
     * Returns setter for common argument.
     * 
     * @return
     */
    public OptionSetter getCommonArgumentSetter();

    /**
     * Return interval how many times common interval should appear on command line
     * 
     * @return
     */
    public RequiredCountInterval getCommonArgumentRequiredCountInterval();

    /**
     * Returns {@link SingleOption} objects of options (specified by parameter) which must be specified together with
     * this option on the command line.
     * 
     * @param optionName
     * @return
     */
    public Collection<SingleOption> getDependentSingleOptionList(String optionName);

    /**
     * Returns {@link SingleOption} objects of options (specified by parameter) which mustn't be specified together with
     * this option on the command line.
     * 
     * @param optionName
     * @return
     */
    public Collection<SingleOption> getIncompatibleSingleOptionList(String optionName);

    /**
     * Returns a summary of about the program that this Options object represents. It will be used for help screen
     * generation.
     * 
     * @return
     */
    public String getDescription();

    /**
     * Returns the name of the command line program that this Options object represents. It will be used for help screen
     * generation.
     * 
     * @return
     */
    public String getName();

    /**
     * Returns the "Usage" line that should be displayed in the help screen.
     * 
     */
    public String getUsageLine();

}
