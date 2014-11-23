package com.teamdev.filestorage.service;


public class Configuration {

//    private final String ROOT_PATH = "F:/NASTYA/Java/workspace/TeamDev/TestFolder/STORAGE";
    private final String ROOT_PATH = "./../TestFolder/STORAGE";
//    private int NUMBER_PARTS = 4;
    private int MAX_USELESS = 20;
    private long STORAGE_CAPACITY = 1024 * 15;
    private String DEAD_LIST = "DeadList.txt";
    private int DEPTH = 3;

    public long getStorageCapacity() { return STORAGE_CAPACITY; }

//    public int getNumberParts() { return NUMBER_PARTS; }

    public String getRotPath() { return ROOT_PATH; }

    public int getMaxUseless() { return MAX_USELESS; }

    public String getDeadList() {
        return DEAD_LIST;
    }

    public int getDepth() { return DEPTH; }


}
