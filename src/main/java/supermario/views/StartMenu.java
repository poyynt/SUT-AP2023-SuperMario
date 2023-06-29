package supermario.views;

import supermario.Main;
import supermario.controllers.AudioController;
import supermario.logic.MapHandler;

import javax.sound.sampled.Clip;
import javax.swing.*;

public class StartMenu implements View {

    private static final StartMenu startMenu = new StartMenu();
    private final JPanel panel = new JPanel();

    private StartMenu() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(e -> {
            StartMenu.getInstance().remove();
            LoginMenu.getInstance().show();
        });
        panel.add(loginButton);
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            StartMenu.getInstance().remove();
            RegisterMenu.getInstance().show();
        });
        panel.add(registerButton);
        JFileChooser fileChooser = new JFileChooser();
        JButton uploadButton = new JButton("Upload Game File");
        uploadButton.addActionListener(e -> {
            int rv = fileChooser.showOpenDialog(panel);
            if (rv == JFileChooser.APPROVE_OPTION) {
                MapHandler.setCustomMapFile(fileChooser.getSelectedFile());
            }
        });
        panel.add(uploadButton);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> Main.exit(0));
        panel.add(exitButton);
    }

    public static StartMenu getInstance() {
        return startMenu;
    }


    @Override
    public void show() {
        AudioController.playWavAudioOnChannel("background", "Title", Clip.LOOP_CONTINUOUSLY);
        MainView.getInstance().setContentView(this);
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
