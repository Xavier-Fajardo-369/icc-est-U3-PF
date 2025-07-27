import javax.swing.SwingUtilities;

import Controllers.MazeControllers;
import Solver.solverImpl.MazeSolverDFS;
import Views.MazeFrame;

public class MazeApp {
    public static void main(String[] args) throws Exception {
         // Asegura que la creación de la GUI se ejecute en el hilo de despacho de eventos
        SwingUtilities.invokeLater(() -> {
            // Crear la ventana principal del laberinto
            MazeFrame frame = new MazeFrame();

            // Crear el controlador del laberinto y pasarle la vista
            MazeControllers controller = new MazeControllers(frame);

            // Mostrar la ventana
            frame.setVisible(true);
        });
       
    }
}
