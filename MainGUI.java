import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * MainGUI is the main window of the Project Organizer.
 * It lets the user create, edit, delete, save, and load projects.
 */
public class MainGUI extends JFrame {

    // GUI components
    private DefaultListModel<Project> projectListModel;
    private JList<Project> lstProjects;

    private JTextField txtTitle;
    private JTextField txtStatus;
    private JTextField txtDueDate; // yyyy-MM-dd

    private JTextArea txtDescription;
    private JTextArea txtCollaborators;
    private JTextArea txtNotes;

    private JButton btnNew;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSaveToFile;
    private JButton btnLoadFromFile;

    // Control object
    private ProjectManager projectManager;

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MainGUI() {
        super("Project Organizer Application");

        projectManager = new ProjectManager();
        projectListModel = new DefaultListModel<>();

        initComponents();
        layoutComponents();
        attachHandlers();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        lstProjects = new JList<>(projectListModel);
        lstProjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        txtTitle = new JTextField(20);
        txtStatus = new JTextField(10);
        txtDueDate = new JTextField(10);

        txtDescription = new JTextArea(5, 30);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);

        txtCollaborators = new JTextArea(4, 30);
        txtCollaborators.setLineWrap(true);
        txtCollaborators.setWrapStyleWord(true);

        txtNotes = new JTextArea(4, 30);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);

        btnNew = new JButton("Add Project");
        btnUpdate = new JButton("Update Project");
        btnDelete = new JButton("Delete Project");
        btnSaveToFile = new JButton("Save All");
        btnLoadFromFile = new JButton("Load");
    }

    private void layoutComponents() {
    setLayout(new BorderLayout(10, 10));

    // ===== LEFT: project list =====
    JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
    leftPanel.setBorder(BorderFactory.createTitledBorder("Projects"));
    leftPanel.add(new JScrollPane(lstProjects), BorderLayout.CENTER);

    JPanel leftButtons = new JPanel(new GridLayout(3, 1, 5, 5));
    leftButtons.add(btnNew);
    leftButtons.add(btnUpdate);
    leftButtons.add(btnDelete);
    leftPanel.add(leftButtons, BorderLayout.SOUTH);

    add(leftPanel, BorderLayout.WEST);

    // ===== RIGHT: details =====
    JPanel rightPanel = new JPanel();
    rightPanel.setBorder(BorderFactory.createTitledBorder("Project Details"));
    rightPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;

    // make fields visually larger
    txtTitle.setColumns(30);
    txtStatus.setColumns(20);
    txtDueDate.setColumns(15);

    txtDescription.setRows(4);
    txtCollaborators.setRows(3);
    txtNotes.setRows(3);

    txtDescription.setPreferredSize(new Dimension(400, 80));
    txtCollaborators.setPreferredSize(new Dimension(400, 60));
    txtNotes.setPreferredSize(new Dimension(400, 60));

    int row = 0;

    // Title
    gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;
    rightPanel.add(new JLabel("Title:"), gbc);

    gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    rightPanel.add(txtTitle, gbc);
    row++;

    // Status
    gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
    gbc.fill = GridBagConstraints.NONE;
    rightPanel.add(new JLabel("Status (To-Do / In-Progress / Completed):"), gbc);

    gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    rightPanel.add(txtStatus, gbc);
    row++;

    // Due date
    gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
    gbc.fill = GridBagConstraints.NONE;
    rightPanel.add(new JLabel("Due Date (yyyy-MM-dd, optional):"), gbc);

    gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    rightPanel.add(txtDueDate, gbc);
    row++;

    // Short Description (big text area)
    gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;
    rightPanel.add(new JLabel("Short Description:"), gbc);

    gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0; gbc.weighty = 0.3;
    gbc.fill = GridBagConstraints.BOTH;
    rightPanel.add(new JScrollPane(txtDescription), gbc);
    row++;

    // Collaborators (big text area)
    gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;
    rightPanel.add(new JLabel("Collaborators (one per line, name <email>):"), gbc);

    gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0; gbc.weighty = 0.2;
    gbc.fill = GridBagConstraints.BOTH;
    rightPanel.add(new JScrollPane(txtCollaborators), gbc);
    row++;

    // Research Notes (big text area)
    gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;
    rightPanel.add(new JLabel("Research Notes / Links (one per line):"), gbc);

    gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0; gbc.weighty = 0.2;
    gbc.fill = GridBagConstraints.BOTH;
    rightPanel.add(new JScrollPane(txtNotes), gbc);
    row++;

    // Bottom buttons (Load / Save All)
    gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
    gbc.weightx = 1.0; gbc.weighty = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    bottomButtons.add(btnLoadFromFile);
    bottomButtons.add(btnSaveToFile);
    rightPanel.add(bottomButtons, gbc);

    add(rightPanel, BorderLayout.CENTER);}
    private void attachHandlers() {
        lstProjects.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Project selected = lstProjects.getSelectedValue();
                if (selected != null) {
                    populateDetails(selected);
                }
            }
        });

        btnNew.addActionListener(e -> handleAddProject());
        btnUpdate.addActionListener(e -> handleUpdateProject());
        btnDelete.addActionListener(e -> handleDeleteProject());
        btnSaveToFile.addActionListener(e -> handleSave());
        btnLoadFromFile.addActionListener(e -> handleLoad());
    }

    private void populateDetails(Project p) {
        txtTitle.setText(p.getTitle());
        txtStatus.setText(p.getStatus());
        txtDueDate.setText(p.getDueDate() != null
                ? p.getDueDate().format(DATE_FMT) : "");

        txtDescription.setText(p.getDescription());

        StringBuilder collabText = new StringBuilder();
        for (Collaborator c : p.getCollaborators()) {
            collabText.append(c.getName());
            if (c.getEmail() != null && !c.getEmail().isEmpty()) {
                collabText.append(" <").append(c.getEmail()).append(">");
            }
            collabText.append("\n");
        }
        txtCollaborators.setText(collabText.toString().trim());

        StringBuilder noteText = new StringBuilder();
        for (ResearchNote n : p.getNotes()) {
            noteText.append(n.getContent());
            if (n.getLink() != null && !n.getLink().isEmpty()) {
                noteText.append(" (").append(n.getLink()).append(")");
            }
            noteText.append("\n");
        }
        txtNotes.setText(noteText.toString().trim());
    }

    /** Build a Project object based on the current form fields. */
    private Project buildProjectFromFields() {
        String title = txtTitle.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Title is required.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String status = txtStatus.getText().trim();
        String dueStr = txtDueDate.getText().trim();
        LocalDate due = null;
        if (!dueStr.isEmpty()) {
            try {
                due = LocalDate.parse(dueStr, DATE_FMT);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid date format. Use yyyy-MM-dd.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        String desc = txtDescription.getText().trim();

        Project p = new Project(title);
        p.setStatus(status.isEmpty() ? "To-Do" : status);
        p.setDueDate(due);
        p.setDescription(desc);

        // collaborators
        String[] collabLines = txtCollaborators.getText().split("\\r?\\n");
        for (String line : collabLines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String name = line;
            String email = "";
            int ltIndex = line.indexOf('<');
            int gtIndex = line.indexOf('>');
            if (ltIndex >= 0 && gtIndex > ltIndex) {
                name = line.substring(0, ltIndex).trim();
                email = line.substring(ltIndex + 1, gtIndex).trim();
            }
            p.addCollaborator(new Collaborator(name, email));
        }

        // notes
        String[] noteLines = txtNotes.getText().split("\\r?\\n");
        for (String line : noteLines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String content = line;
            String link = "";
            int openParen = line.indexOf('(');
            int closeParen = line.lastIndexOf(')');
            if (openParen >= 0 && closeParen > openParen) {
                content = line.substring(0, openParen).trim();
                link = line.substring(openParen + 1, closeParen).trim();
            }
            p.addNote(new ResearchNote(content, link));
        }

        return p;
    }

    private void handleAddProject() {
        Project newProject = buildProjectFromFields();
        if (newProject == null) return;

        projectManager.addProject(newProject);
        refreshProjectList();
        lstProjects.setSelectedValue(newProject, true);
    }

    private void handleUpdateProject() {
        Project selected = lstProjects.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Select a project to update.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Project updated = buildProjectFromFields();
        if (updated == null) return;

        projectManager.updateProject(selected, updated);
        refreshProjectList();
        lstProjects.setSelectedValue(updated, true);
    }

    private void handleDeleteProject() {
        Project selected = lstProjects.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Select a project to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete selected project?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            projectManager.deleteProject(selected);
            refreshProjectList();
            clearDetailFields();
        }
    }

    private void clearDetailFields() {
        txtTitle.setText("");
        txtStatus.setText("");
        txtDueDate.setText("");
        txtDescription.setText("");
        txtCollaborators.setText("");
        txtNotes.setText("");
    }

    private void refreshProjectList() {
        projectListModel.clear();
        List<Project> projects = projectManager.getProjects();
        for (Project p : projects) {
            projectListModel.addElement(p);
        }
    }

    private void handleSave() {
        try {
            projectManager.saveAll();
            JOptionPane.showMessageDialog(this, "Projects saved successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving projects: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLoad() {
        try {
            projectManager.loadAll();
            refreshProjectList();
            JOptionPane.showMessageDialog(this, "Projects loaded successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading projects: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Entry point – you can also make a separate Main class that just creates MainGUI. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}