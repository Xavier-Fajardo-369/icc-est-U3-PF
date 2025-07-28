package Solver;

import java.util.List;

import Models.Cell;

public interface MazeSolver {
    List<Cell> solve(Cell[][] maze, Cell start, Cell end);
    String getName();
    int getSteps(); // ✅ Nuevo método para contar celdas recorridas
}




