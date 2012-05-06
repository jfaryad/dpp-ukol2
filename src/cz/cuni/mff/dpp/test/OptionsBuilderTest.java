package cz.cuni.mff.dpp.test;

import java.util.Collection;

import org.junit.Test;

import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.SingleOption;
import cz.cuni.mff.dpp.impl.converter.FileArgumentConverter;
import cz.cuni.mff.dpp.impl.option.OptionsBuilder;

/**
 * This is an test of how the OptionsBuilder can be used to manually create option definitions for GNU time.
 * 
 * @author jfaryad
 * 
 */
public class OptionsBuilderTest {

    private Options<Object> buildOptions() {
        final OptionsBuilder<Object> builder = new OptionsBuilder<Object>();
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
        builder.setName("time");
        builder.setDescription("time a simple command or give resource usage");
        builder.setUsageLine("time [options] command [arguments...]");
        return builder;
    }

    @Test
    public void testArgumentObligations() {
        final Options<Object> options = buildOptions();
        assert options.getOption("f").isArgumentRequired() == true;
        assert options.getOption("o").isArgumentRequired() == true;
        assert options.getOption("a").getArgumentObligation() == OptionArgumentObligation.FORBIDDEN;
        assert options.getOption("v").getArgumentObligation() == OptionArgumentObligation.FORBIDDEN;
    }

    @Test
    public void testDependencies() {
        final Options<Object> options = buildOptions();
        final Collection<SingleOption> dependencies = options.getDependentSingleOptionList("append");
        assert dependencies.size() == 1;
        final SingleOption dependency = dependencies.iterator().next();
        final Collection<String> dependencyLongNames = dependency.getLongNames();
        assert dependencyLongNames.size() == 1;
        assert dependencyLongNames.iterator().next().equals("output");
    }

}
