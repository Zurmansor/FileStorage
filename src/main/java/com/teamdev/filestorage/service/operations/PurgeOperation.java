package com.teamdev.filestorage.service.operations;


import com.teamdev.filestorage.service.Configuration;
import com.teamdev.filestorage.service.exception.StorageException;
import com.teamdev.filestorage.service.exception.ToManyUselessDeleteOperationsException;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurgeOperation {
    private static Logger logger = Logger.getLogger(PurgeOperation.class.getName());

    private ArrayList<File> fileList= new ArrayList<File>();
    private long occupiedMemory = 0;

    /**
     * Cleans memory to the desired value
     * @param percent
     */
    public void purge (float percent) throws StorageException {
        Configuration configuration = new Configuration();

        collectFiles(new File(configuration.getRotPath()));

        sortFileList();

        File fileStorage  = new File(configuration.getRotPath());

        // how much free memory you need
        float requiredFreeMemory = configuration.getStorageCapacity() / 100 * percent;
        // how much free memory there is now
        FreeStorageSpaceOperation freeStorageSpaceOperation = new FreeStorageSpaceOperation();

        float freeMemory = freeStorageSpaceOperation.freeStorageSpace();

        int checkMaxUseless = 0;
        int deleted = 0;
        long deletedSize = 0;

        // clean the oldest files until you clear the necessary space in memory
        while (freeMemory < requiredFreeMemory && !fileList.isEmpty()) {
            DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
            File fileToDelete = fileList.get(fileList.size() - 1);

            // first look at the size of the file , then remove
            deleted++;
            deletedSize += fileToDelete.length();
            occupiedMemory -= fileToDelete.length();
            deleteFileOperation.deleteFile(fileToDelete.getName());

            fileList.remove(fileList.size()-1);
            fileList.trimToSize();

            float prevFreeMemory = freeMemory;
            freeMemory = configuration.getStorageCapacity() - occupiedMemory;

            if (prevFreeMemory == freeMemory) {
                checkMaxUseless++;
            } else {
                checkMaxUseless = 0;
            }
            // chek if to many useless delete operations
            if (checkMaxUseless >= configuration.getMaxUseless()) {
                logger.log(Level.INFO, "To many useless delete operations");
                throw new ToManyUselessDeleteOperationsException();
            }
        }
        logger.log(Level.INFO, String.format("%d files [%dkb] has been deleted", deleted, deletedSize/1024));
    }

    /**
     * Collects all files of directory
     * @param dir Directory to collect
     */
    private void collectFiles (File dir) {
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                occupiedMemory += file.length();
                fileList.add(file);
            } else {
                collectFiles(file);
            }
        }
    }

    /**
     * Sort FileList by date lastModified
     */
    private void sortFileList() {
        for (int y=0; y < fileList.size()-1; y++) {
            for (int i = 0; i < fileList.size() - 1; i++) {
                if (fileList.get(i).lastModified() > fileList.get(i).lastModified()) {
                    File tempFile = fileList.get(i);
                    fileList.set(i, fileList.get(i + 1));
                    fileList.set(i + 1, tempFile);
                }
            }
        }
    }


}
