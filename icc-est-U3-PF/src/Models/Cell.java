package Models;



public class Cell {
    private int row;
    private int col;
    private boolean walkable;
    private boolean visited;  // NUEVO: indica si fue explorada
    private boolean path;     // NUEVO: indica si forma parte del camino

    public Cell(int row, int col, boolean walkable) {
        this.row = row;
        this.col = col;
        this.walkable = walkable;
        this.visited = false;
        this.path = false;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isWalkable() { return walkable; }
    public void setWalkable(boolean walkable) { this.walkable = walkable; }

    // Nuevos getters y setters para pintar y limpiar correctamente
    public boolean isVisited() { return visited; }
    public void setVisited(boolean visited) { this.visited = visited; }

    public boolean isPath() { return path; }
    public void setPath(boolean path) { this.path = path; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cell other = (Cell) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }


    
   
}
