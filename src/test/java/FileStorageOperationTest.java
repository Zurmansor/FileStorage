import com.teamdev.generator.FileGenerator;
import com.teamdev.service.FileStorageImpl;
import com.teamdev.service.exception.StorageException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.teamdev.service.operations.DeleteFileOperation;
import com.teamdev.service.operations.ReadOperation;
import com.teamdev.service.operations.SearchFileOperation;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FileStorageOperationTest {
    private static Logger logger = Logger.getLogger(FileStorageOperationTest.class.getName());


    @Test
    public void testSaveFile() throws StorageException, IOException {

        FileStorageImpl service = new FileStorageImpl();
        FileGenerator fileGenerator = new FileGenerator();
        File file = fileGenerator.createTempFile();

        String fileName = file.getName();

        logger.log(Level.INFO, "Temp file: " + fileName);
        // check operation
        service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));

        ReadOperation readOperation = new ReadOperation();
        final InputStream inputStream = readOperation.readFile(fileName);

        assertTrue(inputStream != null);
        inputStream.close();
        DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
        deleteFileOperation.deleteFile(fileName);
    }

    @Test
    public void testDeleteFile() throws IOException, StorageException {

        FileStorageImpl service = new FileStorageImpl();
        FileGenerator fileGenerator = new FileGenerator();

        File file = fileGenerator.createTempFile();
        String fileName = file.getName();

        logger.log(Level.INFO, "Temp file: " + fileName);

        service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));

        DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
        // check operation
        deleteFileOperation.deleteFile(fileName);
        SearchFileOperation searchFileOperation = new SearchFileOperation();
        assertTrue(searchFileOperation.searchFile(fileName).exists() == false);
    }

    @Test
    public void testReadFile() throws IOException, StorageException {

        FileStorageImpl service = new FileStorageImpl();
        FileGenerator fileGenerator = new FileGenerator();
        File file = fileGenerator.createTempFile();

        String fileName = file.getName();

        logger.log(Level.INFO, "Temp file: " + fileName);

        service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));

        ReadOperation readOperation = new ReadOperation();
        // check operation
        final InputStream inputStream = readOperation.readFile(fileName);

        assertTrue(inputStream != null);
        inputStream.close();
        DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
        deleteFileOperation.deleteFile(fileName);
    }

}
