package cz.cuni.mff.dpp.example;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;
import cz.cuni.mff.dpp.impl.parser.DefaultCommandLineParser;

public class CommandLineParserExample {

    public static void main(String[] args) {

        // testGnuTimeBean();
        //
        // System.out.println("------------------------------");
        //
        // testGnuTimeBean2();
        //
        // System.out.println("------------------------------");
        //
        // testCommonArgumentTestBean();
        //
        // System.out.println("------------------------------");
        //
        // testEnumArgumentBean();
        //
        // System.out.println("------------------------------");

    }

    public static void testGnuTimeBean() {

        Options<GnuTimeBean> options = OptionsFactory.createOptions(GnuTimeBean.class);
        DefaultCommandLineParser<GnuTimeBean> parser = new DefaultCommandLineParser<GnuTimeBean>(options);

        GnuTimeBean gnuTimeBean = parser.parse(new String[] {});
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "-a" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--help" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--help", "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format", "%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V", "-o",
                "c:\\temp.txt" });
        System.out.println(gnuTimeBean.toString());

    }

    public static void testGnuTimeBean2() {

        Options<GnuTimeBean2> options = OptionsFactory.createOptions(GnuTimeBean2.class);
        DefaultCommandLineParser<GnuTimeBean2> parser = new DefaultCommandLineParser<GnuTimeBean2>(options);

        GnuTimeBean2 gnuTimeBean = parser.parse(new String[] {});
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "-a" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--help" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--help", "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format", "%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V", "-o",
                "c:\\temp.txt" });
        System.out.println(gnuTimeBean.toString());

    }
}
