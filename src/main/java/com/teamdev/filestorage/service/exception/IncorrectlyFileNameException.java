package com.teamdev.filestorage.service.exception;


public class IncorrectlyFileNameException extends StorageException{
    public IncorrectlyFileNameException() {
        super("Incorrectly File Name Exception");
    }

    public IncorrectlyFileNameException(String message) {
        super(message);
    }
}
