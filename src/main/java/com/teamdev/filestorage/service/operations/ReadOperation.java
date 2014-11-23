package com.teamdev.filestorage.service.operations;


import com.teamdev.filestorage.service.exception.StorageException;
import com.teamdev.filestorage.service.exception.FileNotFoundException;
import com.teamdev.filestorage.service.routine.SearchFileOperation;

import java.io.*;

public class ReadOperation{
    /**
     * readFile
     * @param origName
     * @return inputStream
     * @throws com.teamdev.filestorage.service.exception.StorageException
     */
    public InputStream readFile(String origName) throws StorageException {

        SearchFileOperation searchFileOperation = new SearchFileOperation();
        File file;

        file = searchFileOperation.searchFile(origName);
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        return inputStream;
    }
}
