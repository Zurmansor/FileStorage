package com.teamdev.service.operations;


import com.teamdev.service.exception.FileNotFoundException;
import com.teamdev.service.exception.StorageException;
import com.teamdev.service.serialization.SerializationTools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TempFileCleanerOperation {

    private static Logger logger = Logger.getLogger(TempFileCleanerOperation.class.getName());

    public void clean (){
        SerializationTools serializationTools = new SerializationTools();
        HashMap<String, Long> deadList = serializationTools.getDeadList();
        HashMap<String, Long> tempDeadList = new HashMap<String, Long>();

        logger.log(Level.INFO, deadList.size() + " temp file has been found");

        for(String origName : deadList.keySet()) {
            SearchFileOperation searchFileOperation = new SearchFileOperation();
            try {
                File file = searchFileOperation.searchFile(origName);

                long createdTime = getCreatedTime(file.getAbsolutePath());
                if (System.currentTimeMillis() >= createdTime + deadList.get(origName)){
                    DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
                    try {
                        deleteFileOperation.deleteFile(origName);
                        System.out.println(origName + " -- " + deadList.get(origName));
                    } catch (StorageException e) {
//                        e.printStackTrace();
                    }
                    tempDeadList.put(origName, deadList.get(origName));
                }
            } catch (FileNotFoundException e) {
                //TODO: файла нет --> удалить из списка
                tempDeadList.put(origName, deadList.get(origName));
//                e.printStackTrace();
            }
        }

        logger.log(Level.INFO, tempDeadList.size() + " temp file has been deleted");
        serializationTools.deadListUpdate(tempDeadList);

    }

    private long getCreatedTime (String path) {
        try {
            BasicFileAttributes attributes = Files.getFileAttributeView(Paths.get(path), BasicFileAttributeView.class).readAttributes();
            return attributes.creationTime().toMillis();
        } catch (IOException e) {
//            e.printStackTrace();
            return -1;
        }

    }

}
