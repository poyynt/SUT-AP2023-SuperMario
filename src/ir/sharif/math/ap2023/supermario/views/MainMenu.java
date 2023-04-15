package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.AuthenticationHandler;
import ir.sharif.math.ap2023.supermario.logic.GameHandler;
import ir.sharif.math.ap2023.supermario.models.State;

import javax.swing.*;
import java.awt.*;

public class MainMenu implements View {

    private static final MainMenu mainMenu = new MainMenu();
    private final JPanel panel = new JPanel();

    private final JLabel welcomeLabel = new JLabel();
    private final JLabel coinsLabel = new JLabel();

    private MainMenu() {
//        update();
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

        JButton logoutButton = new JButton("Log out");
        logoutButton.addActionListener(e -> {
            AuthenticationHandler.logout();
            MainMenu.getInstance().remove();
            StartMenu.getInstance().show();
        });
        panel.add(logoutButton);
    }

    public static MainMenu getInstance() {
        return mainMenu;
    }


    @Override
    public void show() {
//        MainView.getInstance().getFrame().setContentPane(panel);
        MainView.getInstance().setContentView(this);
        welcomeLabel.setText("Welcome, " + State.getCurrentUser().getUsername() + "!");
        coinsLabel.setText(("Coins: " + State.getCurrentUser().getCoins()));
    }

    @Override
    public void remove() {
//        MainView.getInstance().getFrame().getContentPane().remove(panel);
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
