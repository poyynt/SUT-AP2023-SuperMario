package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.Main;
import ir.sharif.math.ap2023.supermario.logic.AuthenticationHandler;

import javax.swing.*;

public class MainMenu implements View {

    private static final MainMenu mainMenu = new MainMenu();
    private final JPanel panel = new JPanel();

    private MainMenu() {
//        update();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            MainMenu.getInstance().remove();
//            LoginMenu.getInstance().show();
        });
        panel.add(newGameButton);

        JButton continueGameButton = new JButton("Continue Game");
        continueGameButton.addActionListener(e -> {
            MainMenu.getInstance().remove();
//            RegisterMenu.getInstance().show();
        });
        panel.add(continueGameButton);

        JButton storeButton = new JButton("Store");
        storeButton.addActionListener(e -> {
            MainMenu.getInstance().remove();
//            RegisterMenu.getInstance().show();
        });
        panel.add(storeButton);

        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(e -> {
            MainMenu.getInstance().remove();
//            RegisterMenu.getInstance().show();
        });
        panel.add(profileButton);

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
    }

    @Override
    public void remove() {
//        MainView.getInstance().getFrame().getContentPane().remove(panel);
    }

    @Override
    public void update() {
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
