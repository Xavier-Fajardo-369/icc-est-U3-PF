package Views;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controllers.ResultadosManager;

import java.awt.FlowLayout;
import Models.Cell;
import Models.Resultado;
import Solver.MazeSolver;
import Solver.solverImpl.MazeSolverBFS;
import Solver.solverImpl.MazeSolverDFS;
import Solver.solverImpl.MazeSolverRecursivo;
import Solver.solverImpl.MazeSolverRecursivoCompleto;
import Solver.solverImpl.MazeSolverRecursivoCompletoBT;

public class MazeFrame extends JFrame {
    private final MazePanel mazePanel;
    private final JComboBox<MazeSolver> comboSolver;
    private final JButton solveBtn;
    private final ResultadosManager resultadosManager = new ResultadosManager();

    public MazeFrame(Cell[][] maze, Cell start, Cell end) {
        setTitle("Solver de Laberintos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menú superior
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu archivo = new JMenu("Archivo");
        menuBar.add(archivo);

        JMenuItem nuevoItem = new JMenuItem("Nuevo Laberinto");
        JMenuItem resultadosItem = new JMenuItem("Ver Resultados");
        JMenuItem salirItem = new JMenuItem("Salir");

        archivo.add(nuevoItem);
        archivo.add(resultadosItem);
        archivo.add(salirItem);

        resultadosItem.addActionListener(e -> {
            List<Resultado> lista = resultadosManager.obtenerResultados();
            String[] columnas = { "Algoritmo", "Celdas", "Tiempo (ns)" };
            Object[][] datos = new Object[lista.size()][3];

            for (int i = 0; i < lista.size(); i++) {
                Resultado r = lista.get(i);
                datos[i][0] = r.getAlgoritmo();
                datos[i][1] = r.getCeldasRecorridas();
                datos[i][2] = r.getTiempoNano();
            }

            JTable tabla = new JTable(datos, columnas);
            JScrollPane scroll = new JScrollPane(tabla);

            JFrame ventana = new JFrame("Resultados Guardados");
            ventana.setSize(400, 300);
            ventana.add(scroll);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });

        salirItem.addActionListener(e -> System.exit(0));

        // Panel superior
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton setStart = new JButton("Set Start");
        JButton setEnd = new JButton("Set End");
        JButton toggleWall = new JButton("Toggle Wall");
        topPanel.add(setStart);
        topPanel.add(setEnd);
        topPanel.add(toggleWall);
        add(topPanel, BorderLayout.NORTH);

        // Panel central del laberinto
        mazePanel = new MazePanel(maze, start, end);
        add(mazePanel, BorderLayout.CENTER);

        // Panel inferior
        JPanel bottomPanel = new JPanel(new FlowLayout());
        comboSolver = new JComboBox<>();
        comboSolver.addItem(new MazeSolverRecursivoCompletoBT());

        solveBtn = new JButton("Resolver");
        JButton stepBtn = new JButton("Paso a paso");
        JButton clearBtn = new JButton("Limpiar");

        bottomPanel.add(comboSolver);
        bottomPanel.add(solveBtn);
        bottomPanel.add(stepBtn);
        bottomPanel.add(clearBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // Acción del botón Resolver
        solveBtn.addActionListener(e -> {
            MazeSolver solver = (MazeSolver) comboSolver.getSelectedItem();
            long inicio = System.nanoTime();

            solver.solve(mazePanel.getMaze(), mazePanel.getStart(), mazePanel.getEnd());

            long fin = System.nanoTime();
            int pasos = solver.getSteps(); // Asegúrate de tener este método en tu solver

            Resultado r = new Resultado(solver.toString(), pasos, fin - inicio);
            resultadosManager.agregarResultado(r);

            mazePanel.repaint();
        });

        setVisible(true);
    }
}







    


