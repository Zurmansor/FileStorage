package com.teamdev.filestorage.service.operations;


import com.teamdev.filestorage.service.Configuration;
import com.teamdev.filestorage.service.exception.*;
import com.teamdev.filestorage.service.routine.AuxiliaryOperation;
import com.teamdev.filestorage.service.routine.SearchFileOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveFileOperation {

    private static Logger LOG = Logger.getLogger(SaveFileOperation.class.getName());

    /**
     * Save the file in the file storage.
     * Checks whether there is such a file already in the store, if there is issues exception.
     * Save the file on a specially generated path of hashCode file.
     * The file itself is written with the key that was given by the user,
     * however, illegal characters are replaced with "_".
     * @param key
     * @param inputStream
     * @return
     * @throws FileWithTheSameNameAlreadyExistsException, NoFreeSpaceException
     */
    public File saveFile(String key, FileInputStream inputStream) throws
            FileWithTheSameNameAlreadyExistsException, NoFreeSpaceException {

        SearchFileOperation searchFileOperation = new SearchFileOperation();

//        TODO: ЗАМЕНА при сохранении
//        if (!checkValidName(key)){
//            throw  new IncorrectlyFileNameException();
//        }
        // check whether there is a file with the same name in the system

        if (searchFileOperation.searchFile(key) != null) {
            throw new FileWithTheSameNameAlreadyExistsException();
        }

        //create a path for the file ( build folders)
        AuxiliaryOperation auxiliaryOperation = new AuxiliaryOperation();
        String depth;
        try {
            depth = auxiliaryOperation.createPath(key);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }

        // create the ultimate address file
        Configuration configuration = new Configuration();
        final String fixedKey = fixKey(key);
        String newFilePath = configuration.getRootPath() + depth + fixedKey;
        File file = new File(newFilePath);

        FreeStorageSpaceOperation freeStorageSpaceOperation = new FreeStorageSpaceOperation();
        float freeSpace = freeStorageSpaceOperation.freeStorageSpace();

        // if the way is clear - we save
        try {
            if (file.createNewFile()) {
                // read from temp file
                int data = inputStream.read();
                int size = 0;
                String content = "";
                while(data != -1) {
                    content += (char) data;
                    size = content.getBytes().length;
                    data = inputStream.read();

                    if (size > freeSpace) {
                        if (!NotEnoughDiskSpace.onNotEnoughDiskSpace){
                            inputStream.close();
                            DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
                            deleteFileOperation.deleteFile(file.getName());
                            throw new NoFreeSpaceException(depth + fixedKey);
                        }

                    }
                }
                inputStream.close();

                // write to new file
                FileOutputStream outputStream = new FileOutputStream(newFilePath);
                outputStream.write(content.getBytes());
                outputStream.close();

                if (LOG.isLoggable(Level.INFO)) {
                    LOG.log(Level.INFO, "File has been saved: " + depth + fixedKey);
                }
            } else {
                // if file not saved throws Exception
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return new File(newFilePath);
    }

    /**
     * Replacing a key unsuitable characters.
     * @param key
     * @return
     */
    private String fixKey(String key) {
        final String fixedName = key.replaceAll("[^a-z^A-Z^0-9.\\-_!]", "_");
    return fixedName;
    }

    /**
     * Callback pattern
     * true if operation should be continue
     * false if not
     */
    static interface NotEnoughDiskSpace{
        boolean onNotEnoughDiskSpace = false;
    }
}
