package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.models.State;
import ir.sharif.math.ap2023.supermario.models.User;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class LeaderboardMenu implements View {

    private static final LeaderboardMenu leaderboardMenu = new LeaderboardMenu();
    private final JPanel panel = new JPanel();

    private final JPanel list = new JPanel();

    private LeaderboardMenu() {
        panel.setPreferredSize(MainView.getInstance().getFrame().getPreferredSize());

        Dimension listDimension = new Dimension(MainView.getInstance().getFrame().getPreferredSize());
        listDimension.height = State.getAllUsers().size() * 80;

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
        MainView.getInstance().setContentView(this);

        list.removeAll();

        SortedSet<User> scores = new TreeSet<>((o1, o2) -> {
            if (o1.getHighScore() == o2.getHighScore())
                return -1;
            return -Long.compare(o1.getHighScore(), o2.getHighScore());
        });

        scores.addAll(State.getAllUsers());

        Dimension scorePanelDimension = new Dimension(list.getPreferredSize());
        scorePanelDimension.height = 80;
        int scoreRank = 1;
        for (User user: scores) {
            JPanel scorePanel = new JPanel();
            scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
            scorePanel.setPreferredSize(scorePanelDimension);
            scorePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            String username = user.getUsername();
            long score = user.getHighScore();
            scorePanel.add(new JLabel("#" + scoreRank));
            scorePanel.add(Box.createRigidArea(
                    new Dimension(
                            scorePanelDimension.width / 32,
                            scorePanelDimension.height)
            ));
            JLabel usernameLabel = new JLabel();
            usernameLabel.setText(username);
            if (user.equals(State.getCurrentUser())) {
                usernameLabel.setText("You");
                usernameLabel.setForeground(Color.GREEN);
            }
            scorePanel.add(usernameLabel);

            scorePanel.add(Box.createRigidArea(
                    new Dimension(
                            scorePanelDimension.width / 2,
                            scorePanelDimension.height)
            ));
            scorePanel.add(new JLabel("Score: " + score));

            list.add(scorePanel);
            scoreRank++;
        }
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
