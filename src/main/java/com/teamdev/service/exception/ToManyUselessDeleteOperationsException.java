package com.teamdev.service.exception;

public class ToManyUselessDeleteOperationsException extends StorageException{

    public ToManyUselessDeleteOperationsException() {
        super("To many useless delete operations exception");
    }

    public ToManyUselessDeleteOperationsException(String message) {
        super(message);
    }
}
