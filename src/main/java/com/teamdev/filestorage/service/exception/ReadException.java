package com.teamdev.filestorage.service.exception;


public class ReadException extends StorageException{
    public ReadException() {
        super("Read exception");
    }

    public ReadException(String message) {
        super(message);
    }
}
