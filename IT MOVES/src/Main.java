import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Maze.HelloWorld("print");

        // Uncomment line below for a surprise
        // HelloWorld("67");

        // Change "Maze.txt" to any text file you have in the Mazes folder
        // Generates file path to the maze text file
        //Path path = Path.of("Mazes", "Maze4.txt");
        // for cmd compilation
        Path path = Paths.get("C:\\Users\\edrie\\OneDrive\\Desktop\\Code files\\IT MOVES\\Mazes\\Maze6.txt");

        // Variables
        int rows = 0;
        int cols = 0;
        int[][] maze;

        int[] start = new int[0];
        int[] end = new int[0];

        List<int[]> solvedPath = null;

        // Buffered reader instead of Scanner
        try(BufferedReader reader = new BufferedReader(new FileReader(path.toString())))
        {
            System.out.println("File Located");

            // PARSING MAZE SECTION
            // Instantiate Rows and Columns Values
            String line;
            String[] values;
            if ((line = reader.readLine()) != null)
            {
                values = line.split(" ");
                rows = Integer.parseInt(values[0]);
                cols = Integer.parseInt(values[1]);
            }

            // Instantiate 2d matrix
            maze = new int[rows][cols];

            // Maze Parser
            for(int i = 0; i < rows; i++)
            {
                String temp = reader.readLine();

                for(int j = 0; j < cols; j++)
                {
                    switch (temp.charAt(j))
                    {
                        case ' ':
                            maze[i][j] = 0;
                            break;
                        case '#':
                            maze[i][j] = 1;
                            break;
                        case 'S':
                            maze[i][j] = 5;
                            start = new int[]{i, j};
                            break;
                        case 'G':
                            maze[i][j] = 9;
                            end = new int[]{i, j};
                            break;
                    }
                }
            }

            System.out.println("Start: " + Arrays.toString(start));
            System.out.println("End: " + Arrays.toString(end));

            // UNCOMMENT ONE TO TEST ALL SEARCH ALGORITHMS
            solvedPath = MazeSolver.BreadthFirstSearch(maze, start, end, 0.1f);
            //solvedPath = MazeSolver.DepthFirstSearch(maze, start, end, 0.1f);
            // solvedPath = Astar.AstarSearch(maze, start, end);

            // Display Function
            new Maze(maze, rows, cols, solvedPath, start, end);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch(IOException e)
        {
            System.out.println("Error reading file");
        }
    }
}