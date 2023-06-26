package supermario.logic;

import supermario.controllers.Loop;
import supermario.models.BlockObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ItemGravityHandler extends Loop {
    private GravityItem target;
    private double vy = 0;
    private double g = 0.05;

    public ItemGravityHandler(GravityItem target) {
        super(60.0);
        this.target = target;
    }

    @Override
    public void update() {
        int y = target.getY();
        Rectangle hitBox = target.getHitBox();
        hitBox.translate(0, (int) vy);
        boolean collides = false;
        if (MapHandler.sectionObject != null) {
            for (BlockObject b : MapHandler.sectionObject.blocks) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    int oc = hitBox.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (
                            (vy >= 0 && oc == Rectangle2D.OUT_BOTTOM) ||
                                    (vy < 0 && oc == Rectangle2D.OUT_TOP))
                        collides = true;
                }
            }
        }
        if (y + vy > 20 * 32)
            collides = true;
        if (collides)
            vy = 0.;
        else {
            target.setY((int) (y + vy));
            vy += g;
        }
    }
}
