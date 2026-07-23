import java.io.File;

public class Driver {
    public static void main(String[] args) {
        MainMenu mainMenu;
        Maze maze = new Maze(new File("Maze.txt"));

        maze.displayMaze();


    }
}
