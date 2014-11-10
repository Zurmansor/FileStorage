package com.teamdev.service.operations;

import com.teamdev.service.FileStorageImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveFileOperation {

    final String ROOT_PATH = "F:/NASTYA/Java/workspace/TeamDev/TestFolder/STORAGE";
    //TODO: context?
    private static Logger logger = Logger.getLogger(SaveFileOperation.class.getName());

    public void saveFile(String key, FileInputStream inputStream) throws Exception {
        AuxiliaryOperation auxiliaryOperation = new AuxiliaryOperation();
        String depth = auxiliaryOperation.createPath(key);

        // read from temp file
        int data = inputStream.read();
        String content = "";
        while(data != -1) {
            content += (char) data;
            data = inputStream.read();
        }
        inputStream.close();

        // write to new file
        logger.log(Level.INFO, "new file path: " + ROOT_PATH + depth + "/" + key);
        FileOutputStream outputStream = new FileOutputStream(ROOT_PATH + depth + "/" + key);
        outputStream.write(content.getBytes());
        outputStream.close();
    }
}
