package Solver.solverImpl;

import java.util.ArrayList;
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
    private int steps = 0;

    @Override
    public List<Cell> solve(Cell[][] maze, Cell start, Cell end) {
        Map<Cell, Cell> cameFrom = new HashMap<>();
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();
        List<Cell> path = new ArrayList<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            steps++;

            if (current.equals(end)) break;

            for (Cell neighbor : getNeighbors(current, maze)) {
                if (!neighbor.isWalkable() || visited.contains(neighbor)) continue;
                queue.offer(neighbor);
                visited.add(neighbor);
                cameFrom.put(neighbor, current);
            }
        }

        Cell current = end;
        while (current != null && cameFrom.containsKey(current)) {
            path.add(0, current);
            current = cameFrom.get(current);
        }

        if (start.equals(end)) path.add(start);

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
    public String getName() {
        return "BFS";
    }

    @Override
    public String getDescription() {
        return "Búsqueda en anchura usando cola, reconstruye el camino más corto";
    }

    @Override
    public int getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return getName();
    }
}




