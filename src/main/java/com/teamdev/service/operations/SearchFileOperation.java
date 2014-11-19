package com.teamdev.service.operations;


import com.teamdev.service.Configuration;
import com.teamdev.service.exception.FileNotFoundException;
import com.teamdev.service.exception.StorageException;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SearchFileOperation {

    private static Logger logger = Logger.getLogger(SearchFileOperation.class.getName());

    /**
     * search File
     * @param origName
     * @return
     * @throws FileNotFoundException
     */
    public File searchFile (String origName) throws FileNotFoundException {
        String path;
        AuxiliaryOperation auxiliaryOperation = new AuxiliaryOperation();

        path = auxiliaryOperation.nameToPathFile(origName);
      
        Configuration configuration = new Configuration();
        File file = new File(configuration.getRotPath() + path + "/" + origName);

        if (!file.exists()) {
            new FileNotFoundException();
        }

        return file;
    }
}
