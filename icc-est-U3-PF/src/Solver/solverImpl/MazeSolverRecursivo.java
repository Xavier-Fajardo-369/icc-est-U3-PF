package Solver.solverImpl;

import java.util.ArrayList;
import java.util.List;

import Models.Cell;
import Solver.MazeSolver;

public class MazeSolverRecursivo implements MazeSolver {
    private List<Cell> path = new ArrayList<>();

    @Override
    public List<Cell> solve(Cell[][] maze, Cell start, Cell end) {
        path.clear();
        if (search(maze, start.row, start.col, end)) return path;
        return new ArrayList<>();
    }

    private boolean search(Cell[][] maze, int r, int c, Cell end) {
        if (r < 0 || r >= maze.length || c < 0 || c >= maze[0].length) return false;
        Cell current = maze[r][c];
        if (!current.isWalkable() || path.contains(current)) return false;

        path.add(current);
        if (current.equals(end)) return true;

        // Movimientos: abajo e izquierda
        if (search(maze, r + 1, c, end) || search(maze, r, c - 1, end)) return true;

        path.remove(current);
        return false;
    }

    @Override
    public String getName() {
        return "Recursivo ";
    }
}

