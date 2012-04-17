package cz.cuni.mff.dpp.example;

import java.io.File;

import cz.cuni.mff.dpp.annotation.Option;

/**
 * Example of using the annotation @Option to define allowed options for GNU time
 * 
 * @author jfaryad
 * 
 */
public class GnuTimeBean {

    @Option(
            names = { "f", "format" },
            argumentName = "FORMAT",
            description = "Specify output format, possibly overriding the format specified in the environment variable TIME.")
    private String format;

    @Option(
            names = { "o", "output" },
            argumentName = "FILE",
            description = "Do not send the results to stderr, but overwrite the specified file.")
    // TODO add a converter able to convert the string to a file, or annotate a method instead, that would do the
    // conversion
    private File output;

    @Option(
            names = { "a", "append" },
            dependentIds = { "output" },
            description = "(Used together with -o.) Do not overwrite but append.")
    private boolean append;

    @Option(names = { "v", "verbose" }, description = "Give very verbose output about all the program knows about.")
    private boolean verbose;

    @Option(names = { "help" }, description = "Print a usage message on standard output and exit successfully.")
    private boolean help;

    @Option(
            names = { "V", "version" },
            description = "Print version information on standard output, then exit successfully.")
    private boolean version;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

}