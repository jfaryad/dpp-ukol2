package cz.cuni.mff.dpp.impl.converter;

import java.io.File;

import cz.cuni.mff.dpp.api.ArgumentConverter;

/**
 * A simple implementation of a {@link File} converter. It assumes the string argument is the absolute path to the file
 * and calls new File(String) to convert it to a file.
 * 
 * @author jakub
 * 
 */
public class FileArgumentConverter implements ArgumentConverter<File> {

    @Override
    public File convert(final String argument) {
        return new File(argument);
    }

    @Override
    public Class<File> getTargetClass() {
        return File.class;
    }

}
