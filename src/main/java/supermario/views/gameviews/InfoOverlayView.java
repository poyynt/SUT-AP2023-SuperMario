package supermario.views.gameviews;

import supermario.logic.TimeHandler;
import supermario.models.GameState;
import supermario.models.State;

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
        FontMetrics fontMetrics = graphics2D.getFontMetrics();

        graphics2D.setColor(Color.BLUE);
        String help = "Shift + P to pause / exit. Jump with space, move with ← →, enter doors with ↑. ";
        graphics2D.drawString(
                help,
                getPreferredSize().width - fontMetrics.stringWidth(help),
                getPreferredSize().height
        );

        graphics2D.setFont(
                new Font(
                        graphics2D.getFont().getName(),
                        Font.PLAIN,
                        20
                )
        );
        graphics2D.setColor(Color.LIGHT_GRAY);

        String level = "World " + State.getCurrentGame().getLevel() + "᠆" + State.getCurrentGame().getSection();
        graphics2D.drawString(
                level,
                getPreferredSize().width / 2 - fontMetrics.stringWidth(level) / 2,
                fontMetrics.getHeight()
        );

        String lives = " ♥︎ ".repeat(Math.max(0, gameState.getLives()));
        graphics2D.drawString(lives, 0, 2 * fontMetrics.getHeight());

        String coins = " \uD83D\uDFE1 x" + gameState.getCoins();

        graphics2D.drawString(
                coins,
                getPreferredSize().width / 2 - fontMetrics.stringWidth(coins) / 2,
                2 * fontMetrics.getHeight()
                );

        String timeRemaining = TimeHandler.calculateTimeRemaining() + "s";

        graphics2D.drawString(
                timeRemaining,
                getPreferredSize().width - 2 * fontMetrics.stringWidth(timeRemaining),
                2 * fontMetrics.getHeight()
        );
    }
}
