package com.teamdev.filestorage.service.exception;


public class FileNotSavedException extends StorageException{

    public FileNotSavedException() {
        super("File not created");
    }

    public FileNotSavedException(String message) {
        super(message);
    }

}
