package cz.cuni.mff.dpp.example;

import cz.cuni.mff.dpp.impl.option.OptionsBuilder;

/**
 * This is an example of how the OptionsBuilder can be used to manually create option definitions for GNU time.
 * 
 * @author jfaryad
 * 
 */
public class OptionsBuilderExample {

    public static void main(String[] args) {
        OptionsBuilder builder = new OptionsBuilder();
        builder.addOption("f", "format")
                .setArgumentName("FORMAT")
                .setArgumentClass(String.class)
                .setDescription(
                        "Specify output format, possibly overriding the format specified in the environment variable TIME.");
        builder.addOption("o", "output")
                .setArgumentName("FILE")
                .setDescription("Do not send the results to stderr, but overwrite the specified file.")
                .setArgumentConverter(new FileArgumentConverter());
        builder.addOption("a", "append")
                .dependentOn("output")
                .setDescription("(Used together with -o.) Do not overwrite but append.");
        builder.addOption("v", "verbose")
                .setDescription("Give very verbose output about all the program knows about.");
        builder.addOption("help")
                .setDescription("Print a usage message on standard output and exit successfully.");
        builder.addOption("V", "version")
                .setDescription("Print version information on standard output, then exit successfully.");
    }

}
