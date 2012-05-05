package cz.cuni.mff.dpp.impl.parser;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.parser.CommandLineParser;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;

public class DefaultCommandLineParser<T> implements CommandLineParser<T> {

    private final Options<T> options;

    public DefaultCommandLineParser(final Options<T> options) {
        super();
        this.options = options;
    }

    public static <T> Object parse(final Class<T> targetBeanClass, final String[] commnadLine) {
        final Options<T> options = OptionsFactory.createOptions(targetBeanClass);
        final DefaultCommandLineParser<T> parser = new DefaultCommandLineParser<T>(options);
        return parser.parse(commnadLine);
    }

    @Override
    public T parse(final String[] commnadLine) {
        return new ParserImpl<T>(options, commnadLine).getTargetBean();
    }

    @Override
    public Options<T> getOptions() {
        return options;
    }

}
