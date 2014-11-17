package com.teamdev.service.operations;

import com.teamdev.service.Configuration;
import com.teamdev.service.HashCalculator;
import com.teamdev.service.exception.PathIsNotCreatedException;
import com.teamdev.service.exception.StorageException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuxiliaryOperation {

    private static Logger logger = Logger.getLogger(AuxiliaryOperation.class.getName());

    /**
     * Create the path by the hashName of the current file
     *
     * @param origName
     * @return Created path
     * @throws StorageException
     */
    public String createPath(String origName) throws StorageException {
        // получение hashName из origName
        String hashName = origNameToHashName(origName);
        Configuration configuration = new Configuration();
        String target = configuration.getRotPath();
//        String depth = nameToPathFile(hashName);
        String depth = nameToPathFile(origName);

//        TODO: провеить есть ли уже такая папка
        logger.log(Level.INFO, "New path: " + target);

        // get Path object from string
        Path path = FileSystems.getDefault().getPath(configuration.getRotPath() + depth);

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new PathIsNotCreatedException();
        }

        return depth;
    }

    /**
     * Separates the name into four parts
     *
     * @param origName
     * @return
     * @throws
     */
    public String nameToPathFile(String origName)  {
        // получение hashName из origName
        String hashName = origNameToHashName(origName);
        String path = "/";
        int i;
        Configuration configuration = new Configuration();
        int numberParts = configuration.getNumberParts();
        int step = Math.round(hashName.length() / numberParts);

        for (i = 0; i < numberParts - 1; i++) {
            path += hashName.substring(i * step, i * step + step);
            path += "/";
        }
        path += hashName.substring(i * step);

        return path;
    }

    /**
     * get hashName from origName
     * @param origName
     * @return
     */
    public String origNameToHashName(String origName){
        HashCalculator hashCalculator = new HashCalculator();
        String hashName = null;
        try {
            hashName = hashCalculator.getHashName(origName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO,"New file hash: "+hashName);

        return hashName;
    }


}
