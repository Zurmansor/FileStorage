import com.teamdev.filestorage.service.Configuration;
import com.teamdev.filestorage.service.exception.FileWithTheSameNameAlreadyExistsException;
import com.teamdev.filestorage.service.exception.NoFreeSpaceException;
import com.teamdev.filestorage.service.routine.SerializationTools;
import generator.FileGenerator;
import com.teamdev.filestorage.service.FileStorageImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;

public class SaveFileOperationTest {
    private FileStorageImpl service;
    private FileGenerator fileGenerator;
    private Deque<String> createdFiles;
    private Deque<File> tempFiles;

    @Before
    public void initialize() throws IOException {
        service = new FileStorageImpl();
        fileGenerator = new FileGenerator();
        createdFiles = new ArrayDeque<String>();
        tempFiles = new ArrayDeque<File>();
    }

    @After
    public void close() {
        while (!createdFiles.isEmpty()) {
            service.deleteFile(createdFiles.pop());
        }

        while (!tempFiles.isEmpty()) {
            tempFiles.pop().delete();
        }
    }

    @Test
    public void testSaveFile() {
        File file = null;
        try {
            file = fileGenerator.createTempFile();
            tempFiles.add(file);
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
            tempFiles.add(file);
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
    public void testSaveTempFile() throws FileNotFoundException {
        File file = null;
        long life = (int) (Math.random() * 120000 + 2000); //milliseconds


        try {
            file = fileGenerator.createTempFile();
            tempFiles.add(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()), life);
            createdFiles.add(file.getName());
        } catch (FileWithTheSameNameAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (NoFreeSpaceException e) {
            System.out.println(e.getMessage());
        }

        SerializationTools serializationTools = new SerializationTools();
        HashMap<String, Long> deadList;
        deadList = serializationTools.getExpirationTimeList();

        Configuration configuration = new Configuration();
        File df = new File (configuration.getRootPath() + "/" + configuration.getExpirationTimeList());
        df.delete();

        assertTrue(deadList.containsKey(file.getName()) && deadList.get(file.getName()) == life);

    }
}
