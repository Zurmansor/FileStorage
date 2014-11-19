package com.teamdev.service.exception;


public class NoFreeSpaceException extends StorageException{

    public NoFreeSpaceException() {
        super("Storage is full");
    }

    public NoFreeSpaceException(String message) {
        super(message);
    }
}
