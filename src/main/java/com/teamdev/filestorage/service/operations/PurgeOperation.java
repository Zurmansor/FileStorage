package com.teamdev.filestorage.service.operations;


import com.teamdev.filestorage.service.Configuration;

import java.io.File;
import java.util.ArrayList;

public class PurgeOperation {

    private ArrayList<File> fileList= new ArrayList<File>();

    /**
     * Clears the specified amount of memory.
     * Collects all the files in the repository collection ( name and date lastModified).
     * Deletes the old files until the desired amount of memory available
     * @param needToClearSpace in bytes
     */
    public void purge (long needToClearSpace){
        Configuration configuration = new Configuration();

        collectFiles(new File(configuration.getRootPath()));

        sortFileList();
        long freedMemory = 0;

        // clean the oldest files until you clear the necessary space in memory
        while (freedMemory < needToClearSpace && !fileList.isEmpty()) {
            DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
            File fileToDelete = fileList.get(fileList.size() - 1);

            try{
                long tempFileLength = fileToDelete.length();
                deleteFileOperation.deleteFile(fileToDelete.getName());
                freedMemory += tempFileLength;
            } catch (RuntimeException e){

            } finally {
                fileList.remove(fileList.size()-1);
                fileList.trimToSize();
            }
        }
    }

    /**
     * Collects all files of directory
     * @param dir Directory to collect
     */
    private void collectFiles (File dir) {
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile()) {
//                occupiedMemory += file.length();
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
