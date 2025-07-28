package Solver.solverImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import Models.Cell;
import Solver.MazeSolver;

public class MazeSolverBFS implements MazeSolver {
    public String getName() { return "BFS"; }

    public List<Cell> solve(Cell[][] maze, Cell start, Cell end) {
        Queue<Cell> queue = new LinkedList<>();
        Map<Cell, Cell> parent = new HashMap<>();
        Set<Cell> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            if (current.equals(end)) break;

            for (Cell neighbor : getNeighbors(maze, current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<Cell> path = new ArrayList<>();
        Cell current = end;
        while (current != null && parent.containsKey(current)) {
            path.add(current);
            current = parent.get(current);
        }

        if (!path.contains(start)) path.add(start);
        Collections.reverse(path);
        return path;
    }

    private List<Cell> getNeighbors(Cell[][] maze, Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            int r = cell.row + dr[i];
            int c = cell.col + dc[i];
            if (r >= 0 && r < maze.length && c >= 0 && c < maze[0].length) {
                Cell neighbor = maze[r][c];
                if (neighbor.isWalkable()) neighbors.add(neighbor);
            }
        }
        return neighbors;
    }
}

