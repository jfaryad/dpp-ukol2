package cz.cuni.mff.dpp;

/**
 * Represents a definition of one option. A parser uses this object to parse the command line arguments.
 * 
 * @author jakub
 * 
 */
public interface SingleOption {

    /**
     * Returns the id of this option. The id must be unique among all options. The id can be used for example to specify
     * what options this option is incompatible with or which options must be specified if this option is specified.<br>
     * The purpose is to avoid circular dependencies when building the options. For example, the builder creates option
     * "-a" and wants to specify, that it can't be used together with option "-b", but the definition of this option
     * doesn't exist yet.<br>
     * So instead of building both options and additionally adding incompatibilities, the builder can specify the ids of
     * not yet defined options in advance and thus can keep all the definitions for one option on one place.<br>
     * If id is not specified, the first long name of this options is used as id. If it is not specified either, then
     * the first short name of this option is used as id.
     * 
     * @return
     */
    public String getId();

    /**
     * Returns an array of all names (long or short) that represent this option.
     * 
     * @return
     */
    public String[] getNames();

    /**
     * Returns an array of all short names that represent this option.
     * 
     * @return
     */
    public String[] getShortNames();

    /**
     * Returns an array of all long names that represent this option.
     * 
     * @return
     */
    public String[] getLongNames();

    /**
     * Returns true if this option must be specified. If the option is not required, it is by default optional.
     * 
     * @return
     */
    public boolean isRequired();

    /**
     * Returns ids of options that must be specified together with this option.
     * 
     * @return
     */
    public String[] getDependendentIds();

    /**
     * Returns ids of options that must <b>not<b> be specified when this option is specified.
     * 
     * @return
     */
    public String[] getIncompatibleIds();

    /**
     * Returns true if this option accepts an argument.
     * 
     * @return
     */
    public boolean hasArgument();

    /**
     * Returns the class of the argument. If {@link #hasArguments()} returns false, the result of this method is
     * undefined.
     * 
     * @return
     */
    public Class<?> getArgumentClass();

    /**
     * Returns the name of the argument of this option. If this option doesn't accept arguments, the result is
     * undefined.<br>
     * The result of this method can be used when printing a help-page.
     * 
     * @return
     */
    public String getArgumentName();

    /**
     * Returns a description of this option. It will be used for help-page generation.
     * 
     * @return
     */
    public String getDescription();

}
