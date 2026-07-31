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

public class Main extends JFrame implements ActionListener {
    private JPanel subPanel, buttonPanel;
    private JLabel label = new JLabel("<html><center>Maze Runner</center></html>");
    private JButton loadMaze, startSim, exit;

    public Main() {
        this.setTitle("Maze Runner");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        this.getContentPane().setBackground(Color.decode("#454545"));

        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.CENTER;

        c.gridx = 0;

        loadMaze = new JButton("Load Maze");
        loadMaze.setFocusable(false);
        loadMaze.setPreferredSize(new Dimension(150, 50));
        loadMaze.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        loadMaze.setFont(new Font("Century Gothic", Font.BOLD, 15));
        loadMaze.setBackground(Color.LIGHT_GRAY);
        loadMaze.addActionListener(this);
        c.gridy = 0;
        buttonPanel.add(loadMaze, c);

        startSim = new JButton("Start Simulation");
        startSim.setFocusable(false);
        startSim.setPreferredSize(new Dimension(150, 50));
        startSim.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        startSim.setFont(new Font("Century Gothic", Font.BOLD, 15));
        startSim.setBackground(Color.LIGHT_GRAY);
        startSim.addActionListener(this);
        c.gridy = 1;
        buttonPanel.add(startSim, c);

        exit = new JButton("Exit Program");
        exit.setFocusable(false);
        exit.setPreferredSize(new Dimension(150, 50));
        exit.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        exit.setFont(new Font("Century Gothic", Font.BOLD, 15));
        exit.setForeground(Color.WHITE);
        exit.setBackground(Color.decode("#ff7a85"));
        exit.addActionListener(e -> this.dispose());
        c.gridy = 2;
        buttonPanel.add(exit, c);

        subPanel = new JPanel();
        label.setFont(new Font("Century Gothic", Font.BOLD, 30));
        subPanel.add(label);

        this.add(subPanel);
        this.add(buttonPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
        //Maze.HelloWorld("print");

        // Uncomment line below for a surprise
        //Maze.HelloWorld("67");

        // Change "Maze.txt" to any text file you have in the Mazes folder
        // Generates file path to the maze text file
        //Path path = Path.of("Mazes", "Maze4.txt");
        // for cmd compilation
        Path path = Paths.get("C:\\Users\\edrie\\OneDrive\\Desktop\\Code files\\IT MOVES\\Mazes\\Maze2.txt");

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
            //solvedPath = MazeSolver.BreadthFirstSearch(maze, start, end, 0.1f);
            //solvedPath = MazeSolver.DepthFirstSearch(maze, start, end, 0.1f);
            solvedPath = Astar.AstarSearch(maze, start, end);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadMaze) {

        } else if (e.getSource() == startSim) {

        }
    }
}