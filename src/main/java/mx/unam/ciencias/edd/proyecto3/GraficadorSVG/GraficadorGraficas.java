package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.Palabra;
/**
 * Clase abstracta para las gráficas visuales
 */
public abstract class GraficadorGraficas<T> extends GrafEstructura<T> {
    private Lista<Palabra> topN;
    private double prtRestante;

    /**
     * Constructor único que extenderán las demás clases, en este método se calculan los porcentajes necesarios de 
     * las palábras más comunes y del resto si es que hay.
     * @param topN La lista de las N mejores palabras.
     * @param restantes el número de palabras que conforman las palabras restantes.
     * @param totales el número de palabras totales en las que se basará el porcentaje.
     */
    public GraficadorGraficas(Lista<Palabra> topN, int restantes, int totales){
        for(Palabra word : topN){
            word.setPorcent((word.getFrecuencia()/totales)*100); 
        }
        this.topN = topN;
        prtRestante = ((restantes/totales) *100);
    }
    
    /**
     * Metodo para revisar si la lista de las palabras comunes es vacía
     */
    @Override
    protected boolean esVacia() {
        return topN.esVacia();
    }

    /**
     * Metodo que se DEBE sobreescribir regresando la  forma en la que se presenta el SVG
     */
    @Override
    protected String graficaT() {
        return null;
    }

    /**
     * Metodo que se DEBE sobreescribir regresando la  forma en la que se presenta el SVG
     */
    @Override
    protected String graficaTCuerpoOnly(){
        return null;
    }
    
    /**
     * Regresa la Lista de Palabras a la clase que la extienda
     * @return La lista de palabras de la clase
     */
    public Lista<Palabra> getListaPorcentaje(){
        return topN;
    }

    /**
     * Regresa el porcentaje que representa el número totol de palabras restantes
     * @return La variable de clase prtRestante
     */
    public double getprtRestante(){
        return prtRestante;
    }
}
