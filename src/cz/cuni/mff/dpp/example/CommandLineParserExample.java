package cz.cuni.mff.dpp.example;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;
import cz.cuni.mff.dpp.parser.DefaultCommandLineParser;

public class CommandLineParserExample {

    public static void main(String[] args) {

        testGnuTimeBean();
        
        System.out.println("------------------------------");
        
        testGnuTimeBean2();

    }

    public static void testGnuTimeBean() {

        Options options = OptionsFactory.createOptions(GnuTimeBean.class);

        DefaultCommandLineParser parser = new DefaultCommandLineParser(options);

        GnuTimeBean gnuTimeBean = (GnuTimeBean) parser.parse(new String[] {});
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean) parser.parse(new String[] { "-a" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean) parser.parse(new String[] { "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean) parser.parse(new String[] { "--help" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean) parser.parse(new String[] { "--help", "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean) parser.parse(new String[] { "--format", "%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean) parser.parse(new String[] { "--format=%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean) parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean) parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V", "-o",
                "c:\\temp.txt" });
        System.out.println(gnuTimeBean.toString());

    }
    
    public static void testGnuTimeBean2() {

        Options options = OptionsFactory.createOptions(GnuTimeBean2.class);

        DefaultCommandLineParser parser = new DefaultCommandLineParser(options);

        GnuTimeBean2 gnuTimeBean = (GnuTimeBean2) parser.parse(new String[] {});
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean2) parser.parse(new String[] { "-a" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean2) parser.parse(new String[] { "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean2) parser.parse(new String[] { "--help" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean2) parser.parse(new String[] { "--help", "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean2) parser.parse(new String[] { "--format", "%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean2) parser.parse(new String[] { "--format=%s%d%m" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean2) parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V" });
        System.out.println(gnuTimeBean.toString());

        gnuTimeBean = (GnuTimeBean2) parser.parse(new String[] { "--format=%s%d%m", "--help", "-a", "-V", "-o",
                "c:\\temp.txt" });
        System.out.println(gnuTimeBean.toString());

    }

}
