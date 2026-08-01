import java.awt.*;

public class Cursor {
    private Point pos;

    public Cursor(Point pos) {
        //predetermined starting point
        this.pos = pos;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(pos.x * 30, pos.y * 30, 30, 30);
    }

    public void move(int[] nextPos) {
        pos.setLocation(nextPos[1], nextPos[0]);
    }

    public void move(Point nextPos) {
        pos.setLocation(nextPos);
    }

    public Point getPos() {
        return pos;
    }
}
