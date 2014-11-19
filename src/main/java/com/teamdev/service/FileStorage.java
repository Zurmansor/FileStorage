package com.teamdev.service;

import com.teamdev.service.exception.FileNotFoundException;
import com.teamdev.service.exception.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public interface FileStorage {

    public void saveFile(String key, FileInputStream fileInputStream) throws StorageException;

    public void saveFile(String key, FileInputStream fileInputStream, long expTempMils) throws StorageException;

    public void deleteFile(String origName) throws StorageException;

    public File searchFile(String origName) throws FileNotFoundException;

    public InputStream readFile(String origName) throws StorageException;

    public void purge(float percent) throws StorageException;

    public float freeStorageSpace();
}
