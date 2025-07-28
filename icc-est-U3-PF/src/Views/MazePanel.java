package Views;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

import java.util.List;


import Models.Cell;

public class MazePanel extends JPanel {
    private Cell[][] maze;
    private Cell start;
    private Cell end;
    private List<Cell> path = new ArrayList<>();

    public MazePanel(Cell[][] maze, Cell start, Cell end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
    }

    public void setPath(List<Cell> path) {
        this.path = path;
        repaint();
    }

    public Cell[][] getMaze() {
        return maze;
    }

    public Cell getStart() {
        return start;
    }

    public Cell getEnd() {
        return end;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (maze == null) return;

        int cellSize = Math.min(getWidth() / maze[0].length, getHeight() / maze.length);

        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                Cell cell = maze[r][c];
                if (!cell.isWalkable()) {
                    g.setColor(Color.BLACK);
                } else if (cell.equals(start)) {
                    g.setColor(Color.GREEN);
                } else if (cell.equals(end)) {
                    g.setColor(Color.RED);
                } else if (path.contains(cell)) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(c * cellSize, r * cellSize, cellSize, cellSize);
            }
        }
    }
}

