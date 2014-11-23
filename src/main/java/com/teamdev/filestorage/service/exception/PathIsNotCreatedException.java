package com.teamdev.filestorage.service.exception;


public class PathIsNotCreatedException extends  StorageException{

    public PathIsNotCreatedException() {
        super("Path is not created exception");
    }

    public PathIsNotCreatedException(String message) {
        super(message);
    }
}
