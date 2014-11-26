package com.teamdev.filestorage.service.operations;


import com.teamdev.filestorage.service.Configuration;
import com.teamdev.filestorage.service.routine.SearchFileOperation;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteFileOperation {

    private static Logger LOG = Logger.getLogger(DeleteFileOperation.class.getName());

    /**
     * Deletes the file. After deleting the file, there is a removal of the upper empty folders .
     * @param key
     */
    public void deleteFile (String  key) {
        SearchFileOperation searchFileOperation = new SearchFileOperation();
        File file = null;
        Configuration configuration = new Configuration();

        file = searchFileOperation.searchFile(key);
        if (file == null) {
            return;
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
                deleteEmptyDirectory(file.getParentFile(), configuration.getDepth());
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
    private void deleteEmptyDirectory(File file, int depth) {
        File fileParent = file.getParentFile();

        while (depth > 0 && fileParent.list().length < 2){
            if (!file.delete()) {
                continue;
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
