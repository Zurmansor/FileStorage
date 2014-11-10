package com.teamdev.service.operations;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteFileOperation {

    final String ROOT_PATH = "F:/NASTYA/Java/workspace/TeamDev/TestFolder/STORAGE";
    //TODO: context?
    private static Logger logger = Logger.getLogger(DeleteFileOperation.class.getName());

    public void deleteFile (String  key) throws IOException {

        SearchFileOperation searchFileOperation = new SearchFileOperation();

        File file = searchFileOperation.searchFile(key);

        if (!file.exists()) {
            logger.log(Level.INFO, "file is not exist");
            return;
        }

        if (file.delete()){
            logger.log(Level.INFO, "file has been deleted");
        } else {
            logger.log(Level.INFO, "file has not been deleted");
        }

        // TODO: удалить верхние директории если они пусты
        // TODO: вывести сообщение если указанного файла нет

//        File directory = new File(ROOT_PATH + auxiliaryOperation.nameToPathFile(key));
//        directory.isDirectory();
//        directory.exists();
    }
}
