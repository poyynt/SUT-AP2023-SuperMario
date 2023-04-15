package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.GameHandler;

import javax.swing.*;
import java.awt.*;

public class ContinueGameMenu implements View {

    private static final ContinueGameMenu continueGameMenu = new ContinueGameMenu();
    private final JPanel panel = new JPanel();

    private ContinueGameMenu() {
//        update();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public static ContinueGameMenu getInstance() {
        return continueGameMenu;
    }


    @Override
    public void show() {
//        MainView.getInstance().getFrame().setContentPane(panel);
        MainView.getInstance().setContentView(this);
        panel.removeAll();

        for (int i = 0; i < 3; i++) {
            JButton slotButton = new JButton("Slot " + (i + 1));
            int finalI = i;
            slotButton.setEnabled(GameHandler.slotIsContinuable(i));
            slotButton.addActionListener(e -> {
                GameHandler.continueGameInSlot(finalI);
                ContinueGameMenu.getInstance().remove();
                GameView.getInstance().show();
            });
            panel.add(slotButton);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            ContinueGameMenu.getInstance().remove();
            MainMenu.getInstance().show();
        });
        panel.add(Box.createRigidArea(new Dimension(getPanel().getWidth(), 40)));
        panel.add(backButton);
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
