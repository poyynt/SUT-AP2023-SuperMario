package supermario.logic;

import supermario.controllers.Loop;
import supermario.models.EnemyObject;
import supermario.models.ItemObject;
import supermario.models.State;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EnemyCollisionHandler extends Loop {
    private List<EnemyCollisionHandler> instances = new CopyOnWriteArrayList<>();
    protected EnemyObject target;

    public EnemyCollisionHandler(EnemyObject target) {
        super(30.0);
        for (EnemyCollisionHandler instance : instances)
            if (instance.target == target)
                instance.stop();
        instances.removeIf(i -> i.target == target);
        this.target = target;
        instances.add(this);
    }
}
