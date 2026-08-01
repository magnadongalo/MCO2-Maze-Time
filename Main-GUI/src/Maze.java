import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.*;
import java.util.List;

public class Maze extends JFrame {
    private int[][] maze;
    private int rows, cols;

    public Maze(int[][] maze, int rows, int cols, List<int[]> solvedPath, int[] start, int[] end)
    {
        this.maze = maze;
        this.rows = rows;
        this.cols = cols;

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
                    button.setText("Start");
                    button.setBackground(Color.LIGHT_GRAY);
                    button.addActionListener(e -> gridPanel.startSimulation());
                    break;
                case 3:
                    button.setText("Return to Menu");
                    button.setForeground(Color.WHITE);
                    button.setBackground(Color.decode("#ff7a85"));
                    button.addActionListener(e -> {
                        dispose();
                        new Main();
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

    public static JPanel getJPanel(int[][] maze, int i, int j, List<int[]> solvedPath) {
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
