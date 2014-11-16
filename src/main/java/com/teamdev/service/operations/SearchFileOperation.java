package com.teamdev.service.operations;


import com.teamdev.service.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SearchFileOperation {


    //TODO: context?
    private static Logger logger = Logger.getLogger(SearchFileOperation.class.getName());

    /**
     * search File
     * @param origName
     * @return
     * @throws IOException
     */
    public File searchFile (String origName) throws IOException {
        String path;
        AuxiliaryOperation auxiliaryOperation = new AuxiliaryOperation();
        path = auxiliaryOperation.nameToPathFile(origName);

        Configuration configuration = new Configuration();
        File file = new File(configuration.getRotPath() + path + "/" + origName);

//       logger.log(Level.INFO, "new file path: " + ROOT_PATH + depth + "/" + key);

        return file;
    }
}
