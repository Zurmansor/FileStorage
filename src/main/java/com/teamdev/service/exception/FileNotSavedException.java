package com.teamdev.service.exception;


public class FileNotSavedException extends StorageException{

    public FileNotSavedException() {
        super("File not created");
    }

    public FileNotSavedException(String message) {
        super(message);
    }

}
