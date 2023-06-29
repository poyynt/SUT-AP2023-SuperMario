package supermario.models;

import java.awt.*;

public class PipeObject {
    public int x, y;
    public PipeType type;
    public SectionObject section;
    public boolean activated;

    private transient Rectangle hitBox;

    public PipeObject() {
    }

    public PipeObject(int x, int y, PipeType type, SectionObject section, boolean activated) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.section = section;
        this.activated = activated;
        this.hitBox = new Rectangle(x * 32, y * 32, 32, 4 * 32);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
