package com.teamdev.filestorage.service.routine;

import com.teamdev.filestorage.service.Configuration;
import com.teamdev.filestorage.service.exception.PathIsNotCreatedException;
import com.teamdev.filestorage.service.exception.StorageException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public String createPath(String origName) {
        Configuration configuration = new Configuration();
        String target = configuration.getRotPath();
        String pathByName = nameToPath(origName);

//        logger.log(Level.INFO, "New path: " + target);
        // get Path object from string
        Path path = FileSystems.getDefault().getPath(configuration.getRotPath() + pathByName);

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return pathByName;
    }

    /**
     * Computes the path from the name
     *
     * @param origName name of file
     * @return Computed path
     */
    public String nameToPath(String origName)  {
        // get hashName from origName
        int hashName = origName.hashCode();
        String path = "/";
        Configuration configuration = new Configuration();
        int depth = configuration.getDepth();

        for (int i = 0; i < depth; i++) {
            int piece = (int) (hashName % Math.pow(2, 15));
            path += piece + "/";
            hashName = (""+piece).hashCode();
        }
        return path;
    }
}
