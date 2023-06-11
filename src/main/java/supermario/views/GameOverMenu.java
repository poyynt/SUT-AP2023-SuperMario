package supermario.views;

import supermario.controllers.AudioController;
import supermario.models.State;

import javax.swing.*;
import java.awt.*;

public class GameOverMenu implements View {
    private static final GameOverMenu gameOverMenu = new GameOverMenu();

    private final JPanel panel = new JPanel();

    private final JLabel titleLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel();
    private final JButton backButton = new JButton();

    private GameOverMenu() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public static GameOverMenu getInstance() {
        return gameOverMenu;
    }

    @Override
    public void show() {
        AudioController.playWavAudioOnChannel("foreground", "GameOver", 0);
        MainView.getInstance().setContentView(this);

        panel.removeAll();

        titleLabel.setFont(new Font(
                titleLabel.getFont().getName(),
                Font.BOLD,
                24
        ));
        titleLabel.setForeground(Color.RED);
        titleLabel.setText("You Lost!");

        scoreLabel.setText("Score:" + State.getCurrentGame().calculateScore());

        backButton.setText("Back to Main Menu");
        backButton.addActionListener((e) -> {
            GameOverMenu.getInstance().remove();
            MainMenu.getInstance().show();
        });

        panel.add(titleLabel);
        panel.add(scoreLabel);
        panel.add(backButton);
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
