package supermario.views.gameviews;

import supermario.logic.MovementHandler;
import supermario.logic.SpriteLoader;
import supermario.models.GameState;
import supermario.models.State;

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
        int playerState = State.getCurrentGame().getPlayer().getState();
        if (playerState == 0) {
            if (MovementHandler.isPlayerFacingRight())
                graphics2D.drawImage(
                        SpriteLoader.loadSpriteForCharacter(gameState.getCharacter(), 0),
                        gameState.getPlayerX() - gameState.getScreenX() + 32,
                        gameState.getPlayerY(),
                        -32,
                        32,
                        null
                );
            else
                graphics2D.drawImage(
                        SpriteLoader.loadSpriteForCharacter(gameState.getCharacter(), 0),
                        gameState.getPlayerX() - gameState.getScreenX(),
                        gameState.getPlayerY(),
                        null
                );
        }
        else {
            if (MovementHandler.isPlayerFacingRight())
                graphics2D.drawImage(
                        SpriteLoader.loadSpriteForCharacter(gameState.getCharacter(), playerState),
                        gameState.getPlayerX() - gameState.getScreenX() + 32,
                        gameState.getPlayerY() - 32,
                        -32,
                        64,
                        null
                );
            else
                graphics2D.drawImage(
                        SpriteLoader.loadSpriteForCharacter(gameState.getCharacter(), playerState),
                        gameState.getPlayerX() - gameState.getScreenX(),
                        gameState.getPlayerY() - 32,
                        null
                );
        }
    }
}
