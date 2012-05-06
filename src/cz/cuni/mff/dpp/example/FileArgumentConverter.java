package cz.cuni.mff.dpp.example;

import java.io.File;

import cz.cuni.mff.dpp.api.ArgumentConverter;

public class FileArgumentConverter implements ArgumentConverter<File> {

    @Override
    public File convert(String argument) {
        return new File(argument);
    }

    @Override
    public Class<File> getTargetClass() {
        return File.class;
    }

}
