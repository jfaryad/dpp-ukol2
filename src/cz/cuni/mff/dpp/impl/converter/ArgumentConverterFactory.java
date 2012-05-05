package cz.cuni.mff.dpp.impl.converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.dpp.api.ArgumentConverter;
import cz.cuni.mff.dpp.api.ArgumentFormatException;

public class ArgumentConverterFactory {

    private static final Map<Class<?>, ArgumentConverter<?>> CONVERTER_MAP;

    static {

        Map<Class<?>, ArgumentConverter<?>> temp = new HashMap<Class<?>, ArgumentConverter<?>>();

        temp.put(Byte.class, new ArgumentConverter<Byte>() {

            @Override
            public Byte parse(String argument) {
                try {
                    return Byte.parseByte(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Byte> getTargetClass() {
                return Byte.class;
            }
        });

        temp.put(byte.class, new ArgumentConverter<Byte>() {

            @Override
            public Byte parse(String argument) {
                try {
                    return Byte.parseByte(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Byte> getTargetClass() {
                return Byte.class;
            }
        });

        temp.put(Short.class, new ArgumentConverter<Short>() {

            @Override
            public Short parse(String argument) {
                try {
                    return Short.parseShort(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Short> getTargetClass() {
                return Short.class;
            }
        });

        temp.put(short.class, new ArgumentConverter<Short>() {

            @Override
            public Short parse(String argument) {
                try {
                    return Short.parseShort(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Short> getTargetClass() {
                return Short.class;
            }
        });

        temp.put(Integer.class, new ArgumentConverter<Integer>() {

            @Override
            public Integer parse(String argument) {
                try {
                    return Integer.parseInt(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Integer> getTargetClass() {
                return Integer.class;
            }
        });

        temp.put(int.class, new ArgumentConverter<Integer>() {

            @Override
            public Integer parse(String argument) {
                try {
                    return Integer.parseInt(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Integer> getTargetClass() {
                return Integer.class;
            }
        });

        temp.put(Long.class, new ArgumentConverter<Long>() {

            @Override
            public Long parse(String argument) {
                try {
                    return Long.parseLong(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Long> getTargetClass() {
                return Long.class;
            }
        });

        temp.put(long.class, new ArgumentConverter<Long>() {

            @Override
            public Long parse(String argument) {
                try {
                    return Long.parseLong(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Long> getTargetClass() {
                return Long.class;
            }
        });

        temp.put(Float.class, new ArgumentConverter<Float>() {

            @Override
            public Float parse(String argument) {
                try {
                    return Float.parseFloat(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Float> getTargetClass() {
                return Float.class;
            }
        });

        temp.put(float.class, new ArgumentConverter<Float>() {

            @Override
            public Float parse(String argument) {
                try {
                    return Float.parseFloat(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Float> getTargetClass() {
                return Float.class;
            }
        });

        temp.put(Double.class, new ArgumentConverter<Double>() {

            @Override
            public Double parse(String argument) {
                try {
                    return Double.parseDouble(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Double> getTargetClass() {
                return Double.class;
            }
        });

        temp.put(double.class, new ArgumentConverter<Double>() {

            @Override
            public Double parse(String argument) {
                try {
                    return Double.parseDouble(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Double> getTargetClass() {
                return Double.class;
            }
        });

        temp.put(Boolean.class, new ArgumentConverter<Boolean>() {

            @Override
            public Boolean parse(String argument) {
                try {
                    return Boolean.parseBoolean(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Boolean> getTargetClass() {
                return Boolean.class;
            }
        });

        temp.put(boolean.class, new ArgumentConverter<Boolean>() {

            @Override
            public Boolean parse(String argument) {
                try {
                    return Boolean.parseBoolean(argument);
                } catch (NumberFormatException nfe) {
                    throw new ArgumentFormatException(nfe, argument, getTargetClass());
                }
            }

            @Override
            public Class<Boolean> getTargetClass() {
                return Boolean.class;
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
                return Character.class;
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

    public static boolean existsDefaultConverter(Class<?> argumentClass) {
        return CONVERTER_MAP.containsKey(argumentClass) || argumentClass.isEnum();
    }

    private static void checkIsChar(String character) {
        if (character == null || character.length() != 1) {
            throw new ArgumentFormatException(character, Character.class);
        }

    }
}
