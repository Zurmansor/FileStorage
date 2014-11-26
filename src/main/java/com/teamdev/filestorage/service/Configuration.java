package com.teamdev.filestorage.service;


public class Configuration {

    private final String ROOT_PATH = "./../TestFolder/STORAGE";
    private final int MAX_USELESS = 20;
    private final long STORAGE_CAPACITY = 1024 * 150;
    private final String DEAD_LIST = "ExpirationTimeList.txt";
    private final int DEPTH = 3;

    public long getStorageCapacity() { return STORAGE_CAPACITY; }

    public String getRootPath() { return ROOT_PATH; }

    public int getMaxUseless() { return MAX_USELESS; }

    public String getExpirationTimeList() {
        return DEAD_LIST;
    }

    public int getDepth() { return DEPTH; }


}
