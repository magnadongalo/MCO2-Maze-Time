import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.*;

public class MazeSolver {
    private static AudioInputStream audioInput;
    private static Clip clip;

    private static class Point
    {
        int x;
        int y;
        Point parent;
        public Point(int x, int y, Point parent)
        {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

    public static List<int[]> BreadthFirstSearch(int[][] maze, int[] start, int[] end, double timePerCheck)
    {
        // Make a list for path thing
        List<int[]> path = new ArrayList<>();

        // guard clause

        // rows and columns
        int rows = maze.length;
        int cols = maze[0].length;

        int nodesChecked = 0;

        // BFS uses a queue to check each node
        // Think of BFS as like a wave going through the maze
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        boolean solved = false;

        // Directions of movement
        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        // Initialize Starting Point
        Point startPoint = new Point(start[0], start[1], null);
        queue.add(startPoint);
        visited[start[0]][start[1]] = true;

        // Keep searching until maze is solved or queue is empty
        while(!queue.isEmpty() && !solved)
        {
            // Dequeue starting point to check neighbor points
            Point current = queue.poll();

            // valid path has been found
            if (current.x == end[0] && current.y == end[1])
            {
                System.out.println("found path!");
                solved = true;

                Point temp = current;
                while (temp != null)
                {
                    path.addFirst(new int[]{temp.x, temp.y});
                    temp = temp.parent;
                }
            }
            else
            {
                // search through all neighbor points per point
                for (int[] direction : directions)
                {
                    int nextX = current.x + direction[0];
                    int nextY = current.y + direction[1];

                    if (nextX >= 0 && nextX < rows &&  nextY >= 0 && nextY < cols
                            && (maze[nextX][nextY] == 0 || maze[nextX][nextY] == 9)
                            && !visited[nextX][nextY])
                    {
                        visited[nextX][nextY] = true;
                        queue.add(new Point(nextX, nextY, current));
                        nodesChecked++;
                    }
                }
            }
        }

        // debug
        for (int[] points : path)
        {
            System.out.println(points[0] + " " + points[1]);
        }

        System.out.println("Nodes Checked: " + nodesChecked);

        // no solution
        if (!solved)
        {
            System.out.println("No solution.");
        }

        // the path to be returned
        return path;
    }

    public static List<int[]> DepthFirstSearch(int[][] maze, int[] start, int[] end, double timePerCheck)
    {
        List<int[]> path = new ArrayList<>();

        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];

        if (searchDfs(maze, start, end, visited, path))
        {
            return path;
        }
        else
        {
            System.out.println("No solution.");
            return null;
        }
    }

    public static boolean searchDfs(int[][] maze, int[] point, int[] target,
                              boolean[][] visited, List<int[]> path)
    {
        int x = point[0];
        int y = point[1];

        // Directions of movement
        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        if (x < 0 ||
                x >= maze.length ||
                y < 0 ||
                y >= maze[0].length ||
                maze[x][y] == 1 || visited[x][y])
        {
            return false;
        }

        visited[x][y] = true;
        path.add(new int[]{x, y});

        if (x == target[0] && y == target[1])
        {
            return true;
        }

        for (int[] direction : directions)
        {
            int nextX = point[0] + direction[0];
            int nextY = point[1] + direction[1];

            int[] nextPoint = new int[]{nextX, nextY};

            if (searchDfs(maze, nextPoint, target, visited, path))
            {
                return true;
            }
        }

        path.removeLast();
        return false;
    }

    public static void playSound(String location) {

        try {
            File musicPath = new File(location);

            if (musicPath.exists()) {
                audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);

                clip.start();
            }
            else
                System.out.println("File not found...");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void stopSounds(){
        clip.close();
    }
}
