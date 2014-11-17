package com.teamdev.service.operations;


import com.teamdev.service.Configuration;
import com.teamdev.service.exception.FileHasNotBeenDeletedException;
import com.teamdev.service.exception.StorageException;
import com.teamdev.service.exception.ToManyUselessDeleteOperationsException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurgeOperation {
    private static Logger logger = Logger.getLogger(PurgeOperation.class.getName());

    private ArrayList<File> fileList= new ArrayList<File>();
    private long occupiedMemory = 0;

    /**
     *Cleans memory to the desired value
     * @param percent
     */
    public void purge (float percent) throws StorageException{
        // TODO EXCEPTION
        Configuration configuration = new Configuration();

        collectFiles(new File(configuration.getRotPath()));

        sortFileList();

        File fileStorage  = new File(configuration.getRotPath());

        // сколько нужно свободной памяти вообще от общей.
        float requiredFreeMemory = configuration.getStorageCapacity() / 100 * percent;
        // сколько свободной памяти сейчас есть
        float freeMemory = configuration.getStorageCapacity() - occupiedMemory;

        int checkMaxUseless = 0;
        int deleted = 0;
        long deletedSize = 0;

        // очищаем самые старые файлы, пока не освободим нужное место в памяти
        while (freeMemory < requiredFreeMemory && !fileList.isEmpty()) {
            DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
            File fileToDelete = fileList.get(fileList.size() - 1);

            // сначала смотрим размер файла, потом удаляем
            deleted++;
            deletedSize += fileToDelete.length();
            occupiedMemory -= fileToDelete.length();
//            TODO: проверить удалился ли файл
            try {
                deleteFileOperation.deleteFile(fileToDelete.getName());
            } catch (StorageException e) {
                throw new FileHasNotBeenDeletedException();
            }

            fileList.remove(fileList.size()-1);
            fileList.trimToSize();

            float prevFreeMemory = freeMemory;
            freeMemory = configuration.getStorageCapacity() - occupiedMemory;

            if (prevFreeMemory == freeMemory) {
                checkMaxUseless++;
            } else {
                checkMaxUseless = 0;
            }

            if (checkMaxUseless >= configuration.getMaxUseless()) {
                logger.log(Level.INFO, "To many useless delete operations");
                throw new ToManyUselessDeleteOperationsException();
//                TODO: ADD EXCEPTION
//                break;
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
