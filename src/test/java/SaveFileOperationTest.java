import com.teamdev.filestorage.service.exception.FileWithTheSameNameAlreadyExistsException;
import com.teamdev.filestorage.service.exception.NoFreeSpaceException;
import generator.FileGenerator;
import com.teamdev.filestorage.service.FileStorageImpl;
import com.teamdev.filestorage.service.exception.StorageException;
import com.teamdev.filestorage.service.operations.DeleteFileOperation;
import com.teamdev.filestorage.service.operations.ReadOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public class SaveFileOperationTest {
    private static Logger LOGGER = Logger.getLogger(SaveFileOperationTest.class.getName());

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
    public void testSaveFile() {
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
    }

    @Test (expected = FileWithTheSameNameAlreadyExistsException.class)
    public void testSaveFileExist() throws FileWithTheSameNameAlreadyExistsException{
        File file = null;
        try {
            file = fileGenerator.createTempFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));
            createdFiles.add(file.getName());
            service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));
        } catch (NoFreeSpaceException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("System error.");
        }

    }

    @Test
    public void testSave50Files() {
        int i = 50;
        while (i > 0) {
            testSaveFile();
            i--;
        }
    }

    @Test
    public void testDeleteFile() {

    }
}
