package Controllers;

import DAO.AlgorithmResultDAO;
import Models.AlgorithmResult;
import Models.Cell;
import Models.SolveResults;
import Solver.MazeSolver;

public class MazeControllers {
    private Cell[][] maze;
    private MazeSolver solver;
    private AlgorithmResultDAO resultDAO;

    public MazeControllers(Cell[][] maze, MazeSolver solver, AlgorithmResultDAO resultDAO) {
        this.maze = maze;
        this.solver = solver;
        this.resultDAO = resultDAO;
    }

    public SolveResults solve(Cell start, Cell end) {
        long startTime = System.nanoTime();
        var path = solver.solve(maze, start, end);
        long endTime = System.nanoTime();

        SolveResults results = new SolveResults(path, endTime - startTime);
        resultDAO.saveResult(new AlgorithmResult(solver.getName(), path.size(), results.getExecutionTime()));
        return results;
    }


    
}
