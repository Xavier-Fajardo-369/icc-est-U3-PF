package Models;



public class Cell {
    private int row;
    private int col;
    private boolean walkable;

    public Cell(int row, int col, boolean walkable) {
        this.row = row;
        this.col = col;
        this.walkable = walkable;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isWalkable() { return walkable; }
    public void setWalkable(boolean walkable) { this.walkable = walkable; }

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
