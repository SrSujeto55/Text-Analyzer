package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import mx.unam.ciencias.edd.Lista;
import  mx.unam.ciencias.edd.proyecto3.Palabra;
/**
 * Clase para graficar Barras con SVG
 */
public class GraficaBarras<T> extends GraficadorGraficas<T> {
    /**ALoot of variables */
    private int largoAncho;
    private Lista<Palabra> data;
    private String rectangulos = "";
    private String text = "";
    private String lineas = "";
    private int margen;
    private int total;
    private int restantes;

    /**
     * Constructor de clase
     * @param topN El top n palabras mas usadas
     * @param restantes las palabras restantes que no están en el topn
     * @param totales el número total de palabras
     * @param largoAncho el alrgo y ancho del lienzo
     */
    public GraficaBarras(Lista<Palabra> topN, int restantes, int totales, int largoAncho){
        super(topN, restantes, totales);
        this.largoAncho = largoAncho;
        data = getFixedTop();
        margen = 30;
        total = totales;
        this.restantes = restantes;
    }

    /**
     * Metodo que regresa la representación en SVG de la gráfica de pastel
     */
    @Override
    protected String graficaT() {
        Calcula();
        return SVGraph.declaracionXML() + "\n" +
               SVGraph.empienzaSVG(largoAncho, largoAncho, "#barras") + "\n" +
               rectangulos + "\n" +
               lineas + "\n" +
               text + "\n" +
               SVGraph.finalizaSVG();
    }

    /**
     * Realiza los calculos correspondientes para hacer la grafica de barras
     */
    private void Calcula(){
        inicializa();
        int areaUtil = largoAncho - 2*margen;
        int offset = margen + 10;
        int desplazamientox = 0;
        int proporcional = 0;
        int desplazamientoy = 0;

        for(Palabra word : data){
            desplazamientox += offset;
            proporcional = (int) ((word.getPorcet() * areaUtil)/100);
            desplazamientoy = (areaUtil - proporcional) + margen;
            rectangulos += SVGraph.creaRectacngulo(desplazamientox, (areaUtil - desplazamientoy) + margen, 20, proporcional, "black", "white");
            text += SVGraph.creaTexto(((Integer) word.getFrecuencia()).toString(), "black", 10, desplazamientox + 10, desplazamientoy - 5);
            text += SVGraph.creaTexto(word.getPalabra(), "black", 5, desplazamientox + 10, largoAncho - (margen - 10)); 
        }

        desplazamientox += offset;
        proporcional = (int) ((((restantes * total)/100) * areaUtil)/100);
        desplazamientoy = (areaUtil - proporcional) + margen;

        rectangulos += SVGraph.creaRectacngulo(desplazamientox, (areaUtil - desplazamientoy) + margen, 20, proporcional, "black", "white");
        text += SVGraph.creaTexto(((Integer) restantes).toString(), "black", 10, desplazamientox + 10, desplazamientoy - 5);
        text += SVGraph.creaTexto("*Otros*", "black", 5, desplazamientox + 10, largoAncho - (margen - 10)); 
    }

    /**
     * Inicializa valores principales, como el círculo de pastel y la linea inicial en 0
     */
    private void inicializa(){
        lineas += SVGraph.creaLinea(margen, margen, margen, (largoAncho - margen) + 10, 3, "black");
        lineas += SVGraph.creaLinea(margen - 10, largoAncho - margen, largoAncho - margen, largoAncho - margen, 3, "black");
        lineas += SVGraph.creaLinea(margen - 10, margen, margen + 10, margen, 2, "black");
        lineas += SVGraph.creaLinea(margen - 10, (largoAncho - 2*margen)/2 + margen, margen + 10, (largoAncho - 2*margen)/2 + margen, 2, "black");
        text += SVGraph.creaTexto(((Integer)(total)).toString(), "black", 10, margen, margen - 5);
        text += SVGraph.creaTexto(((Integer)(total/2)).toString(), "black", 10, margen - 10, (largoAncho - 2*margen)/2 + margen - 5);
    }

    /**
     *Metodo que regresa la representación en SVG de la gráfica de pastel (Solo cuerpo)
     */
    @Override
    protected String graficaTCuerpoOnly(){
        Calcula();
        return SVGraph.declaracionXML() + "\n" +
               SVGraph.empienzaSVG(largoAncho, largoAncho, "#barras") + "\n" +
               rectangulos + "\n" +
               lineas + "\n" +
               text + "\n" +
               SVGraph.finalizaSVG();
    }
}
