package ir.sharif.math.ap2023.supermario.views.gameviews;

import ir.sharif.math.ap2023.supermario.logic.MovementHandler;
import ir.sharif.math.ap2023.supermario.logic.SpriteLoader;
import ir.sharif.math.ap2023.supermario.models.GameState;
import ir.sharif.math.ap2023.supermario.models.State;

import java.awt.*;

public class PlayerView extends Base {

    public PlayerView() {
        super();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        GameState gameState = State.getCurrentGame();
        if (MovementHandler.isPlayerFacingRight())
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteForCharacter(gameState.getCharacter()),
                    gameState.getPlayerX() - gameState.getScreenX() + 64,
                    gameState.getPlayerY(),
                    -64,
                    64,
                    null
            );
        else
            graphics2D.drawImage(
                    SpriteLoader.loadSpriteForCharacter(gameState.getCharacter()),
                    gameState.getPlayerX() - gameState.getScreenX(),
                    gameState.getPlayerY(),
                    null
            );
    }
}
