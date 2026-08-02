import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Astar {

    public static class Node implements Comparable<Node>
    {
        public int x, y;
        public int gCost;
        public int hCost;
        public int fCost;

        public Node parent;

        public Node(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.fCost, o.fCost);
        }

        @Override
        public boolean equals(Object obj) {
            boolean result = false;

            if (this == obj) {
                result = true;
            }
            else if (obj instanceof Node other)
            {
                result = (this.x == other.x) && (this.y == other.y);
            }

            return result;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }

    public static class StarInstance
    {
        private CustomPriorityQueue openList = new CustomPriorityQueue();
        private boolean[][] closedList;
        private int[][] maze;
        private int[] end;
        public boolean solved = false;
        private Node goalNode = null;

        public boolean impossible = false;

        public StarInstance(int[][] maze, int[] start, int[] end) {
            this.maze = maze;
            this.end = end;
            this.closedList = new boolean[maze.length][maze[0].length];

            Node startNode = new Node(start[0], start[1]);
            Node endNode = new Node(end[0], end[1]);
            startNode.gCost = 0;
            startNode.hCost = manhattanDistance(startNode, endNode);
            startNode.fCost = startNode.gCost + startNode.hCost;
            openList.add(startNode);
        }

        public Node step() {
            Node result = null;

            // We only process if we aren't already finished or in an impossible state
            if (!this.solved && !this.impossible) {

                // 1. Check for impossible state
                if (openList.isEmpty()) {
                    this.impossible = true;
                } else {
                    // 2. Main processing logic
                    Node current = openList.poll();
                    closedList[current.x][current.y] = true;

                    // Check if goal reached
                    if (current.x == end[0] && current.y == end[1]) {
                        this.solved = true;
                        this.goalNode = current;
                        result = current;
                    } else {
                        // 3. Process Neighbors
                        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                        for (int[] direction : directions) {
                            int newX = current.x + direction[0];
                            int newY = current.y + direction[1];

                            if (newX < maze.length && newY < maze[0].length && newX >= 0 && newY >= 0 &&
                                    maze[newX][newY] != 1 && !closedList[newX][newY]) {

                                int tentative = current.gCost + 1;
                                Node neighbour = new Node(newX, newY);
                                neighbour.gCost = tentative;
                                neighbour.hCost = manhattanDistance(neighbour, new Node(end[0], end[1]));
                                neighbour.fCost = neighbour.gCost + neighbour.hCost;
                                neighbour.parent = current;

                                boolean skip = false;
                                // Loop without break
                                for (Node node : openList.getNodes()) {
                                    if (node.equals(neighbour) && node.gCost <= neighbour.gCost) {
                                        skip = true;
                                    }
                                }

                                if (!skip) {
                                    openList.remove(neighbour);
                                    openList.add(neighbour);
                                }
                            }
                        }
                        // Store the current node as the result to return
                        result = current;
                    }
                }
            }

            // Single return point
            return result;
        }

        public List<int[]> getPath() {
            return reconstructPath(goalNode);
        }

        public boolean[][] getVisited() { return closedList; }

        public int getVisitedCount() {
            int count = 0;
            boolean[][] visited = getVisited();
            for (boolean[] booleans : visited) {
                for (boolean aBoolean : booleans) {
                    if (aBoolean) {
                        count++;
                    }
                }
            }
            return count;
        }
    }

    // Worded Algorithm from Sebastian Lague (Game Development Goat)
    public static List<int[]> AstarSearch(int[][] maze, int[] start, int[] end)
    {
        List<int[]> path = new ArrayList<>();

        int rows = maze.length;
        int cols = maze[0].length;

        CustomPriorityQueue openList = new CustomPriorityQueue();
        boolean[][] closedList = new boolean[rows][cols];

        Node startNode = new Node(start[0], start[1]);
        Node endNode = new Node(end[0], end[1]);

        startNode.gCost = 0;
        startNode.hCost = manhattanDistance(startNode, endNode);
        startNode.fCost = startNode.gCost + startNode.hCost;

        openList.add(startNode);

        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        boolean solved = false;

        while (!openList.isEmpty() && !solved)
        {
            Node current = openList.poll();
            closedList[current.x][current.y] = true;

            System.out.println("Evaluating Cell: Row " + current.x + ", Col " +
                    current.y + " | Queue Size: " + openList.getSize());

            if (current.equals(endNode))
            {
                path = reconstructPath(current);
                solved = true;
            }

            for (int[] direction : directions)
            {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (newX < rows && newY < cols && newX >= 0 && newY >= 0 &&
                        maze[newX][newY] != 1 && !closedList[newX][newY])
                {
                    int tentative = current.gCost + 1;

                    Node neighbour = new Node(newX, newY);
                    neighbour.gCost = tentative;
                    neighbour.hCost = manhattanDistance(neighbour, endNode);
                    neighbour.fCost = neighbour.gCost + neighbour.hCost;
                    neighbour.parent = current;

                    boolean skip = false;
                    for (Node node : openList.getNodes())
                    {
                        if (node.equals(neighbour) && node.gCost <= neighbour.gCost)
                        {
                            skip = true;
                            break;
                        }
                    }

                    if (!skip)
                    {
                        openList.remove(neighbour);
                        openList.add(neighbour);
                    }
                }
            }
        }

        if (path.isEmpty())
        {
            System.out.println("No path found!");
        }

        return path;
    }

    private static int manhattanDistance(Node start, Node end)
    {
        return  Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
    }

    private static List<int[]> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        List<int[]> intPath = new ArrayList<>();
        Node current = node;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);

        for (Node pathNode : path)
        {
            intPath.add(new int[]{pathNode.x, pathNode.y});
        }

        return intPath;
    }
}
