import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Panel extends JPanel implements ActionListener {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final int UNIT_SIZE = 20;
    public static final int DELAY = 25;

    protected String[] maze;
    protected Player player;
    protected boolean running = false;
    protected Timer timer;
    protected Point goal;

    protected JButton exit = new JButton("<html><center>EXIT<br>PROGRAM</center></html>");

    public Panel(Maze maze) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        this.maze = maze.getMaze();
        goal = new Point(getGoalPos());

        exit.setFont(new Font("Nintendo NES Font", Font.PLAIN, 15));
        exit.setHorizontalAlignment(SwingConstants.CENTER);
        exit.addActionListener(this);

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
            g.setColor(Color.GRAY);

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
            musicPath = new File("a winner is you.wav");

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
        } catch (Exception _){}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //called every DELAY milliseconds
        checkGoal();
        repaint();
    }
}
