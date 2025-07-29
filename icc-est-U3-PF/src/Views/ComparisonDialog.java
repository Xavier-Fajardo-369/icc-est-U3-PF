package Views;

import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ComparisonDialog extends JDialog {

    public ComparisonDialog(JFrame parent) {
        super(parent, "Gráficos Comparativos", true);
        
        // --- Usa un tamaño fijo para la ventana ---
        setSize(1200, 700);
        setLayout(new GridLayout(1, 2, 10, 10));

        try {
            // Cargar imagen de tiempo
            ImageIcon tiempoIconOriginal = new ImageIcon(getClass().getResource("/resources/comparacion_tiempo.png"));
            // Escalar la imagen
            Image tiempoImg = tiempoIconOriginal.getImage().getScaledInstance(520, 480, Image.SCALE_SMOOTH);
            ImageIcon tiempoIconEscalado = new ImageIcon(tiempoImg);
            JLabel tiempoLabel = new JLabel(tiempoIconEscalado);

            // Cargar imagen de longitud
            ImageIcon longitudIconOriginal = new ImageIcon(getClass().getResource("/resources/comparacion_longitud.png"));
            // Escalar la imagen
            Image longitudImg = longitudIconOriginal.getImage().getScaledInstance(520, 480, Image.SCALE_SMOOTH);
            ImageIcon longitudIconEscalada = new ImageIcon(longitudImg);
            JLabel longitudLabel = new JLabel(longitudIconEscalada);
            
            add(tiempoLabel);
            add(longitudLabel);

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this,
                "Error: No se pudieron cargar las imágenes.\nAsegúrate de que los archivos estén en la carpeta 'src/resources'.",
                "Error de Recurso",
                JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}