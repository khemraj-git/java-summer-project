// MainFrame.java
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;




class MainFrame extends JFrame {
    private JTextField nameField, phoneField, emailField, searchField, addressField, dobField;
    private DefaultListModel<Contact> contactListModel;
    private JList<Contact> contactList;
    private ArrayList<Contact> fullList = new ArrayList<>();
    private JButton addButton, deleteButton, searchButton, viewButton;
    private JScrollPane contactScrollPane;
    private static final String FILE_NAME = "contacts.csv";

    public MainFrame() {
        setTitle("Digital Address Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));


        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        searchField = new JTextField();
        addressField = new JTextField();
        dobField = new JTextField();



        inputPanel.add(labeledField("Name:", nameField));
        inputPanel.add(labeledField("Phone:", phoneField));
        inputPanel.add(labeledField("Email:", emailField));
        inputPanel.add(labeledField("Address:", addressField));
        inputPanel.add(labeledField("Date-of-Birth:", dobField));
        inputPanel.add(labeledField("Search:", searchField));


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addButton = new JButton("Add Contact");
        deleteButton = new JButton("Delete Selected");
        searchButton = new JButton("Search");
        viewButton = new JButton("View Contacts");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(viewButton);

        panel.add(buttonPanel, BorderLayout.SOUTH); // ✅ Add to UI


        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);

        panel.add(inputPanel, BorderLayout.NORTH);
        contactScrollPane = new JScrollPane(contactList);
        contactScrollPane.setVisible(false); // hide list initially
        panel.add(contactScrollPane, BorderLayout.CENTER);

        panel.add(buttonPanel, BorderLayout.SOUTH);



        addButton.addActionListener(e -> addContact());
        deleteButton.addActionListener(e -> deleteSelectedContact());
        searchButton.addActionListener(e -> searchContact());
        viewButton.addActionListener(e -> toggleView());



        add(panel);
        loadContactsFromCSV();
        setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String DOB = dobField.getText();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || DOB.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contact contact = new Contact(name, phone, email, address, DOB);
        contactListModel.addElement(contact);
        fullList.add(contact);
        saveContactsToCSV();


        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        dobField.setText("");
    }

    private void deleteSelectedContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex != -1) {
            Contact removed = contactListModel.getElementAt(selectedIndex);
            contactListModel.remove(selectedIndex);    // remove from GUI list
            fullList.remove(removed);                 // remove from full list (CSV logic)
            saveContactsToCSV();                      // save updated list to CSV
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void searchContact() {
        String query = searchField.getText().toLowerCase();
        contactListModel.clear();
        for (Contact c : fullList) {
            if (c.getName().toLowerCase().contains(query) ||
                    c.getPhone().toLowerCase().contains(query) ||
                    c.getEmail().toLowerCase().contains(query)) {
                contactListModel.addElement(c);
            }
        }
    }
    private void saveContactsToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Contact c : fullList) {
                writer.println(c.getName() + "," + c.getPhone() + "," + c.getEmail() + "," + c.getAddress() + "," + c.getDob());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContactsFromCSV() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                fullList.clear();
                contactListModel.clear();
                while (scanner.hasNextLine()) {
                    String[] parts = scanner.nextLine().split(",");
                    if (parts.length == 5) {
                        Contact c = new Contact(parts[0], parts[1], parts[2], parts[3], parts[4]);
                        fullList.add(c);
                        contactListModel.addElement(c);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void toggleView() {
        contactScrollPane.setVisible(!contactScrollPane.isVisible());
        revalidate();
        repaint();
    }

    private JPanel labeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(new JLabel(labelText), BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }






}
