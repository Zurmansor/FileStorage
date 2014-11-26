package com.teamdev.filestorage.service;

import com.teamdev.filestorage.service.exception.FileNotFoundException;
import com.teamdev.filestorage.service.exception.FileWithTheSameNameAlreadyExistsException;
import com.teamdev.filestorage.service.exception.NoFreeSpaceException;
import com.teamdev.filestorage.service.exception.StorageException;

import java.io.FileInputStream;
import java.io.InputStream;

public interface FileStorage {

    public void saveFile(String key, FileInputStream fileInputStream) throws
            FileWithTheSameNameAlreadyExistsException, NoFreeSpaceException;

    public void saveFile(String key, FileInputStream fileInputStream, long expirationTempMillis) throws
            FileWithTheSameNameAlreadyExistsException, NoFreeSpaceException;

    public void deleteFile(String key);

    public InputStream readFile(String key) throws FileNotFoundException;

    public void purge(long bytes);

    public long freeStorageSpace();
}
