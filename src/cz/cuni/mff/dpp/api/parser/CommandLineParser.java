package cz.cuni.mff.dpp.api.parser;

import cz.cuni.mff.dpp.api.Options;

public interface CommandLineParser<T> {

    String COMMON_ARGUMENT_DELIMITER = "--";

    String SHORT_OPTION_PREFIX = "-";

    String LONG_OPTION_PREFIX = "--";

    int SHORT_OPTION_NAME_LENGTH = 1;

    T parse(String[] commnadLine);

    Options<T> getOptions();

}
