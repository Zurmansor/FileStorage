package com.teamdev.service;

//import java.io.File;
import com.teamdev.generator.FileGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageService {

    private static Logger logger = Logger.getLogger(StorageService.class.getName());

    public static void main(String[] args) throws Exception {

        FileGenerator fileGenerator = new FileGenerator();
        String tempFilePath = fileGenerator.createTempFile();
        logger.log(Level.INFO, "Temp file: " + tempFilePath);

        ChecksumCalculator checksumCalculator = new ChecksumCalculator();
        String hashName = checksumCalculator.getHash(tempFilePath);

        logger.log(Level.INFO, "New file hash: " + hashName);

        FileStorageImpl service = new FileStorageImpl();
        service.saveFile(hashName, new FileInputStream(tempFilePath));

        service.deleteFile("c690d46deea50cf88be11450e3fccde27725a207");

//        cb308db2853382f73e2057ed6c7bc389c64ac259

//        test();
    }

    void  copyFile (String  root, String target) throws IOException {
      /*  final Path tempFile = Files.createTempFile("nio-temp", ".tmp");
        Path path = FileSystems.getDefault().getPath(target);
        Files.createDirectories(path);*/


        Path pathSource = Paths.get(root);
        Path pathDestination = Paths.get(target);
        try {
            Files.move(pathSource, pathDestination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Source file copied successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void test() {
        String folder = "F:\\NASTYA\\Java\\workspace\\TeamDev\\TestFolder\\a.txt";

        File dir = new File(folder);

//        Timestamp stamp = new Timestamp(dir.lastModified());
//        Date date = new Date(stamp.getTime());

    }
}


