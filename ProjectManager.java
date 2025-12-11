import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Control object that manages the list of projects and delegates
 * persistence to FileManager.
 */
public class ProjectManager {

    private List<Project> projects;
    private FileManager fileManager;

    public ProjectManager() {
        this.projects    = new ArrayList<>();
        this.fileManager = new FileManager("projects.dat");
    }

    public void addProject(Project p) {
        projects.add(p);
    }

    public void updateProject(Project oldProject, Project newProject) {
        int idx = projects.indexOf(oldProject);
        if (idx >= 0) {
            projects.set(idx, newProject);
        }
    }

    public void deleteProject(Project p) {
        projects.remove(p);
    }

    public List<Project> getProjects() {
        // return a copy so GUI can’t accidentally mutate internal list
        return new ArrayList<>(projects);
    }

    public void saveAll() throws IOException {
        fileManager.write(projects);
    }

    public void loadAll() throws IOException, ClassNotFoundException {
        this.projects = fileManager.read();
    }
}
