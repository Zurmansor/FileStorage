package com.teamdev.filestorage.service.routine;


import com.teamdev.filestorage.service.Configuration;

import java.io.File;

public class SearchFileOperation {

    /**
     * Search File
     * @param key
     * @return
     */
    public File searchFile (String key) {
        String path;
        AuxiliaryOperation auxiliaryOperation = new AuxiliaryOperation();
        path = auxiliaryOperation.nameToPath(key);
        Configuration configuration = new Configuration();
        File file = new File(configuration.getRootPath() + path + "/" + key);

        if (!file.exists()) {
            return null;
        }

        return file;
    }
}
