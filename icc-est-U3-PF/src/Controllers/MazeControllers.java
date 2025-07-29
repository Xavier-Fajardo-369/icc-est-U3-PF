package Controllers;

import DAO.AlgorithmResultDAO;
import Models.AlgorithmResult;
import Models.Cell;
import Models.SolveResults;
import Views.MazePanel;
import Solver.MazeSolver;

import java.util.ArrayList;
import java.util.List;

public class MazeControllers {
    private Cell[][] maze;
    private MazeSolver solver;
    private AlgorithmResultDAO resultDAO;
    private MazePanel mazePanel;   // <-- referencia a la vista (panel)
    private List<Cell> pathActual = new ArrayList<>();
    private int pasoIndex = 0;

    public MazeControllers(Cell[][] maze, MazeSolver solver, AlgorithmResultDAO resultDAO, MazePanel mazePanel) {
        this.maze = maze;
        this.solver = solver;
        this.resultDAO = resultDAO;
        this.mazePanel = mazePanel;  // guardamos referencia para actualizar la vista
    }

    // Ejecuta la solución completa, guarda resultados y almacena el camino para pasos
    public SolveResults solve(Cell start, Cell end) {
        long startTime = System.nanoTime();
        pathActual = solver.solve(maze, start, end);  // guardamos camino completo para paso a paso
        pasoIndex = 0;  // reset índice de pasos
        long endTime = System.nanoTime();

        SolveResults results = new SolveResults(pathActual, endTime - startTime);
        resultDAO.saveResult(new AlgorithmResult(solver.getName(), pathActual.size(), results.getExecutionTime()));

        // Opcional: mostrar el camino completo en la vista
        mazePanel.setPath(pathActual);

        return results;
    }

    // Ejecuta el siguiente paso del camino (muestra la ruta parcial incrementando 1 celda)
    public void ejecutarPaso() {
        if (pathActual == null || pathActual.isEmpty()) return;

        if (pasoIndex < pathActual.size()) {
            List<Cell> parcial = new ArrayList<>(pathActual.subList(0, pasoIndex + 1));
            mazePanel.setPath(parcial);  // Actualiza la vista con el camino parcial
            pasoIndex++;
        }
    }

    // Limpia el camino visualizado y reinicia índices
    public void limpiarCamino() {
        pathActual.clear();
        pasoIndex = 0;
        mazePanel.setPath(new ArrayList<>());
    }

    // Métodos getter/setter si necesitas exponer datos
    public List<Cell> getPathActual() {
        return pathActual;
    }

    public int getPasoIndex() {
        return pasoIndex;
    }
}
