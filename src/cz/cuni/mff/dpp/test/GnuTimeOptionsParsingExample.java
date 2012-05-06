package cz.cuni.mff.dpp.test;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;
import cz.cuni.mff.dpp.impl.parser.DefaultCommandLineParser;

/**
 * An example of how options for GNU time can be parsed. It is a manual test, so it is intended to be executed as a java
 * application and then the results are printed on system out.
 * 
 * @author jakub
 * 
 */
public class GnuTimeOptionsParsingExample {

    public static void main(final String[] args) {

        testGnuTimeBean();

        System.out.println("------------------------------");

        testGnuTimeBean2();

        System.out.println("------------------------------");
    }

    public static void testGnuTimeBean() {

        final Options<GnuTimeBean> options = OptionsFactory.createOptions(GnuTimeBean.class);
        final DefaultCommandLineParser<GnuTimeBean> parser = new DefaultCommandLineParser<GnuTimeBean>(options);

        GnuTimeBean gnuTimeBean = parser.parse(new String[] {});
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "-a", "-o=temp.txt" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--help" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--help", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format", "%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V", "-o",
                "c:\\temp.txt" });
        System.out.println(gnuTimeBean.toString());
    }

    public static void testGnuTimeBean2() {

        final Options<GnuTimeBean2> options = OptionsFactory.createOptions(GnuTimeBean2.class);
        final DefaultCommandLineParser<GnuTimeBean2> parser = new DefaultCommandLineParser<GnuTimeBean2>(options);

        GnuTimeBean2 gnuTimeBean = parser.parse(new String[] {});
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "-a", "-o=temp.txt" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--help" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--help", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format", "%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V", "-o",
                "c:\\temp.txt" });
        System.out.println(gnuTimeBean.toString());

    }
}
