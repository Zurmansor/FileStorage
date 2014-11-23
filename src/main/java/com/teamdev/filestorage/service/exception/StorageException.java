package com.teamdev.filestorage.service.exception;

/**
 * General class of exceptions storage
 */
public class StorageException extends Exception{

    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }
}
