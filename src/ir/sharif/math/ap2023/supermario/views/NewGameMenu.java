package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.GameHandler;

import javax.swing.*;
import java.awt.*;

public class NewGameMenu implements View {

    private static final NewGameMenu newGameMenu = new NewGameMenu();
    private final JPanel panel = new JPanel();

    private NewGameMenu() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 0; i < 3; i++) {
            JButton slotButton = new JButton("Slot " + (i + 1));
            int finalI = i;
            slotButton.addActionListener(e -> {
                GameHandler.makeNewGameInSlot(finalI);
                NewGameMenu.getInstance().remove();
                NewGameOptionsMenu.getInstance().show();
            });
            panel.add(slotButton);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            NewGameMenu.getInstance().remove();
            MainMenu.getInstance().show();
        });
        panel.add(Box.createRigidArea(new Dimension(getPanel().getWidth(), 40)));
        panel.add(backButton);
    }

    public static NewGameMenu getInstance() {
        return newGameMenu;
    }


    @Override
    public void show() {
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
