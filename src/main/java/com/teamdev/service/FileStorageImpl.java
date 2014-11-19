package com.teamdev.service;

import com.teamdev.service.exception.*;
import com.teamdev.service.exception.FileNotFoundException;
import com.teamdev.service.operations.*;
import com.teamdev.service.serialization.SerializationTools;
import java.io.*;
import java.util.logging.Logger;

public class FileStorageImpl implements FileStorage {

    private static Logger logger = Logger.getLogger(FileStorageImpl.class.getName());


    @Override
    public void saveFile(String origName, FileInputStream fileInputStream) throws StorageException {
       // call the saveFile method
        SaveFileOperation saveFileOperation = new SaveFileOperation();
        saveFileOperation.saveFile(origName,fileInputStream);
    }

    @Override
    public void saveFile(String origName, FileInputStream fileInputStream, long expTempMils) throws StorageException {
//        fileInputStream.l
        SaveFileOperation saveFileOperation = new SaveFileOperation();
//        File file = null;
        saveFileOperation.saveFile(origName, fileInputStream);

       //check if the parameter is specified expTempMils
       if (expTempMils > 0){
           SerializationTools serializationTools = new SerializationTools();
           // call the putToDeadList method
           serializationTools.putToDeadList(origName, expTempMils);
        }
    }

    @Override
    public void deleteFile(String origName) throws StorageException {
        DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
        deleteFileOperation.deleteFile(origName);
    }

    @Override
    public File searchFile(String key) throws FileNotFoundException {
        SearchFileOperation searchFileOperation = new SearchFileOperation();
        return searchFileOperation.searchFile(key);
    }

    @Override
    public InputStream readFile(String key) throws StorageException {
        ReadOperation readOperation = new ReadOperation();
        readOperation.readFile(key);

        return null;
    }

    @Override
    public void purge(float percent) throws StorageException {
        PurgeOperation purgeOperation = new PurgeOperation();
        purgeOperation.purge(percent);

    }

    @Override
    public float freeStorageSpace() {
        FreeStorageSpaceOperation freeStorageSpaceOperation = new FreeStorageSpaceOperation();
        return freeStorageSpaceOperation.freeStorageSpace();
    }
}
