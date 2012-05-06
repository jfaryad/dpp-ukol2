package cz.cuni.mff.dpp.impl.parser;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.parser.CommandLineParser;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;

/**
 * 
 * Default {@link CommandLineParser} implementation.
 * 
 * Uses this command line algorithm: <br>
 * 1. Target bean is created (must have parameterless public constructor) <br>
 * <br>
 * 2. command line parsing: for every command line element, it is determined whether it is: <br>
 * a) short option (starts with - and one character, e.g. -a), <br>
 * b) united short option (starts with - and contains multiple characters, e.g. -abc) <br>
 * c) long option (starts with -- a contains more characters, e.g. --output) <br>
 * d) option with parameter (normal option after character = and parameter, e.g. -f=tmp.txt or --file=tmt.txt or
 * -abf=tmp.txt, in this case option parameter belongs to last short option) <br>
 * e) option parameter (it is not option format and follows after option with required parameter without this parameter
 * (e.g. without =value)) <br>
 * e) common argument delimiter (--) <br>
 * f) common argument (everything after -- and elements, that are not options and option parameters) <br>
 * <br>
 * 3. validation: <br>
 * In this step every option, option parameter and common argument are validated (e.g. occurrences count, if option is
 * permitted and so on...). If some validation fails, an appropriate exception is thrown. <br>
 * <br>
 * 4. Target bean is populated. <br>
 * Appropriate methods are invoked, fields are set. Option parameters and common arguments are converted and validated
 * by costumer validators during this phase.
 * 
 * 
 * @author Tom
 * 
 * @param <T>
 *            - target bean (bean used for command line information storing) type
 */
public class DefaultCommandLineParser<T> implements CommandLineParser<T> {

    private final Options<T> options;

    public DefaultCommandLineParser(final Options<T> options) {
        super();
        this.options = options;
    }

    /**
     * Create a {@link Options} configuration creation from the annotated bean and parse the command line
     * 
     * @param targetBeanClass
     *            - annotated bean
     * @param commnadLine
     *            - command line from main method
     * @return a populated bean
     */
    public static <T> T parse(final Class<T> targetBeanClass, final String[] commnadLine) {
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
