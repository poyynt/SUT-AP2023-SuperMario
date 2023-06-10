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
