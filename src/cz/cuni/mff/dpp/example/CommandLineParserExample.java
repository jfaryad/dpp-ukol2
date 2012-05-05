package cz.cuni.mff.dpp.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.dpp.annotation.CommonArgument;
import cz.cuni.mff.dpp.annotation.ParameterOption;
import cz.cuni.mff.dpp.annotation.SimpleOption;
import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.ArgumentFormatException;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
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

        testTestBean();
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

    private static void testCommonArgumentTestBean() {

        Options options = OptionsFactory.createOptions(CommonArgumentTestBean.class);
        DefaultCommandLineParser parser = new DefaultCommandLineParser(options);

        CommonArgumentTestBean bean = (CommonArgumentTestBean) parser.parse(new String[] {});
        System.out.println(bean.toString());

        bean = (CommonArgumentTestBean) parser.parse(new String[] { "20" });
        System.out.println(bean.toString());

        bean = (CommonArgumentTestBean) parser.parse(new String[] { "100", "200", "300" });
        System.out.println(bean.toString());

    }

    public static void testEnumArgumentBean() {

        Options options = OptionsFactory.createOptions(EnumArgumentBean.class);
        DefaultCommandLineParser parser = new DefaultCommandLineParser(options);

        EnumArgumentBean bean = (EnumArgumentBean) parser.parse(new String[] {});
        System.out.println(bean.toString());

        bean = (EnumArgumentBean) parser.parse(new String[] { "FORBIDDEN" });
        System.out.println(bean.toString());

        bean = (EnumArgumentBean) parser.parse(new String[] { "OPTIONAL", "FORBIDDEN", "REQUIRED" });
        System.out.println(bean.toString());

    }

    public static void testTestBean() {

        Options options = OptionsFactory.createOptions(TestBean.class);
        DefaultCommandLineParser parser = new DefaultCommandLineParser(options);

        parser.parse(new String[] { "aaa", "bb" });

    }

    public static class CommonArgumentTestBean {

        private final List<Integer> argumentList = new ArrayList<Integer>();

        @CommonArgument
        public void addArgument(int argument) {
            argumentList.add(argument);
        }

        @Override
        public String toString() {
            return "CommonArgumentTestBean [argumentList=" + argumentList + "]";
        }
    }

    public static class EnumArgumentBean {
        private final List<OptionArgumentObligation> argumentList = new ArrayList<OptionArgumentObligation>();

        @CommonArgument
        public void addArgument(OptionArgumentObligation argument) {
            argumentList.add(argument);
        }

        @Override
        public String toString() {
            return "CommonArgumentTestBean [argumentList=" + argumentList + "]";
        }
    }

    public static class TestBean {

        @ParameterOption(names = "aaa", argumentConverter = CommandLineParserExample.TestArgumentConverter.class)
        private File neco = null;

        @SimpleOption(names = "aaaa")
        private boolean neco2;

        @ParameterOption(names = "bb", minRequiredCount = 0, maxRequiredCount = 3, parameterRequired = false)
        private Integer someNumberFrom2To10;

        @CommonArgument(minRequiredCount = 1, maxRequiredCount = 3)
        public void setNeco3(String neco3) {
        }

    }

    public static class TestArgumentConverter implements ArgumentConverter<File> {

        public TestArgumentConverter() {
        }

        @Override
        public File parse(String argument) {
            throw new ArgumentFormatException(argument, this.getClass());
        }

        @Override
        public Class<File> getTargetClass() {
            return File.class;
        }

    }

}
