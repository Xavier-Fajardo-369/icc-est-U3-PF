package Models;



public class Cell {
    public final int row;
    public final int col;
    private boolean walkable;

    public Cell(int row, int col, boolean walkable) {
        this.row = row;
        this.col = col;
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell) {
            Cell other = (Cell) obj;
            return this.row == other.row && this.col == other.col;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return row * 31 + col;
    }


    

    
}
