package com.teamdev.service.exception;


public class FileWithTheSameNameAlreadyExistsException extends StorageException{

    public FileWithTheSameNameAlreadyExistsException() {
        super("File with the same name already exists");
    }

    public FileWithTheSameNameAlreadyExistsException(String message) {
        super(message);
    }

}
