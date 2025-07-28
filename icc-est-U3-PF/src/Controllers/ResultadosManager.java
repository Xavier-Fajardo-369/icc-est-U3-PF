package Controllers;

import java.util.ArrayList;
import java.util.List;

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


    
}
