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
        HashMap<String, Long> deadList = serializationTools.getDeadList();
        HashMap<String, Long> tempDeadList = new HashMap<String, Long>();
        if (LOG.isLoggable(Level.INFO)) {
            LOG.log(Level.INFO, deadList.size() + " temp file has been found");
        }
        for(String key : deadList.keySet()) {
            SearchFileOperation searchFileOperation = new SearchFileOperation();
                File file = searchFileOperation.searchFile(key);
                if (file == null) {
                    tempDeadList.put(key, deadList.get(key));
                    continue;
                }

                long createdTime = getCreatedTime(file.getAbsolutePath());
                if (System.currentTimeMillis() >= createdTime + deadList.get(key)) {
                    DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
                    deleteFileOperation.deleteFile(key);
                    tempDeadList.put(key, deadList.get(key));
                }
        }
        if (LOG.isLoggable(Level.INFO)) {
            LOG.log(Level.INFO, tempDeadList.size() + " temp file has been deleted");
        }
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
