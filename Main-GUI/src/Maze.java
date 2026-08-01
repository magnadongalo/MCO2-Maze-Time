import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.*;
import java.util.List;

public class Maze extends JFrame {
    private int[][] maze;
    private int rows, cols;
    private List<int[]> solvedPath;
    private int[] start;
    private int[] end;

    public Maze(int[][] maze, int rows, int cols, List<int[]> solvedPath, int[] start, int[] end)
    {
        this.maze = maze;
        this.rows = rows;
        this.cols = cols;
        this.solvedPath = solvedPath;
        this.start = start;
        this.end = end;

        // JFRAME GUI SECTION -----------------------------------------------------------------
        this.setTitle("Maze");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        this.getContentPane().setBackground(Color.decode("#454545"));

        // Generate Maze from parsed 2D int matrix using GridLayout
        // All squares will be of the same size and colored according to their text char
        MazePanel gridPanel = new MazePanel(new GridLayout(rows, cols), maze, rows, cols, start, end, solvedPath);

        // Options Panel
        JPanel optionPanel = new JPanel(new GridBagLayout());
        optionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        optionPanel.setBackground(Color.WHITE);

        // Layout constraints. Essentially grid spacing controls
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.CENTER;

        // Button Generation
        for (int i = 0; i < 5; i++)
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
                    button.addActionListener(e -> {
                        MazeSolver.playSound("kh3menuOpen.wav");
                        this.solvedPath = MazeSolver.BreadthFirstSearch(this.maze, this.start, this.end, 0.1f);
                        gridPanel.setSolvedPath(this.solvedPath);
                    });
                    break;
                case 1:
                    button.setText("DFS");
                    button.setBackground(Color.LIGHT_GRAY);
                    button.addActionListener(e -> {
                        MazeSolver.playSound("kh3menuOpen.wav");
                        this.solvedPath = MazeSolver.DepthFirstSearch(this.maze, this.start, this.end, 0.1f);
                        gridPanel.setSolvedPath(this.solvedPath);
                    });
                    break;
                case 2:
                    button.setText("A*");
                    button.setBackground(Color.LIGHT_GRAY);
                    button.addActionListener(e -> {
                        MazeSolver.playSound("kh3menuOpen.wav");
                        this.solvedPath = Astar.AstarSearch(this.maze, this.start, this.end);
                        gridPanel.setSolvedPath(this.solvedPath);
                    });
                    break;
                case 3:
                    button.setText("Start");
                    button.setBackground(Color.LIGHT_GRAY);
                    button.addActionListener(e -> {
                        MazeSolver.playSound("kh3select.wav");
                        gridPanel.startSimulation();
                    });
                    break;
                case 4:
                    button.setText("Return to Menu");
                    button.setForeground(Color.WHITE);
                    button.setBackground(Color.decode("#ff7a85"));
                    button.addActionListener(e -> {
                        dispose();
                        new Main(this.maze, this.rows, this.cols, this.start, this.end, this.solvedPath);
                        MazeSolver.playSound("kh3cancel.wav");
                    });
                    break;
            }

            // layout shit
            c.gridx = 0;
            c.gridy = i;
            optionPanel.add(button, c);
        }

        // Add all together
        this.add(gridPanel);
        this.add(optionPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Nothing to see here...
     *
     * @param input what's the word gng
     */
    public static void HelloWorld(String input)
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
