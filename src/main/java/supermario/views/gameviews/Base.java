package supermario.views.gameviews;

import supermario.views.MainView;

import javax.swing.*;

public abstract class Base extends JPanel {
    public Base() {
        setPreferredSize(MainView.getInstance().getFrame().getPreferredSize());
        setOpaque(false);
    }

}
