package cz.cuni.mff.dpp.impl.parser;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.parser.CommandLineParser;
import cz.cuni.mff.dpp.impl.option.OptionsFactory;

/**
 * 
 * Default {@link CommandLineParser} implementation.
 * 
 * Uses this command line algorithm:
 * 
 * 1. Target bean is create (must have parameterless public constructor)
 * 
 * 2. command line parsing: for every command line element is determined whether it is:
 * 
 * a) short option (starts with - and one character, e.g. -a),
 * 
 * b) united short option (starts with - and contains multiple characters, e.g. -abc)
 * 
 * c) long option (starts with -- a contains more characters, e.g. --output)
 * 
 * d) option with parameter (normal option after character = and parameter, e.g. -f=tmp.txt or --file=tmt.txt or
 * -abf=tmp.txt, in this case option parameter belongs to last short option)
 * 
 * e) option parameter (it is not option format and follows after option with required parameter without this parameter
 * (e.g. without =value))
 * 
 * e) common argument delimiter (--)
 * 
 * f) common argument (everything after -- and elements, that are not options and option parameters)
 * 
 * 3. validation:
 * 
 * In this step is every option, option parameter and common argument validated (e.g. occurrences count, is option
 * permitted and so on...). If some validation fails appropriate exception is thrown.
 * 
 * 4. Target bean is population.
 * 
 * Appropriate methods are invoked, fields are setted. Option parameters and common arguments are converted and
 * validated by costumer validators during this phase.
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
     * Connect {@link Options} configuration creation form annotated bean and comman lin parsing together
     * 
     * @param targetBeanClass
     *            - annotated bean
     * @param commnadLine
     *            - command line from main method
     * @return
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
