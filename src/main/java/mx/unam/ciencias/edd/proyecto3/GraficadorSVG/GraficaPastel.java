package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.Palabra;

public class GraficaPastel<T> extends GraficadorGraficas<T>{
    private Lista<Palabra> data;
    private String texto = "";
    private String circulo = "";
    private String lineas = "";
    private int largoAncho;
    private double desplazamientoRadian = 0;
    private double centro;
    private int radio;


    public GraficaPastel(Lista<Palabra> topN, int restantes, int totales, int largoAncho){
        super(topN, restantes, totales);
        this.largoAncho = largoAncho;
        data = getListaPorcentaje();
    }

     /**
     * Metodo que regresa la representación en SVG de la gráfica de pastel
     */
    @Override
    protected String graficaT() {
        Calcula();
        return SVGraph.declaracionXML() + "\n" +
               SVGraph.empienzaSVG(largoAncho, largoAncho) + "\n" +
               circulo + "\n" +
               lineas  + "\n" +
               texto + "\n" +
               SVGraph.finalizaSVG();
    }

    /**
     *Metodo que regresa la representación en SVG de la gráfica de pastel (Solo cuerpo)
     */
    @Override
    protected String graficaTCuerpoOnly(){
        Calcula();
        return circulo + "\n" +
               lineas  + "\n" +
               texto + "\n";
    }


    /**
     * Método para hacer los cálculos necesarios para el SVG
     */
    private void Calcula(){
        inicializa();
        for(Palabra word : data){
            double porcentaje = word.getPorcet();
            desplazamientoRadian += (((porcentaje * 360)/100)*Math.PI)/180;
            double x = centro - (Math.cos(desplazamientoRadian) * radio);
            double y = centro - (Math.sin(desplazamientoRadian) * radio);
            lineas += SVGraph.creaLineaG(centro, centro, x, y, 2, "black");
            texto += SVGraph.creaTextoG(word.toString(), "yellow", 20, x, y);
        }
    }

    /**
     * Inicializa valores principales, como el círculo de pastel y la linea inicial en 0
     */
    private void inicializa(){
        centro = largoAncho/2;
        radio =(int) centro - 50;
        circulo += SVGraph.creaCirculoG(centro, centro, radio, "white");
        lineas += SVGraph.creaLineaG(centro, centro, 50, centro, 2, "black");
    }
}
