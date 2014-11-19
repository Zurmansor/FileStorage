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

    /**
     * Deletes the file
     * @param origName
     * @throws StorageException
     */
    public void deleteFile (String  origName) throws StorageException{

        SearchFileOperation searchFileOperation = new SearchFileOperation();
        File file = null;

        try {
        //search file
        file = searchFileOperation.searchFile(origName);
        // if file is not found throw exception
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "our path ------------- " + file.getAbsolutePath());
        // if file is not exists throw exception
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        //If a file is deleted delete empty folders top
        if (file.delete()){
            logger.log(Level.INFO, "file has been deleted");

            // delete empty folders top
            if (file.getParentFile().list().length == 0) {
                Configuration configuration = new Configuration();
                // if can not removes directory throw exception
                try {
                    deleteEmptyDirectory(file.getParentFile(), configuration.getNumberParts());
                } catch (DirectoryNotDeletedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //otherwise throw exception
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
