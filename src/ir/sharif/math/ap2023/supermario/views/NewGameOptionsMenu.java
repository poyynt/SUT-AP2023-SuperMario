package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.GameHandler;
import ir.sharif.math.ap2023.supermario.models.GameCharacter;
import ir.sharif.math.ap2023.supermario.models.GameState;
import ir.sharif.math.ap2023.supermario.models.State;
import ir.sharif.math.ap2023.supermario.models.User;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class NewGameOptionsMenu implements View {
    private static final NewGameOptionsMenu newGameOptionsMenu = new NewGameOptionsMenu();

    private final JPanel panel = new JPanel();

    private NewGameOptionsMenu() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    }

    public static NewGameOptionsMenu getInstance() {
        return newGameOptionsMenu;
    }

    @Override
    public void show() {
        MainView.getInstance().setContentView(this);

        panel.removeAll();

        ButtonGroup difficultyButtonGroup = new ButtonGroup();
        boolean flag = false;
        for (String s: GameState.allDifficulties) {
            JRadioButton radioButton = new JRadioButton();
            if (!flag) {
                radioButton.setSelected(true);
                flag = true;
            }
            radioButton.setText(s);
            radioButton.addItemListener(e -> State.getCurrentGame().setDifficulty(s));
            panel.add(radioButton);
            difficultyButtonGroup.add(radioButton);
        }

        panel.add(new JSeparator());

        User currentUser = State.getCurrentUser();
        ButtonGroup characterButtonGroup = new ButtonGroup();
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
            panel.add(radioButton);
            characterButtonGroup.add(radioButton);
        }

        panel.add(new JSeparator());

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            GameHandler.startNewGame();
            NewGameOptionsMenu.getInstance().remove();
            GameView.getInstance().show();
        });
        panel.add(startButton);
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
