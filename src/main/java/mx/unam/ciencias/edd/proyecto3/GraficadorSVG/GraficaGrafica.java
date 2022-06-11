package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
/**
 * clase para graficar una grafica con SVG, la clase implementa los metodos faltantes de
 * @link{GrafEstructura}
 */
import mx.unam.ciencias.edd.VerticeGrafica;

public class GraficaGrafica<T> extends GrafEstructura<T> {

    private Grafica<T> graph;
    private int elementos;
    private int DimensionesCuadradas;
    private int margen = 10;
    private int radioCirculo;
    private int tamanoLetra = 4;
    private double Centro;
    private int cRadio;
    private double incrementoRad;
    private String vertices = "";
    private String aristas = "";
    private String text = "";
    private Lista<coord> Coordenados = new Lista<>();
    private Lista<VerticeGrafica<T>> vert = new Lista<>();


    /**
     * Clase interna para guardar coordenadas para aristas
     */
    private class coord{
        private double X;
        private double Y;
        private T elemento;

        public coord(double X, double Y, T elemento){
            this.X = X;
            this.Y = Y;
            this.elemento = elemento;
        }
    }

    /**
     * Constructor unico para la construccion del SVG de la grafica
     */
    public GraficaGrafica(Lista<T> data){
        graph = construyeGrafica(data);
        elementos = graph.getElementos();
        graph.paraCadaVertice((v) -> vert.agregaFinal(v));
    }

    /**
     * metodo para ver si la grafica es vacia
     */
    @Override
    protected boolean esVacia() {
        return graph.esVacia();
    }

    /**
     * Metodo para regresar la representacion en SVG de la grafica
     * (Cuerpo Only) Este metodo unicamente debe ejecutrse si se quiere obtener SOLO el cuerpo
     */
    @Override
    protected String graficaTCuerpoOnly() throws IllegalArgumentException{
        Calcula();
        return aristas + "\n" +
               vertices + "\n" + 
               text;
    }

     /**
     * Metodo para regresar la representacion en SVG de la grafica
     */
    @Override
    protected String graficaT() throws IllegalArgumentException{
        Calcula();
        return SVGraph.declaracionXML() + "\n" + 
               SVGraph.empienzaSVG(DimensionesCuadradas, DimensionesCuadradas) + "\n" + 
               aristas + "\n" +
               vertices + "\n" + 
               text + "\n" + 
               SVGraph.finalizaSVG();
    }

    /**
     * Metodo para realizar los calculos necesarios
     * ALERTA POR METODO CUADRATICO
     */
    private void Calcula(){
        radioCirculo = (int) Math.ceil((tamanoLetra*3)/2);
        DimensionesCuadradas = elementos*radioCirculo*2 + 2*margen;
        Centro = (int) Math.ceil(DimensionesCuadradas/2);
        cRadio = (int) Math.ceil((DimensionesCuadradas - margen*2)/2);
        incrementoRad = ((360)/elementos)*(Math.PI/180);
        double anguloA = 0;
        for(VerticeGrafica<T> v : vert){
            double x = Math.cos(anguloA)*cRadio;
            x = Centro - x;
            double y = Math.sin(anguloA)*cRadio;
            y = Centro -y;

            vertices += SVGraph.creaCirculoG(x, y, radioCirculo, "#F29325") + "\n";
            Coordenados.agregaFinal(new coord(x, y, v.get()));
            text += SVGraph.creaTextoG(v.get().toString(), "#000000", tamanoLetra, x, y+1);
            anguloA += incrementoRad;

            for(VerticeGrafica<T> vecino : v.vecinos()){
                coord vec = getCoord(vecino, Coordenados);
                    if(vec != null)
                        aristas += SVGraph.creaLineaG(x, y, vec.X, vec.Y, 1, "#007172");
                    
            }
        } 
    }

    private coord getCoord(VerticeGrafica<T> v, Lista<coord> coordenados){
        for(coord xy : coordenados)
            if(xy.elemento.equals(v.get()))
                return xy;
            return null;
    }

    private Grafica<T> construyeGrafica(Lista<T> datos){
        if(datos.getElementos()%2 != 0){
            System.out.println("Necesitamos un numero par de elementos");
            System.exit(1);
        }

        Grafica<T> graph = new Grafica<>();

        // Agregamos los vértices a la gráfica.
        for (T vertice : datos){
            if (!graph.contiene(vertice))
                graph.agrega(vertice);
        }
            

        // Creamos las aristas de la gráfica.
        int i = 0;
        T V0 = null;
        for (T vertice : datos){
            if (i++ % 2 == 0)
                V0 = vertice;

            else if (V0.equals(vertice))
                continue;

            else if (!graph.sonVecinos(V0, vertice))
                graph.conecta(V0, vertice);
        }

        return graph;
    }
    
}
