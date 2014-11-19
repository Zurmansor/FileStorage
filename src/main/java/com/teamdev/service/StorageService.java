package com.teamdev.service;


import com.teamdev.generator.FileGenerator;
import com.teamdev.service.exception.StorageException;
import com.teamdev.service.operations.TempFileCleanerOperation;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageService {

    private static Logger logger = Logger.getLogger(StorageService.class.getName());

    public static void main(String[] args) {
        // запуск автоматического очистителя временных файлов
        TempFileCleanerOperation tempFileCleanerOperation = new TempFileCleanerOperation();
        tempFileCleanerOperation.clean();
        //генерация файла
        FileGenerator fileGenerator = new FileGenerator();
        FileStorageImpl service = new FileStorageImpl();

        int fc = 30;
        while (fc > 0) {

            File file = null;
            try {
                file = fileGenerator.createTempFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            logger.log(Level.INFO, "Temp file: " + file.getName());

            try {
                service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));
            } catch (StorageException e) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }

            fc--;
        }


        int fcl = 0;
        while (fcl > 0) {
            long life = (int)(Math.random()*120000+2000); //milliseconds
            File file = null;
            try {
                file = fileGenerator.createTempFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            try {
                service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()), life);
            } catch (StorageException e) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            fcl--;
        }

        try {
            service.purge(30);
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }


//        System.out.println(fs);



//        TempFileCleanerOperation cleaner =  new TempFileCleanerOperation();
//        cleaner.clean();

    }
}


