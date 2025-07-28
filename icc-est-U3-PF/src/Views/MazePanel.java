package Views;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;

import java.util.List;


import Models.Cell;

public class MazePanel extends JPanel {
    private Cell[][] maze;
    private Cell start;
    private Cell end;
    private List<Cell> path = new ArrayList<>();

    public MazePanel() {
        int filas = 10;
        int columnas = 10;
        maze = new Cell[filas][columnas];
        for (int r = 0; r < filas; r++) {
            for (int c = 0; c < columnas; c++) {
                maze[r][c] = new Cell(r, c, true);
            }
        }
        start = null;
        end = null;
    }

    public MazePanel(Cell[][] maze, Cell start, Cell end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
    }

    public void setPath(List<Cell> path) {
        this.path = path;
        repaint();
    }

    public void setStart(Cell start) {
        this.start = start;
        repaint();
    }

    public void setEnd(Cell end) {
        this.end = end;
        repaint();
    }

    public Cell[][] getMaze() { return maze; }
    public Cell getStart() { return start; }
    public Cell getEnd() { return end; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (maze == null || maze.length == 0 || maze[0].length == 0) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int rows = maze.length;
        int cols = maze[0].length;
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = maze[r][c];
                int x = c * cellWidth;
                int y = r * cellHeight;

                if (!cell.isWalkable()) {
                    g2.setColor(Color.BLACK);
                } else if (start != null && cell.equals(start)) {
                    g2.setColor(Color.GREEN);
                } else if (end != null && cell.equals(end)) {
                    g2.setColor(Color.RED);
                } else if (path.contains(cell)) {
                    g2.setColor(Color.LIGHT_GRAY);
                } else {
                    g2.setColor(Color.WHITE);
                }

                g2.fillRect(x, y, cellWidth, cellHeight);
                g2.setColor(Color.GRAY);
                g2.drawRect(x, y, cellWidth, cellHeight);
            }
        }
    }
}