package com.teamdev.service.operations;


import com.teamdev.service.Configuration;
import com.teamdev.service.exception.FileNotSavedException;
import com.teamdev.service.exception.FileWithTheSameNameAlreadyExistsException;
import com.teamdev.service.exception.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
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

        // проверка есть ли файл с таким названием в системе
        if (searchFileOperation.searchFile(origName).exists()){
            // бросаем исключение - Файл с таким именем уже существует
            throw new FileWithTheSameNameAlreadyExistsException();
            //TODO: описать в JDoc EXCEPTION
        }

        //создаем путь для файла (строим папки)
        AuxiliaryOperation auxiliaryOperation = new AuxiliaryOperation();
        String depth = auxiliaryOperation.createPath(origName);

        // создаем окончательный адресс файла
        Configuration configuration = new Configuration();
        String newFilePath = configuration.getRotPath() + depth + "/" + origName;
        File file = new File(newFilePath);

        // если путь свободен - мы записываемся
        try {
            if (file.createNewFile()) {
                // read from temp file
                int data = inputStream.read();
                String content = "";
                while(data != -1) {
                    content += (char) data;
                    data = inputStream.read();
                }
                inputStream.close();

                // write to new file
                FileOutputStream outputStream = new FileOutputStream(newFilePath);
                outputStream.write(content.getBytes());
                logger.log(Level.INFO, "new file path: " + newFilePath);
                outputStream.close();
            } else {
                throw new FileNotSavedException();
                // исключение File Exist либо по другой причине не удалось создать
                //TODO: EXCEPTION
                //        TODO: если создать файл не удалось, возвращать null
            }
        } catch (IOException e) {
            throw new FileNotSavedException();
        }
//        FileRegister fileRegisterItem = new FileRegister();
//        fileRegisterItem.setHashName();
        return new File(newFilePath);
    }
}
