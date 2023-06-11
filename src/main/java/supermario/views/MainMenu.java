package supermario.views;

import supermario.Main;
import supermario.controllers.AudioController;
import supermario.logic.AuthenticationHandler;
import supermario.logic.GameHandler;
import supermario.models.State;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class MainMenu implements View {

    private static final MainMenu mainMenu = new MainMenu();
    private final JPanel panel = new JPanel();

    private final JLabel welcomeLabel = new JLabel();
    private final JLabel coinsLabel = new JLabel();

    private MainMenu() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        welcomeLabel.setFont(new Font(
                welcomeLabel.getFont().getName(),
                Font.BOLD,
                24
        ));
        panel.add(welcomeLabel);

        panel.add(coinsLabel);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            MainMenu.getInstance().remove();
            NewGameMenu.getInstance().show();
        });
        panel.add(newGameButton);

        JButton continueGameButton = new JButton("Continue Game");
        continueGameButton.addActionListener(e -> {
            MainMenu.getInstance().remove();
            ContinueGameMenu.getInstance().show();
        });
        panel.add(continueGameButton);

        JButton storeButton = new JButton("Store");
        storeButton.addActionListener(e -> {
            MainMenu.getInstance().remove();
            StoreMenu.getInstance().show();
        });
        panel.add(storeButton);

        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(e -> {
            MainMenu.getInstance().remove();
            ProfileMenu.getInstance().update();
            ProfileMenu.getInstance().show();
        });
        panel.add(profileButton);

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(e -> {
            MainMenu.getInstance().remove();
            LeaderboardMenu.getInstance().show();
        });
        panel.add(leaderboardButton);

        JButton muteButton = new JButton("Mute");
        muteButton.addActionListener(e -> {
            AudioController.mute();
        });
        JButton unMuteButton = new JButton("Unmute");
        unMuteButton.addActionListener(e -> {
            AudioController.unMute();
        });
        panel.add(muteButton);
        panel.add(unMuteButton);

        JButton logoutButton = new JButton("Log out");
        logoutButton.addActionListener(e -> {
            AuthenticationHandler.logout();
            MainMenu.getInstance().remove();
            StartMenu.getInstance().show();
        });
        panel.add(logoutButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> Main.exit(0));
        panel.add(exitButton);
    }

    public static MainMenu getInstance() {
        return mainMenu;
    }


    @Override
    public void show() {
        AudioController.playWavAudioOnChannel("background", "Title", Clip.LOOP_CONTINUOUSLY);
        MainView.getInstance().setContentView(this);
        welcomeLabel.setText("Welcome, " + State.getCurrentUser().getUsername() + "!");
        coinsLabel.setText(("Coins: " + State.getCurrentUser().getCoins()));
    }

    @Override
    public void remove() {
        GameHandler.clearNotStartedSlots();
    }

    @Override
    public void update() {
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
