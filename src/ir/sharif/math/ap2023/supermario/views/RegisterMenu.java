package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.AuthenticationHandler;
import ir.sharif.math.ap2023.supermario.logic.RegistrationResult;

import javax.swing.*;
import java.awt.*;

public class RegisterMenu implements View {
    private static final RegisterMenu registerMenu = new RegisterMenu();

    private final JPanel panel = new JPanel();

    private final JLabel errorLabel;
    private final JLabel successLabel;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton registerButton;

    private RegisterMenu() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        errorLabel = new JLabel("");
        errorLabel.setPreferredSize(new Dimension(MainView.getInstance().getWidth(), 24));
        errorLabel.setForeground(Color.RED);

        successLabel = new JLabel("");
        successLabel.setPreferredSize(new Dimension(MainView.getInstance().getWidth(), 24));
        successLabel.setForeground(Color.GREEN);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField("");
        usernameField.setMaximumSize(new Dimension(MainView.getInstance().getWidth() * 3 / 8, 24));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(MainView.getInstance().getWidth() * 3 / 8, 24));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            RegisterMenu.getInstance().remove();
            StartMenu.getInstance().show();
        });

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.length() == 0) {
                errorLabel.setText("Username cannot be empty.");
                successLabel.setText("");
                usernameField.setText(null);
                passwordField.setText(null);
                return;
            }
            if (password.length() == 0) {
                errorLabel.setText("Password cannot be empty.");
                successLabel.setText("");
                usernameField.setText(null);
                passwordField.setText(null);
                return;
            }

            RegistrationResult result = AuthenticationHandler.registerNewUser(username, password);

            if (result == RegistrationResult.ERROR_USERNAME_EXISTS) {
                errorLabel.setText("Username already exists.");
                successLabel.setText("");
                usernameField.setText(null);
                passwordField.setText(null);
            }
            else if (result == RegistrationResult.ERROR_UNKNOWN) {
                errorLabel.setText("An error occurred.");
                successLabel.setText("");
                usernameField.setText(null);
                passwordField.setText(null);
            }
            else if (result == RegistrationResult.SUCCESS) {
                errorLabel.setText("");
                successLabel.setText("Account created successfully. Go back to the main menu and log in.");
                usernameField.setEnabled(false);
                passwordField.setEnabled(false);
                registerButton.setEnabled(false);
            }

        });

        panel.add(errorLabel);
        panel.add(successLabel);
        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(backButton);
        panel.add(registerButton);

    }

    public static RegisterMenu getInstance() {
        return registerMenu;
    }

    @Override
    public void show() {
        MainView.getInstance().setContentView(this);
    }

    @Override
    public void remove() {
        errorLabel.setText("");
        successLabel.setText("");
        usernameField.setText(null);
        passwordField.setText(null);
        usernameField.setEnabled(true);
        passwordField.setEnabled(true);
        registerButton.setEnabled(true);

    }

    @Override
    public void update() {

    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
