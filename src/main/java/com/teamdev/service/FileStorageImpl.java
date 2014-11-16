package com.teamdev.service;

import com.teamdev.service.operations.DeleteFileOperation;
import com.teamdev.service.operations.PurgeOperation;
import com.teamdev.service.operations.SaveFileOperation;
import com.teamdev.service.operations.SearchFileOperation;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStorageImpl implements FileStorage {

    private static Logger logger = Logger.getLogger(FileStorageImpl.class.getName());

    private FileStorageContext fileStorageContext;
    private LinkedHashMap fileRegister;


    public FileStorageImpl() throws IOException {
        fileStorageContext = new FileStorageContext();
        fileRegister = fileStorageContext.getFileRegister();
    }

    @Override
    public void saveFile(String origName, FileInputStream fileInputStream) throws Exception {
       // вызываю метод save
        SaveFileOperation saveFileOperation = new SaveFileOperation();
        File newFile = saveFileOperation.saveFile(origName,fileInputStream);

        //РЕГИСТРЫ
//        Path newFilePath = FileSystems.getDefault().getPath(newFile.getAbsolutePath());

//        BasicFileAttributes fileAttributes = Files.readAttributes(newFilePath, BasicFileAttributes.class);
//        fileRegister.put(newFile.getName(), fileAttributes.creationTime().toMillis());
//        fileStorageContext.syncToFile();
    }

    @Override
    public void deleteFile(String key) {
        DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
        try {
            deleteFileOperation.deleteFile(key);
            fileRegister.remove(key);
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
    public void purge(float percent) {
        PurgeOperation purgeOperation = new PurgeOperation();
        try {
            purgeOperation.purge(percent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
