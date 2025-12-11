import java.io.Serializable;

/** Entity object representing a research note or reference link. */
public class ResearchNote implements Serializable {

    private String content;
    private String link;

    public ResearchNote(String content, String link) {
        this.content = content;
        this.link    = link;
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }
}
