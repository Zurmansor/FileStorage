package com.teamdev.filestorage.service;

public class StorageService {

    public static void main(String[] args) {

       /* // запуск автоматического очистителя временных файлов
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
            long life = (int) (Math.random() * 120000 + 2000); //milliseconds
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
        }*/

    }
}

