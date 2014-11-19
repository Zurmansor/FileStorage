package com.teamdev.service.operations;


import com.teamdev.service.Configuration;
import com.teamdev.service.exception.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveFileOperation {

    private static Logger logger = Logger.getLogger(SaveFileOperation.class.getName());

    /**
     * saves the file
     * @param origName
     * @param inputStream
     * @return
     * @throws StorageException
     */
    public File saveFile(String origName, FileInputStream inputStream) throws StorageException {

        SearchFileOperation searchFileOperation = new SearchFileOperation();

        if (!checkValidName(origName)){
            throw  new IncorrectlyFileNameException();
        }
        // check whether there is a file with the same name in the system
        if (searchFileOperation.searchFile(origName).exists()){
            // if file exists throws Exception - A file with this name already exists
            throw new FileWithTheSameNameAlreadyExistsException();
        }

        //create a path for the file ( build folders)
        AuxiliaryOperation auxiliaryOperation = new AuxiliaryOperation();
        String depth = auxiliaryOperation.createPath(origName);

        // create the ultimate address file
        Configuration configuration = new Configuration();
        String newFilePath = configuration.getRotPath() + depth + "/" + origName;
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
                        throw new NoFreeSpaceException();
                    }
                }
                inputStream.close();

                // write to new file
                FileOutputStream outputStream = new FileOutputStream(newFilePath);
                outputStream.write(content.getBytes());
                logger.log(Level.INFO, "new file path: " + newFilePath);
                outputStream.close();
            } else {
                // if file not saved throws Exception
                throw new FileNotSavedException();
            }
        } catch (IOException e) {
            throw new FileNotSavedException();
        }
        return new File(newFilePath);
    }

    private boolean checkValidName(String origName){

            String pattern = "[a-zA-z0-9.\\-_! ]+";
            return origName.matches(pattern);

    }


}
