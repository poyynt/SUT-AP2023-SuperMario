package supermario.views;

import supermario.logic.*;
import supermario.models.KeyboardState;
import supermario.models.State;
import supermario.views.gameviews.InfoOverlayView;
import supermario.views.gameviews.PlayerView;
import supermario.views.gameviews.TilesView;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class GameView implements View {
    private static final GameView gameView = new GameView();

    private final JPanel panel = new JPanel();

    @SuppressWarnings("FieldCanBeLocal")
    private final TilesView tilesView = new TilesView();
    @SuppressWarnings("FieldCanBeLocal")
    private final PlayerView playerView = new PlayerView();
    @SuppressWarnings("FieldCanBeLocal")
    private final InfoOverlayView infoOverlayView = new InfoOverlayView();

    private GameView() {
        panel.setPreferredSize(MainView.getInstance().getFrame().getPreferredSize());
        panel.setLayout(new OverlayLayout(panel));

        panel.add(infoOverlayView);
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
        MapHandler.loadSection();
        MovementHandler.tick();
        GravityHandler.tick();
        ItemCollisionHandler.handleItemCollisions();
        TimeHandler.tick();
        MapHandler.itemsTick();

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