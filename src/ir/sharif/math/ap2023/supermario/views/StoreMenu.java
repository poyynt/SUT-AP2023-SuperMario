package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.CharacterLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StoreMenu implements View {

    private static final StoreMenu storeMenu = new StoreMenu();
    private final JPanel panel = new JPanel();

    private List<StoreMenuCharacterPanel> characterPanelList = new ArrayList<>();

    private StoreMenu() {
//        update();
        panel.setPreferredSize(MainView.getInstance().getFrame().getPreferredSize());

        JPanel grid = new JPanel();
        Dimension gridDimension = new Dimension(MainView.getInstance().getFrame().getPreferredSize());
        gridDimension.height -= 240;
        grid.setPreferredSize(gridDimension);
        grid.setLayout(new GridLayout(3, 4));
        for (String characterName: CharacterLoader.getListOfCharacters())
            characterPanelList.add(new StoreMenuCharacterPanel(CharacterLoader.getCharacter(characterName)));

        for (StoreMenuCharacterPanel characterPanel: characterPanelList)
            grid.add(characterPanel);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            StoreMenu.getInstance().remove();
            MainMenu.getInstance().show();
        });

        panel.add(grid);
        panel.add(backButton);
    }

    public static StoreMenu getInstance() {
        return storeMenu;
    }


    @Override
    public void show() {
//        MainView.getInstance().getFrame().setContentPane(panel);
        MainView.getInstance().setContentView(this);
    }

    @Override
    public void remove() {
//        MainView.getInstance().getFrame().getContentPane().remove(panel);
        for (StoreMenuCharacterPanel characterPanel: characterPanelList)
            characterPanel.reset();
    }

    @Override
    public void update() {
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
