package Models;

import java.util.List;

public class SolveResults {
    private List<Cell> path;
    private long executionTime;

    public SolveResults(List<Cell> path, long executionTime) {
        this.path = path;
        this.executionTime = executionTime;
    }

    public List<Cell> getPath() { return path; }
    public long getExecutionTime() { return executionTime; }


    
}
