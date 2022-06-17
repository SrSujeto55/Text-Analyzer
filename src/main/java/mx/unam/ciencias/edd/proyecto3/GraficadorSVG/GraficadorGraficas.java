package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.Palabra;
/**
 * Clase abstracta para las gráficas visuales
 */
public abstract class GraficadorGraficas<T> extends GrafEstructura<T> {
    private Lista<Palabra> topN;

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
     * Regresa la lista de palabras con su respectivo porcentaje
     * @return lo de arriba
     */
    protected Lista<Palabra> getFixedTop(){
        return topN;
    }

    /**
     * Metodo que se DEBE sobreescribir regresando la  forma en la que se presenta el SVG
     */
    @Override
    protected String graficaTCuerpoOnly(){
        return null;
    }

}
