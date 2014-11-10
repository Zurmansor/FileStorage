package com.teamdev.service.operations;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuxiliaryOperation {

    final String ROOT_PATH = "F:/NASTYA/Java/workspace/TeamDev/TestFolder/STORAGE";
    //TODO: context?

    private static Logger logger = Logger.getLogger(AuxiliaryOperation.class.getName());


    public String createPath (String charPath) throws IOException {
        String target = ROOT_PATH;
        String depth = "";

        depth = nameToPathFile(charPath);
/*        for (Integer i = 0; i < charPath.length() - 1; i++) {
            depth += "/" + charPath.charAt(i);
        }*/

//        TODO: провеить есть ли уже такая папка
        logger.log(Level.INFO, "New path: " + target);

        // get Path object from string
        Path path = FileSystems.getDefault().getPath(ROOT_PATH + depth);
        Files.createDirectories(path);

        return depth;
    }

    public String nameToPathFile (String  key) throws IOException {
        String path = "";

        for (Integer i = 0; i < key.length()-1; i++) {
            path += "/" + key.charAt(i);
        }
        return path;
    }

}
