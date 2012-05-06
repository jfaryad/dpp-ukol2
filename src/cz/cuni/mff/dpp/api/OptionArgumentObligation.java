package cz.cuni.mff.dpp.api;

/**
 * Option parameter requirement indication
 * @author Tom
 *
 */
public enum OptionArgumentObligation {

    /**
     * Option parameters is required
     */
    REQUIRED,
    
    /**
     * Option parameters is optional
     */
    OPTIONAL,
    
    /**
     * Option parameters is  not allowed
     */
    FORBIDDEN

}
