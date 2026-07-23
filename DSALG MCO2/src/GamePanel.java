import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final int UNIT_SIZE = 20;
    public static final int GAME_UNITS = WIDTH * HEIGHT / UNIT_SIZE;
    public static final int DELAY = 25;

    private String[] maze;
    private Player player;
    private boolean running = false;
    private Timer timer;
    private Point goal;

    public GamePanel(Maze maze) {
        Point temp;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        this.maze = maze.getMaze();
        goal = new Point(getGoalPos());
        player = new Player(getStartingPos());

        this.addKeyListener(this);
        startSimulation();
    }

    public void startSimulation() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Point getStartingPos() {
        int i, j;
        int x=0, y=0;

        for (i=0; i<maze.length; i++) {
            for (j=0; j<maze.length; j++) {
                if (maze[i].charAt(j) == 'S') {
                    x = j;
                    y = i;
                }
            }
        }

        return new Point(x, y);
    }

    public Point getGoalPos() {
        int i, j;
        int x=0, y=0;

        for (i=0; i<maze.length; i++) {
            for (j=0; j<maze.length; j++) {
                if (maze[i].charAt(j) == 'G') {
                    x = j;
                    y = i;
                }
            }
        }

        return new Point(x, y);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        int i, j;

        if (running) {
            for (i=0; i<HEIGHT/UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, WIDTH, i*UNIT_SIZE);
            }

            for (i=0; i<maze.length; i++) {
                for (j=0; j<maze.length; j++) {
                    if (maze[i].charAt(j) != ' ') {
                        if (maze[i].charAt(j) == '#') {
                            g.setColor(Color.WHITE);
                            g.fillRect(j*UNIT_SIZE, i*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        }
                        else if (maze[i].charAt(j) == 'S') {
                            g.setColor(Color.BLUE);
                            g.fillRect(j*UNIT_SIZE, i*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        }
                        else if (maze[i].charAt(j) == 'G') {
                            g.setColor(Color.RED);
                            g.fillRect(j*UNIT_SIZE, i*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        }
                    }
                }
            }

            player.draw(g);
        }
        else
            reachGoal(g);
    }

    public void checkGoal() {
        if (player.getPos().equals(goal)) {
            running = false;
        }

        if (!running)
            timer.stop();
    }

    public void reachGoal(Graphics g) {
        File musicPath;
        FontMetrics metrics;

        g.setColor(Color.GREEN);
        g.setFont(new Font("Nintendo NES Font", Font.PLAIN, 40));
        metrics = getFontMetrics(g.getFont());
        g.drawString("GOAL REACHED!", (WIDTH - metrics.stringWidth("GOAL REACHED!"))/2, HEIGHT/2);

        try {
            musicPath = new File("ff3 fanfare short.wav");

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
        } catch (Exception _){}
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e, maze);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //called every DELAY milliseconds
        checkGoal();
        repaint();
    }
}
