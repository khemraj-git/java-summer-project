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
    private JButton deleteButton, searchButton;
    private static final String FILE_NAME = "contacts.csv";

    public MainFrame() {
        setTitle("Digital Address Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        searchField = new JTextField();
        addressField = new JTextField();
        dobField = new JTextField();



        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Search:"));
        inputPanel.add(searchField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Date-of-Birth:"));
        inputPanel.add(dobField);

        JButton addButton = new JButton("Add Contact");
        deleteButton = new JButton("Delete Selected");
        searchButton = new JButton("Search");
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(contactList), BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.SOUTH);


        addButton.addActionListener(e -> addContact());
        deleteButton.addActionListener(e -> deleteSelectedContact());
        searchButton.addActionListener(e -> searchContact());


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




}
