package com.teamdev.service;

import com.teamdev.service.exception.*;
import com.teamdev.service.exception.FileNotFoundException;
import com.teamdev.service.operations.*;
import com.teamdev.service.serialization.SerializationTools;
import sunw.io.*;

import java.io.*;
import java.util.LinkedHashMap;
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
    public void saveFile(String origName, FileInputStream fileInputStream) throws FileNotSavedException {
       // вызываю метод save
        SaveFileOperation saveFileOperation = new SaveFileOperation();
        try {
            saveFileOperation.saveFile(origName,fileInputStream);
        } catch (StorageException e) {
            throw new FileNotSavedException();
        }
    }

    @Override
    public void saveFile(String origName, FileInputStream fileInputStream, long expTempMils) throws FileNotSavedException {
        SaveFileOperation saveFileOperation = new SaveFileOperation();
        File newFile = null;
        try {
            newFile = saveFileOperation.saveFile(origName,fileInputStream);
        } catch (StorageException e) {
            throw new FileNotSavedException();
        }

       if (expTempMils > 0){
           SerializationTools serializationTools = new SerializationTools();
           serializationTools.putToDeadList(origName, expTempMils);
        }


    }

    @Override
    public void deleteFile(String origName) {
        DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
        try {
            deleteFileOperation.deleteFile(origName);
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File searchFile(String key) {
        SearchFileOperation searchFileOperation = new SearchFileOperation();
        try {
            return searchFileOperation.searchFile(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public InputStream readFile(String key) {
        ReadOperation readOperation = new ReadOperation();
        try {
            readOperation.readFile(key);
        } catch (StorageException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void purge(float percent) {
        PurgeOperation purgeOperation = new PurgeOperation();
        try {
            purgeOperation.purge(percent);
        } catch (StorageException e) {
            e.printStackTrace();
        }

    }


}
