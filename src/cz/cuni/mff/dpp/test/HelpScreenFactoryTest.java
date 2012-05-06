package cz.cuni.mff.dpp.test;

import org.junit.Test;

import cz.cuni.mff.dpp.api.HelpScreenFactory;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.helpscreen.DefaultHelpScreenFactory;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;

public class HelpScreenFactoryTest {

    private static final String EXPECTED_HELP_SCREEN = "time - time a simple command or give resource usage\n\n"
            +
            "Usage: time [options] command [arguments...]\n\n"
            +
            "Options:\n"
            +
            "    -V, --version                 Print version information on standard output, then exit successfully.\n"
            +
            "    -a, --append                  (Used together with -o.) Do not overwrite but append.\n"
            +
            "    -f FORMAT, --format=FORMAT    Specify output format, possibly overriding the format specified in the environment variable TIME.\n"
            +
            "    --help                        Print a usage message on standard output and exit successfully.\n" +
            "    -o FILE, --output=FILE        Do not send the results to stderr, but overwrite the specified file.\n" +
            "    -v, --verbose                 Give very verbose output about all the program knows about.\n\n";

    @Test
    public void testGeneratedHelpScree() {
        final Options<GnuTimeBean> options = OptionsFactory.createOptions(GnuTimeBean.class);
        final HelpScreenFactory helpScreenFactory = new DefaultHelpScreenFactory(options);
        assert helpScreenFactory.generateHelpScreen().equals(EXPECTED_HELP_SCREEN);
    }
}
