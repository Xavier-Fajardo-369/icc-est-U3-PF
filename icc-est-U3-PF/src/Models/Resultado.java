package Models;

public class Resultado {
    private final String algoritmo;
    private final int celdasRecorridas;
    private final long tiempoNano;

    public Resultado(String algoritmo, int celdasRecorridas, long tiempoNano) {
        this.algoritmo = algoritmo;
        this.celdasRecorridas = celdasRecorridas;
        this.tiempoNano = tiempoNano;
    }

    public String getAlgoritmo() { return algoritmo; }
    public int getCeldasRecorridas() { return celdasRecorridas; }
    public long getTiempoNano() { return tiempoNano; }


    
}
