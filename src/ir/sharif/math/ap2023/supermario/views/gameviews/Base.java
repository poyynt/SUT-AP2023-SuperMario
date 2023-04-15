package ir.sharif.math.ap2023.supermario.views.gameviews;

import ir.sharif.math.ap2023.supermario.views.MainView;

import javax.swing.*;

public abstract class Base extends JPanel {
    public Base() {
        setPreferredSize(MainView.getInstance().getFrame().getPreferredSize());
        setOpaque(false);
    }

    public abstract void tick();
}
