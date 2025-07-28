package Solver.solverImpl;

import java.util.ArrayList;
import java.util.List;

import Models.Cell;
import Solver.MazeSolver;

public class MazeSolverRecursivoCompleto implements MazeSolver {
    private List<Cell> path = new ArrayList<>();

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

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            if (search(maze, r + dr[i], c + dc[i], end)) return true;
        }

        path.remove(current);
        return false;
    }

    public String getName() {
        return "Recursivo Completo";
    }
}


