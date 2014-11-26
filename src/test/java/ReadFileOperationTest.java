import com.teamdev.filestorage.service.exception.StorageException;
import generator.FileGenerator;
import com.teamdev.filestorage.service.FileStorageImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class ReadFileOperationTest {
    @Test
    public void testReadFile() throws IOException, StorageException {

        FileStorageImpl service = new FileStorageImpl();
        FileGenerator fileGenerator = new FileGenerator();
        File file = fileGenerator.createTempFile();

        String fileName = file.getName();

        service.saveFile(file.getName(), new FileInputStream(file.getAbsolutePath()));

        // check operation
        final InputStream inputStream = service.readFile(fileName);

        Assert.assertTrue(inputStream != null);
        inputStream.close();

        service.deleteFile(fileName);
    }
}
