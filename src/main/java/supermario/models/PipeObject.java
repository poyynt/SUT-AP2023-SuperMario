package supermario.models;

public class PipeObject {
    public final int x, y;
    public final PipeType type;
    public final SectionObject section;
    public final boolean activated;

    public PipeObject(int x, int y, PipeType type, SectionObject section, boolean activated) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.section = section;
        this.activated = activated;
    }
}
