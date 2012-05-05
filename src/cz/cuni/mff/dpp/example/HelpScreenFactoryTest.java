package cz.cuni.mff.dpp.example;

import cz.cuni.mff.dpp.api.HelpScreenFactory;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.helpscreen.DefaultHelpScreenFactory;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;

public class HelpScreenFactoryTest {

    public static void main(final String[] args) {
        final Options options = OptionsFactory.createOptions(GnuTimeBean.class);
        final HelpScreenFactory helpScreenFactory = new DefaultHelpScreenFactory(options);
        System.out.println(helpScreenFactory.generateHelpScreen());
    }
}
