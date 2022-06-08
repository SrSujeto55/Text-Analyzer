package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * clase para graficar Arboles rojinegros, extiende a GraficaArbol
 */
public class GraficaABRojinegro<T extends Comparable<T>> extends GraficaArbol<T> {

    public GraficaABRojinegro(ArbolRojinegro<T> arbol, int borde) {
        super(arbol, borde);
    }

    /**
     * Se sobreescribe el metodo de @link{graficaVertice} para asignar color
     */
    @Override
    protected String graficaVertice(VerticeArbolBinario<T> v, int x, int y, int r){

        Color color = ((ArbolRojinegro<T>) AB).getColor(v);
        return SVGraph.creaCirculo(x, y, r, 
        color == Color.NEGRO ? "black" : "#F29325", color == Color.NEGRO ? "black" : "#F29325") + "\n";
    }

    /**
     * Se sobreescribe el metodo de @link{graficaTexto} para asignar textos de colorines
     */
    @Override
    protected String graficaTexto(VerticeArbolBinario<T> v, int x, int y){
        Color color = ((ArbolRojinegro<T>) AB).getColor(v);
        return SVGraph.creaTexto(v.toString(), color == Color.NEGRO ? "white" : "#000000", 
        4, x, y+1);
    }     
}
