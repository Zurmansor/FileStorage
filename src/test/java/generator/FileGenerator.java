package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class FileGenerator {

    final String TMP_FILE_NAME = "tmp_file";
    final String  TMP_FILE_EXT = ".txt";
    final String ALPHABET = "qwertyuiopasdfghjklzxcvbnm1234567890";
    final int MAX_TEXT_LENGTH = 1000;

    private static Logger logger = Logger.getLogger(FileGenerator.class.getName());

    /**
     * Creates temp file with randomly generated text.
     * @return Created file.
     * @throws IOException
     */
    public File createTempFile() throws IOException {
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

        return tmpFile;
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
