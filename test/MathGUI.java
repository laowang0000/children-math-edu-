import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MathGUI extends JFrame {
    private UserManager userManager;
    private JTextField usernameField, nameField;
    private JPasswordField passwordField;
    private JButton registerButton, loginButton;
    private JPanel mainPanel;

    public MathGUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Children Math Learning System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        mainPanel = new JPanel(new CardLayout());
        add(mainPanel);

        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createRegisterPanel(), "Register");

        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        panel.add(loginButton);
        panel.add(registerButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = userManager.loginUser(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                // Open the user menu with the logged-in user.
                new UserMenu(userManager, user);
                dispose(); // Close the login screen.
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> showRegisterPanel());

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Username:"));
        JTextField regUsernameField = new JTextField();
        panel.add(regUsernameField);

        panel.add(new JLabel("Password:"));
        JPasswordField regPasswordField = new JPasswordField();
        panel.add(regPasswordField);

        JButton regConfirmButton = new JButton("Register");
        JButton backToLoginButton = new JButton("Back to Login");
        panel.add(regConfirmButton);
        panel.add(backToLoginButton);

        regConfirmButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = regUsernameField.getText();
            String password = new String(regPasswordField.getPassword());
            if (userManager.registerUser(name, username, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please log in.");
                showLoginPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backToLoginButton.addActionListener(e -> showLoginPanel());

        return panel;
    }

    private void showLoginPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Login");
    }

    private void showRegisterPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Register");
    }
}
