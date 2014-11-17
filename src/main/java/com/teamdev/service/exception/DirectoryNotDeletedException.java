package com.teamdev.service.exception;


public class DirectoryNotDeletedException extends StorageException{

    public DirectoryNotDeletedException() {
        super("Directory not deleted exception");
    }

    public DirectoryNotDeletedException(String message) {
        super(message);
    }
}
