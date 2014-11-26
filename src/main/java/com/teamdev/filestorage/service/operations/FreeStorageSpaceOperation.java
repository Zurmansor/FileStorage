package com.teamdev.filestorage.service.operations;

import com.teamdev.filestorage.service.Configuration;

import java.io.File;

public class FreeStorageSpaceOperation {

    private long occupiedMemory = 0;

    /**
     * Info how much free space in the storage
     * @return freeMemory in bytes
     */
    public long freeStorageSpace() {
        Configuration configuration = new Configuration();
        File fileStorage = new File(configuration.getRootPath());

        weighFiles(fileStorage);

        long freeMemory = configuration.getStorageCapacity() - occupiedMemory;
        return freeMemory;
    }

    /**
     * Collects all weigh files of directory`
     * @param dir Directory to collect
     */
    private void weighFiles(File dir) {
        File[] files = dir.listFiles();
        if (files == null){
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                occupiedMemory += file.length();
            } else {
                weighFiles(file);
            }
        }

    }
}