import generator.FileGenerator;
import com.teamdev.filestorage.service.Configuration;
import com.teamdev.filestorage.service.exception.FileWithTheSameNameAlreadyExistsException;
import com.teamdev.filestorage.service.exception.NoFreeSpaceException;
import com.teamdev.filestorage.service.FileStorageImpl;
import com.teamdev.filestorage.service.exception.StorageException;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.teamdev.filestorage.service.operations.DeleteFileOperation;
import com.teamdev.filestorage.service.routine.SearchFileOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DeleteFileOperationTest {
    private static Logger LOG = Logger.getLogger(DeleteFileOperationTest.class.getName());
    private FileStorageImpl service;
    private FileGenerator fileGenerator;
    private Deque<String> createdFiles;

    @Before
    public void initialize() throws IOException {
        service = new FileStorageImpl();
        fileGenerator = new FileGenerator();
        createdFiles = new ArrayDeque<String>();
    }

    @After
    public void close() {
        while (!createdFiles.isEmpty()) {
            service.deleteFile(createdFiles.pop());
        }
    }

    @Test
    public void testDeleteFile() throws IOException, StorageException {

        File file = fileGenerator.createTempFile();
        String fileName = file.getName();

        LOG.log(Level.INFO, "Temp file: " + fileName);

        service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));

        DeleteFileOperation deleteFileOperation = new DeleteFileOperation();
        // check operation
        deleteFileOperation.deleteFile(fileName);
        SearchFileOperation searchFileOperation = new SearchFileOperation();
        assertTrue(searchFileOperation.searchFile(fileName) == null);
    }


    @Test
    public void testPurgeFile() throws IOException {

        createFiles(20);

        // how much free memory there is now
        Configuration configuration = new Configuration();
        long occupiedSpaceBeforePurge = configuration.getStorageCapacity() - service.freeStorageSpace();

        long purgeConst = 0;

        if (occupiedSpaceBeforePurge > 0){
            purgeConst = occupiedSpaceBeforePurge/2;
        }

        service.purge(purgeConst);

        assertTrue(occupiedSpaceBeforePurge - purgeConst >= configuration.getStorageCapacity() - service.freeStorageSpace());
    }

    void createFiles(int number){
        number = 20;
        while (number > 0) {

            File file = null;
            try {
                file = fileGenerator.createTempFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));
                createdFiles.add(file.getName());
            } catch (FileWithTheSameNameAlreadyExistsException e) {
                System.out.println(e.getMessage());
            } catch (NoFreeSpaceException e) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                System.out.println("System error.");
            }

            number--;
        }
    }


}
