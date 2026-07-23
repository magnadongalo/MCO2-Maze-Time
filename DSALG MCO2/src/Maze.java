import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze {
    private int rows;
    private int cols;
    private String[] maze;
    private boolean[][] visited;

    //For ANSI Coloring
    private final String BLUE = "\u001B[34m";
    private final String RED = "\u001B[31m";
    private final String RESET = "\u001B[0m";

    public Maze (File f) {
        String temp;
        ArrayList<String> res = new ArrayList<>();
        String[] tempArr;
        Scanner sc;
        int i = 0;

        try {
            sc = new Scanner(f);
            while (sc.hasNextLine()) {
                temp = sc.nextLine();

                if (i == 0) {
                    //Assume the first two numbers are normally inputted
                    tempArr = temp.split(" ");

                    if (Integer.parseInt(tempArr[0]) >= 15 && Integer.parseInt(tempArr[0]) <= 30)
                        rows = Integer.parseInt(tempArr[0]);
                    else
                        rows = 15;

                    if (Integer.parseInt(tempArr[1]) >= 15 && Integer.parseInt(tempArr[1]) <= 30)
                        cols = Integer.parseInt(tempArr[1]);
                    else
                        cols = 15;

                } else {
                    //The maze is read starting row 1
                    temp = temp.replace('#','█');
                    temp = temp.replace("S", BLUE + "█" + RESET);
                    temp = temp.replace("G", RED + "█" + RESET);
                    res.add(i-1, temp);
                }

                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

        maze = res.toArray(new String[rows]);
        visited = new boolean[rows][cols];
    }

    public void displayMaze() {
        for (String s : maze) {
            System.out.println(s);
        }
    }
}
