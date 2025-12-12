import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving/loading the list of projects using Java serialization.
 */
public class FileManager {

    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }


    public List<Project> read() throws IOException, ClassNotFoundException {
        File f = new File(fileName);
        if (!f.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            return (List<Project>) obj;
        }
    }

    public void write(List<Project> projects) throws IOException {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(projects);
        }
    }
}
