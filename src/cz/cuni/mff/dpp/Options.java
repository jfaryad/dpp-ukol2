package cz.cuni.mff.dpp;

/**
 * 
 * Object holding information about all specified options as well as some other information like whether non-option
 * arguments are allowed etc.<br>
 * This object will be passed to a command line parser.
 * 
 * @author jakub
 * 
 */
public interface Options {

    /**
     * Adds a new option definition. If the new option is not valid considering already added options (e.g. it contains
     * a synonym already used in another option) an exception is thrown.
     * 
     * @param option
     *            a new option.
     */
    public void addOption(SingleOption option);

    /**
     * Returns a list off all defined options. Returns an empty list if no options are specified.
     * 
     */
    public SingleOption[] getOptions();

    /**
     * Returns the option with the specified name (long or short). If no such option exists, null is returned.
     * 
     * @param optionName
     *            the name of the option to return.
     * @return option with the given name or null
     */
    public SingleOption getOption(String optionName);

    /**
     * A flag defining if additional arguments (arguments not belonging to any option) are allowed.
     * 
     */
    public boolean nonOptionArgumentsAllowed();
}
