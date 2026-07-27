import java.awt.*;
import java.nio.file.Path;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        HelloWorld("print");

        // Uncomment line below for a surprise
//        HelloWorld("67");

        // Change "Maze.txt" to any text file you have in the Mazes folder
        // Generates file path to the maze text file
        Path path = Path.of("Mazes", "Maze4.txt");

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
            // solvedPath = MazeSolver.BreadthFirstSearch(maze, start, end, 0.1f);
            // solvedPath = MazeSolver.DepthFirstSearch(maze, start, end, 0.1f);
            // solvedPath = Astar.AstarSearch(maze, start, end);

            // Display Function
            UserInterface(maze, rows, cols, solvedPath);
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

    private static void UserInterface(int[][] maze, int rows, int cols, List<int[]> solvedPath)
    {
        // JFRAME GUI SECTION
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        frame.getContentPane().setBackground(Color.decode("#454545"));

        // Generate Maze from parsed 2D int matrix using GridLayout
        // All squares will be of the same size and colored according to their text char
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                JPanel squarePanel = getJPanel(maze, i, j, solvedPath);
                gridPanel.add(squarePanel);
            }
        }

        // Options Panel
        JPanel optionPanel = new JPanel(new GridBagLayout());
        optionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        optionPanel.setBackground(Color.WHITE);

        // Layout constraints. Essentially grid spacing controls
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.CENTER;

        // Button Generation
        for (int i = 0; i < 4; i++)
        {
            // Button Constructor
            JButton button = new JButton(String.valueOf(i));
            button.setFocusable(false);
            button.setPreferredSize(new Dimension(150, 50));
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            button.setFont(new Font("Century Gothic", Font.BOLD, 15));

            // Name the button based on i
            switch (i)
            {
                case 0:
                    button.setText("BFS");
                    button.setBackground(Color.LIGHT_GRAY);
                    break;
                case 1:
                    button.setText("DFS");
                    button.setBackground(Color.LIGHT_GRAY);
                    break;
                case 2:
                    button.setText("Astar");
                    button.setBackground(Color.LIGHT_GRAY);
                    break;
                case 3:
                    button.setText("Return to menu");
                    button.setForeground(Color.WHITE);
                    button.setBackground(Color.decode("#ff7a85"));
                    break;
            }

            // layout shit
            c.gridx = 0;
            c.gridy = i;
            optionPanel.add(button, c);
        }

        // Add all together
        frame.add(gridPanel);
        frame.add(optionPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel getJPanel(int[][] maze, int i, int j, List<int[]> solvedPath) {
        JPanel squarePanel = new JPanel();
        switch (maze[i][j])
        {
            case 0:
                if (solvedPath != null && solvedPath.stream().anyMatch(x -> x[0] == i && x[1] == j))
                {
                    System.out.println("Matched with: " + i + ", " + j);
                    squarePanel.setBackground(Color.decode("#ffdd78"));
                }
                else
                {
                    squarePanel.setBackground(Color.WHITE);
                }
                break;
            case 1:
                squarePanel.setBackground(Color.BLACK);
                break;
            case 5:
                squarePanel.setBackground(Color.BLUE);
                break;
            case 9:
                squarePanel.setBackground(Color.RED);
                break;
        }
        squarePanel.setPreferredSize(new Dimension(30, 30));
        return squarePanel;
    }

    /**
     * Nothing to see here...
     *
     * @param input what's the word gng
     */
    private static void HelloWorld(String input)
    {
        // why even question it?

        if (input.equals("print"))
        {
            System.out.println("Hello World!");
        }
        else if (input.equals("67"))
        {
            JOptionPane.showMessageDialog(
                    null,
                    "This code is ass. Session Terminated.",
                    "cooked af",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(67);
        }
        else
        {
            System.out.println("You typed it wrong.");
        }
    }
}