import java.awt.*;
import java.nio.file.Path;
import java.io.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("Mazes", "Maze3.txt");

        int rows = 0;
        int cols = 0;
        int[][] maze;

        try(BufferedReader reader = new BufferedReader(new FileReader(path.toString())))
        {
            System.out.println("File Located");

            // Parsing Starts Here

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
                            break;
                        case 'G':
                            maze[i][j] = 9;
                            break;
                    }
                }
            }

            // JFrame Starts Here
            JFrame frame = new JFrame("Maze");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

            JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
            gridPanel.setBackground(Color.GRAY);

            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    JPanel squarePanel = getJPanel(maze, i, j);
                    gridPanel.add(squarePanel);
                }
            }

            JPanel optionPanel = new JPanel(new GridBagLayout());
            optionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(5, 5, 5, 5);
            c.anchor = GridBagConstraints.CENTER;

            optionPanel.setBackground(Color.WHITE);

            for (int i = 0; i < 4; i++)
            {
                JButton button = new JButton(String.valueOf(i));
                button.setBackground(Color.LIGHT_GRAY);
                button.setFocusable(false);
                button.setPreferredSize(new Dimension(150, 50));
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                switch (i)
                {
                    case 0:
                        button.setText("BFS");
                        break;
                    case 1:
                        button.setText("DFS");
                        break;
                    case 2:
                        button.setText("Astar");
                        break;
                    case 3:
                        button.setText("Return to menu");
                        break;
                }

                c.gridx = 0;
                c.gridy = i;
                optionPanel.add(button, c);
            }

            frame.add(gridPanel);
            frame.add(optionPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
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

    private static JPanel getJPanel(int[][] maze, int i, int j) {
        JPanel squarePanel = new JPanel();
        switch (maze[i][j])
        {
            case 0:
                squarePanel.setBackground(Color.WHITE);
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

}