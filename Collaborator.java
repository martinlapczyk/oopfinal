import java.io.Serializable;

/** Entity object representing a collaborator/partner on a project. */
public class Collaborator implements Serializable {

    private String name;
    private String email;

    public Collaborator(String name, String email) {
        this.name  = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
