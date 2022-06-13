package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.proyecto3.Palabra;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase para crear los graficadores que grafican las gráficas
 * Puede graficar Arboles AVL y Arboles Rojinegros con los elementos recibidos.
 */
public class Graficador {
    /**Lista de elementos para crear arbol AVL/Rojinegro */    /**El graficador que nos permitirá graficar nuestras estructuras */
    private GrafEstructura<Palabra> graficador;
    /** Instancia de CalculadorSVG para conseguir los cálculos necesaarios y listas correspondientes */
    private CalculadorSVG palabrasComunes;
    /** Los datos para llenar y crear los árboles y gráficas en base a las 15 palabras más comunes*/
    private Lista<Palabra> data;
    /** El número total de palabras que conforman el resto de palabras no contenidas en las más comunes */
    private int palRestantes;
    /** El número total de palabras evaluadas, para tener un registro de porcentaje */
    private int total;
    
    /**
     * Constructor único que nos inicializa la variable data
     * @param data la lista con la que se crearán nuestros árboles
     */
    public Graficador(Diccionario<String, Palabra> contador){
        palabrasComunes = new CalculadorSVG(contador);
        data = palabrasComunes.getPalabrasFrecuentes(15);
        palRestantes = palabrasComunes.getElementosRestantes();
        total = palabrasComunes.getTotalPalabras();
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

    /**
     * Método para conseguir la gráfca de pastel graficada como SVG, cuando se use este método se debe hacer algo con el String
     * @return El String representante de la estructura en SVG
     */
    public String getGraficapastel(){
        graficador = new GraficaPastel<>(data, palRestantes, total, 700);
        return graficador.getGrafica();
    }

    /**
     * Método para conseguir la gráfica de barras graficada como SVG, cuando se use este método se debe hacer algo con el String
     * @return El String representante de la estructura en SVG
     */
    public String getGraficaBarras(){
        graficador = new GraficaBarras<>(data, palRestantes, total);
        return graficador.getGrafica();
    }


}
