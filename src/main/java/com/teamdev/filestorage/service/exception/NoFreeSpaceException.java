package com.teamdev.filestorage.service.exception;


public class NoFreeSpaceException extends StorageException{

    public NoFreeSpaceException() {
        super("Storage is full");
    }

    public NoFreeSpaceException(String fileName) {
        super("Storage is full: file " + fileName + " has not been saved");
    }

//    public NoFreeSpaceException(String message) {
//        super(message);
//    }
}
