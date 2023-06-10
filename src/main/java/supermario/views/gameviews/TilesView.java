package supermario.views.gameviews;

import supermario.logic.MapHandler;

import java.awt.*;

public class TilesView extends Base {

    public TilesView() {
        super();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        MapHandler.loadSection();
        MapHandler.paint(graphics2D);
    }
}
