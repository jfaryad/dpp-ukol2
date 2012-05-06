package cz.cuni.mff.dpp.impl.converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.ArgumentFormatException;

/**
 * Factory for default converters, i.e. primitive types, wrapper types, enum types and {@link String}
 * @author Tom
 *
 */
public class ArgumentConverterFactory {

    private static final Map<Class<?>, ArgumentConverter<?>> CONVERTER_MAP;

    static {

        Map<Class<?>, ArgumentConverter<?>> temp = new HashMap<Class<?>, ArgumentConverter<?>>();

        temp.put(Byte.class, new ArgumentConverter<Byte>() {

            @Override
            public Byte convert(String argument) {
                try {
                    return Byte.parseByte(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Byte> getTargetClass() {
                return Byte.class;
            }
        });

        temp.put(byte.class, new ArgumentConverter<Byte>() {

            @Override
            public Byte convert(String argument) {
                try {
                    return Byte.parseByte(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Byte> getTargetClass() {
                return Byte.class;
            }
        });

        temp.put(Short.class, new ArgumentConverter<Short>() {

            @Override
            public Short convert(String argument) {
                try {
                    return Short.parseShort(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Short> getTargetClass() {
                return Short.class;
            }
        });

        temp.put(short.class, new ArgumentConverter<Short>() {

            @Override
            public Short convert(String argument) {
                try {
                    return Short.parseShort(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Short> getTargetClass() {
                return Short.class;
            }
        });

        temp.put(Integer.class, new ArgumentConverter<Integer>() {

            @Override
            public Integer convert(String argument) {
                try {
                    return Integer.parseInt(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Integer> getTargetClass() {
                return Integer.class;
            }
        });

        temp.put(int.class, new ArgumentConverter<Integer>() {

            @Override
            public Integer convert(String argument) {
                try {
                    return Integer.parseInt(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Integer> getTargetClass() {
                return Integer.class;
            }
        });

        temp.put(Long.class, new ArgumentConverter<Long>() {

            @Override
            public Long convert(String argument) {
                try {
                    return Long.parseLong(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Long> getTargetClass() {
                return Long.class;
            }
        });

        temp.put(long.class, new ArgumentConverter<Long>() {

            @Override
            public Long convert(String argument) {
                try {
                    return Long.parseLong(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument,this.getClass());
                }
            }

            @Override
            public Class<Long> getTargetClass() {
                return Long.class;
            }
        });

        temp.put(Float.class, new ArgumentConverter<Float>() {

            @Override
            public Float convert(String argument) {
                try {
                    return Float.parseFloat(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Float> getTargetClass() {
                return Float.class;
            }
        });

        temp.put(float.class, new ArgumentConverter<Float>() {

            @Override
            public Float convert(String argument) {
                try {
                    return Float.parseFloat(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Float> getTargetClass() {
                return Float.class;
            }
        });

        temp.put(Double.class, new ArgumentConverter<Double>() {

            @Override
            public Double convert(String argument) {
                try {
                    return Double.parseDouble(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Double> getTargetClass() {
                return Double.class;
            }
        });

        temp.put(double.class, new ArgumentConverter<Double>() {

            @Override
            public Double convert(String argument) {
                try {
                    return Double.parseDouble(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument,this.getClass());
                }
            }

            @Override
            public Class<Double> getTargetClass() {
                return Double.class;
            }
        });

        temp.put(Boolean.class, new ArgumentConverter<Boolean>() {

            @Override
            public Boolean convert(String argument) {
                try {
                    return Boolean.parseBoolean(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Boolean> getTargetClass() {
                return Boolean.class;
            }
        });

        temp.put(boolean.class, new ArgumentConverter<Boolean>() {

            @Override
            public Boolean convert(String argument) {
                try {
                    return Boolean.parseBoolean(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, this.getClass());
                }
            }

            @Override
            public Class<Boolean> getTargetClass() {
                return Boolean.class;
            }
        });

        temp.put(Character.class, new ArgumentConverter<Character>() {

            @Override
            public Character convert(String argument) {
                checkIsChar(argument, this.getClass());
                return argument.charAt(0);
            }

            @Override
            public Class<Character> getTargetClass() {
                return Character.class;
            }
        });

        temp.put(char.class, new ArgumentConverter<Character>() {

            @Override
            public Character convert(String argument) {
                checkIsChar(argument, this.getClass());
                return argument.charAt(0);
            }

            @Override
            public Class<Character> getTargetClass() {
                return Character.class;
            }
        });

        temp.put(String.class, new ArgumentConverter<String>() {

            @Override
            public String convert(String argument) {
                return argument;
            }

            @Override
            public Class<String> getTargetClass() {
                return String.class;
            }
        });

        CONVERTER_MAP = Collections.unmodifiableMap(temp);

    }

    /**
     * Return default argument converter for the specified class
     * @param argumentClass
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> ArgumentConverter<T> getDefaultConverter(Class<T> argumentClass) {

        if (argumentClass == null) {
            throw new IllegalArgumentException("Parameter argumentClazz have to be initialized.");
        }

        ArgumentConverter<T> result = (ArgumentConverter<T>) CONVERTER_MAP.get(argumentClass);

        if (result == null && argumentClass.isEnum()) {

            result = new EnumArgumentConverter(argumentClass);
        }

        if (result == null) {
            throw new IllegalStateException("For class: " + argumentClass.toString()
                    + " exists no default argument converter.");
        }

        return result;
    }

    /**
     * Return {@code true} if default converter for specified type exists, otherwise {@code false}
     * @param argumentClass
     * @return
     */
    public static boolean existsDefaultConverter(Class<?> argumentClass) {
        return CONVERTER_MAP.containsKey(argumentClass) || argumentClass.isEnum();
    }

    private static void checkIsChar(String character, Class<? extends ArgumentConverter<?>> argumentConverterClass) {
        if (character == null || character.length() != 1) {
            throw new ArgumentFormatException(character, argumentConverterClass);
        }

    }
}
