package Solver;

import java.util.List;

import Models.Cell;

public interface MazeSolver {
    List<Cell> solve(Cell[][] maze, Cell start, Cell end);
    String getName();
    int getSteps();          // ✅ Método para contar celdas recorridas
    String getDescription(); // ✅ Nuevo método agregado para mostrar descripción
}




