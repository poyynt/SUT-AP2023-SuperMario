package supermario.models;

import java.util.List;

public class SectionObject {
    public int length;
    public int time;
    public List<BlockObject> blocks;
    public List<EnemyObject> enemies;
    public List<PipeObject> pipes;
    public PipeObject spawnPipe = null;
}
