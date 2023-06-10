package supermario.views;

import supermario.logic.AuthenticationHandler;
import supermario.logic.LoginResult;

import javax.swing.*;
import java.awt.*;

public class LoginMenu implements View {
    private static final LoginMenu loginMenu = new LoginMenu();

    private final JPanel panel = new JPanel();

    private final JLabel errorLabel;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    private LoginMenu() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        errorLabel = new JLabel("");
        errorLabel.setPreferredSize(new Dimension(MainView.getInstance().getWidth(), 24));
        errorLabel.setForeground(Color.RED);

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
            LoginMenu.getInstance().remove();
            StartMenu.getInstance().show();
        });

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            LoginResult result = AuthenticationHandler.login(username, password);
            if (result == LoginResult.ERROR_INCORRECT_CREDENTIALS) {
                errorLabel.setText("Username or password is incorrect.");
                passwordField.setText(null);
            }
            else if (result == LoginResult.ERROR_UNKNOWN) {
                errorLabel.setText("An error occurred.");
                passwordField.setText(null);
            }
            else if (result == LoginResult.SUCCESS) {
                LoginMenu.getInstance().remove();
                MainMenu.getInstance().show();
            }
        });

        panel.add(errorLabel);
        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(backButton);
        panel.add(loginButton);

    }

    public static LoginMenu getInstance() {
        return loginMenu;
    }

    @Override
    public void show() {
        MainView.getInstance().setContentView(this);
    }

    @Override
    public void remove() {
        errorLabel.setText("");
        usernameField.setText(null);
        passwordField.setText(null);
    }

    @Override
    public void update() {

    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
