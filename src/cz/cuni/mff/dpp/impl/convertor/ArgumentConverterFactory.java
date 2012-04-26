package cz.cuni.mff.dpp.impl.convertor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.dpp.api.ArgumentConverter;

public class ArgumentConverterFactory {

    private static final Map<Class<?>, ArgumentConverter<?>> CONVERTER_MAP;

    static {

        Map<Class<?>, ArgumentConverter<?>> temp = new HashMap<Class<?>, ArgumentConverter<?>>();

        ArgumentConverter<Integer> integerConverter = new ArgumentConverter<Integer>() {

            @Override
            public Integer parse(String argument) {
                return Integer.parseInt(argument);
                // todo prelozit vyjimku
            }
        };

        temp.put(int.class, integerConverter);
        temp.put(Integer.class, integerConverter);

        CONVERTER_MAP = Collections.unmodifiableMap(temp);

    }

    public static <T> ArgumentConverter<T> getInstance(Class<T> argumentClazz) {

        if (argumentClazz == null) {
            throw new NullPointerException("Parameter argumentClazz have to be initialized.");
        }

        ArgumentConverter<T> result = (ArgumentConverter<T>) CONVERTER_MAP.get(argumentClazz);

        if (result == null) {
            throw new IllegalStateException("For class: " + argumentClazz.toString()
                    + " exists no default argument converter.");
        }

        return result;
    }

    public static boolean existsInstance(Class<?> argumentClazz) {
        return CONVERTER_MAP.containsKey(argumentClazz);
    }

}
