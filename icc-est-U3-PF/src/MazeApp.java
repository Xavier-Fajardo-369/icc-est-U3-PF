import javax.swing.JOptionPane;

import Models.Cell;
import Views.MazeFrame;


public class MazeApp {
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(() -> {
            int rows = 10;
            int cols = 10;

            try {
                String filasStr = JOptionPane.showInputDialog("Ingrese número de filas:");
                String columnasStr = JOptionPane.showInputDialog("Ingrese número de columnas:");

                rows = Integer.parseInt(filasStr);
                cols = Integer.parseInt(columnasStr);

                if (rows <= 0 || cols <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "¡Por favor ingresa números positivos válidos!");
                System.exit(0);
            }

            Cell[][] maze = new Cell[rows][cols];

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    maze[r][c] = new Cell(r, c, true); // todas caminables
                }
            }

            MazeFrame frame = new MazeFrame(maze); //  ¡Constructor corregido!
            frame.setVisible(true);
        });
    }


}


    

    

