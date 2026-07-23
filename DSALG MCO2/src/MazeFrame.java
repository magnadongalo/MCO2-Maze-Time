import javax.swing.*;

public class MazeFrame extends JFrame {

    public MazeFrame(Maze maze) {
        GamePanel gamepanel = new GamePanel(maze);

        this.add(gamepanel);
        this.addKeyListener(gamepanel);
        this.setTitle("Maze");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
