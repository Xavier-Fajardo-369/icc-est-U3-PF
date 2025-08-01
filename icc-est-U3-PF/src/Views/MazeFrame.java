package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

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
    private List<Cell> caminoActualPasoAPaso;
    private int pasoActual = 0;
    private JButton solveBtn;
    private ResultadosManager resultadosManager = new ResultadosManager();
    private ModoEdicion modoActual = ModoEdicion.NONE;
    private JLabel lblDescripcion;

    public MazeFrame(Cell[][] maze) {
        setTitle("Solver de Laberintos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menú "Archivo"
        JMenuBar menuBar = new JMenuBar();
        JMenu archivoMenu = new JMenu("Archivo");

        JMenuItem nuevoItem = new JMenuItem("Nuevo Laberinto");
        nuevoItem.addActionListener(e -> {
            try {
                String filasStr = JOptionPane.showInputDialog(this, "Ingrese número de filas:");
                String columnasStr = JOptionPane.showInputDialog(this, "Ingrese número de columnas:");
                if (filasStr == null || columnasStr == null) return;

                int rows = Integer.parseInt(filasStr.trim());
                int cols = Integer.parseInt(columnasStr.trim());
                if (rows <= 0 || cols <= 0) throw new NumberFormatException();

                Cell[][] nuevo = new Cell[rows][cols];
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        nuevo[r][c] = new Cell(r, c, true);
                    }
                }

                mazePanel.setMaze(nuevo);
                mazePanel.setStart(null);
                mazePanel.setEnd(null);
                mazePanel.setPath(new java.util.ArrayList<>());
                caminoActualPasoAPaso = null;
                pasoActual = 0;
                mazePanel.repaint();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valores inválidos. Intente nuevamente.");
            }
        });

        JMenuItem verResultadosItem = new JMenuItem("Ver Resultados");
        verResultadosItem.addActionListener(e -> {
            JFrame resultadoFrame = new JFrame("Resultados Guardados");
            resultadoFrame.setSize(600, 400);
            resultadoFrame.setLocationRelativeTo(this);
            resultadoFrame.setLayout(new BorderLayout());

            JTable tabla = resultadosManager.generarTablaResultados();
            resultadoFrame.add(new JScrollPane(tabla), BorderLayout.CENTER);

            JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnLimpiar = new JButton("Limpiar Resultados");
            JButton btnGraficar = new JButton("Graficar Resultados");

            btnLimpiar.addActionListener(ev -> {
                resultadosManager.limpiarResultados();
                JOptionPane.showMessageDialog(resultadoFrame, "Resultados limpiados.");
                resultadoFrame.dispose();
            });

            btnGraficar.addActionListener(ev -> {
                JFrame grafico = new JFrame("Comparación de algoritmos");
                grafico.setSize(800, 500);
                grafico.setLocationRelativeTo(resultadoFrame);
                grafico.setLayout(new BorderLayout());
                grafico.add(new GraficosResultadosPanel(resultadosManager.obtenerResultados()), BorderLayout.CENTER);
                grafico.setVisible(true);
            });

            botonesPanel.add(btnLimpiar);
            botonesPanel.add(btnGraficar);
            resultadoFrame.add(botonesPanel, BorderLayout.SOUTH);
            resultadoFrame.setVisible(true);
        });

        archivoMenu.add(nuevoItem);
        archivoMenu.add(verResultadosItem);
        menuBar.add(archivoMenu);
        setJMenuBar(menuBar);

        // Panel de edición (botones para modo)
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

        // Panel del laberinto
        mazePanel = new MazePanel(maze, null, null);
        add(mazePanel, BorderLayout.CENTER);

        // Añadir MouseListener para interactuar con el laberinto y modificar según modoActual
        mazePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int cols = mazePanel.getMaze()[0].length;
                int rows = mazePanel.getMaze().length;

                int cellWidth = mazePanel.getWidth() / cols;
                int cellHeight = mazePanel.getHeight() / rows;

                int col = e.getX() / cellWidth;
                int row = e.getY() / cellHeight;

                if (row < 0 || row >= rows || col < 0 || col >= cols) return;

                Cell clickedCell = mazePanel.getMaze()[row][col];

                switch (modoActual) {
                    case SET_START:
                        mazePanel.setStart(clickedCell);
                        break;
                    case SET_END:
                        mazePanel.setEnd(clickedCell);
                        break;
                    case TOGGLE_WALL:
                        clickedCell.setWalkable(!clickedCell.isWalkable());
                        break;
                    default:
                        // No hacer nada
                        break;
                }

                mazePanel.clearPath();
                caminoActualPasoAPaso = null;
                pasoActual = 0;

                mazePanel.repaint();
            }
        });

        // Panel inferior
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
        JButton pasoBtn = new JButton("Paso a Paso");

        controlPanel.add(solveBtn);
        controlPanel.add(clearBtn);
        controlPanel.add(pasoBtn);

        bottomPanel.add(controlPanel, BorderLayout.CENTER);

        // Label para descripción algoritmo
        lblDescripcion = new JLabel("Descripción del algoritmo");
        bottomPanel.add(lblDescripcion, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Sincronización entre lista visual y combo
        listaVisual.addListSelectionListener(e -> {
            String sel = listaVisual.getSelectedValue();
            for (int i = 0; i < comboSolver.getItemCount(); i++) {
                if (comboSolver.getItemAt(i).toString().equals(sel)) {
                    comboSolver.setSelectedIndex(i);
                    break;
                }
            }
        });

        // Acción "Paso a Paso"
        pasoBtn.addActionListener(e -> {
            if (mazePanel.getStart() == null || mazePanel.getEnd() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar inicio y fin.");
                return;
            }

            if (caminoActualPasoAPaso == null || pasoActual == 0) {
                MazeSolver solver = (MazeSolver) comboSolver.getSelectedItem();
                caminoActualPasoAPaso = solver.solve(mazePanel.getMaze(), mazePanel.getStart(), mazePanel.getEnd());
                if (caminoActualPasoAPaso == null || caminoActualPasoAPaso.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontró camino.");
                    return;
                }
                pasoActual = 0;
            }

            if (pasoActual < caminoActualPasoAPaso.size()) {
                List<Cell> parcial = caminoActualPasoAPaso.subList(0, pasoActual + 1);
                mazePanel.setPath(parcial);
                pasoActual++;
            } else {
                JOptionPane.showMessageDialog(this, "Ya se mostró todo el camino paso a paso.");
                caminoActualPasoAPaso = null;
                pasoActual = 0;
            }
        });

        // Acción "Resolver instantáneamente"
        solveBtn.addActionListener(e -> {
            if (mazePanel.getStart() == null || mazePanel.getEnd() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar inicio y fin.");
                return;
            }

            MazeSolver solver = (MazeSolver) comboSolver.getSelectedItem();
            List<Cell> camino = solver.solve(mazePanel.getMaze(), mazePanel.getStart(), mazePanel.getEnd());
            if (camino == null || camino.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontró camino.");
            } else {
                mazePanel.setPath(camino);
            }
        });

        // Acción "Limpiar"
        clearBtn.addActionListener(e -> {
            Cell[][] m = mazePanel.getMaze();
            for (int r = 0; r < m.length; r++) {
                for (int c = 0; c < m[0].length; c++) {
                    m[r][c].setVisited(false);
                    m[r][c].setPath(false);
                }
            }
            mazePanel.setPath(new java.util.ArrayList<>());
            caminoActualPasoAPaso = null;
            pasoActual = 0;
            mazePanel.repaint();
        });

        // Actualizar descripción del algoritmo seleccionado
        comboSolver.addActionListener(e -> {
            MazeSolver solver = (MazeSolver) comboSolver.getSelectedItem();
            lblDescripcion.setText(solver.getDescription());
        });

        setVisible(true);
    }
}




        





        






