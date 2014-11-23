package com.teamdev.filestorage.service;

import com.teamdev.filestorage.service.exception.FileWithTheSameNameAlreadyExistsException;
import com.teamdev.filestorage.service.exception.NoFreeSpaceException;
import com.teamdev.filestorage.service.exception.StorageException;
import com.teamdev.filestorage.service.exception.FileNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public interface FileStorage {

    public void saveFile(String key, FileInputStream fileInputStream) throws
            FileWithTheSameNameAlreadyExistsException, NoFreeSpaceException;

    public void saveFile(String key, FileInputStream fileInputStream, long expTempMils) throws StorageException;

    public void deleteFile(String origName);

//    public File searchFile(String origName) throws FileNotFoundException;

    public InputStream readFile(String origName) throws StorageException;

    public void purge(float percent) throws StorageException;

    public float freeStorageSpace();
}
