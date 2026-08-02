import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class MazePanel extends JPanel implements ActionListener {
    private final Cursor cursor;
    private final Point start, end;
    private final int WIDTH, HEIGHT;
    private boolean running = false;
    private final Timer timer = new Timer(50, this);
    private final int[][] maze;
    private List<int[]> solvedPath = null;
    private final List<int[]> checkedPaths = null;
    private int counter = 0;

    private MazeSolver.BFSInstance bfsInstance;
    private MazeSolver.DFSInstance dfsInstance;
    private Astar.StarInstance astarInstance;
    private Timer animationTimer;
    private final Maze reference;


    public MazePanel(java.awt.LayoutManager layoutManager, int[][] maze, int rows, int cols,
                     int[] start, int[] end, List<int[]> solvedPath, Maze reference) {
        super(layoutManager);
        WIDTH = cols * 30;
        HEIGHT = rows * 30;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.reference = reference;

        this.start = new Point(start[1], start[0]);
        this.end = new Point(end[1], end[0]);

        this.maze = Arrays.copyOf(maze, maze.length);
        this.solvedPath = solvedPath;

        cursor = new Cursor(this.start);
        this.setVisible(true);
        //startSimulation();
    }

    public void startAnimatedBFS(int[][] maze, int[] start, int[] end, int delayMs) {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        // 2. Clear state variables to ensure a clean start
        this.solvedPath = null;
        this.dfsInstance = null; // Clear sibling instances
        this.astarInstance = null;

        final int[] stepCount = {0};
        long startTime = System.nanoTime();

        // 3. Re-initialize the instance
        bfsInstance = new MazeSolver.BFSInstance(maze, start, end);

        animationTimer = new Timer(delayMs, e -> {
            MazeSolver.Point current = bfsInstance.step();

            stepCount[0]++;

            if (bfsInstance.solved) {
                animationTimer.stop();
                setSolvedPath(bfsInstance.getPath());

                long endTime = System.nanoTime();
                long totalTimeNs = (endTime - startTime);
                long totalDelayNs = (long) stepCount[0] * delayMs * 1_000_000L;

                double pureTimeMs = (totalTimeNs - totalDelayNs) / 1_000_000.0;

                System.out.println("Nodes Checked: " + bfsInstance.getVisitedCount());
                System.out.println("Path Length: " + solvedPath.size());
                System.out.println("Pure Algorithmic Time: " + pureTimeMs + " ms");

                if (reference != null)
                {
                    reference.SetData(bfsInstance.getVisitedCount(), solvedPath.size(), pureTimeMs);
                }

                repaint();
            }
            else if (bfsInstance.impossible)
            {
                animationTimer.stop();

                long endTime = System.nanoTime();
                long totalTimeNs = (endTime - startTime);
                long totalDelayNs = (long) stepCount[0] * delayMs * 1_000_000L;

                double pureTimeMs = (totalTimeNs - totalDelayNs) / 1_000_000.0;

                System.out.println("Nodes Checked: " + bfsInstance.getVisitedCount());
                System.out.println("Path Length: 0");
                System.out.println("Pure Algorithmic Time: " + pureTimeMs + " ms");

                if (reference != null) {
                    reference.SetData(bfsInstance.getVisitedCount(), 0, pureTimeMs);
                }
            }
            else if (current != null) {
                repaint();
            } else {
                animationTimer.stop();
            }
        });

        animationTimer.start();
    }

    public void startAnimatedDFS(int[][] maze, int[] start, int[] end, int delayMs) {
        // 1. Stop the previous timer if it's running
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        // 2. Reset state variables
        this.solvedPath = null;
        this.bfsInstance = null;
        this.astarInstance = null;

        // 3. Initialize the DFS instance[cite: 5]
        dfsInstance = new MazeSolver.DFSInstance(maze, start, end);

        final int[] stepCount = {0};
        long startTime = System.nanoTime();

        // 4. Create and start the new timer
        animationTimer = new Timer(delayMs, e -> {
            MazeSolver.Point current = dfsInstance.step();

            stepCount[0]++;

            if (dfsInstance.solved) {
                animationTimer.stop();
                setSolvedPath(dfsInstance.getPath());

                long endTime = System.nanoTime();
                long totalTimeNs = (endTime - startTime);
                long totalDelayNs = (long) stepCount[0] * delayMs * 1_000_000L;

                double pureTimeMs = (totalTimeNs - totalDelayNs) / 1_000_000.0;

                System.out.println("Nodes Checked: " + dfsInstance.getVisitedCount());
                System.out.println("Path Length: " + solvedPath.size());
                System.out.println("Pure Algorithmic Time: " + pureTimeMs + " ms");

                if (reference != null)
                {
                    reference.SetData(dfsInstance.getVisitedCount(), solvedPath.size(), pureTimeMs);
                }

                repaint();
            }
            else if (dfsInstance.impossible)
            {
                animationTimer.stop();

                long endTime = System.nanoTime();
                long totalTimeNs = (endTime - startTime);
                long totalDelayNs = (long) stepCount[0] * delayMs * 1_000_000L;

                double pureTimeMs = (totalTimeNs - totalDelayNs) / 1_000_000.0;

                System.out.println("Nodes Checked: " + dfsInstance.getVisitedCount());
                System.out.println("Path Length: 0");
                System.out.println("Pure Algorithmic Time: " + pureTimeMs + " ms");

                if (reference != null) {
                    reference.SetData(dfsInstance.getVisitedCount(), 0, pureTimeMs);
                }
            }
            else if (current != null) {
                repaint();
            } else {
                animationTimer.stop();
            }
        });

        animationTimer.start();
    }

    public void startAnimatedAstar(int[][] maze, int[] start, int[] end, int delayMs) {
        // 1. Stop the previous timer if it's running[cite: 4]
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        // 2. Reset state variables[cite: 4]
        this.solvedPath = null;
        this.bfsInstance = null;
        this.dfsInstance = null;

        final int[] stepCount = {0};
        long startTime = System.nanoTime();

        // 3. Initialize the Astar instance[cite: 6]
        astarInstance = new Astar.StarInstance(maze, start, end);

        // 4. Create and start the new timer[cite: 4]
        animationTimer = new Timer(delayMs, e -> {
            Astar.Node current = astarInstance.step();

            stepCount[0]++;

            if (astarInstance.solved) {
                animationTimer.stop();

                setSolvedPath(astarInstance.getPath());

                long endTime = System.nanoTime();
                long totalTimeNs = (endTime - startTime);
                long totalDelayNs = (long) stepCount[0] * delayMs * 1_000_000L;

                double pureTimeMs = (totalTimeNs - totalDelayNs) / 1_000_000.0;

                System.out.println("Nodes Checked: " + astarInstance.getVisitedCount());
                System.out.println("Path Length: " + solvedPath.size());
                System.out.println("Pure Algorithmic Time: " + pureTimeMs + " ms");

                if (reference != null) {
                    reference.SetData(astarInstance.getVisitedCount(), solvedPath.size(), pureTimeMs);
                }

                repaint();
            }
            else if (astarInstance.impossible)
            {
                animationTimer.stop();

                long endTime = System.nanoTime();
                long totalTimeNs = (endTime - startTime);
                long totalDelayNs = (long) stepCount[0] * delayMs * 1_000_000L;

                double pureTimeMs = (totalTimeNs - totalDelayNs) / 1_000_000.0;

                System.out.println("Nodes Checked: " + astarInstance.getVisitedCount());
                System.out.println("Path Length: 0");
                System.out.println("Pure Algorithmic Time: " + pureTimeMs + " ms");

                if (reference != null) {
                    reference.SetData(astarInstance.getVisitedCount(), 0, pureTimeMs);
                }
            }
            else if (current != null) {
                //System.out.println("No puh");
                repaint();
            } else {
                animationTimer.stop();
            }
        });

        animationTimer.start();
    }

    public void setSolvedPath(List<int[]> solvedPath) {
        stopSimulation();
        this.solvedPath = solvedPath;
    }

    public void startSimulation() {
        this.running = true;
        timer.start();
    }

    public void stopSimulation() {
        this.running = false;
        counter = 0;
        cursor.move(start);
        repaint();
        timer.stop();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

        if (running || !cursor.getPos().equals(start) || cursor.getPos().equals(end))
            cursor.draw(g);
    }

    public void draw(Graphics g) {
        int i, j;
        g.setColor(Color.GRAY);

        boolean[][] visited = null;
        if (bfsInstance != null) visited = bfsInstance.getVisited();
        else if (dfsInstance != null) visited = dfsInstance.getVisited();
        else if (astarInstance != null) visited = astarInstance.getVisited();

        for (i = 0; i < HEIGHT / 30; i++) {
            for (j = 0; j < WIDTH / 30; j++) {
                switch (maze[i][j]) {
                    case 0:
                        int tempI = i;
                        int tempJ = j;
                        // Draw final path if solved
                        if (solvedPath != null && solvedPath.stream().anyMatch(x -> x[0] == tempI && x[1] == tempJ)) {
                            g.setColor(Color.decode("#ffdd78"));
                        }
                        // Draw visited search nodes during animation
                        else if (visited != null && visited[i][j]) {
                            g.setColor(Color.LIGHT_GRAY);
                        }
                        else {
                            g.setColor(Color.WHITE);
                        }
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

                g.fillRect(j * 30, i * 30, 30, 30);
            }
        }
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
        //called every 100 milliseconds
        move();
        checkGoal();
        revalidate();
        repaint();
    }
}
