package Solver.solverImpl;

import java.util.ArrayList;
import java.util.List;

import Models.Cell;
import Solver.MazeSolver;

public class MazeSolverRecursivo implements MazeSolver {
    private List<Cell> path = new ArrayList<>();
    private int steps = 0;

    @Override
    public List<Cell> solve(Cell[][] maze, Cell start, Cell end) {
        path.clear();
        steps = 0;
        search(maze, start.getRow(), start.getCol(), end);
        return path;
    }

    private boolean search(Cell[][] maze, int r, int c, Cell end) {
        if (r < 0 || r >= maze.length || c < 0 || c >= maze[0].length) return false;

        Cell current = maze[r][c];
        if (!current.isWalkable() || path.contains(current)) return false;

        path.add(current);
        steps++;

        if (current.equals(end)) return true;

        // Solo abajo (⬇️) y a la izquierda (◀️)
        int[] dr = {1, 0};   // fila +1 (abajo), sin cambio vertical
        int[] dc = {0, -1};  // sin cambio horizontal, columna -1 (izquierda)

        for (int i = 0; i < 2; i++) {
            if (search(maze, r + dr[i], c + dc[i], end)) return true;
        }

        path.remove(current);
        return false;
    }

    @Override
    public String getName() { return "Recursivo"; }

    @Override
    public int getSteps() { return steps; }

    @Override
    public String toString() { return getName(); }
}


