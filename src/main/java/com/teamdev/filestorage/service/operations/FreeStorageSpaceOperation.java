package com.teamdev.filestorage.service.operations;

import com.teamdev.filestorage.service.Configuration;

import java.io.File;

public class FreeStorageSpaceOperation {

    private float occupiedMemory = 0;

    /**
     * info how much free space in the storage
     * @return in bytes
     */
    public float freeStorageSpace() {
        Configuration configuration = new Configuration();
        File fileStorage = new File(configuration.getRotPath());

        weighFiles(fileStorage);

        float freeMemory = configuration.getStorageCapacity() - occupiedMemory;
        return freeMemory;
    }

    /**
     * Collects all weigh files of directory`
     * @param dir Directory to collect
     */
    private void weighFiles(File dir) {
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                occupiedMemory += file.length();
            } else {
                weighFiles(file);
            }
        }

    }
}