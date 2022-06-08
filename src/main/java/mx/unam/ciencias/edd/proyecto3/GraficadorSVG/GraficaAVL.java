package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase para crear una representacion SVG de un arbol AVL extiende a graficaArbol
 */
public class GraficaAVL<T extends Comparable<T>> extends GraficaArbol<T> {

    public GraficaAVL(ArbolAVL<T> data, int borde) {
        super(data, borde);
    }

    /**
     * Metodo que sobreescribe a @link{graficaTexto} asignando un valor extra, 
     * su balance de vertice
     */
    @Override
    protected String graficaTexto(VerticeArbolBinario<T> v, int x, int y){
        
        String vertice = v.toString();
        String[] parts = vertice.split(" ");

        return SVGraph.creaTexto(parts[0], "#000000", 
        4, x, y+1) + "\n" + 
        SVGraph.creaTexto("{" + parts[1] + "}", "#000000", 6, x+17, y+1); 
    }
    
}
