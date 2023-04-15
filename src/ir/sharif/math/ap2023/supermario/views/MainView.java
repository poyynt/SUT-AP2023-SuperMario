package ir.sharif.math.ap2023.supermario.views;


import javax.swing.*;
import java.awt.*;

public class MainView {

    private static final MainView mainView = new MainView();
    private final JFrame frame = new JFrame("Super Mario");
    private final int width = 12 * 64;
    private final int height = 8 * 64;

    private View contentView;

    private MainView() {
        setupFrame();
    }

    public static MainView getInstance() {
        return mainView;
    }

    public void setupFrame() {
        frame.setUndecorated(true);
        frame.setSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getX();
    }

    public void update() {
        if (contentView != null) contentView.update();
        frame.repaint();
        frame.revalidate();
    }

    public JFrame getFrame() {
        return frame;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
        this.getFrame().setContentPane(contentView.getPanel());
    }
}
