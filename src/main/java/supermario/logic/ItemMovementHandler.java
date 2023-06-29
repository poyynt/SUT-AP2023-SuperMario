package supermario.logic;

import supermario.controllers.Loop;
import supermario.models.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemMovementHandler extends Loop {
    private List<ItemMovementHandler> instances = new CopyOnWriteArrayList<>();
    private ItemObject target;
    private double vx = 1.25;

    public ItemMovementHandler(ItemObject target) {
        super(60.0);
        for (ItemMovementHandler instance: instances)
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
        if (target.type == ItemType.COIN || target.type == ItemType.FLOWER)
            return;
        int x = target.getX();
        Rectangle hitBox = target.getHitBox();
        hitBox.translate((int) vx, 0);
        boolean collides = false;
        if (MapHandler.sectionObject != null) {
            for (BlockObject b : MapHandler.sectionObject.blocks) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    int oc = hitBox.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (
                            (vx >= 0 && oc == Rectangle2D.OUT_RIGHT) ||
                                    (vx <= 0 && oc == Rectangle2D.OUT_LEFT))
                        collides = true;
                }
            }
            for (PipeObject b : MapHandler.sectionObject.pipes) {
                Rectangle blockHitBox = b.getHitBox();
                if (blockHitBox.intersects(hitBox)) {
                    int oc = hitBox.outcode(blockHitBox.getCenterX(), blockHitBox.getCenterY());
                    if (
                            (vx >= 0 && oc == Rectangle2D.OUT_RIGHT) ||
                                    (vx <= 0 && oc == Rectangle2D.OUT_LEFT))
                        collides = true;
                }
            }
        }
        if (collides)
            vx = -vx;
        else {
            target.setX((int) (x + vx));
        }
    }
}
