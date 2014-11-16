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
        File file = fileGenerator.createTempFile();
        logger.log(Level.INFO, "Temp file: " + file.getName());


        FileStorageImpl service = new FileStorageImpl();

//        service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));
//        service.deleteFile("tmp_file7331723319734799093.txt");

        Configuration configuration = new Configuration();
        File fileStorage  = new File(configuration.getRotPath());

        System.out.println("size = "+fileStorage.getUsableSpace());
    }
}


