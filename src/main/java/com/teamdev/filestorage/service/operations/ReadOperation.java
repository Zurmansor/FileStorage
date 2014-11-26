package com.teamdev.filestorage.service.operations;


import com.teamdev.filestorage.service.exception.FileNotFoundException;
import com.teamdev.filestorage.service.routine.SearchFileOperation;

import java.io.*;

public class ReadOperation{
    /**
     * Read File. Search for the file on a key, if such a file exists, returns it to the stream.
     * @param key
     * @return inputStream
     * @throws FileNotFoundException
     */
    public InputStream readFile(String key) throws FileNotFoundException {

        SearchFileOperation searchFileOperation = new SearchFileOperation();
        File file;

        file = searchFileOperation.searchFile(key);

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException();
        }

        return inputStream;
    }
}
