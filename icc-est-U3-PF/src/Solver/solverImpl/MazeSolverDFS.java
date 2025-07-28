package Solver.solverImpl;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.Stack;

import Models.Cell;
import Solver.MazeSolver;





public class MazeSolverDFS implements MazeSolver {
    private int steps = 0;

    @Override
    public List<Cell> solve(Cell[][] maze, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        Set<Cell> visited = new HashSet<>();
        Stack<Cell> stack = new Stack<>();

        stack.push(start);
        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            if (!current.isWalkable() || visited.contains(current)) continue;

            path.add(current);
            visited.add(current);
            steps++;

            if (current.equals(end)) break;

            for (Cell neighbor : getNeighbors(current, maze)) {
                stack.push(neighbor);
            }
        }

        return path;
    }

    private List<Cell> getNeighbors(Cell cell, Cell[][] maze) {
        List<Cell> neighbors = new ArrayList<>();
        int r = cell.getRow();
        int c = cell.getCol();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr >= 0 && nr < maze.length && nc >= 0 && nc < maze[0].length)
                neighbors.add(maze[nr][nc]);
        }

        return neighbors;
    }

    @Override
    public String getName() { return "DFS"; }

    @Override
    public int getSteps() { return steps; }

    @Override
    public String toString() { return getName(); }
}

