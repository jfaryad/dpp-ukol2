package cz.cuni.mff.dpp.impl.option;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.dpp.annotation.CommonArgument;
import cz.cuni.mff.dpp.annotation.OptionsDefinition;
import cz.cuni.mff.dpp.annotation.ParameterOption;
import cz.cuni.mff.dpp.annotation.SimpleOption;
import cz.cuni.mff.dpp.annotation.Validator;
import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.OptionArgumentObligation;
import cz.cuni.mff.dpp.api.OptionSetter;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.RequiredOccurrenceCountInterval;
import cz.cuni.mff.dpp.api.parser.CommandLineParser;
import cz.cuni.mff.dpp.impl.converter.ArgumentConverterFactory;
import cz.cuni.mff.dpp.impl.converter.DummyArgumentConverter;
import cz.cuni.mff.dpp.impl.optionsetter.FieldOptionSetter;
import cz.cuni.mff.dpp.impl.optionsetter.MethodOptionSetter;
import cz.cuni.mff.dpp.impl.validator.ValidatorFactory;

/**
 * This class contains method, that creates {@link Options} object from annotated bean.
 * 
 * @author Tom
 * 
 */
public final class OptionsFactory {

    private OptionsFactory() {
    }

    /**
     * Builds {@link Options} configuration object from annotated bean, that is used in {@link CommandLineParser}. Go to
     * {@link Errors} to see possible errors during annotation bean processing
     * 
     * @param targetBeanClass
     *            - annotated bean, that should be used to store command line informations
     * @return {@link Options} configuration object
     * 
     */
    public static <T> Options<T> createOptions(final Class<T> targetBeanClass) {
        return new AnnotatedBeanOptionsBuilder<T>(targetBeanClass).getOptionsBuilder();
    }

    // =============================================================================================================
    // =============================================================================================================
    // =============================================================================================================

    private static final class AnnotatedBeanOptionsBuilder<T> {

        private static final Map<Class<?>, Object> DEFAULT_VALUES_MAP;

        static {
            final Map<Class<?>, Object> temp = new HashMap<Class<?>, Object>();
            temp.put(byte.class, new Byte((byte) 0));
            temp.put(char.class, new Character('\0'));
            temp.put(short.class, new Short((short) 0));
            temp.put(int.class, new Integer(0));
            temp.put(long.class, new Long(0));

            temp.put(float.class, new Float(0));
            temp.put(double.class, new Double(0));

            temp.put(boolean.class, Boolean.FALSE);
            DEFAULT_VALUES_MAP = Collections.unmodifiableMap(temp);
        }

        private static final Map<Class<?>, Class<?>> PRIMITIVE_2_WRAPPER_MAP;

        static {
            final Map<Class<?>, Class<?>> temp = new HashMap<Class<?>, Class<?>>();
            temp.put(byte.class, Byte.class);
            temp.put(char.class, Character.class);
            temp.put(short.class, Short.class);
            temp.put(int.class, Integer.class);
            temp.put(long.class, Long.class);
            temp.put(float.class, Float.class);
            temp.put(double.class, Double.class);
            temp.put(boolean.class, Boolean.class);
            PRIMITIVE_2_WRAPPER_MAP = Collections.unmodifiableMap(temp);
        }

        private static final Boolean SIMPLE_OPTION_DEFAULT_VALUE = Boolean.TRUE;

        private static final RequiredOccurrenceCountInterval SIMPLE_OPTION_REQUIRED_COUNT_INTERVAL =
                new RequiredOccurrenceCountInterval(0, 1);

        private final OptionsBuilder<T> optionsBuilder;

        private final Class<T> beanClass;

        private boolean isCommonArgumentConfigured = false;

        private AnnotatedBeanOptionsBuilder(final Class<T> beanClass) {
            this.beanClass = beanClass;
            this.optionsBuilder = new OptionsBuilder<T>();

            build();
        }

        private void build() {
            configOptions();
            processOptionsDefinitionAnnotation();
            addSingleOptions();
        }

        private void configOptions() {
            optionsBuilder.setTargetBeanClass(beanClass);
        }

        private void addSingleOptions() {

            final Field[] fields = beanClass.getDeclaredFields();
            for (final Field field : fields) {
                processAnnotations(new FieldOptionTarget(field));
            }

            final Method[] methods = beanClass.getDeclaredMethods();
            for (final Method method : methods) {
                processAnnotations(new MethodOptionTarget(method));
            }

        }

        private void processOptionsDefinitionAnnotation() {
            final OptionsDefinition optionsDefinition = beanClass.getAnnotation(OptionsDefinition.class);
            if (optionsDefinition != null) {
                optionsBuilder.setDescription(optionsDefinition.description());
                optionsBuilder.setName(optionsDefinition.name());
                optionsBuilder.setUsageLine(optionsDefinition.usage());
            }
        }

        private void processAnnotations(final AbstractOptionTarget optionTarget) {

            if (optionTarget.hasMultipleAnnotations()) {
                Errors.MULTIPLE_ANNOTATIONS.throwException(optionTarget.getMemberName(), optionTarget.getTargetName());
            } else if (optionTarget.hasSimpleOption()) {
                optionTarget.checkTarget();
                processSimpleOption(optionTarget);
            } else if (optionTarget.hasParameterOption()) {
                optionTarget.checkTarget();
                processParameterOption(optionTarget);
            } else if (optionTarget.hasCommonArgument()) {
                optionTarget.checkTarget();
                processCommonArgument(optionTarget);
            } else {
                // no option annotation - nothing to do
            }
        }

        private void processCommonArgument(final AbstractOptionTarget optionTarget) {

            checkMultipleCommonArgument();

            optionsBuilder.setCommonArgumentSetter(optionTarget.createOptionSetter());
            final CommonArgument commonArgument = optionTarget.getCommonArgument();
            final ArgumentConverter<?> commonArgumentConverter = getArgumentConverter(
                    commonArgument.argumentConverter(), optionTarget.getTargetClass());
            optionsBuilder.setCommonArgumentConverter(commonArgumentConverter);

            final RequiredOccurrenceCountInterval interval = createRequiredCountInterval(
                    commonArgument.minRequiredCount(),
                    commonArgument.maxRequiredCount());

            optionsBuilder.setCommonArgumentRequiredCountInterval(interval);
        }

        private void checkMultipleCommonArgument() {
            if (isCommonArgumentConfigured) {
                Errors.MULTIPLE_COMMON_ARGUMENT.throwException();
            } else {
                isCommonArgumentConfigured = true;
            }
        }

        private SingleOptionBuilder processSimpleOption(final AbstractOptionTarget optionTarget) {

            final SimpleOption simpleOption = optionTarget.getSimpleOption();

            final String optionNames[] = simpleOption.names();
            checkOptionNames(optionNames);

            checkIsDeclaringClassBoolean(optionTarget.getTargetClass());

            return optionsBuilder.addOption(optionNames)
                    .setArgumentObligation(OptionArgumentObligation.FORBIDDEN)
                    .setDescription(simpleOption.description())
                    .dependentOn(simpleOption.dependentOn())
                    .incompatibleWith(simpleOption.incompatibleWith())
                    .setDefaultValue(SIMPLE_OPTION_DEFAULT_VALUE)
                    .setOptionSetter(optionTarget.createOptionSetter())
                    .setRequiredCountInterval(SIMPLE_OPTION_REQUIRED_COUNT_INTERVAL);
        }

        private static void checkIsDeclaringClassBoolean(final Class<?> targetClass) {
            if (targetClass != boolean.class && targetClass != Boolean.class) {
                Errors.SIMPLE_OPTION_BAD_TARGET_TYPE.throwException();
            }
        }

        private void processParameterOption(final AbstractOptionTarget optionTarget) {

            final ParameterOption parameterOption = optionTarget.getParameterOption();

            final String[] optionNames = parameterOption.names();
            checkOptionNames(optionNames);

            final SingleOptionBuilder builder = optionsBuilder
                    .addOption(optionNames)
                    .setArgumentObligation(translateOptionParameterRequired(parameterOption.parameterRequired()))
                    .setDescription(parameterOption.description())
                    .setArgumentName(parameterOption.argumentName())
                    .dependentOn(parameterOption.dependentOn())
                    .incompatibleWith(parameterOption.incompatibleWith())
                    .setOptionSetter(optionTarget.createOptionSetter())
                    .setRequiredCountInterval(createRequiredCountInterval(parameterOption.minRequiredCount(),
                            parameterOption.maxRequiredCount()));

            final ArgumentConverter<?> argumentConverter = getArgumentConverter(parameterOption.argumentConverter(),
                    optionTarget.getTargetClass());
            builder.setArgumentConverter(argumentConverter);

            addValidatorsToSingleOptionBuilder(parameterOption, builder);

            builder.setDefaultValue(getDefaultValue(parameterOption, argumentConverter, optionTarget.getTargetClass()));
        }

        private static RequiredOccurrenceCountInterval createRequiredCountInterval(final int min, final int max) {

            if (!RequiredOccurrenceCountInterval.isValid(min, max)) {
                Errors.BAD_REQUIRED_COUNT_INTERVAL.throwException(min, max);
            }

            return new RequiredOccurrenceCountInterval(min, max);
        }

        private static void addValidatorsToSingleOptionBuilder(
                final ParameterOption parameterOption,
                final SingleOptionBuilder builder) {

            for (final Validator validator : parameterOption.validators()) {
                builder.addValidator(ValidatorFactory.createValidator(
                        validator.validatorClass(),
                        builder.getArgumentConverter(),
                        validator.constructorParams()));
            }
        }

        private static Object getDefaultValue(final ParameterOption parameterOption,
                final ArgumentConverter<?> argumentConverter, final Class<?> setterTargetClass) {

            final String[] defaultParameter = parameterOption.defaultParameter();
            if (defaultParameter.length == 0) {
                return DEFAULT_VALUES_MAP.get(setterTargetClass);
            } else if (defaultParameter.length == 1) {
                return convertDefaultValue(defaultParameter[0], argumentConverter);
            } else if (defaultParameter.length > 1) {
                Errors.DEFAULT_VALUE_BAD_INITIALIZATION.throwException();
            }
            throw new IllegalStateException("This should really never happen");
        }

        private static Object convertDefaultValue(final String defaultParameter,
                final ArgumentConverter<?> argumentConverter) {
            try {
                return argumentConverter.convert(defaultParameter);
            } catch (final Exception e) {
                Errors.DEFAULT_VALUE_CONVERTING_ERROR.throwException(e, defaultParameter,
                        argumentConverter.getTargetClass());
            }
            throw new IllegalStateException("This should really never happen");
        }

        private ArgumentConverter<?> getArgumentConverter(
                final Class<? extends ArgumentConverter<?>> argumentConverterClass,
                final Class<?> targetClass) {

            if (DummyArgumentConverter.class == argumentConverterClass) {
                return getDefaultArgumentConverter(targetClass);
            } else {
                checkArgumentConverter(argumentConverterClass, targetClass);
                return createInstance(argumentConverterClass);
            }
        }

        private ArgumentConverter<?> getDefaultArgumentConverter(final Class<?> targetClass) {
            if (ArgumentConverterFactory.existsDefaultConverter(targetClass)) {
                return ArgumentConverterFactory.getDefaultConverter(targetClass);
            } else {
                Errors.ARGUMENT_CONVERTER_NOT_SPECIFIED.throwException(targetClass.toString());
            }
            throw new IllegalStateException("This should really never happen");
        }

        private static void checkArgumentConverter(final Class<? extends ArgumentConverter<?>> argumentConverterClass,
                final Class<?> targetClass) {

            Method method = null;
            try {
                method = argumentConverterClass.getMethod("parse", String.class);
            } catch (final Exception e) {
                Errors.COMMON_ERROR.throwException(e);
            }

            final Class<?> returnType = method.getReturnType();
            if (!targetClass.isAssignableFrom(returnType) && !isSamePrimitiveAndWrapperType(targetClass, returnType)) {
                Errors.ARGUMENT_CONVERTERBAD_RETURN_TYPE.throwException(argumentConverterClass.toString(),
                        returnType.toString(), targetClass.toString());
            }
        }

        private static boolean isSamePrimitiveAndWrapperType(final Class<?> primitiveClass, final Class<?> wrapperClass) {
            // this is approximation, in fact it is possible to assign primitive type from same or lesser type
            return primitiveClass.isPrimitive() && PRIMITIVE_2_WRAPPER_MAP.get(primitiveClass) == wrapperClass;
        }

        private ArgumentConverter<?> createInstance(final Class<? extends ArgumentConverter<?>> argumentConverterClass) {

            Constructor<? extends ArgumentConverter<?>> constructor = null;
            try {
                constructor = argumentConverterClass.getConstructor();
            } catch (final Exception e) {
                Errors.ARGUMENT_CONVERTER_CONSTRUCTOR.throwException(e, argumentConverterClass.toString());
            }

            ArgumentConverter<?> instance = null;

            try {
                instance = constructor.newInstance();
            } catch (final Exception e) {
                Errors.ARGUMENT_CONVERTER_CREATION_ERROR.throwException(e, argumentConverterClass.toString());
            }

            return instance;

        }

        private OptionArgumentObligation translateOptionParameterRequired(final boolean parameterRequired) {
            return parameterRequired ? OptionArgumentObligation.REQUIRED : OptionArgumentObligation.OPTIONAL;
        }

        private void checkOptionNames(final String[] optionNames) {

            for (final String optionName : optionNames) {

                if (!isValidOptionName(optionName)) {
                    Errors.INVALID_OPTION_NAME.throwException(optionName);
                }

                if (optionsBuilder.isExistsOption(optionName)) {
                    Errors.MULTIPLE_CONFIGURATION_FOR_ONE_OPTION.throwException(optionName);
                }
            }
        }

        private boolean isValidOptionName(String optionName) {
            return !optionName.contains(CommandLineParser.OPTION_VALUE_DELIMITER)
                    && !optionName.startsWith(CommandLineParser.SHORT_OPTION_PREFIX);
        }

        private OptionsBuilder<T> getOptionsBuilder() {
            return optionsBuilder;
        }

        // =============================================================================================================
        // =============================================================================================================
        // =============================================================================================================

        private static abstract class AbstractOptionTarget {

            protected abstract Class<?> getTargetClass();

            protected abstract AccessibleObject getAccessibleObject();

            protected ParameterOption getParameterOption() {
                return getAccessibleObject().getAnnotation(ParameterOption.class);
            }

            protected SimpleOption getSimpleOption() {
                return getAccessibleObject().getAnnotation(SimpleOption.class);
            }

            protected CommonArgument getCommonArgument() {
                return getAccessibleObject().getAnnotation(CommonArgument.class);
            }

            private boolean hasParameterOption() {
                return getParameterOption() != null;
            }

            private boolean hasSimpleOption() {
                return getSimpleOption() != null;
            }

            private boolean hasCommonArgument() {
                return getCommonArgument() != null;
            }

            private boolean hasMultipleAnnotations() {
                int count = 0;
                if (hasCommonArgument()) {
                    count++;
                }
                if (hasParameterOption()) {
                    count++;
                }
                if (hasSimpleOption()) {
                    count++;
                }
                return count > 1;
            }

            protected abstract OptionSetter createOptionSetter();

            protected abstract String getMemberName();

            protected abstract String getTargetName();

            protected abstract int getModifiers();

            protected abstract void checkTargetMember();

            protected void checkTarget() {

                final int modifiers = getModifiers();
                if (Modifier.isStatic(modifiers)) {
                    Errors.MEMBER_STATIC.throwException(getTargetName());
                }

                checkTargetMember();
            }

        }

        private static class FieldOptionTarget extends AbstractOptionTarget {

            private final Field field;

            public FieldOptionTarget(final Field field) {
                super();
                this.field = field;
            }

            @Override
            protected Class<?> getTargetClass() {
                return field.getType();
            }

            @Override
            protected OptionSetter createOptionSetter() {
                return new FieldOptionSetter(field);
            }

            @Override
            protected AccessibleObject getAccessibleObject() {
                return field;
            }

            @Override
            protected String getMemberName() {
                return "field";
            }

            @Override
            protected String getTargetName() {
                return field.getName();
            }

            @Override
            protected int getModifiers() {
                return field.getModifiers();
            }

            @Override
            protected void checkTargetMember() {
                if (Modifier.isFinal(getModifiers())) {
                    Errors.MEMBER_FINAL.throwException(getTargetName());
                }
            }
        }

        private static class MethodOptionTarget extends AbstractOptionTarget {

            private final Method method;

            public MethodOptionTarget(final Method method) {
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
            protected OptionSetter createOptionSetter() {
                return new MethodOptionSetter(method);
            }

            @Override
            protected String getMemberName() {
                return "method";
            }

            @Override
            protected String getTargetName() {
                return method.getName();
            }

            @Override
            protected int getModifiers() {
                return method.getModifiers();
            }

            @Override
            protected void checkTargetMember() {
                if (method.getReturnType() != void.class || method.getParameterTypes().length != 1) {
                    Errors.METHOD_BAD_SIGNATURE.throwException(getTargetName());
                }
            }
        }
    }

    /**
     * 
     * Type of errors, that can happen during annotation processing
     * 
     * @author Tom
     * 
     */
    private static enum Errors {

        MULTIPLE_ANNOTATIONS("Usage of multiple annotations on the same class member (%s %s) is not allowed."),
        MULTIPLE_COMMON_ARGUMENT("Multiple usage of the annotation @CommonAgument is not allowed."),
        ARGUMENT_CONVERTER_NOT_SPECIFIED(
                "No default converter exists for the type: %s and no custom converter is supplied."),
        ARGUMENT_CONVERTER_CREATION_ERROR("An exception was caught while creating a converter with class: %s."),
        ARGUMENT_CONVERTER_CONSTRUCTOR(
                "An exception was caught while obtaining a public parameterless constructor of the converter with class: %s."),
        ARGUMENT_CONVERTERBAD_RETURN_TYPE(
                "Converter with class: %s converts to the type %s, but it is not possible to assign to the type %s"),
        COMMON_ERROR("An unexcepted error was thrown during the processing of the annotated bean."),
        SIMPLE_OPTION_BAD_TARGET_TYPE(
                "Only boolean/Boolean fieldsor methods with one boolean/Boolean parameter can be tagged with the annotation @SimpleOption."),
        DEFAULT_VALUE_BAD_INITIALIZATION(
                "At most one value is allowed for 'defaultParameter' in the 'ParameterOption' annotation."),
        DEFAULT_VALUE_CONVERTING_ERROR("Error while converting the default value: %s to the class: %s."),
        MULTIPLE_CONFIGURATION_FOR_ONE_OPTION("There are at least two configurations for option: %s"),
        MEMBER_STATIC(
                "Static field/method: %s cannot be tagged with @SimpleOption, @ParameterOption or @CommonArgument."),
        MEMBER_FINAL(
                "Final field/method: %s cannot be tagged with @SimpleOption, @ParameterOption or @CommonArgument.."),
        METHOD_BAD_SIGNATURE("Tagged method: %s must have void return type and exactly one parameter."),
        BAD_REQUIRED_COUNT_INTERVAL("Bad bounds (%d, %d) in 'minRequiredCount' and 'maxRequiredCount' parameters."),
        INVALID_OPTION_NAME("Option name %s is invalid.");

        private final String errorText;

        private Errors(final String errorText) {
            this.errorText = errorText;
        }

        private void throwException(final Object... args) {
            throw new MissConfiguratedAnnotationException(this, String.format(errorText, args));
        }

        private void throwException(final Exception exception, final Object... args) {
            throw new MissConfiguratedAnnotationException(this, String.format(errorText, args), exception);
        }

    }

    public static class MissConfiguratedAnnotationException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private final Errors error;

        public MissConfiguratedAnnotationException(final Errors error, final String message, final Throwable cause) {
            super(message, cause);
            this.error = error;
        }

        public MissConfiguratedAnnotationException(final Errors error, final String message) {
            super(message);
            this.error = error;
        }

        public MissConfiguratedAnnotationException(final Errors error, final Throwable cause) {
            super(cause);
            this.error = error;
        }

        public Errors getError() {
            return error;
        }

    }
}
