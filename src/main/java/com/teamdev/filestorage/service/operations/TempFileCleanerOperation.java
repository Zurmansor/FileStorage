package com.teamdev.filestorage.service.operations;


import com.teamdev.filestorage.service.routine.SearchFileOperation;
import com.teamdev.filestorage.service.serialization.SerializationTools;
import com.teamdev.filestorage.service.exception.StorageException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TempFileCleanerOperation {

    private static Logger LOGGER = Logger.getLogger(TempFileCleanerOperation.class.getName());

    /**
     * clean old temp files
     */
    public void clean (){
        SerializationTools serializationTools = new SerializationTools();
        HashMap<String, Long> deadList = serializationTools.getDeadList();
        HashMap<String, Long> tempDeadList = new HashMap<String, Long>();

        LOGGER.log(Level.INFO, deadList.size() + " temp file has been found");

        for(String origName : deadList.keySet()) {
            SearchFileOperation searchFileOperation = new SearchFileOperation();
                File file = searchFileOperation.searchFile(origName);
                if (file == null) {
                    tempDeadList.put(origName, deadList.get(origName));
                    continue;
                }

                long createdTime = getCreatedTime(file.getAbsolutePath());
                if (System.currentTimeMillis() >= createdTime + deadList.get(origName)) {
                    DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
                    deleteFileOperation.deleteFile(origName);
                    tempDeadList.put(origName, deadList.get(origName));
                }
        }

        LOGGER.log(Level.INFO, tempDeadList.size() + " temp file has been deleted");
        serializationTools.deadListUpdate(tempDeadList);

    }

    private long getCreatedTime (String path) {
        try {
            BasicFileAttributes attributes = Files.getFileAttributeView(Paths.get(path), BasicFileAttributeView.class).readAttributes();
            return attributes.creationTime().toMillis();
        } catch (IOException e) {
            return -1;
        }

    }

}
