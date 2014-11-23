package com.teamdev.filestorage.service.operations;


import com.teamdev.filestorage.service.Configuration;
import com.teamdev.filestorage.service.exception.DirectoryNotDeletedException;
import com.teamdev.filestorage.service.exception.FileHasNotBeenDeletedException;
import com.teamdev.filestorage.service.exception.FileNotFoundException;
import com.teamdev.filestorage.service.exception.StorageException;
import com.teamdev.filestorage.service.routine.SearchFileOperation;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteFileOperation {

    private static Logger LOG = Logger.getLogger(DeleteFileOperation.class.getName());

    /**
     * Deletes the file
     * @param origName
     * @throws StorageException
     */
    public void deleteFile (String  origName) {

        SearchFileOperation searchFileOperation = new SearchFileOperation();
        File file = null;
        Configuration configuration = new Configuration();

        file = searchFileOperation.searchFile(origName);
        if (file == null) {
            try {
                deleteEmptyDirectory(file.getParentFile(), configuration.getDepth());
            } catch (DirectoryNotDeletedException e) {
                throw new RuntimeException();
            }
        }

        //If the file has been deleted, delete empty folders top
        String fileName = file.getName();
        if (file.delete()){
            if (LOG.isLoggable(Level.INFO)) {
                LOG.log(Level.INFO, "File has been deleted: " + fileName);
            }

            // delete empty folders top
            if (file.getParentFile().list().length == 0) {
                // if can not removes directory throw exception
                try {
                    deleteEmptyDirectory(file.getParentFile(), configuration.getDepth());
                } catch (DirectoryNotDeletedException e) {
                    throw new RuntimeException();
                }
            }
        } else {
            //otherwise throw exception
            if (LOG.isLoggable(Level.INFO)) {
                LOG.log(Level.INFO, "File has not been deleted: " + fileName);
            }
            throw new RuntimeException();
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
