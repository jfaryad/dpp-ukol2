/**
 * All the annotations used for defining options.
 * <p/>
 * For example
 * <pre>
 * ...
 * @SimpleOption(names = { "v" })
 * private boolean verbose;
 * ...
 * @ParameterOption(
 *          names = { "o", "output" },
 *          argumentName = "FILE",
 *          argumentConverter = FileArgumentConverter.class)
 * private File file;    
 * ...
 * @CommonArgument
 * private String pathToSomeFile;        
 * </pre>
 * 
 */
package cz.cuni.mff.dpp.annotation;

