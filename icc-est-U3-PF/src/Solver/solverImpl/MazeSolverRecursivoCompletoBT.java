package Solver.solverImpl;

import java.util.ArrayList;
import java.util.List;

import Models.Cell;
import Solver.MazeSolver;

public class MazeSolverRecursivoCompletoBT implements MazeSolver {
    private List<Cell> bestPath = new ArrayList<>();

    public List<Cell> solve(Cell[][] maze, Cell start, Cell end) {
        bestPath.clear();
        backtrack(new ArrayList<>(), maze, start.row, start.col, end);
        return bestPath;
    }

    private void backtrack(List<Cell> currentPath, Cell[][] maze, int r, int c, Cell end) {
        if (r < 0 || r >= maze.length || c < 0 || c >= maze[0].length) return;
        Cell current = maze[r][c];
        if (!current.isWalkable() || currentPath.contains(current)) return;

        currentPath.add(current);
        if (current.equals(end)) {
            if (bestPath.isEmpty() || currentPath.size() < bestPath.size())
                bestPath = new ArrayList<>(currentPath);
            currentPath.remove(current);
            return;
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            backtrack(currentPath, maze, r + dr[i], c + dc[i], end);
        }
        currentPath.remove(current);
    }

    public String getName() {
        return "Recursivo Completo BT";
    }
}

