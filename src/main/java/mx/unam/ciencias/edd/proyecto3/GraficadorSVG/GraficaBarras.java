package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import mx.unam.ciencias.edd.Lista;
import  mx.unam.ciencias.edd.proyecto3.Palabra;
/**
 * Clase para graficar Barras con SVG
 */
public class GraficaBarras<T> extends GraficadorGraficas<T> {
    public GraficaBarras(Lista<Palabra> topN, int restantes, int totales){
        super(topN, restantes, totales);
    }

    /**
     * Metodo que regresa la representación en SVG de la gráfica de pastel
     */
    @Override
    protected String graficaT() {
        return null;
    }

    /**
     *Metodo que regresa la representación en SVG de la gráfica de pastel (Solo cuerpo)
     */
    @Override
    protected String graficaTCuerpoOnly(){
        return null;
    }
}
