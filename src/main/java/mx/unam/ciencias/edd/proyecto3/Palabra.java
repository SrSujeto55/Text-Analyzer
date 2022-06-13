package mx.unam.ciencias.edd.proyecto3;
/**
 * Clase para representar palabras y su ocurrencia
 */
public class Palabra implements Comparable<Palabra> {
    private String palabra;
    private Integer frecuencia;
    private double porcent = 0;

    public Palabra(String palabra, int frecuencia){
        this.palabra = palabra;
        this.frecuencia = frecuencia;
    }

    public void setPorcent(double porcent){
        this.porcent = porcent;
    }

    public double getPorcet(){
        return porcent;
    }

    public String getPalabra(){
        return palabra;
    }

    public int getFrecuencia(){
        return frecuencia;
    }

    @Override
    public String toString(){
        return String.format("%s, %d - %d", palabra, frecuencia, porcent);
    }

    @Override
    public int compareTo(Palabra b) {
        return frecuencia.compareTo(b.frecuencia);
    }
    
}
