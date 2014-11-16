package com.teamdev.service;

public class FileRegister {
    private String hashName;
    private long lastModificationTime;

    public FileRegister(String hashName) {
        this.hashName = hashName;
    }

    public FileRegister(String hashName, long lastModificationTime) {
        this.hashName = hashName;
        this.lastModificationTime = lastModificationTime;
    }

    public long getLastModificationTime() {
        return lastModificationTime;
    }
    public String getHashName() {
        return hashName;
    }

    public void setLastModificationTime(long lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }
}
