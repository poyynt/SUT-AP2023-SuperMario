package supermario.models;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SectionObject {
    public int length;
    public int time;
    public CopyOnWriteArrayList<BlockObject> blocks;
    public CopyOnWriteArrayList<EnemyObject> enemies;
    public CopyOnWriteArrayList<PipeObject> pipes;
    public transient List<ItemObject> items = new CopyOnWriteArrayList<>();
    public transient List<BulletObject> bullets = new CopyOnWriteArrayList<>();
    public PipeObject spawnPipe = null;
}
