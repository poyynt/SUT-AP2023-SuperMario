package ir.sharif.math.ap2023.supermario.views.gameviews;

import ir.sharif.math.ap2023.supermario.logic.TimeHandler;
import ir.sharif.math.ap2023.supermario.models.GameState;
import ir.sharif.math.ap2023.supermario.models.State;

import java.awt.*;

public class InfoOverlayView extends Base {

    public InfoOverlayView() {
        super();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        GameState gameState = State.getCurrentGame();
        graphics2D.setFont(
                new Font(
                        graphics2D.getFont().getName(),
                        Font.PLAIN,
                        20
                )
        );
        graphics2D.setColor(Color.LIGHT_GRAY);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();

        String lives = " ♥︎ ".repeat(Math.max(0, gameState.getLives()));
        graphics2D.drawString(lives, 0, fontMetrics.getHeight());

        String coins = " \uD83D\uDFE1 x" + gameState.getCoins();

        graphics2D.drawString(
                coins,
                getPreferredSize().width / 2 - fontMetrics.stringWidth(coins) / 2,
                fontMetrics.getHeight()
                );

        String timeRemaining = TimeHandler.calculateTimeRemaining() + "s";

        graphics2D.drawString(
                timeRemaining,
                getPreferredSize().width - fontMetrics.stringWidth(timeRemaining),
                fontMetrics.getHeight()
        );
    }
}
