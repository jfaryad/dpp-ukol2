package cz.cuni.mff.dpp.api;

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
     * Returns names of options that must be specified together with this option.
     * 
     * @return
     */
    public Collection<String> getDependentList();

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

    /**
     * Returns the converter of this option's parameter or {@code null} if no converter is set (for options without
     * parameter).
     * 
     * @return
     */
    public ArgumentConverter<?> getArgumentConverter();

    /**
     * Returns a default value of the argument of this option (in case the argument is optional and not provided on the
     * command line), to be passed to {@link OptionSetter#setOption(Object, Object)}
     * 
     * @return
     */
    public Object getDefaultValue();

    /**
     * Returns an implementation of {@link OptionSetter}, whose method {@link OptionSetter#setOption(Object, Object)}
     * should be called for this option
     * 
     * @return
     */
    public OptionSetter getOptionSetter();

    /**
     * Returns all {@link ArgumentValidator}s set for this option.
     * 
     * @return
     */
    public Collection<ArgumentValidator<?>> getValidators();

    /**
     * Returns interval how many times must be this option specified. Default interval is <0,1>.
     * 
     * @return
     */
    public RequiredCountInterval getRequiredCountInterval();

    /**
     * Returns first name for this option (in order in which option names were configured)
     * 
     * @return
     */
    public String getFirstOptionName();

}
