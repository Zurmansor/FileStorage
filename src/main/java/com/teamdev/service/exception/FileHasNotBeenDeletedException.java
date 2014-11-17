package com.teamdev.service.exception;


public class FileHasNotBeenDeletedException extends StorageException{

    public FileHasNotBeenDeletedException() {
        super("File not deleted");
    }

    public FileHasNotBeenDeletedException(String message) {
        super(message);
    }
}
