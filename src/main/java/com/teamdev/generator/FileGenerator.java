package com.teamdev.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileGenerator {

    final String ROOT = "F:\\NASTYA\\Java\\workspace\\TeamDev\\TestFolder\\";
    final String TMP_FILE_NAME = "tmp_file";
    final String  TMP_FILE_EXT = ".txt";
    final String ALPHABET = "qwertyuiopasdfghjklzxcvbnm1234567890";
    final int MAX_TEXT_LENGTH = 1000;

    private static Logger logger = Logger.getLogger(FileGenerator.class.getName());

    /**
     * Generate text file
     * @return path to the created file
     * @deprecated
     */
    public String createFile() {
        String path = "";

        String tmpFilePath = ROOT + TMP_FILE_NAME + TMP_FILE_EXT;
        Path file = Paths.get(tmpFilePath);

        List<String> lines = new ArrayList<String>();
        lines.add(generateText());


        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * Creates temp file with randomly generated text.
     * @return Absolute path to the created file.
     * @throws IOException
     */
    public String createTempFile() throws IOException {
        File tmpFile = File.createTempFile(TMP_FILE_NAME, TMP_FILE_EXT);
//        Files.c
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(new FileWriter(tmpFile));
            writer.println(generateText());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return tmpFile.getAbsolutePath();
    }

    /**
     * Generate random text for the new file
     * @return Generated text
     */
    private String generateText () {
        String text = "";
        int length = (int)(Math.random() * MAX_TEXT_LENGTH) + 10;
        int lines = 1;

        while (text.length() < length) {
            text += ALPHABET.charAt((int)(Math.random()*ALPHABET.length()));
            int spaceProbability = (int)(Math.random()*100);

            // sometimes added space or newline
            if (spaceProbability > 97) {
                text += '\n';
                lines ++;
            } else if (spaceProbability > 85) {
                text += ' ';
            }
        }

//        logger.log(Level.INFO, String.format("Random string [length: %d, lines: %d]:\n%s", length, lines, text));
        return text;
    }
}
