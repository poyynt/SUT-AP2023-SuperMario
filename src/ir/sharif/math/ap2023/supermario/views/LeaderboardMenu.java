package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.CharacterLoader;
import ir.sharif.math.ap2023.supermario.models.State;
import ir.sharif.math.ap2023.supermario.models.User;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LeaderboardMenu implements View {

    private static final LeaderboardMenu leaderboardMenu = new LeaderboardMenu();
    private final JPanel panel = new JPanel();

    private List<StoreMenuCharacterPanel> characterPanelList = new ArrayList<>();

    private JPanel list = new JPanel();

    private LeaderboardMenu() {
//        update();
        panel.setPreferredSize(MainView.getInstance().getFrame().getPreferredSize());

        Dimension listDimension = new Dimension(MainView.getInstance().getFrame().getPreferredSize());
        listDimension.height = State.getAllUsers().size() * 240;

        list.setPreferredSize(listDimension);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(list);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            LeaderboardMenu.getInstance().remove();
            MainMenu.getInstance().show();
        });

        panel.add(scrollPane);
        panel.add(backButton);
    }

    public static LeaderboardMenu getInstance() {
        return leaderboardMenu;
    }


    @Override
    public void show() {
//        MainView.getInstance().getFrame().setContentPane(panel);
        MainView.getInstance().setContentView(this);

        list.removeAll();

        SortedSet<User> scores = new TreeSet<>(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getHighScore() == o2.getHighScore())
                    return -1;
                return -Long.compare(o1.getHighScore(), o2.getHighScore());
            }
        });

        scores.addAll(State.getAllUsers());

        System.out.println(State.getAllUsers().size());

        Dimension scorePanelDimension = new Dimension(list.getPreferredSize());
        scorePanelDimension.height = 240;
        for (User user: scores) {
            JPanel scorePanel = new JPanel();
            scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
            scorePanel.setPreferredSize(scorePanelDimension);

            String username = user.getUsername();
            long score = user.getHighScore();
            scorePanel.add(new JLabel(username));
            scorePanel.add(new JLabel("Score: " + score));

            list.add(scorePanel);
        }
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