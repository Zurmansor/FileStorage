package com.teamdev.filestorage.service;

public class StorageService {

    public static void main(String[] args) {
        /*
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

        /*
        try {
            service.purge(30);
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
        */

//        int a = 654;
//        int b = 565;
//        double c = a % b;
//        System.out.println(c);

/*        String key = "3";
        int hash = key.hashCode();
        int l1 = (int) (hash % Math.pow(2, 15));
        System.out.println(hash + " -- " + l1);

        hash = (""+l1).hashCode();
        int l2 = (int) (hash % Math.pow(2, 15));
        System.out.println(hash + " -- " + l2);

        hash = (""+l2).hashCode();
        int l3 = (int) (hash % Math.pow(2, 15));

        System.out.println(hash + " -- " + l3);*/
    }
}


