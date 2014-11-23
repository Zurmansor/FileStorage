package com.teamdev.filestorage.service.routine;


import com.teamdev.filestorage.service.Configuration;
import com.teamdev.filestorage.service.exception.FileNotFoundException;

import java.io.File;
import java.util.logging.Logger;

public class SearchFileOperation {

    private static Logger logger = Logger.getLogger(SearchFileOperation.class.getName());

    /**
     * search File
     * @param origName
     * @return
     */
    public File searchFile (String origName) {
        String path;
        AuxiliaryOperation auxiliaryOperation = new AuxiliaryOperation();

        path = auxiliaryOperation.nameToPath(origName);
      
        Configuration configuration = new Configuration();
        File file = new File(configuration.getRotPath() + path + "/" + origName);

        if (!file.exists()) {
            return null;
        }

        return file;
    }
}
