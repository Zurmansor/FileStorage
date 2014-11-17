package com.teamdev.service;


import com.teamdev.generator.FileGenerator;
import com.teamdev.service.exception.StorageException;
import com.teamdev.service.operations.TempFileCleanerOperation;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageService {

    private static Logger logger = Logger.getLogger(StorageService.class.getName());

    public static void main(String[] args) throws Exception {

        //генерация файла
        FileGenerator fileGenerator = new FileGenerator();
        FileStorageImpl service = new FileStorageImpl();

        int fc = 0;
        while (fc > 0) {
            File file = fileGenerator.createTempFile();
            logger.log(Level.INFO, "Temp file: " + file.getName());

            service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));

            fc--;
        }

//        service.deleteFile("deewf");
        long life = 120000; //milliseconds (2 min)

        File file = fileGenerator.createTempFile();
        service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()), life);

        TempFileCleanerOperation cleaner =  new TempFileCleanerOperation();
        cleaner.clean();

    }
}


