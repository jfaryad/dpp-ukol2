package cz.cuni.mff.dpp.impl.parser;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.parser.CommandLineParser;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;

/**
 * 
 * Default {@link CommandLineParser} implementation.
 * 
 * Uses this command line parsing algorithm:
 *
 * 
 * 
 * 
 * @author Tom
 *
 * @param <T> - target bean (bean used for command line information storing) type
 */
public class DefaultCommandLineParser<T> implements CommandLineParser<T> {

    private final Options<T> options;

    public DefaultCommandLineParser(final Options<T> options) {
        super();
        this.options = options;
    }

    /**
     * Connect {@link Options} configuration creation form annotated bean and comman lin parsing together
     * 
     * @param targetBeanClass - annotated bean
     * @param commnadLine - command line from main method
     * @return 
     */
    public static <T> Object parse(final Class<T> targetBeanClass, final String[] commnadLine) {
        final Options<T> options = OptionsFactory.createOptions(targetBeanClass);
        final DefaultCommandLineParser<T> parser = new DefaultCommandLineParser<T>(options);
        return parser.parse(commnadLine);
    }

    /**
     * Parse command line from entry main method. It's excepted that this command line is processed by JVM
     */
    @Override
    public T parse(final String[] commnadLine) {
        return new ParserImpl<T>(options, commnadLine).getTargetBean();
    }

    /**
     * Return option configuration implementation
     */
    @Override
    public Options<T> getOptions() {
        return options;
    }

}
