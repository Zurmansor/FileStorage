package com.teamdev.filestorage.service.cleaner;


import com.teamdev.filestorage.service.operations.DeleteFileOperation;
import com.teamdev.filestorage.service.routine.SearchFileOperation;
import com.teamdev.filestorage.service.routine.SerializationTools;

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

    private static Logger LOG = Logger.getLogger(TempFileCleanerOperation.class.getName());

    /**
     * Clean old temp files
     */
    public void clean (){
        SerializationTools serializationTools = new SerializationTools();
        HashMap<String, Long> expirationTimeList = serializationTools.getExpirationTimeList();
        HashMap<String, Long> tempExpirationTimeList = new HashMap<String, Long>();
        if (LOG.isLoggable(Level.INFO)) {
            LOG.log(Level.INFO, expirationTimeList.size() + " temp file has been found");
        }
        for(String key : expirationTimeList.keySet()) {
            SearchFileOperation searchFileOperation = new SearchFileOperation();
                File file = searchFileOperation.searchFile(key);
                if (file == null) {
                    tempExpirationTimeList.put(key, expirationTimeList.get(key));
                    continue;
                }

                long createdTime = getCreatedTime(file.getAbsolutePath());
                if (System.currentTimeMillis() >= createdTime + expirationTimeList.get(key)) {
                    DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
                    deleteFileOperation.deleteFile(key);
                    tempExpirationTimeList.put(key, expirationTimeList.get(key));
                }
        }
        if (LOG.isLoggable(Level.INFO)) {
            LOG.log(Level.INFO, tempExpirationTimeList.size() + " temp file has been deleted");
        }
        serializationTools.expirationTimeListUpdate(tempExpirationTimeList);

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
