package Views;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ResultadosDialog extends JDialog {
    public ResultadosDialog(JFrame parent, String algoritmo, int pasos) {
        super(parent, "Resultado", true);
        setLayout(new BorderLayout());

        String mensaje = String.format("Algoritmo: %s\nPasos recorridos: %d", algoritmo, pasos);
        JTextArea area = new JTextArea(mensaje);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(area, BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        add(cerrar, BorderLayout.SOUTH);

        setSize(300, 150);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}


