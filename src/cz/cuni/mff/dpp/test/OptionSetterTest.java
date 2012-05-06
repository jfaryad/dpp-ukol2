package cz.cuni.mff.dpp.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

import cz.cuni.mff.dpp.example.GnuTimeBean;
import cz.cuni.mff.dpp.impl.optionsetter.FieldOptionSetter;
import cz.cuni.mff.dpp.impl.optionsetter.MethodOptionSetter;

public class OptionSetterTest {

    @Test
    public void testFieldOptionSetter() throws NoSuchFieldException, SecurityException {
        final Field field = GnuTimeBean.class.getDeclaredField("append");
        final FieldOptionSetter fos = new FieldOptionSetter(field);
        final GnuTimeBean bean = new GnuTimeBean();

        assert bean.isAppend() == false;

        fos.setOption(bean, true);
        assert bean.isAppend() == true;

        fos.setOption(bean, Boolean.FALSE);
        assert bean.isAppend() == false;
    }

    @Test
    public void testMethodOptionSetter() throws NoSuchMethodException, SecurityException {
        final Method method = GnuTimeBean.class.getDeclaredMethod("setAppend", boolean.class);
        final MethodOptionSetter mos = new MethodOptionSetter(method);
        final GnuTimeBean bean = new GnuTimeBean();

        assert bean.isAppend() == false;

        mos.setOption(bean, true);
        assert bean.isAppend() == true;

        mos.setOption(bean, Boolean.FALSE);
        assert bean.isAppend() == false;
    }

}
