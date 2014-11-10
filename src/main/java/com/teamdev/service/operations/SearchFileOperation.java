package com.teamdev.service.operations;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchFileOperation {
    final String ROOT_PATH = "F:/NASTYA/Java/workspace/TeamDev/TestFolder/STORAGE";
    //TODO: context?

    private static Logger logger = Logger.getLogger(SearchFileOperation.class.getName());

   public File searchFile (String  key) throws IOException {
        String path;
        AuxiliaryOperation auxiliaryOperation = new AuxiliaryOperation();

        path = auxiliaryOperation.nameToPathFile(key);
        File file = new File(ROOT_PATH + path + "/" + key);

//       logger.log(Level.INFO, "new file path: " + ROOT_PATH + depth + "/" + key);

        return file;
    }
}
