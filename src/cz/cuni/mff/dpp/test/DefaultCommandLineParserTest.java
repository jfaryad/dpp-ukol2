package cz.cuni.mff.dpp.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import cz.cuni.mff.dpp.annotation.CommonArgument;
import cz.cuni.mff.dpp.annotation.ParameterOption;
import cz.cuni.mff.dpp.annotation.SimpleOption;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.RequiredOccurrenceCountInterval;
import cz.cuni.mff.dpp.api.parser.exception.DependentOptionsException;
import cz.cuni.mff.dpp.api.parser.exception.IncompatibleOptionsException;
import cz.cuni.mff.dpp.api.parser.exception.LongOptionException;
import cz.cuni.mff.dpp.api.parser.exception.OptionParameterFormatException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredCommonArgumentCountException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionCountException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionParameterException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionParameterException;
import cz.cuni.mff.dpp.impl.converter.FileArgumentConverter;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;
import cz.cuni.mff.dpp.impl.parser.DefaultCommandLineParser;

public class DefaultCommandLineParserTest {

    @Test
    public void test1() {

        Test1Bean bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] {});
        assertEquals(new Test1Bean(false, false, null), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-a" });
        assertEquals(new Test1Bean(true, false, null), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-a", "-b" });
        assertEquals(new Test1Bean(true, true, null), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-a", "--be-be" });
        assertEquals(new Test1Bean(true, true, null), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-ab" });
        assertEquals(new Test1Bean(true, true, null), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-ab", "-p", "value" });
        assertEquals(new Test1Bean(true, true, "value"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-ab", "-p=value" });
        assertEquals(new Test1Bean(true, true, "value"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-ab", "--params=value" });
        assertEquals(new Test1Bean(true, true, "value"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-ab", "--params=value", "ca1" });
        assertEquals(new Test1Bean(true, true, "value", "ca1"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-ab", "ca2", "--params=value", "ca1" });
        assertEquals(new Test1Bean(true, true, "value", "ca2", "ca1"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-ab", "ca2", "--params=value", "ca1",
                "ca3" });
        assertEquals(new Test1Bean(true, true, "value", "ca2", "ca1", "ca3"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "--ma" });
        assertEquals(new Test1Bean(true, false, null), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "--ma", "--mb" });
        assertEquals(new Test1Bean(true, true, null), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "--ma", "--mbe-be" });
        assertEquals(new Test1Bean(true, true, null), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "--ma", "--mb", "--mp", "value" });
        assertEquals(new Test1Bean(true, true, "value"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "--ma", "--mb", "--mp=value" });
        assertEquals(new Test1Bean(true, true, "value"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "--ma", "--mb", "--mparams=value" });
        assertEquals(new Test1Bean(true, true, "value"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class,
                new String[] { "--ma", "--mb", "--mparams=value", "ca1" });
        assertEquals(new Test1Bean(true, true, "value", "ca1"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "--ma", "--mb", "ca2", "--mparams=value",
                "ca1" });
        assertEquals(new Test1Bean(true, true, "value", "ca2", "ca1"), bean);

        bean = DefaultCommandLineParser.parse(Test1Bean.class, new String[] { "-ab", "ca2", "--mparams=value", "ca1",
                "ca3" });
        assertEquals(new Test1Bean(true, true, "value", "ca2", "ca1", "ca3"), bean);

    }

    @Test
    public void testCommonArgumentTestBean() {

        Options<CommonArgumentTestBean> options = OptionsFactory.createOptions(CommonArgumentTestBean.class);
        DefaultCommandLineParser<CommonArgumentTestBean> parser = new DefaultCommandLineParser<CommonArgumentTestBean>(
                options);

        CommonArgumentTestBean bean = parser.parse(new String[] {});
        assertEquals(new CommonArgumentTestBean(), bean);

        bean = parser.parse(new String[] { "20" });
        assertEquals(new CommonArgumentTestBean(null, "20"), bean);

        bean = parser.parse(new String[] { "100", "200", "300" });
        assertEquals(new CommonArgumentTestBean(null, "100", "200", "300"), bean);

        bean = parser.parse(new String[] { "--", "100", "200", "300" });
        assertEquals(new CommonArgumentTestBean(null, "100", "200", "300"), bean);

        bean = parser.parse(new String[] { "10", "--", "100", "200", "300" });
        assertEquals(new CommonArgumentTestBean(null, "10", "100", "200", "300"), bean);

        bean = parser.parse(new String[] { "--param", "314", "--", "100", "200", "300" });
        assertEquals(new CommonArgumentTestBean(314, "100", "200", "300"), bean);

        bean = parser.parse(new String[] { "--param", "314", "--", "100", "200", "300", "--param", "314" });
        assertEquals(new CommonArgumentTestBean(314, "100", "200", "300", "--param", "314"), bean);

    }

    @Test
    public void testEnumArgumentBean() {

        Options<EnumArgumentBean> options = OptionsFactory.createOptions(EnumArgumentBean.class);
        DefaultCommandLineParser<EnumArgumentBean> parser = new DefaultCommandLineParser<EnumArgumentBean>(options);

        EnumArgumentBean bean = (EnumArgumentBean) parser.parse(new String[] {});
        assertEquals(new EnumArgumentBean(), bean);
        System.out.println(bean.toString());

        bean = (EnumArgumentBean) parser.parse(new String[] { "FORBIDDEN" });
        assertEquals(new EnumArgumentBean(OptionArgumentObligation.FORBIDDEN), bean);

        bean = (EnumArgumentBean) parser.parse(new String[] { "OPTIONAL", "FORBIDDEN", "REQUIRED" });
        assertEquals(new EnumArgumentBean(OptionArgumentObligation.OPTIONAL, OptionArgumentObligation.FORBIDDEN,
                OptionArgumentObligation.REQUIRED), bean);

    }
    
    private static void assertEquals(Object obj1, Object obj2) {
        assert obj1.equals(obj2);
    }

    @Test(expected = UnexceptedOptionException.class)
    public void testUnexceptedOption() {
        DefaultCommandLineParser.parse(TestBean.class, new String[] { "-x" });
    }

    @Test(expected = UnexceptedOptionParameterException.class)
    public void testUnexceptedOptionParameter() {
        DefaultCommandLineParser.parse(TestBean.class, new String[] { "-b=d" });
    }

    @Test(expected = RequiredOptionParameterException.class)
    public void testRequiredOptionParameter() {
        DefaultCommandLineParser.parse(TestBean.class, new String[] { "--bb" });
    }

    @Test(expected = RequiredOptionCountException.class)
    public void testRequiredOptionCount() {
        DefaultCommandLineParser.parse(TestBean.class, new String[] { "--bb=1", "--bb=1", "--bb=1", "--bb=1" });
    }

    @Test(expected = RequiredCommonArgumentCountException.class)
    public void testRequiredCommonArgumentCount() {
        DefaultCommandLineParser.parse(TestBean.class, new String[] { "a", "b", "c", "a" });
    }

    @Test(expected = OptionParameterFormatException.class)
    public void testOptionParameterFormat() {
        DefaultCommandLineParser.parse(TestBean.class, new String[] { "--bb", "10a" });
    }

    @Test(expected = LongOptionException.class)
    public void testLongOption() {
        DefaultCommandLineParser.parse(TestBean.class, new String[] { "--b" });
    }

    @Test(expected = IncompatibleOptionsException.class)
    public void testIncompatibleOptions() {
        DefaultCommandLineParser.parse(TestBean.class, new String[] { "-a", "-b", "-c", "false" });
    }

    @Test(expected = DependentOptionsException.class)
    public void testDependentOptions() {
        DefaultCommandLineParser.parse(TestBean.class, new String[] { "-a" });
    }

    // =================================================================================================================
    // =================================================================================================================
    // =================================================================================================================

    public static class Test1Bean {

        @SimpleOption(names = "a")
        private boolean a;

        @SimpleOption(names = { "b", "be-be" })
        private boolean b;

        @ParameterOption(names = { "p", "params" })
        private String param;

        private List<String> commonArguments = new ArrayList<String>();

        public Test1Bean(boolean a, boolean b, String param, String... commonArguments) {
            super();
            this.a = a;
            this.b = b;
            this.param = param;
            this.commonArguments = Arrays.asList(commonArguments);
        }

        public Test1Bean() {
            super();
        }

        /**
         * @param a
         *            the a to set
         */
        @SimpleOption(names = "ma")
        public void setA(boolean a) {
            this.a = a;
        }

        /**
         * @param b
         *            the b to set
         */
        @SimpleOption(names = { "mb", "mbe-be" })
        public void setB(boolean b) {
            this.b = b;
        }

        /**
         * @param param
         *            the param to set
         */
        @ParameterOption(names = { "mp", "mparams" })
        public void setParam(String param) {
            this.param = param;
        }

        /**
         * @param commonArguments
         *            the commonArguments to set
         */
        public void setCommonArguments(List<String> commonArguments) {
            this.commonArguments = commonArguments;
        }

        @CommonArgument(maxRequiredCount = 100)
        public void addCommonArgument(String value) {
            commonArguments.add(value);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (a ? 1231 : 1237);
            result = prime * result + (b ? 1231 : 1237);
            result = prime * result + ((commonArguments == null) ? 0 : commonArguments.hashCode());
            result = prime * result + ((param == null) ? 0 : param.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Test1Bean other = (Test1Bean) obj;
            if (a != other.a)
                return false;
            if (b != other.b)
                return false;
            if (commonArguments == null) {
                if (other.commonArguments != null)
                    return false;
            } else if (!commonArguments.equals(other.commonArguments))
                return false;
            if (param == null) {
                if (other.param != null)
                    return false;
            } else if (!param.equals(other.param))
                return false;
            return true;
        }

    }

    // =================================================================================================================
    // =================================================================================================================
    // =================================================================================================================

    public static class CommonArgumentTestBean {

        private List<String> argumentList = new ArrayList<String>();

        @ParameterOption(names = "param")
        private Integer param;

        public CommonArgumentTestBean(Integer param, String... commonArguments) {
            super();
            this.param = param;
            this.argumentList = Arrays.asList(commonArguments);
        }

        public CommonArgumentTestBean() {
        }

        @CommonArgument(maxRequiredCount = RequiredOccurrenceCountInterval.MAX_BOUND)
        public void addArgument(String argument) {
            argumentList.add(argument);
        }

        @Override
        public String toString() {
            return "CommonArgumentTestBean [argumentList=" + argumentList + "]";
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((argumentList == null) ? 0 : argumentList.hashCode());
            result = prime * result + ((param == null) ? 0 : param.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CommonArgumentTestBean other = (CommonArgumentTestBean) obj;
            if (argumentList == null) {
                if (other.argumentList != null)
                    return false;
            } else if (!argumentList.equals(other.argumentList))
                return false;
            if (param == null) {
                if (other.param != null)
                    return false;
            } else if (!param.equals(other.param))
                return false;
            return true;
        }
    }

    // =================================================================================================================
    // =================================================================================================================
    // =================================================================================================================

    public static class EnumArgumentBean {
        private List<OptionArgumentObligation> argumentList = new ArrayList<OptionArgumentObligation>();

        public EnumArgumentBean(OptionArgumentObligation... argumentList) {
            super();
            this.argumentList = Arrays.asList(argumentList);
        }

        public EnumArgumentBean() {
            super();
        }

        @CommonArgument(maxRequiredCount = 3)
        public void addArgument(OptionArgumentObligation argument) {
            argumentList.add(argument);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((argumentList == null) ? 0 : argumentList.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            EnumArgumentBean other = (EnumArgumentBean) obj;
            if (argumentList == null) {
                if (other.argumentList != null)
                    return false;
            } else if (!argumentList.equals(other.argumentList))
                return false;
            return true;
        }
    }

    // =================================================================================================================
    // =================================================================================================================
    // =================================================================================================================
    public static class TestBean {

        @ParameterOption(names = "aaa", argumentConverter = FileArgumentConverter.class)
        private File neco = null;

        @SimpleOption(names = "a", dependentOn = { "c" }, incompatibleWith = "b")
        private boolean s1;

        @SimpleOption(names = "b")
        private boolean s2;

        @ParameterOption(names = "c")
        private boolean s3;

        @ParameterOption(names = "bb", minRequiredCount = 0, maxRequiredCount = 3, parameterRequired = true)
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

}
