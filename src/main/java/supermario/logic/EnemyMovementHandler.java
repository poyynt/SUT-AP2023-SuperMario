package supermario.logic;

import supermario.controllers.Loop;
import supermario.models.EnemyObject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EnemyMovementHandler extends Loop {
    private List<EnemyMovementHandler> instances = new CopyOnWriteArrayList<>();
    protected EnemyObject target;

    public EnemyMovementHandler(EnemyObject target) {
        super(15.0);
        for (EnemyMovementHandler instance: instances)
            if (instance.target == target)
                instance.stop();
        instances.removeIf(i -> i.target == target);
        this.target = target;
        instances.add(this);
    }
}
