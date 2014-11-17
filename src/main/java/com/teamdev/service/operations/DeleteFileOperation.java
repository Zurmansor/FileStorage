package com.teamdev.service.operations;


import com.teamdev.service.Configuration;
import com.teamdev.service.exception.DirectoryNotDeletedException;
import com.teamdev.service.exception.FileHasNotBeenDeletedException;
import com.teamdev.service.exception.FileNotFoundException;
import com.teamdev.service.exception.StorageException;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteFileOperation {

    private static Logger logger = Logger.getLogger(DeleteFileOperation.class.getName());

    public void deleteFile (String  origName) throws StorageException{

        SearchFileOperation searchFileOperation = new SearchFileOperation();
        File file = null;

        try {
        file = searchFileOperation.searchFile(origName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "наш путь ------------- " + file.getAbsolutePath());

        if (!file.exists()) {
            throw new FileNotFoundException();
            //TODO: EXCEPTION
//            logger.log(Level.INFO, "file is not exist");
//            return;
        }

        if (file.delete()){
            logger.log(Level.INFO, "file has been deleted");
//          logger.log(Level.INFO, "list: " + file.getParentFile().list().length);

            // удаление верхних пустых папок
            if (file.getParentFile().list().length == 0) {
                Configuration configuration = new Configuration();
                try {
                    deleteEmptyDirectory(file.getParentFile(), configuration.getNumberParts());
                } catch (DirectoryNotDeletedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //TODO: EXCEPTION
            logger.log(Level.INFO, "file has not been deleted");
            throw new FileHasNotBeenDeletedException();
        }
    }

    /**
     * delete Empty Directory
     * @param file
     * @param depth
     */
    private void deleteEmptyDirectory(File file, int depth) throws DirectoryNotDeletedException{
        File fileParent = file.getParentFile();

        while (depth > 0 && fileParent.list().length < 2){
            if (!file.delete()) {
                throw new DirectoryNotDeletedException();
//                logger.log(Level.INFO, "directory can't be deleted");
//                break;
            }
            file = fileParent;
            fileParent = file.getParentFile();
            depth--;
        }

        if (file.list().length == 0) {
            file.delete();
        }
    }

}
