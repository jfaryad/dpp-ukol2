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
import cz.cuni.mff.dpp.api.RequiredCountInterval;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;
import cz.cuni.mff.dpp.impl.parser.DefaultCommandLineParser;

public class CommandLineParserExample {

    public static void main(String[] args) {

//         testGnuTimeBean();
//        
//         System.out.println("------------------------------");
//        
//         testGnuTimeBean2();
//        
//         System.out.println("------------------------------");
//        
//         testCommonArgumentTestBean();
//        
//         System.out.println("------------------------------");
//        
//         testEnumArgumentBean();
//        
//         System.out.println("------------------------------");

        testTestBean();
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

    private static void testCommonArgumentTestBean() {

        Options<CommonArgumentTestBean> options = OptionsFactory.createOptions(CommonArgumentTestBean.class);
        DefaultCommandLineParser<CommonArgumentTestBean> parser = new DefaultCommandLineParser<CommonArgumentTestBean>(
                options);

        CommonArgumentTestBean bean = parser.parse(new String[] {});
        System.out.println(bean.toString());

        bean = parser.parse(new String[] { "20" });
        System.out.println(bean.toString());

        bean = parser.parse(new String[] { "100", "200", "300" });
        System.out.println(bean.toString());

    }

    public static void testEnumArgumentBean() {

        Options<EnumArgumentBean> options = OptionsFactory.createOptions(EnumArgumentBean.class);
        DefaultCommandLineParser<EnumArgumentBean> parser = new DefaultCommandLineParser<EnumArgumentBean>(options);

        EnumArgumentBean bean = (EnumArgumentBean) parser.parse(new String[] {});
        System.out.println(bean.toString());

        bean = (EnumArgumentBean) parser.parse(new String[] { "FORBIDDEN" });
        System.out.println(bean.toString());

        bean = (EnumArgumentBean) parser.parse(new String[] { "OPTIONAL", "FORBIDDEN", "REQUIRED" });
        System.out.println(bean.toString());

    }

    public static void testTestBean() {

        Object bean = DefaultCommandLineParser.parse(TestBean.class, new String[] { "-abc", "vvv" });
        System.out.println(bean.toString());

    }

    public static class CommonArgumentTestBean {

        private final List<Integer> argumentList = new ArrayList<Integer>();

        @CommonArgument(maxRequiredCount = RequiredCountInterval.MAX_BOUND)
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

        @CommonArgument(maxRequiredCount = 3)
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

        @SimpleOption(names = "a", dependentOn = { "c" }, incompatibleWith = "abv")
        private boolean s1;

        @SimpleOption(names = "b")
        private boolean s2;

        @ParameterOption(names = "c")
        private boolean s3;

        @ParameterOption(names = "bb", minRequiredCount = 0, maxRequiredCount = 3, parameterRequired = false)
        private Integer someNumberFrom2To10;

        @CommonArgument(minRequiredCount = 0, maxRequiredCount = 3)
        public void setNeco3(String neco3) {
        }

        @Override
        public String toString() {
            return "TestBean [neco=" + neco + ", s1=" + s1 + ", s2=" + s2 + ", s3=" + s3 + ", someNumberFrom2To10="
                    + someNumberFrom2To10 + "]";
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
