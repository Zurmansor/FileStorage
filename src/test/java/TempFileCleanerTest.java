import com.teamdev.filestorage.service.FileStorageImpl;
import com.teamdev.filestorage.service.cleaner.TempFileCleanerOperation;
import com.teamdev.filestorage.service.cleaner.ToggleFileCleanerImpl;
import com.teamdev.filestorage.service.exception.FileWithTheSameNameAlreadyExistsException;
import com.teamdev.filestorage.service.exception.NoFreeSpaceException;
import com.teamdev.filestorage.service.routine.SearchFileOperation;
import generator.FileGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;

import static junit.framework.TestCase.assertTrue;

public class TempFileCleanerTest {
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
    public void testFileCleanerBefore() throws FileNotFoundException {
        File file = null;
        long life = 5000; //milliseconds
        try {
            file = fileGenerator.createTempFile();
            tempFiles.add(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(file.getAbsolutePath());
        try {
            service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()), life);
            createdFiles.add(file.getName());
        } catch (FileWithTheSameNameAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (NoFreeSpaceException e) {
            System.out.println(e.getMessage());
        }

        final TempFileCleanerOperation tempFileCleanerOperation = new TempFileCleanerOperation();
        tempFileCleanerOperation.clean();

        SearchFileOperation searchFileOperation = new SearchFileOperation();
        File fileTemp = searchFileOperation.searchFile(file.getName());
        assertTrue(fileTemp != null && fileTemp.exists());
    }

    @Test
    @Ignore
    public void testFileCleanerAfter() throws FileNotFoundException {
        File file = null;
        long life = 0; //milliseconds
        try {
            file = fileGenerator.createTempFile();
            tempFiles.add(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(file.getAbsolutePath());
        try {
            service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()), life);
            createdFiles.add(file.getName());
        } catch (FileWithTheSameNameAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (NoFreeSpaceException e) {
            System.out.println(e.getMessage());
        }

        final TempFileCleanerOperation tempFileCleanerOperation = new TempFileCleanerOperation();
        tempFileCleanerOperation.clean();



        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            String aa;
            @Override
            public void run() {
                //call the Cleaner method
                SearchFileOperation searchFileOperation = new SearchFileOperation();
                File fileTemp = searchFileOperation.searchFile(file.getName());
                assertTrue(fileTemp != null && fileTemp.exists());
            }
        };
        timer.schedule(timerTask, 3000);
    }

}
