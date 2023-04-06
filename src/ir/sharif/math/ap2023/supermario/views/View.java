package ir.sharif.math.ap2023.supermario.views;

import javax.swing.*;

interface View {
    void show();

    void remove();

    void update();

    JPanel getPanel();

}
