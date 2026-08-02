[README.md](https://github.com/user-attachments/files/30638301/README.md)
**============================================================================================**

**CCDSALG MCO2: Maze Traversal Simulation**

**============================================================================================**



This Java program simulates maze traversal with the use of the Breadth-First Search (BFS)

algorithm, Depth-First Search (DFS) algorithm, and A-star (A\*) algorithm.



**COMPILATION:**

This program is compiled and ran with the following commands:



javac Driver.java

java Driver



**HOW TO USE:**

The user will see the title card upon boot-up. They are given three options in the form of

buttons in the interface: 



1. *Load a maze (saved in a .txt file format)*
The program opens a dialog box wherein the user can choose a file to load. It must be a
.txt file formatted like such:

15 15 
###############
#S            #
######### #####
#   G#        #
#   ## ###### #
#      #      #
#########  #  #
#          #  #
# #### ### #  #
# #       #####
# # ## ## #####
#       #     #
# ########### #
#     #       #
###############

The first two numbers serve as the dimension of the maze. Rows by columns, in that order.
The minimum size of the maze is 15 rows by 15 columns, while the maximum is 30 rows by 30
columns.
 
2. *Start the simulation (if a valid maze is loaded)*
Upon clicking the option for starting the simulation, a new window will be brought up
containing the display and the maze itself.

There are five buttons and a text field on the right side of the display. The first three 
buttons correspond to the pathfinding algorithms BFS, DFS, and A\*.

The text field below dictates how fast in milliseconds the pathfinding animation will play.
After the pathfinding animation has played, statistics below will be updated, noting the
nodes checked, the final path length, and the time in milliseconds to find the solution.

The user can then click start to play another animation wherein a green circle traces the
correct path to the goal.

The "Return to Menu" button returns the user to the main menu.

3. *Exit the program entirely.*



AUTHORS:

* Esguerra, Gabriel Benedict
* Maullon, Edriel Lexine 
* Tolentino, Johann Haree 



