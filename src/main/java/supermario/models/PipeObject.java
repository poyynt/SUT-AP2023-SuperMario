package supermario.models;

import java.awt.*;

public class PipeObject {
    public final int x, y;
    public final PipeType type;
    public final SectionObject section;
    public final boolean activated;

    private transient final Rectangle hitBox;

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
