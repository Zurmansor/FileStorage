package com.teamdev.filestorage.service.routine;

import com.teamdev.filestorage.service.Configuration;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class AuxiliaryOperation {


    /**
     * Create the path by the hashCode of the current file
     *
     * @param key
     * @return Created path
     */
    public String createPath(String key) {
        Configuration configuration = new Configuration();
        String target = configuration.getRootPath();
        String pathByName = nameToPath(key);


        // get Path object from string
        Path path = FileSystems.getDefault().getPath(configuration.getRootPath() + pathByName);

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
     * @param key name of file
     * @return Computed path
     */
    public String nameToPath(String key)  {
        String path = "/";
        int hashName = key.hashCode();
        path += (int) (hashName % Math.pow(2, 15)) + "/";
        path += (int) (hashName % Math.pow(2, 7)) + "/";
        path += (int) (hashName % Math.pow(2, 3)) + "/";
        return path;
    }
}
