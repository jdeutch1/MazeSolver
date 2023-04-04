/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution()
    {
        // Stack to hold the reversed cell order
        Stack<MazeCell> reversedCells = new Stack<>();

        // Array list that will hold the solution (in order)
        ArrayList<MazeCell> solution = new ArrayList<>();

        // Intermediate cell
        MazeCell interCell = maze.getEndCell();

        /* Put cells into reversedCells stack by accessing
         the parent cell of each cell in order */
        while (!interCell.equals(maze.getStartCell()))
        {
            reversedCells.push(interCell.getParent());
            interCell = interCell.getParent();
        }

        // Add the cells in stack to solution ArrayList
        /* Because stack is FIFO the steps below will reverse
         the order of the cells we put into the reversedCells stack */
        while (!reversedCells.isEmpty())
        {
            solution.add(reversedCells.pop());
        }
        solution.add(maze.getEndCell());

        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS()
    {
        Stack<MazeCell> toExplore = new Stack<>();
        MazeCell currCell = maze.getStartCell();

        // While-loop to add cells to stack
        /* DFS requires a stack because we want to go
         as far as we can in the puzzle until we are stopped -- the stack
          allows us to do this because the most recent cells to visit will be at
           the top of the stack (FIFO) */
        while (!currCell.equals(maze.getEndCell()))
        {
            // Rows and cols of current cell
            int row = currCell.getRow();
            int col = currCell.getCol();

            // Push cells (if they exist) and set parent to current cell
            if (maze.isValidCell(row - 1, col))
            {
                toExplore.push(maze.getCell(row - 1, col));
                maze.getCell(row - 1, col).setParent(currCell);
            }
            if (maze.isValidCell(row, col + 1))
            {
                toExplore.push(maze.getCell(row, col + 1));
                maze.getCell(row, col + 1).setParent(currCell);
            }
            if (maze.isValidCell(row + 1, col))
            {
                toExplore.push(maze.getCell(row + 1, col));
                maze.getCell(row + 1, col).setParent(currCell);
            }
            if (maze.isValidCell(row, col - 1))
            {
                toExplore.push(maze.getCell(row, col - 1));
                maze.getCell(row, col - 1).setParent(currCell);
            }

            // Update the current cell to the cell on top of stack
            currCell.setExplored(true);
            currCell = toExplore.pop();
        }

        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS()
    {
        Queue<MazeCell> toExplore = new LinkedList<>();
        MazeCell currCell = maze.getStartCell();

        // While-loop to add cells to queue
        /* BFS requires a queue because we want to progress in the maze
         one step at a time -- the queue allows us to do this because the
         most recent cells to visit will be at the bottom of the stack (FILO) */
        while (!currCell.equals(maze.getEndCell()))
        {
            // Rows and cols of current cell
            int row = currCell.getRow();
            int col = currCell.getCol();

            // Push cells (if they exist) and set parent to current cell
            if (maze.isValidCell(row - 1, col))
            {
                toExplore.add(maze.getCell(row - 1, col));
                maze.getCell(row - 1, col).setParent(currCell);
            }
            if (maze.isValidCell(row, col + 1))
            {
                toExplore.add(maze.getCell(row, col + 1));
                maze.getCell(row, col + 1).setParent(currCell);
            }
            if (maze.isValidCell(row + 1, col))
            {
                toExplore.add(maze.getCell(row + 1, col));
                maze.getCell(row + 1, col).setParent(currCell);
            }
            if (maze.isValidCell(row, col - 1))
            {
                toExplore.add(maze.getCell(row, col - 1));
                maze.getCell(row, col - 1).setParent(currCell);
            }

            // Update the current cell to the cell on top of stack
            currCell.setExplored(true);
            currCell = toExplore.remove();
        }

        return getSolution();
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
