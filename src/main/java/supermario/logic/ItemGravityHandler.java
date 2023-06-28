package supermario.logic;

import java.util.List;
import supermario.controllers.Loop;
import supermario.models.BlockObject;
import supermario.models.ItemObject;
import supermario.models.ItemType;
import supermario.models.State;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemGravityHandler extends Loop {
    private List<ItemGravityHandler> instances = new CopyOnWriteArrayList<>();
    private GravityItem target;
    private double vy = 0;
    private double g = 0.08;
    private int groundFrames = 0;
    private int framesElapsed = 0;

    public ItemGravityHandler(GravityItem target) {
        super(60.0);
        for (ItemGravityHandler instance: instances)
            if (instance.target == target)
                instance.stop();
        instances.removeIf(i -> i.target == target);
        this.target = target;
        instances.add(this);
    }

    @Override
    public void update() {
        if (State.getCurrentGame() == null || !State.getCurrentGame().isRunning())
            return;
        framesElapsed++;
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
        if (y + vy >= 20 * 32) {
            collides = true;
        }
        if (y + vy + 5 >= 20 * 32)
            groundFrames++;
        else {
            groundFrames = 0;
            System.out.println("DD DD" + y + " " + vy);
        }
        if (collides)
            vy = 0.;
        else {
            target.setY((int) (y + vy));
            vy += g;
        }
        if (((ItemObject) target).type == ItemType.STAR && groundFrames >= 1. * getFPS()) {
            vy = -3.54;
        }
    }
}
