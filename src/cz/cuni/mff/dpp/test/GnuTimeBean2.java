package cz.cuni.mff.dpp.test;

import java.io.File;

import cz.cuni.mff.dpp.annotation.OptionsDefinition;
import cz.cuni.mff.dpp.annotation.ParameterOption;
import cz.cuni.mff.dpp.annotation.SimpleOption;

/**
 * Example of using the annotation @Option to define allowed options for GNU time. Also an example of annotating a
 * method is given (to convert string to file)
 * 
 * 
 * @author jfaryad
 * 
 */
@OptionsDefinition(
        name = "time",
        description = "time a simple command or give resource usage",
        usage = "time [options] command [arguments...]")
public class GnuTimeBean2 {

    @ParameterOption(
            names = { "f", "format" },
            argumentName = "FORMAT",
            description = "Specify output format, possibly overriding the format specified in the environment variable TIME.")
    private String format;

    /* this field is set by the parser by calling outputFile(fileName) when parsing the option --output */
    private File output;

    @SimpleOption(
            names = { "a", "append" },
            dependentOn = { "output" },
            description = "(Used together with -o.) Do not overwrite but append.")
    private boolean append;

    @SimpleOption(
            names = { "v", "verbose" },
            description = "Give very verbose output about all the program knows about.")
    private boolean verbose;

    @SimpleOption(names = { "help" }, description = "Print a usage message on standard output and exit successfully.")
    private boolean help;

    @SimpleOption(
            names = { "V", "version" },
            description = "Print version information on standard output, then exit successfully.")
    private boolean version;

    @ParameterOption(
            names = { "o", "output" },
            argumentName = "FILE",
            description = "Do not send the results to stderr, but overwrite the specified file.")
    public void outputFile(final String fileName) {
        this.output = new File(fileName);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(final File output) {
        this.output = output;
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(final boolean append) {
        this.append = append;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(final boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(final boolean help) {
        this.help = help;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(final boolean version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "GnuTimeBean2 [format=" + format + ", output=" + output + ", append=" + append + ", verbose=" + verbose
                + ", help=" + help + ", version=" + version + "]";
    }

}
