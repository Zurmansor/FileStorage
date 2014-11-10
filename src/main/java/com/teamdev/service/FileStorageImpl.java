package com.teamdev.service;

import com.teamdev.service.operations.DeleteFileOperation;
import com.teamdev.service.operations.SaveFileOperation;
import com.teamdev.service.operations.SearchFileOperation;

import java.io.*;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStorageImpl implements FileStorage {

//    final String ROOT_PATH = "F:/NASTYA/Java/workspace/TeamDev/TestFolder/STORAGE";

//    private static Logger logger = Logger.getLogger(FileStorageImpl.class.getName());


    @Override
    public void saveFile(String key, FileInputStream fileInputStream) throws Exception {
        SaveFileOperation saveFileOperation = new SaveFileOperation();
        saveFileOperation.saveFile(key,fileInputStream);
    }

    @Override
    public void deleteFile(String key) {
        DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
        try {
            deleteFileOperation.deleteFile(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File searchFile(String key) {
        SearchFileOperation searchFileOperation = new SearchFileOperation();
        try {
            return searchFileOperation.searchFile(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public InputStream readFile(String key) {
        return null;
    }

    @Override
    public void purge(Integer percent) {

    }


}
