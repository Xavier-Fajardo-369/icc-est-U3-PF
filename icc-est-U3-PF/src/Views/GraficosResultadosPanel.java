package Views;

import java.util.List;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import Models.Resultado;

public class GraficosResultadosPanel extends JPanel {

    public GraficosResultadosPanel(List<Resultado> resultados) {
        setLayout(new BorderLayout());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Resultado r : resultados) {
            dataset.addValue(r.getTiempoNano(), "Tiempo (ns)", r.getAlgoritmo());
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Tiempos de Ejecución por Algoritmo",
            "Algoritmo",
            "Tiempo (ns)",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }
}






