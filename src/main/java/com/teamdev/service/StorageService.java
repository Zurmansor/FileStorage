package com.teamdev.service;


import com.teamdev.generator.FileGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageService {

    private static Logger logger = Logger.getLogger(StorageService.class.getName());

    public static void main(String[] args) throws Exception {

        //генерация файла
        FileGenerator fileGenerator = new FileGenerator();
        FileStorageImpl service = new FileStorageImpl();

        int fc = 70;
        while (fc > 0) {
            File file = fileGenerator.createTempFile();
            logger.log(Level.INFO, "Temp file: " + file.getName());

            service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));

            fc--;
        }

        service.purge(30);


//        service.deleteFile("tmp_file3773039040724081430.txt");

//        service.purge(10);

    }
}


