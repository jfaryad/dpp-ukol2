package cz.cuni.mff.dpp.impl.option;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.dpp.annotation.ParameterOption;
import cz.cuni.mff.dpp.annotation.SimpleOption;
import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.OptionSetter;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.impl.convertor.ArgumentConverterFactory;
import cz.cuni.mff.dpp.impl.optionsetter.FieldOptionSetter;
import cz.cuni.mff.dpp.impl.optionsetter.MethodOptionSetter;

/**
 * This class contains methods, which create Options objects from different parameters
 * 
 * @author Tom
 * 
 */
public final class OptionsFactory {

    private OptionsFactory() {

    }

    public static Options createOptions(Class<?> beanClass) {
        return new AnnotatedBeanOptionsBuilder(beanClass).getOptionsBuilder();
    }

    private static final class AnnotatedBeanOptionsBuilder {

        private static final Map<Class<?>, Object> DEFAULT_VALUES_MAP;

        static {
            Map<Class<?>, Object> temp = new HashMap<Class<?>, Object>();
            // temp.put(Byte.class, new Byte((byte) 0));
            temp.put(byte.class, new Byte((byte) 0));
            // temp.put(Character.class, new Character('\0'));
            temp.put(char.class, new Character('\0'));
            // temp.put(Short.class, new Short((short) 0));
            temp.put(short.class, new Short((short) 0));
            // temp.put(Integer.class, new Integer(0));
            temp.put(int.class, new Integer(0));
            // temp.put(Long.class, new Long(0));
            temp.put(long.class, new Long(0));

            // temp.put(Float.class, new Float(0));
            temp.put(float.class, new Float(0));
            // temp.put(Double.class, new Double(0));
            temp.put(double.class, new Double(0));

            // temp.put(Boolean.class, Boolean.FALSE);
            temp.put(boolean.class, Boolean.FALSE);
            DEFAULT_VALUES_MAP = Collections.unmodifiableMap(temp);
        }

        private static final Boolean SIMPLE_OPTION_DEFAULT_VALUE = Boolean.TRUE;

        private final OptionsBuilder optionsBuilder;

        private final Class<?> beanClass;

        private AnnotatedBeanOptionsBuilder(Class<?> beanClass) {
            this.beanClass = beanClass;
            this.optionsBuilder = new OptionsBuilder();

            build();

        }

        private void build() {

            optionsBuilder.setTargetBeanClass(beanClass);
            addOptions();

        }

        private void addOptions() {

            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                processAnnotations(new FieldOptionTarget(field));
            }

            Method[] methods = beanClass.getMethods();
            for (Method method : methods) {
                processAnnotations(new MethodOptionTarget(method));
            }

        }

        private void processAnnotations(AbstractOtionTarget optionTarget) {

            if (optionTarget.hasSimpleOption() && optionTarget.hasParameterOption()) {
                throw new MissConfiguratedAnnotationException(
                        "It is not allowed to have ParameterOption and SimpleOption on the same field or method");
            } else if (optionTarget.hasSimpleOption()) {
                processSimpleOption(optionTarget);
            } else if (optionTarget.hasParameterOption()) {
                processParameterOption(optionTarget);
            } else {
                // no option annotation - nothing todo
            }
        }

        private SingleOptionBuilder processSimpleOption(AbstractOtionTarget optionTarget) {

            SimpleOption simpleOption = optionTarget.getSimpleOption();

            String optionNames[] = simpleOption.names();
            checkOptionNames(optionNames);

            checkIsDeclaringClassBoolean(optionTarget.getTargetClass());

            return optionsBuilder.addOption(optionNames)
                    .setArgumentObligation(OptionArgumentObligation.FORBIDDEN)
                    .setDescription(simpleOption.description())
                    .dependentOn(simpleOption.dependentOn())
                    .incompatibleWith(simpleOption.incompatibleWith())
                    .setRequired(false)
                    .setDefaultValue(SIMPLE_OPTION_DEFAULT_VALUE)
                    .setOptionSetter(optionTarget.createOptionSetter());
        }

        private static void checkIsDeclaringClassBoolean(Class<?> targetClass) {
            if (targetClass != boolean.class && targetClass != Boolean.class) {
                throw new MissConfiguratedAnnotationException(
                        "It's not allowed declaration of the field/method parameter with type different from boolean/Boolean");
            }
        }

        private void processParameterOption(AbstractOtionTarget optionTarget) {

            ParameterOption parameterOption = optionTarget.getParameterOption();

            String[] optionNames = parameterOption.names();
            checkOptionNames(optionNames);

            optionTarget.checkTargetObject();

            SingleOptionBuilder builder = optionsBuilder.addOption(optionNames)
                    .setArgumentObligation(translateOptionParameterRequired(parameterOption.parameterRequired()))
                    .setDescription(parameterOption.description())
                    .setArgumentName(parameterOption.argumentName())
                    .dependentOn(parameterOption.dependentOn())
                    .incompatibleWith(parameterOption.incompatibleWith())
                    .setRequired(parameterOption.optionRequired())
                    .setOptionSetter(optionTarget.createOptionSetter());

            ArgumentConverter<?> argumentConverter = createArgumentConverter(parameterOption.argumentConverter(),
                    optionTarget.getTargetClass());
            builder.setArgumentConverter(argumentConverter);

            builder.setDefaultValue(getDefaultValue(parameterOption, argumentConverter));

            // todo maybe check return type of argument converter with target class

        }

        private static Object getDefaultValue(ParameterOption parameterOption, ArgumentConverter<?> argumentConverter) {
            String[] defaultParameter = parameterOption.defaultParameter();
            if (defaultParameter.length == 0) {
                return DEFAULT_VALUES_MAP.get(argumentConverter.getClass());
            } else if (defaultParameter.length == 1) {
                try {
                    return argumentConverter.parse(defaultParameter[0]);
                } catch (Exception e) {
                    throw new MissConfiguratedAnnotationException("Error during converting of the default value: "
                            + defaultParameter[0] + " to the class: " + argumentConverter.getTargetClass(), e);
                }
            } else if (defaultParameter.length > 1) {
                throw new MissConfiguratedAnnotationException(
                        "For 'ParameterOption' annotation can be parameter 'defaultParameter' initialized at most with one value");
            } else {
                throw new IllegalStateException("This should really never happen");
            }
        }

        private ArgumentConverter<?> createArgumentConverter(Class<?> argumentConverterClass, Class<?> targetClass) {
            if (ParameterOption.DEFAULT_ARGUMENT_CONVERTER_CLASS == argumentConverterClass) {
                if (ArgumentConverterFactory.existsInstance(targetClass)) {
                    return ArgumentConverterFactory.getInstance(targetClass);
                } else {
                    throw new MissConfiguratedAnnotationException("For the type: " + targetClass.toString()
                            + " is neither configurated argumentConverter nor it is type with default converter");
                }
            } else {
                try {
                    Constructor<?> constructor = argumentConverterClass.getConstructor();
                    return (ArgumentConverter<?>) constructor.newInstance();
                } catch (Exception e) {
                    // todo it could be good to create for this error new type of the exception
                    throw new MissConfiguratedAnnotationException(
                            "Error during creation argument converter of the class: "
                                    + argumentConverterClass.toString() + ".", e);
                }
            }
        }

        private OptionArgumentObligation translateOptionParameterRequired(boolean parameterRequired) {
            return parameterRequired ? OptionArgumentObligation.REQUIRED : OptionArgumentObligation.OPTIONAL;
        }

        private void checkOptionNames(String[] optionNames) {

            for (String optionName : optionNames) {
                if (optionsBuilder.isExistsOption(optionName)) {
                    throw new MissConfiguratedAnnotationException("There are at least two configurations for option '"
                            + optionName + "'");
                }
            }
        }

        private OptionsBuilder getOptionsBuilder() {
            return optionsBuilder;
        }

        private static abstract class AbstractOtionTarget {

            protected abstract Class<?> getTargetClass();

            protected abstract AccessibleObject getAccessibleObject();

            protected ParameterOption getParameterOption() {
                return getAccessibleObject().getAnnotation(ParameterOption.class);
            }

            protected SimpleOption getSimpleOption() {
                return getAccessibleObject().getAnnotation(SimpleOption.class);
            }

            private boolean hasParameterOption() {
                return null != getParameterOption();
            }

            private boolean hasSimpleOption() {
                return null != getSimpleOption();
            }

            protected abstract void checkTargetObject();

            protected abstract OptionSetter createOptionSetter();

        }

        private static class FieldOptionTarget extends AbstractOtionTarget {

            private final Field field;

            public FieldOptionTarget(Field field) {
                super();
                this.field = field;
            }

            @Override
            protected Class<?> getTargetClass() {
                return field.getType();
            }

            @Override
            protected void checkTargetObject() {
                // todo test for example for final...

            }

            @Override
            protected OptionSetter createOptionSetter() {
                return new FieldOptionSetter(field);
            }

            @Override
            protected AccessibleObject getAccessibleObject() {
                return field;
            }

        }

        private static class MethodOptionTarget extends AbstractOtionTarget {

            private final Method method;

            public MethodOptionTarget(Method method) {
                super();
                this.method = method;
            }

            @Override
            protected Class<?> getTargetClass() {
                return method.getParameterTypes()[0];
            }

            @Override
            protected AccessibleObject getAccessibleObject() {
                return method;
            }

            @Override
            protected void checkTargetObject() {
                // todo have one parameter, returns void and so on...
            }

            @Override
            protected OptionSetter createOptionSetter() {
                return new MethodOptionSetter(method);
            }

        }

    }

    public static class MissConfiguratedAnnotationException extends RuntimeException {

        /**
		 * 
		 */
        private static final long serialVersionUID = 1L;

        public MissConfiguratedAnnotationException() {
            super();
            // TODO Auto-generated constructor stub
        }

        public MissConfiguratedAnnotationException(String message, Throwable cause) {
            super(message, cause);
            // TODO Auto-generated constructor stub
        }

        public MissConfiguratedAnnotationException(String message) {
            super(message);
            // TODO Auto-generated constructor stub
        }

        public MissConfiguratedAnnotationException(Throwable cause) {
            super(cause);
            // TODO Auto-generated constructor stub
        }

    }

}
