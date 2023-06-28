package supermario.logic;

import supermario.controllers.Loop;
import supermario.models.ItemObject;
import supermario.models.State;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemCollisionHandler extends Loop {
    private List<ItemCollisionHandler> instances = new CopyOnWriteArrayList<>();
    private ItemObject target;

    public ItemCollisionHandler(ItemObject target) {
        super(30.0);
        for (ItemCollisionHandler instance : instances)
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
        Rectangle hitBox = target.getHitBox();
        Rectangle marioHitBox = State.getCurrentGame().getPlayer().getHitBox();
        if (hitBox.intersects(marioHitBox))
            target.getConsumed();
    }
}
