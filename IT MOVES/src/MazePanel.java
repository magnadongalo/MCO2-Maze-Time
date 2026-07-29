import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class MazePanel extends JPanel implements ActionListener {
    private Cursor cursor;
    private final Point start, end;
    private final int WIDTH, HEIGHT;
    private boolean running = false;
    private Timer timer;
    private final int[][] maze;
    private final List<int[]> solvedPath;
    private int counter = 0;

    public MazePanel(java.awt.LayoutManager layoutManager, int[][] maze, int rows, int cols,
                     int[] start, int[] end, List<int[]> solvedPath) {
        super(layoutManager);
        WIDTH = cols * 30;
        HEIGHT = rows * 30;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.start = new Point(start[0], start[1]);
        this.end = new Point(end[0], end[1]);

        this.maze = Arrays.copyOf(maze, maze.length);
        this.solvedPath = solvedPath;

        cursor = new Cursor(this.start);
        //his.setVisible(true);
        startSimulation();
    }

    public void startSimulation() {
        this.running = true;
        timer = new Timer(100, this);
        timer.start();
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

            for (i=0; i<HEIGHT/30; i++) {
                for (j=0; j<WIDTH/30; j++) {
                    switch (maze[i][j]) {
                        case 0:
                            int tempI = i;
                            int tempJ = j;
                            if (solvedPath != null && solvedPath.stream().anyMatch(x -> x[0] == tempI && x[1] == tempJ))
                                g.setColor(Color.decode("#ffdd78"));
                            else
                                g.setColor(Color.WHITE);
                            break;
                        case 1:
                            g.setColor(Color.BLACK);
                            break;
                        case 5:
                            g.setColor(Color.BLUE);
                            break;
                        case 9:
                            g.setColor(Color.RED);
                            break;
                    }

                    g.fillRect(j*30, i*30, 30, 30);
                }
            }

            cursor.draw(g);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void checkGoal() {
        if (cursor.getPos().equals(end)) {
            running = false;
        }

        if (!running)
            timer.stop();
    }

    public void move() {
        if (counter < solvedPath.size())
            cursor.move(solvedPath.get(counter));
        counter++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //called every 25 milliseconds
        move();
        checkGoal();
        repaint();
    }
}
