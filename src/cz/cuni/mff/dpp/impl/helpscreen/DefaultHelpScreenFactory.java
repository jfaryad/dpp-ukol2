package cz.cuni.mff.dpp.impl.helpscreen;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import cz.cuni.mff.dpp.api.HelpScreenFactory;
import cz.cuni.mff.dpp.api.Options;
import cz.cuni.mff.dpp.api.SingleOption;

/**
 * Default implementation of {@link HelpScreenFactory} <br>
 * Options are displayed in two columns: names and description. The description column is aligned at 4 spaces after the
 * end of the longest names column. Other rows are padded with spaces. The output looks like this:<br>
 * 
 * <pre>
 * programName - program description
 * 
 * Usage: programName [options] [arguments]
 * 
 * Options:
 *     -a, --firt-option                firt option description
 *     -b, --second-option              second option description
 *     -c ARG, --parameter-option=ARG   third option description
 * </pre>
 * 
 * @author jakub
 * 
 */
public class DefaultHelpScreenFactory implements HelpScreenFactory {

    private final static String NEWLINE = "\n";
    private final static String DESCRIPTION_SEPARATOR = " - ";
    private final static String SHORT_OPTION_PREFIX = "-";
    private final static String LONG_OPTION_PREFIX = "--";
    private final static String OPTION_NAMES_DELIMITER = ", ";
    private final static String PADDING_SPACE = " ";
    private final static String DEFAULT_OPTION_INDENTATION = "    ";
    private final static String SHORT_OPTION_ARGUMENT_DELIMITER = " ";
    private final static String LONG_OPTION_ARGUMENT_DELIMITER = "=";
    private final static String OPTION_SECTION_HEADER = "Options:";
    private final static String USAGE_SECTION_HEADER = "Usage: ";

    private final StringBuilder helpScreen = new StringBuilder();
    private final Options<?> options;
    private final Set<SingleOption> singleOptions = new TreeSet<SingleOption>(new SingleOptionComparator());
    private final StringBuilder[] optionNames;
    private final String[] optionDescriptions;;

    public DefaultHelpScreenFactory(final Options<?> options) {
        this.options = options;
        this.singleOptions.addAll(options.getOptions());
        this.optionNames = new StringBuilder[singleOptions.size()];
        this.optionDescriptions = new String[singleOptions.size()];
    }

    @Override
    public String generateHelpScreen() {
        helpScreen.append(descriptionLine());
        helpScreen.append(NEWLINE);
        helpScreen.append(NEWLINE);
        helpScreen.append(USAGE_SECTION_HEADER);
        helpScreen.append(usageLine());
        helpScreen.append(NEWLINE);
        helpScreen.append(NEWLINE);
        helpScreen.append(OPTION_SECTION_HEADER);
        helpScreen.append(NEWLINE);
        appendOptions();
        helpScreen.append(NEWLINE);
        return helpScreen.toString();
    }

    private void appendOptions() {
        parseOptionNamesAndDescriptions();
        padNamesColumnToSameWidth();
        appendOptionNamesWithDescriptions();
    }

    private void parseOptionNamesAndDescriptions() {
        int optionIndex = 0;
        for (final SingleOption option : singleOptions) {
            optionNames[optionIndex] = optionNamesColumn(option);
            optionDescriptions[optionIndex] = option.getDescription();
            optionIndex++;
        }
    }

    private void appendOptionNamesWithDescriptions() {
        for (int optionIndex = 0; optionIndex < optionNames.length; optionIndex++) {
            helpScreen.append(optionNames[optionIndex]);
            helpScreen.append(optionDescriptions[optionIndex]);
            helpScreen.append(NEWLINE);
        }
    }

    private void padNamesColumnToSameWidth() {
        final int maxLength = longestOptionNamesColumn();
        for (final StringBuilder names : optionNames) {
            final int actualLenght = names.length();
            for (int i = 0; i < maxLength - actualLenght; i++) {
                names.append(PADDING_SPACE);
            }
            names.append(DEFAULT_OPTION_INDENTATION);
        }
    }

    private int longestOptionNamesColumn() {
        int max = 0;
        for (final StringBuilder names : optionNames) {
            if (names.length() > max) {
                max = names.length();
            }
        }
        return max;
    }

    private StringBuilder optionNamesColumn(final SingleOption option) {
        final StringBuilder names = new StringBuilder();
        final String shortNames = parseShortNames(option);
        final String longNames = parseLongNames(option);

        names.append(shortNames);
        if (!shortNames.trim().isEmpty() && !longNames.trim().isEmpty()) {
            names.append(OPTION_NAMES_DELIMITER);
        }
        names.append(longNames);
        return names;
    }

    private String parseShortNames(final SingleOption option) {
        final StringBuilder names = new StringBuilder(DEFAULT_OPTION_INDENTATION);
        final Iterator<String> shortNamesIterator = option.getShortNames().iterator();
        while (shortNamesIterator.hasNext()) {
            final String shortName = shortNamesIterator.next();

            names.append(SHORT_OPTION_PREFIX);
            names.append(shortName);

            if (option.hasArgument() && !isNullOrEmpty(option.getArgumentName())) {
                names.append(SHORT_OPTION_ARGUMENT_DELIMITER);
                names.append(option.getArgumentName());
            }

            if (shortNamesIterator.hasNext()) {
                names.append(OPTION_NAMES_DELIMITER);
            }
        }
        return names.toString();
    }

    private String parseLongNames(final SingleOption option) {
        final StringBuilder names = new StringBuilder();
        final Iterator<String> longNamesIterator = option.getLongNames().iterator();
        while (longNamesIterator.hasNext()) {
            final String shortName = longNamesIterator.next();

            names.append(LONG_OPTION_PREFIX);
            names.append(shortName);

            if (option.hasArgument() && !isNullOrEmpty(option.getArgumentName())) {
                names.append(LONG_OPTION_ARGUMENT_DELIMITER);
                names.append(option.getArgumentName());
            }

            if (longNamesIterator.hasNext()) {
                names.append(OPTION_NAMES_DELIMITER);
            }
        }
        return names.toString();
    }

    private String usageLine() {
        String usageLine = options.getUsageLine();
        if (usageLine == null) {
            usageLine = "";
        }
        return usageLine;
    }

    private String descriptionLine() {
        final StringBuilder descriptionLine = new StringBuilder();
        if (!isNullOrEmpty(options.getName())) {
            descriptionLine.append(options.getName());
        }
        if (bothNameAndDescriptionDefined()) {
            descriptionLine.append(DESCRIPTION_SEPARATOR);
        }
        if (!isNullOrEmpty(options.getDescription())) {
            descriptionLine.append(options.getDescription());
        }
        return descriptionLine.toString();
    }

    private boolean bothNameAndDescriptionDefined() {
        return !isNullOrEmpty(options.getName()) && !isNullOrEmpty(options.getDescription());
    }

    private boolean isNullOrEmpty(final String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Compares two options by the first letter of their fist option name.
     * 
     * @author jakub
     * 
     */
    private static class SingleOptionComparator implements Comparator<SingleOption> {

        @Override
        public int compare(final SingleOption arg0, final SingleOption arg1) {
            final String option1Start = arg0.getFirstOptionName().substring(0, 1);
            final String option2Start = arg1.getFirstOptionName().substring(0, 1);
            return option1Start.compareTo(option2Start);
        }

    }
}
