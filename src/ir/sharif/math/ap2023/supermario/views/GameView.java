package ir.sharif.math.ap2023.supermario.views;

import ir.sharif.math.ap2023.supermario.logic.GameHandler;
import ir.sharif.math.ap2023.supermario.logic.GravityHandler;
import ir.sharif.math.ap2023.supermario.logic.MovementHandler;
import ir.sharif.math.ap2023.supermario.models.KeyboardState;
import ir.sharif.math.ap2023.supermario.models.State;
import ir.sharif.math.ap2023.supermario.views.gameviews.PlayerView;
import ir.sharif.math.ap2023.supermario.views.gameviews.TilesView;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class GameView implements View {
    private static final GameView gameView = new GameView();

    private final JPanel panel = new JPanel();

    private final TilesView tilesView = new TilesView();
    private final PlayerView playerView = new PlayerView();

    private GameView() {
        panel.setPreferredSize(MainView.getInstance().getFrame().getPreferredSize());
        panel.setLayout(new OverlayLayout(panel));

        panel.add(playerView);
        panel.add(tilesView);
    }

    public static GameView getInstance() {
        return gameView;
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
        State.getCurrentGame().incrementFramesElapsed();
        MovementHandler.tick();
        GravityHandler.tick();


        tilesView.tick();

        if (KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_SHIFT, false)
                && KeyboardState.pressedKeys.getOrDefault(KeyEvent.VK_P, false)) {
            GameHandler.pause();
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
