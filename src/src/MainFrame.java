// MainFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class MainFrame extends JFrame {
    private JTextField nameField, phoneField, emailField;
    private DefaultListModel<Contact> contactListModel;
    private JList<Contact> contactList;
    private JButton deleteButton;

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

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);

        JButton addButton = new JButton("Add Contact");
        deleteButton = new JButton("Delete Selected");
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(contactList), BorderLayout.CENTER);

        addButton.addActionListener(e -> addContact());
        deleteButton.addActionListener(e -> deleteSelectedContact());

        add(panel);
        setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contact contact = new Contact(name, phone, email);
        contactListModel.addElement(contact);

        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void deleteSelectedContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex != -1) {
            contactListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
