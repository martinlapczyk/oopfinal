import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Entity object representing one project. */
public class Project implements Serializable {

    private String title;
    private String description;
    private String status;      // To-Do, In-Progress, Completed
    private LocalDate dueDate;

    private List<Collaborator> collaborators;
    private List<ResearchNote> notes;

    public Project(String title) {
        this.title = title;
        this.description = "";
        this.status = "To-Do";
        this.dueDate = null;
        this.collaborators = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    // Getters / setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public List<ResearchNote> getNotes() {
        return notes;
    }

    public void addCollaborator(Collaborator c) {
        collaborators.add(c);
    }

    public void addNote(ResearchNote n) {
        notes.add(n);
    }

    @Override
    public String toString() {
        String base = title;
        if (status != null && !status.isEmpty()) {
            base += " [" + status + "]";
        }
        return base;
    }
}

