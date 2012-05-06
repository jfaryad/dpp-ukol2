package cz.cuni.mff.dpp.api.parser;

import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.parser.exception.CommandLineParserException;
import cz.cuni.mff.dpp.api.parser.exception.CommonArgumentFormatException;
import cz.cuni.mff.dpp.api.parser.exception.DependentOptionsException;
import cz.cuni.mff.dpp.api.parser.exception.IncompatibleOptionsException;
import cz.cuni.mff.dpp.api.parser.exception.OptionParameterFormatException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredCommonArgumentCountException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionCountException;
import cz.cuni.mff.dpp.api.parser.exception.RequiredOptionParameterException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionException;
import cz.cuni.mff.dpp.api.parser.exception.UnexceptedOptionParameterException;
import cz.cuni.mff.dpp.api.parser.exception.ValidationException;

/**
 * @author Tom
 * 
 * Interface to be implemented by command line parser
 *
 * @param <T> - type target bean (bean used for options storing)
 */
public interface CommandLineParser<T> {

    String COMMON_ARGUMENT_DELIMITER = "--";

    String SHORT_OPTION_PREFIX = "-";

    String LONG_OPTION_PREFIX = "--";

    String OPTION_VALUE_DELIMITER = "=";

    int SHORT_OPTION_NAME_LENGTH = 1;

    /**
     * 
     * @param commnadLine - command line from {@code main} method. I.e. processed by JVM.
     * @return target been, that stores parsed command line
     * 
     * @throws CommandLineParserException and exceptions inherited from it
     * 
     * @see {@link CommonArgumentFormatException}, {@link DependentOptionsException}, 
     * {@link IncompatibleOptionsException}, {@link OptionParameterFormatException}, 
     * {@link RequiredCommonArgumentCountException}, {@link RequiredOptionCountException},
     * {@link RequiredOptionParameterException}, {@link UnexceptedException},
     * {@link UnexceptedOptionException}, {@link UnexceptedOptionParameterException},
     * {@link ValidationException}
     * 
     */
    T parse(String[] commnadLine);

    /**
     * Returns options configuration
     * @return options configuration
     */
    Options<T> getOptions();

}
