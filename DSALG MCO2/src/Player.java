import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player {

    private BufferedImage img;
    private Point pos;

    public Player(Point pos) {
        //predetermined starting point
        this.pos = pos;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(pos.x * GamePanel.UNIT_SIZE, pos.y * GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
    }

    public void keyPressed(KeyEvent e, String[] maze) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
        case KeyEvent.VK_UP:
            if (maze[pos.y-1].charAt(pos.x) != '#')
                pos.translate(0, -1);
            break;
        case KeyEvent.VK_A:
        case KeyEvent.VK_LEFT:
            if (maze[pos.y].charAt(pos.x-1) != '#')
                pos.translate(-1, 0);
            break;
        case KeyEvent.VK_S:
        case KeyEvent.VK_DOWN:
            if (maze[pos.y+1].charAt(pos.x) != '#')
                pos.translate(0, 1);
            break;
        case KeyEvent.VK_D:
        case KeyEvent.VK_RIGHT:
            if (maze[pos.y].charAt(pos.x+1) != '#')
                pos.translate(1, 0);
            break;
        }
    }

    public Point getPos() {
        return pos;
    }
}
