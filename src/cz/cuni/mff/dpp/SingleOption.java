package cz.cuni.mff.dpp;

import java.util.Collection;

/**
 * Represents a definition of one option. A parser uses this object to parse the command line arguments.
 * 
 * @author jakub
 * 
 */
public interface SingleOption {

    /**
     * Returns an array of all names (long or short) that represent this option.
     * 
     * @return
     */
    public Collection<String> getNames();

    /**
     * Returns an array of all short names that represent this option.
     * 
     * @return
     */
    public Collection<String> getShortNames();

    /**
     * Returns an array of all long names that represent this option.
     * 
     * @return
     */
    public Collection<String> getLongNames();

    /**
     * Returns true if this option must be specified. If the option is not required, it is by default optional.
     * 
     * @return
     */
    public boolean isRequired();

    /**
     * Returns names of options that must be specified together with this option.
     * 
     * @return
     */
    public Collection<String> getDependendentList();

    /**
     * Returns names of options that must <b>not<b> be specified when this option is specified.
     * 
     * @return
     */
    public Collection<String> getIncompatibleList();

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
     * Returns true if this option must always come with an argument.
     * 
     * @return
     */
    public boolean isArgumentRequired();

    /**
     * Defines wheter an argument is mandatory, optional of forbidden for this option.
     */
    public OptionArgumentObligation getArgumentObligation();

    /**
     * Returns a description of this option. It will be used for help-page generation.
     * 
     * @return
     */
    public String getDescription();

    // TODO something like getArgumentConverter, that would return a converter from string to the expected argument
    // type.

}
