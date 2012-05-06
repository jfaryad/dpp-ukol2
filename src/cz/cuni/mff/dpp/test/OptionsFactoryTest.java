package cz.cuni.mff.dpp.test;

import java.io.File;

import org.junit.Test;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.converter.FileArgumentConverter;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;

public class OptionsFactoryTest {

    public static void main(final String[] args) {

        final Options<GnuTimeBean> createOptions = OptionsFactory.createOptions(GnuTimeBean.class);

        System.out.println(createOptions);

        final Options<GnuTimeBean2> createOptions2 = OptionsFactory.createOptions(GnuTimeBean2.class);

        System.out.println(createOptions2);

    }

    @Test
    public void testOptionsFactoryOnlyFieldAnnotations() {
        final Options<GnuTimeBean> options = OptionsFactory.createOptions(GnuTimeBean.class);

        assert options.getOptions().size() == 7;

        assert options.getOption("a") != null;
        assert options.getOption("append") == options.getOption("a");
        assert options.getOption("a").hasArgument() == false;
        assert options.getOption("a").isArgumentRequired() == false;
        assert options.getOption("a").getDependentList().iterator().next().equals("output");
        assert options.getDependentSingleOptionList("append").iterator().next().getLongNames().iterator().next()
                .equals("output");

        assert options.getOption("f") != null;
        assert options.getOption("format") == options.getOption("f");
        assert options.getOption("f").hasArgument();
        assert options.getOption("f").isArgumentRequired();
        assert options.getOption("f").getArgumentName().equals("FORMAT");

        assert options.getOption("o") != null;
        assert options.getOption("output") == options.getOption("o");
        assert options.getOption("o").hasArgument();
        assert options.getOption("o").isArgumentRequired();
        assert options.getOption("o").getArgumentName().equals("FILE");
        assert options.getOption("o").getArgumentClass() == File.class;
        assert options.getOption("o").getArgumentConverter().getClass() == FileArgumentConverter.class;
    }

    @Test
    public void testOptionsFactoryWithAnnotations() {
        final Options<GnuTimeBean2> options = OptionsFactory.createOptions(GnuTimeBean2.class);

        assert options.getOptions().size() == 7;

        assert options.getOption("a") != null;
        assert options.getOption("append") == options.getOption("a");
        assert options.getOption("a").hasArgument() == false;
        assert options.getOption("a").isArgumentRequired() == false;
        assert options.getOption("a").getDependentList().iterator().next().equals("output");
        assert options.getDependentSingleOptionList("append").iterator().next().getLongNames().iterator().next()
                .equals("output");

        assert options.getOption("f") != null;
        assert options.getOption("format") == options.getOption("f");
        assert options.getOption("f").hasArgument();
        assert options.getOption("f").isArgumentRequired();
        assert options.getOption("f").getArgumentName().equals("FORMAT");

        assert options.getOption("o") != null;
        assert options.getOption("output") == options.getOption("o");
        assert options.getOption("o").hasArgument();
        assert options.getOption("o").isArgumentRequired();
        assert options.getOption("o").getArgumentName().equals("FILE");
        assert options.getOption("o").getArgumentClass() == null;
    }
}
