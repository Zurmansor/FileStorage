package com.teamdev.filestorage.service.exception;


public class FileNotFoundException extends StorageException{

    public FileNotFoundException() {
        super("File not found");
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}
