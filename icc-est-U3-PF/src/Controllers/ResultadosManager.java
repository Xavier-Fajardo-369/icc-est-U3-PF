package Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Models.Resultado;

public class ResultadosManager {
    private final List<Resultado> resultados = new ArrayList<>();

    public void agregarResultado(Resultado r) {
        resultados.add(r);
    }

    public List<Resultado> obtenerResultados() {
        return resultados;
    }

    public void limpiarResultados() {
        resultados.clear();
    }

    // 🔹 Método para generar tabla visual
    public JTable generarTablaResultados() {
        String[] columnas = {"Algoritmo", "Celdas Recorridas", "Tiempo (ns)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Resultado r : resultados) {
            Object[] fila = {
                r.getAlgoritmo(),
                r.getCeldasRecorridas(),
                r.getTiempoNano()
            };
            modelo.addRow(fila);
        }

        return new JTable(modelo);
    }
}


   
    

