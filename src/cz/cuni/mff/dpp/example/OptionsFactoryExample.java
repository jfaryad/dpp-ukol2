package cz.cuni.mff.dpp.example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;
import cz.cuni.mff.dpp.impl.optionsetter.FieldOptionSetter;
import cz.cuni.mff.dpp.impl.optionsetter.MethodOptionSetter;

public class OptionsFactoryExample {

    public static void main(String[] args) {

        Options createOptions = OptionsFactory.createOptions(GnuTimeBean.class);

        System.out.println(createOptions);

        Options createOptions2 = OptionsFactory.createOptions(GnuTimeBean2.class);

        System.out.println(createOptions2);

        testFieldOptionSetter();

        testMethodOptionSetter();

    }

    public static void testFieldOptionSetter() {

        System.out.println("======testFieldOptionSetter======");

        try {

            Field field = GnuTimeBean.class.getDeclaredField("append");
            FieldOptionSetter fos = new FieldOptionSetter(field);
            GnuTimeBean bean = new GnuTimeBean();

            System.out.println("append: " + bean.isAppend());
            fos.setOption(bean, true);
            System.out.println("append: " + bean.isAppend());
            fos.setOption(bean, Boolean.FALSE);
            System.out.println("append: " + bean.isAppend());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    public static void testMethodOptionSetter() {

        System.out.println("======testFieldMethodSetter======");

        try {

            Method method = GnuTimeBean.class.getDeclaredMethod("setAppend", boolean.class);
            MethodOptionSetter mos = new MethodOptionSetter(method);
            GnuTimeBean bean = new GnuTimeBean();

            System.out.println("append: " + bean.isAppend());
            mos.setOption(bean, true);
            System.out.println("append: " + bean.isAppend());
            mos.setOption(bean, Boolean.FALSE);
            System.out.println("append: " + bean.isAppend());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

}
