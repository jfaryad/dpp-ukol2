package cz.cuni.mff.dpp.api;

/**
 * Generates a help screen describing the usage of the options for the given program.
 * 
 * @author jakub
 * 
 */
public interface HelpScreenFactory {

    /**
     * Generates a help screen describing the usage of the options for the given program.
     * 
     */
    public String generateHelpScreen();
}
