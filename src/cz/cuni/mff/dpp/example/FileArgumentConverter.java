package cz.cuni.mff.dpp.example;

import java.io.File;

import cz.cuni.mff.dpp.api.ArgumentConverter;

public class FileArgumentConverter implements ArgumentConverter<File>{

    @Override
    public File parse(String argument) {
        return new File(argument);
    }

    @Override
    public Class<File> getTargetClass() {
        // TODO Auto-generated method stub
        return File.class;
    }

}
