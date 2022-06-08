package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

/**
 * Clase abstracta generica para la grafiacion de una estructura, las estructuras de datos 
 * van a extener esta clase e implementar los metodos faltantes;
 */

public abstract class GrafEstructura<T>{

    /**
     * Metodo para regresar la graficacion de la grafica vacia en el caso de que esta 
     * no tenga elementos o la grafica normal en e caso contrario
     * @return String de creacion del SVG
     */
    public String getGrafica(){
        if(esVacia())
            return graficaVacia();
        else
            return graficaT();
    }

    public String getCuerpoOnly(){
        if(esVacia())
            return graficaVaciaCuerpoOnly();
        else
            return graficaTCuerpoOnly();
    }

    /**
     * Metodo pensado para reescribirse en alguna estructura, no debe ser usado
     * por omision en ningun lado
     * @return 0 por defecto
     */
    public int getAltura(){
        return 0; 
    }

    /**
     * Metodo pensado para reescribirse en alguna estructura, no debe ser usado
     * por omision en ningun lado a menos que sea sobreescrito
     * @return 0 por defecto
     */
    public int getAncho(){
        return 0;
    }

    /**
     * Metodo para regresar el diseno de SVG por defecto para una graficacion vacia
     * @return el String creador del SVG
     */
    public String graficaVacia(){
        return SVGraph.declaracionXML() + 
                SVGraph.empienzaSVG(200, 200) +
                SVGraph.creaLinea(0, 0, 200, 200, 2, "#0185d1") + "\n" + 
                SVGraph.creaLinea(200, 0, 0, 200, 2, "#ff9900") + "\n" + 
                SVGraph.creaCirculo(0, 0, 20, "#ff9900", "#ff9900") + "\n" + 
                SVGraph.creaCirculo(200, 0, 20, "#0185d1", "#0185d1") + "\n" + 
                SVGraph.creaCirculo(200, 200, 20, "#ff9900", "#ff9900") + "\n" + 
                SVGraph.creaCirculo(0, 200, 20, "#0185d1", "#0185d1") + "\n" + 
                SVGraph.creaCirculo(100, 100, 50, "#ffbd06", "#ffffff") + "\n" + 
                SVGraph.creaTexto("Empty", "#000000", 20, 100, 105) + "\n" + 
                SVGraph.finalizaSVG();
    }

    public String graficaVaciaCuerpoOnly(){
        return SVGraph.creaLinea(0, 0, 200, 200, 2, "#0185d1") + "\n" + 
                SVGraph.creaLinea(200, 0, 0, 200, 2, "#ff9900") + "\n" + 
                SVGraph.creaCirculo(0, 0, 20, "#ff9900", "#ff9900") + "\n" + 
                SVGraph.creaCirculo(200, 0, 20, "#0185d1", "#0185d1") + "\n" + 
                SVGraph.creaCirculo(200, 200, 20, "#ff9900", "#ff9900") + "\n" + 
                SVGraph.creaCirculo(0, 200, 20, "#0185d1", "#0185d1") + "\n" + 
                SVGraph.creaCirculo(100, 100, 50, "#ffbd06", "#ffffff") + "\n" + 
                SVGraph.creaTexto("Empty", "#000000", 20, 100, 105);
    }

    /** Metodo implementable para saber si una estructura es vacia */
    protected abstract boolean esVacia();
    /** Metodo para graficar una estructura */
    protected abstract String graficaT();
    /** Metodo para obtener el unicamente el cuerpo de la estructura en SVG */
    protected abstract String graficaTCuerpoOnly();
}
