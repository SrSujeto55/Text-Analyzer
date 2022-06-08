package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase para crear los graficadores que grafican las gráficas
 * Puede graficar Arboles AVL y Arboles Rojinegros con los elementos recibidos.
 */
public class Graficador<T extends Comparable<T>> {
    /**Lista de elementos para crear arbol AVL/Rojinegro */
    private Lista<T> data;
    /**El graficador que nos permitirá graficar nuestras estructuras */
    private GrafEstructura<T> graficador;

    /**
     * Constructor único que nos inicializa la variable data
     * @param data la lista con la que se crearán nuestros árboles
     */
    public Graficador(Lista<T> data){
        this.data = data;
    }

    /**
     * Método para conseguir el arbol AVL graficado como SVG, cuando se use este método se debe hacer algo con el String
     * @return El String representante de la estructura en SVG
     */
    public String getGraficaAVL(){
        graficador = new GraficaAVL<>(new ArbolAVL<>(data), 20);
        return graficador.getGrafica();
    }

     /**
     * Método para conseguir el Arbol Rojinegro graficado como SVG, cuando se use este método se debe hacer algo con el String
     * @return El String representante de la estructura en SVG
     */
    public String getGraficaRojinegro(){
        graficador = new GraficaABRojinegro<>(new ArbolRojinegro<>(data), 20);
        return graficador.getGrafica();
    }


}
