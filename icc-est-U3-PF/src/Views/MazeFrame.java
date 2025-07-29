package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controllers.ResultadosManager;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Models.Cell;
import Models.ModoEdicion;
import Models.Resultado;
import Solver.MazeSolver;
import Solver.solverImpl.MazeSolverBFS;
import Solver.solverImpl.MazeSolverDFS;
import Solver.solverImpl.MazeSolverRecursivo;
import Solver.solverImpl.MazeSolverRecursivoCompleto;
import Solver.solverImpl.MazeSolverRecursivoCompletoBT;

public class MazeFrame extends JFrame {
    private MazePanel mazePanel;
    private JComboBox<MazeSolver> comboSolver;
    private JButton solveBtn;
    private ResultadosManager resultadosManager = new ResultadosManager();
    private ModoEdicion modoActual = ModoEdicion.NONE;

    public MazeFrame(Cell[][] maze) {
        setTitle("Solver de Laberintos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior para edición
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton setStart = new JButton("Set Start");
        JButton setEnd = new JButton("Set End");
        JButton toggleWall = new JButton("Toggle Wall");
        setStart.addActionListener(e -> modoActual = ModoEdicion.SET_START);
        setEnd.addActionListener(e -> modoActual = ModoEdicion.SET_END);
        toggleWall.addActionListener(e -> modoActual = ModoEdicion.TOGGLE_WALL);
        topPanel.add(setStart);
        topPanel.add(setEnd);
        topPanel.add(toggleWall);
        add(topPanel, BorderLayout.NORTH);

        mazePanel = new MazePanel(maze, null, null);
        add(mazePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        DefaultListModel<String> listaModel = new DefaultListModel<>();
        listaModel.addElement("Recursivo");
        listaModel.addElement("Recursivo Completo");
        listaModel.addElement("Recursivo Completo BT");
        listaModel.addElement("DFS");
        listaModel.addElement("BFS");
        JList<String> listaVisual = new JList<>(listaModel);
        listaVisual.setPreferredSize(new Dimension(150, 100));
        bottomPanel.add(new JScrollPane(listaVisual), BorderLayout.WEST);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        comboSolver = new JComboBox<>();
        comboSolver.setMaximumRowCount(6);
        comboSolver.setPreferredSize(new Dimension(200, 25));

        comboSolver.addItem(new MazeSolverRecursivo());
        comboSolver.addItem(new MazeSolverRecursivoCompleto());
        comboSolver.addItem(new MazeSolverRecursivoCompletoBT());
        comboSolver.addItem(new MazeSolverDFS());
        comboSolver.addItem(new MazeSolverBFS());

        controlPanel.add(new JLabel("Algoritmo:"));
        controlPanel.add(comboSolver);

        solveBtn = new JButton("Resolver");
        JButton clearBtn = new JButton("Limpiar");
        controlPanel.add(solveBtn);
        controlPanel.add(clearBtn);
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Listener para sincronizar la selección del JList con el JComboBox
        listaVisual.addListSelectionListener(e -> {
            String sel = listaVisual.getSelectedValue();
            for (int i = 0; i < comboSolver.getItemCount(); i++) {
                if (comboSolver.getItemAt(i).getName().contains(sel)) {
                    comboSolver.setSelectedIndex(i);
                    break;
                }
            }
        });

        solveBtn.addActionListener(e -> {
            if (mazePanel.getStart() == null || mazePanel.getEnd() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar inicio y fin.");
                return;
            }
            MazeSolver solver = (MazeSolver) comboSolver.getSelectedItem();
            long inicio = System.nanoTime();
            List<Cell> camino = solver.solve(mazePanel.getMaze(), mazePanel.getStart(), mazePanel.getEnd());
            long fin = System.nanoTime();

            if (camino == null || camino.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontró un camino.");
                return;
            }

            Resultado r = new Resultado(solver.getName(), solver.getSteps(), fin - inicio);
            resultadosManager.agregarResultado(r);

            mazePanel.setPath(camino);
        });

        mazePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Cell[][] mat = mazePanel.getMaze();
                int rows = mat.length, cols = mat[0].length;
                int cw = mazePanel.getWidth() / cols;
                int ch = mazePanel.getHeight() / rows;
                int c = e.getX() / cw;
                int r = e.getY() / ch;
                if (r < 0 || r >= rows || c < 0 || c >= cols) return;
                Cell clicked = mat[r][c];
                if (modoActual == ModoEdicion.SET_START) {
                    mazePanel.setStart(clicked);
                } else if (modoActual == ModoEdicion.SET_END) {
                    mazePanel.setEnd(clicked);
                } else if (modoActual == ModoEdicion.TOGGLE_WALL) {
                    clicked.setWalkable(!clicked.isWalkable());
                    mazePanel.repaint();
                }
            }
        });

        // *** Aquí agregamos el listener para el botón Limpiar ***
        clearBtn.addActionListener(e -> {
            // Limpiar el camino visualizado
            mazePanel.setPath(new java.util.ArrayList<>());

            // Quitar inicio y fin
            mazePanel.setStart(null);
            mazePanel.setEnd(null);

            // Restaurar todas las celdas como caminables
            Cell[][] mazeActual = mazePanel.getMaze();
            for (int r = 0; r < mazeActual.length; r++) {
                for (int c = 0; c < mazeActual[0].length; c++) {
                    mazeActual[r][c].setWalkable(true);
                }
            }

            // Limpiar resultados guardados
            resultadosManager.limpiarResultados();

            // Repintar panel para reflejar los cambios
            mazePanel.repaint();
        });

        setVisible(true);
    }
}