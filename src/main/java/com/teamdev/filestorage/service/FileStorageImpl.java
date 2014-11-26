package com.teamdev.filestorage.service;

import com.teamdev.filestorage.service.exception.*;
import com.teamdev.filestorage.service.exception.FileNotFoundException;
import com.teamdev.filestorage.service.operations.*;
import com.teamdev.filestorage.service.routine.SerializationTools;
import java.io.*;

public class FileStorageImpl implements FileStorage {

    @Override
    public void saveFile(String key, FileInputStream fileInputStream) throws
            FileWithTheSameNameAlreadyExistsException, NoFreeSpaceException {
        SaveFileOperation saveFileOperation = new SaveFileOperation();
        saveFileOperation.saveFile(key,fileInputStream);
    }

    @Override
    public void saveFile(String key, FileInputStream fileInputStream, long expirationTempMillis) throws
            FileWithTheSameNameAlreadyExistsException, NoFreeSpaceException {
        SaveFileOperation saveFileOperation = new SaveFileOperation();
        saveFileOperation.saveFile(key, fileInputStream);

        //check if the parameter is specified expirationTempMillis
        if (expirationTempMillis > 0){
            SerializationTools serializationTools = new SerializationTools();
            serializationTools.putToExpirationTimeList(key, expirationTempMillis);
        }
    }

    @Override
    public void deleteFile(String key) {
        DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
        deleteFileOperation.deleteFile(key);
    }

    @Override
    public InputStream readFile(String key) throws FileNotFoundException {
        ReadOperation readOperation = new ReadOperation();
        return readOperation.readFile(key);
    }

    @Override
    public void purge(long bytes) {
        PurgeOperation purgeOperation = new PurgeOperation();
        purgeOperation.purge(bytes);

    }

    @Override
    public long freeStorageSpace() {
        FreeStorageSpaceOperation freeStorageSpaceOperation = new FreeStorageSpaceOperation();
        return freeStorageSpaceOperation.freeStorageSpace();
    }
}
