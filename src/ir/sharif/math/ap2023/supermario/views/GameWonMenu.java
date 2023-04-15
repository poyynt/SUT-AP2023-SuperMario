package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.models.State;

import javax.swing.*;
import java.awt.*;

public class GameWonMenu implements View {
    private static final GameWonMenu gameWonMenu = new GameWonMenu();

    private final JPanel panel = new JPanel();

    private final JLabel titleLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel();
    private final JButton backButton = new JButton();

    private GameWonMenu() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public static GameWonMenu getInstance() {
        return gameWonMenu;
    }

    @Override
    public void show() {
        MainView.getInstance().setContentView(this);

        panel.removeAll();

        titleLabel.setFont(new Font(
                titleLabel.getFont().getName(),
                Font.BOLD,
                24
        ));
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setText("You Won!");

        scoreLabel.setText("Score:" + State.getCurrentGame().calculateScore());

        backButton.setText("Back to Main Menu");
        backButton.addActionListener((e) -> {
            GameWonMenu.getInstance().remove();
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
