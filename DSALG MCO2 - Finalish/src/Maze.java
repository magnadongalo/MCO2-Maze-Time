import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import java.util.List;

public class Maze extends JFrame {
    private int[][] maze;
    private int rows, cols;
    private List<int[]> solvedPath;
    private int[] start;
    private int[] end;

    private JTextField delayField;

    private int NodesChecked;
    private int PathLength;
    private double runtime;

    private JLabel nodesLabel;
    private JLabel pathLabel;
    private JLabel timeLabel;

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
        MazePanel gridPanel = new MazePanel(new GridLayout(rows, cols), maze, rows, cols, start, end, solvedPath, this);

        // Options Panel
        JPanel optionPanel = new JPanel(new GridBagLayout());
        optionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        optionPanel.setBackground(Color.WHITE);

        // Layout constraints. Essentially grid spacing controls
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;

        int gridY = 0; // Dynamic vertical layout index

        delayField = new JTextField("50");

        // BFS, DFS, and A* Buttons
        for (int i = 0; i < 3; i++)
        {
            JButton button = createOptionButton();
            switch (i)
            {
                case 0:
                    button.setText("BFS");
                    button.addActionListener(e -> {
                        MazeSolver.playSound("kh3select.wav");

                        int delay = 50; // Default or read from delayField
                        try {
                            delay = Integer.parseInt(delayField.getText().trim());
                        } catch (NumberFormatException ex) {
                            delay = 50;
                        }

                        // Trigger step-by-step BFS animation
                        gridPanel.startAnimatedBFS(this.maze, this.start, this.end, delay);
                    });
                    break;
                case 1:
                    button.setText("DFS");
                    button.addActionListener(e -> {
                        MazeSolver.playSound("kh3select.wav");

                        int delay = 50;
                        try {
                            delay = Integer.parseInt(delayField.getText().trim());
                        } catch (NumberFormatException ex) {
                            delay = 50;
                        }

                        // Trigger step-by-step DFS animation
                        gridPanel.startAnimatedDFS(this.maze, this.start, this.end, delay);
                    });
                    break;
                case 2:
                    button.setText("A*");
                    button.addActionListener(e -> {
                        MazeSolver.playSound("kh3select.wav");
                        int delay = 50;
                        try { delay = Integer.parseInt(delayField.getText().trim()); }
                        catch (NumberFormatException ex) { delay = 50; }

                        gridPanel.startAnimatedAstar(this.maze, this.start, this.end, delay);
                    });
                    break;
            }
            c.gridy = gridY++;
            optionPanel.add(button, c);
        }

        // --- ANIMATION TIME LABEL & TEXT FIELD ---
        JLabel delayLabel = new JLabel("Animation time (ms):");
        delayLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        c.gridy = gridY++;
        optionPanel.add(delayLabel, c);

        delayField = new JTextField("50"); // Default 50 ms
        delayField.setPreferredSize(new Dimension(300, 30));
        delayField.setHorizontalAlignment(JTextField.CENTER);
        delayField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        c.gridy = gridY++;
        optionPanel.add(delayField, c);

        // --- START BUTTON ---
        JButton startButton = createOptionButton();
        startButton.setText("Start");
        startButton.setBackground(Color.decode("#a6da95"));
        startButton.addActionListener(e -> {
            MazeSolver.playSound("kh3select.wav");
            try {
                // Parse the value for your specific use case
                int inputValue = Integer.parseInt(delayField.getText().trim());

                // TODO: Use 'inputValue' for your intended functionality here
                System.out.println("Input value: " + inputValue);

                // The timer remains at its default speed (50ms) as defined in MazePanel.java
                gridPanel.startSimulation();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        c.gridy = gridY++;
        optionPanel.add(startButton, c);

        // --- RETURN TO MENU BUTTON ---
        JButton returnButton = createOptionButton();
        returnButton.setText("Return to Menu");
        returnButton.setForeground(Color.WHITE);
        returnButton.setBackground(Color.decode("#ff7a85"));
        returnButton.addActionListener(e -> {
            dispose();
            new Main(this.maze, this.rows, this.cols, this.start, this.end, this.solvedPath);
            MazeSolver.playSound("kh3cancel.wav");
        });
        c.gridy = gridY++;
        optionPanel.add(returnButton, c);

        // Test
        nodesLabel = new JLabel("Nodes Checked: " + NodesChecked);
        nodesLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        c.gridy = gridY++;
        optionPanel.add(nodesLabel, c);

        pathLabel = new JLabel("Path length: " + PathLength);
        pathLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        c.gridy = gridY++;
        optionPanel.add(pathLabel, c);

        timeLabel = new JLabel("Algorithmic Time: " + runtime);
        timeLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        c.gridy = gridY++;
        optionPanel.add(timeLabel, c);

        // Add all together
        this.add(gridPanel);
        this.add(optionPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Helper method to keep button styling consistent
    private JButton createOptionButton() {
        JButton button = new JButton();
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(300, 50));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setFont(new Font("Century Gothic", Font.BOLD, 15));
        button.setBackground(Color.LIGHT_GRAY);
        return button;
    }

    public static void HelloWorld(String input)
    {
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

    public void SetData(int nodesChecked, int pathLength, double runtime)
    {
        this.NodesChecked = nodesChecked;
        this.PathLength = pathLength;
        this.runtime = runtime;

        if (nodesLabel != null) nodesLabel.setText("Nodes Checked: " + nodesChecked);
        if (pathLabel != null) pathLabel.setText("Path length: " + pathLength);
        if (timeLabel != null) timeLabel.setText("Algorithmic Time: " + runtime + " ms");
    }
}