package com.teamdev.service.operations;


import com.teamdev.service.exception.FileNotFoundException;
import com.teamdev.service.exception.StorageException;

import java.io.*;

public class ReadOperation{
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
