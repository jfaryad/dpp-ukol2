package cz.cuni.mff.dpp.impl.convertor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.dpp.api.ArgumentConverter;

public class ArgumentConverterFactory {

    private static final Map<Class<?>, ArgumentConverter<?>> CONVERTER_MAP;

    static {

        Map<Class<?>, ArgumentConverter<?>> temp = new HashMap<Class<?>, ArgumentConverter<?>>();

        temp.put(Byte.class, new ArgumentConverter<Byte>() {

            @Override
            public Byte parse(String argument) {
                return Byte.parseByte(argument);
            }

            @Override
            public Class<Byte> getTargetClass() {
                return Byte.class;
            }
        });

        temp.put(byte.class, new ArgumentConverter<Byte>() {

            @Override
            public Byte parse(String argument) {
                return Byte.parseByte(argument);
            }

            @Override
            public Class<Byte> getTargetClass() {
                return byte.class;
            }
        });

        temp.put(Short.class, new ArgumentConverter<Short>() {

            @Override
            public Short parse(String argument) {
                return Short.parseShort(argument);
            }

            @Override
            public Class<Short> getTargetClass() {
                return Short.class;
            }
        });

        temp.put(short.class, new ArgumentConverter<Short>() {

            @Override
            public Short parse(String argument) {
                return Short.parseShort(argument);
            }

            @Override
            public Class<Short> getTargetClass() {
                return short.class;
            }
        });

        temp.put(Integer.class, new ArgumentConverter<Integer>() {

            @Override
            public Integer parse(String argument) {
                return Integer.parseInt(argument);
            }

            @Override
            public Class<Integer> getTargetClass() {
                return Integer.class;
            }
        });

        temp.put(int.class, new ArgumentConverter<Integer>() {

            @Override
            public Integer parse(String argument) {
                return Integer.parseInt(argument);
            }

            @Override
            public Class<Integer> getTargetClass() {
                return int.class;
            }
        });

        temp.put(Long.class, new ArgumentConverter<Long>() {

            @Override
            public Long parse(String argument) {
                return Long.parseLong(argument);
            }

            @Override
            public Class<Long> getTargetClass() {
                return Long.class;
            }
        });

        temp.put(long.class, new ArgumentConverter<Long>() {

            @Override
            public Long parse(String argument) {
                return Long.parseLong(argument);
            }

            @Override
            public Class<Long> getTargetClass() {
                return long.class;
            }
        });

        temp.put(Float.class, new ArgumentConverter<Float>() {

            @Override
            public Float parse(String argument) {
                return Float.parseFloat(argument);
            }

            @Override
            public Class<Float> getTargetClass() {
                return Float.class;
            }
        });

        temp.put(float.class, new ArgumentConverter<Float>() {

            @Override
            public Float parse(String argument) {
                return Float.parseFloat(argument);
            }

            @Override
            public Class<Float> getTargetClass() {
                return float.class;
            }
        });

        temp.put(Double.class, new ArgumentConverter<Double>() {

            @Override
            public Double parse(String argument) {
                return Double.parseDouble(argument);
            }

            @Override
            public Class<Double> getTargetClass() {
                return Double.class;
            }
        });

        temp.put(double.class, new ArgumentConverter<Double>() {

            @Override
            public Double parse(String argument) {
                return Double.parseDouble(argument);
            }

            @Override
            public Class<Double> getTargetClass() {
                return double.class;
            }
        });

        temp.put(Boolean.class, new ArgumentConverter<Boolean>() {

            @Override
            public Boolean parse(String argument) {
                return Boolean.parseBoolean(argument);
            }

            @Override
            public Class<Boolean> getTargetClass() {
                return Boolean.class;
            }
        });

        temp.put(boolean.class, new ArgumentConverter<Boolean>() {

            @Override
            public Boolean parse(String argument) {
                return Boolean.parseBoolean(argument);
            }

            @Override
            public Class<Boolean> getTargetClass() {
                return boolean.class;
            }
        });

        temp.put(Character.class, new ArgumentConverter<Character>() {

            @Override
            public Character parse(String argument) {
                checkIsChar(argument);
                return argument.charAt(0);
            }

            @Override
            public Class<Character> getTargetClass() {
                return Character.class;
            }
        });

        temp.put(char.class, new ArgumentConverter<Character>() {

            @Override
            public Character parse(String argument) {
                checkIsChar(argument);
                return argument.charAt(0);
            }

            @Override
            public Class<Character> getTargetClass() {
                return char.class;
            }
        });

        temp.put(String.class, new ArgumentConverter<String>() {

            @Override
            public String parse(String argument) {
                return argument;
            }

            @Override
            public Class<String> getTargetClass() {
                return String.class;
            }
        });

        CONVERTER_MAP = Collections.unmodifiableMap(temp);

    }

    public static <T> ArgumentConverter<T> getInstance(Class<T> argumentClass) {

        if (argumentClass == null) {
            throw new NullPointerException("Parameter argumentClazz have to be initialized.");
        }

        ArgumentConverter<T> result = (ArgumentConverter<T>) CONVERTER_MAP.get(argumentClass);

        if (result == null && argumentClass.isEnum()) {
            // todo - replace this dirty code
            result = new EnumArgumentConverter(argumentClass);
        }

        if (result == null) {
            throw new IllegalStateException("For class: " + argumentClass.toString()
                    + " exists no default argument converter.");
        }

        return result;
    }

    public static boolean existsInstance(Class<?> argumentClass) {
        return CONVERTER_MAP.containsKey(argumentClass) || argumentClass.isEnum();
    }

    private static void checkIsChar(String character) {
        if (character == null || character.length() != 1) {
            throw new IllegalArgumentException("String '" + character
                    + "' is no character, which have to have lenght 1 char");
        }

    }

}
