package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.AuthenticationHandler;
import ir.sharif.math.ap2023.supermario.logic.LoginResult;
import ir.sharif.math.ap2023.supermario.models.GameCharacter;
import ir.sharif.math.ap2023.supermario.models.State;
import ir.sharif.math.ap2023.supermario.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class ProfileMenu implements View {
    private static final ProfileMenu profileMenu = new ProfileMenu();

    private final JPanel panel = new JPanel();

    private JLabel usernameLabel = new JLabel();
    private JLabel highScoreLabel = new JLabel();
    private JPanel radioButtonPanel = new JPanel();

    private ProfileMenu() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        usernameLabel.setFont(new Font(
                usernameLabel.getFont().getName(),
                Font.BOLD,
                24
        ));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            ProfileMenu.getInstance().remove();
            MainMenu.getInstance().show();
        });

        panel.add(usernameLabel);
        panel.add(highScoreLabel);
        panel.add(radioButtonPanel);

        panel.add(backButton);
    }

    public static ProfileMenu getInstance() {
        return profileMenu;
    }

    @Override
    public void show() {
//        MainView.getInstance().getFrame().setContentPane(panel);
        MainView.getInstance().setContentView(this);

        User currentUser = State.getCurrentUser();
        usernameLabel.setText(currentUser.getUsername());
        highScoreLabel.setText("High Score: " + currentUser.getHighScore());

        radioButtonPanel.removeAll();

        ButtonGroup buttonGroup = new ButtonGroup();
        for (GameCharacter c: currentUser.getOwnedCharacters()) {
            JRadioButton radioButton = new JRadioButton();
            radioButton.setText(c.getName());
            if (currentUser.getCurrentCharacter().equals(c))
                radioButton.setSelected(true);
            radioButton.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    currentUser.setCurrentCharacter(c);
                }
            });

            radioButtonPanel.add(radioButton);
            buttonGroup.add(radioButton);
        }
    }

    @Override
    public void remove() {
    }

    @Override
    public void update() {

    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
