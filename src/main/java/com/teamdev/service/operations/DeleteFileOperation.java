package com.teamdev.service.operations;


import com.teamdev.service.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteFileOperation {

    private static Logger logger = Logger.getLogger(DeleteFileOperation.class.getName());

    public void deleteFile (String  origName) throws IOException {

        SearchFileOperation searchFileOperation = new SearchFileOperation();
        File file = searchFileOperation.searchFile(origName);
        logger.log(Level.INFO, "наш путь ------------- " + file.getAbsolutePath());
        if (!file.exists()) {
            //TODO: EXCEPTION
            logger.log(Level.INFO, "file is not exist");
            return;
        }

        if (file.delete()){
            logger.log(Level.INFO, "file has been deleted");
//          logger.log(Level.INFO, "list: " + file.getParentFile().list().length);
            // удаление верхних пустых папок
            if (file.getParentFile().list().length == 0) {
                Configuration configuration = new Configuration();
                deleteEmptyDirect(file.getParentFile(), configuration.getNumberParts());
            }
        } else {
            //TODO: EXCEPTION
            logger.log(Level.INFO, "file has not been deleted");
        }

/*        if (file.getParent().isEmpty()){

        }*/
//        File directory = new File(ROOT_PATH + auxiliaryOperation.nameToPathFile(key));
//        directory.isDirectory();
//        directory.exists();

    }

    /**
     * delete Empty Directory
     * @param file
     * @param depth
     */
    private void deleteEmptyDirect(File file, int depth){
        File fileParent = file.getParentFile();

        while(depth > 0 && fileParent.list().length < 2){
            if (!file.delete()) {
                logger.log(Level.INFO, "directory can't be deleted");
                break;
            }

            file = fileParent;
            fileParent = file.getParentFile();
            depth--;
        }
    }

}
