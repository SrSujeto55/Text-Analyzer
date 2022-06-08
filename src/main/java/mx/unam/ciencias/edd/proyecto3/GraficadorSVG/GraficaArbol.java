package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase para graficar arboles completos en SVG la clase implementa los metodos faltantes de
 * @link{GrafEstructura}
 */

public abstract class GraficaArbol<T> extends GrafEstructura<T> {
    /**El arbol binario completo a trabajar */
    protected ArbolBinario<T> AB;
    public int alturaSVG;
    public int anchoSVG;
    private int bordes;
    private int cambioY;
    private int cambioX;
    private int radioVertice;
    private int tamLetra = 4;
    private String conexiones = "";
    private String verticeSTR = "";
    private String texto = "";
    
    /**
     * Clase interna privada para asignar coordenadas y guardarlas para su uso posterior
     */
    private class cood{
        private int x;
        private int y;
        public cood(int x, int y){
            this.x = x; 
            this.y = y;
        }
    }

    /**
     * Cosntructor unico que inicializa los valores importantes y asigna un arbol
     * @param data Un arbol Binario y sus posibles extensiones
     */
    public GraficaArbol(ArbolBinario<T> data, int borde){
        AB = data;
        bordes = borde;
        cambioY = 50;
        cambioX = 30;
    }


    /**
     * metodo para verificar si el arbol es vacio
     */
    @Override
    protected boolean esVacia() {
        return AB.esVacia();
    }

    /**
     * metodo para regresar el String construido con la representacion SVG del arbol
     */
    @Override
    protected String graficaT() {
        Calcula();
        GraficaStruc();
        return SVGraph.declaracionXML() + "\n" +
               SVGraph.empienzaSVG(anchoSVG, alturaSVG) + "\n" +
               conexiones + "\n" +
               verticeSTR + "\n" +
               texto + "\n" +
               SVGraph.finalizaSVG();
    }

    /**
     * metodo para regresar el String construido con la representacion SVG del arbol 
     * (Cuerpo Only) Este metodo unicamente debe ejecutrse si se quiere obtener SOLO el cuerpo
     */
    @Override
    protected String graficaTCuerpoOnly() {
        Calcula();
        GraficaStruc();
        return conexiones + "\n" +
               verticeSTR + "\n" +
               texto;
    }


    private void Calcula(){
        radioVertice = (int) Math.ceil((tamLetra * 3)/2);
        alturaSVG = AB.altura() * (cambioY + (radioVertice * 2)) + (radioVertice * 2) + (bordes * 2);
    }

    public void GraficaStruc(){
        Pila<VerticeArbolBinario<T>> vertices = new Pila<>();
        Pila<Integer> nivel = new Pila<>();
        Pila<cood> coords = new Pila<>();
        VerticeArbolBinario<T> vertice = AB.esVacia() ? null : AB.raiz();
        anchoSVG = bordes;

        meteRamaIzquierda(vertice, vertices, nivel, 0);
        int cambioAnch = 2 * radioVertice + cambioX;
        int cambioLarg = 2 * radioVertice + cambioY;

        while(!vertices.esVacia()){
            vertice = vertices.saca();
            int lvl = nivel.saca();

            int CooX = anchoSVG + radioVertice;
            int CooY = bordes + cambioLarg*lvl + radioVertice;

            verticeSTR += graficaVertice(vertice, CooX, CooY, radioVertice);

            texto += graficaTexto(vertice, CooX, CooY);

            anchoSVG += cambioAnch;

            if(vertice.hayDerecho())
                meteRamaIzquierda(vertice.derecho(), vertices, nivel, lvl+1);

            conectaVertices(vertice, coords, CooX, CooY);
        }
        
    }

    private void conectaVertices(VerticeArbolBinario<T> vertice, Pila<cood> coords, int x, int y){
        if(vertice.hayIzquierdo()){
            cood hijoXY = coords.saca();
            conexiones += SVGraph.creaLinea(hijoXY.x, hijoXY.y, x, y, 2, "#007172");
        }

        if(esDerecho(vertice)){
            cood hijoXY = coords.saca();
            conexiones += SVGraph.creaLinea(hijoXY.x, hijoXY.y, x, y, 2, "#007172");
        }

        if(vertice.hayDerecho())
            coords.mete(new cood(x, y));

        if(esIzquierdo(vertice))
            coords.mete(new cood(x, y));
    }

    private void meteRamaIzquierda(VerticeArbolBinario<T> v, Pila<VerticeArbolBinario<T>> vertices,
     Pila<Integer> vertNivel, int nivel){
         if(v == null)
            return;
        VerticeArbolBinario<T> vertAux = v;
        int nivelAux = nivel;

        vertices.mete(vertAux);
        vertNivel.mete(nivelAux);

        while(vertAux.hayIzquierdo()){
            vertAux = vertAux.izquierdo();
            vertices.mete(vertAux);
            vertNivel.mete(++nivelAux);
        }
    }

    private boolean esIzquierdo(VerticeArbolBinario<T> v){
        if(v == AB.raiz())
            return false;

        if(!v.padre().hayIzquierdo())
            return false;

        return v.padre().izquierdo() == v;     
    }

    private boolean esDerecho(VerticeArbolBinario<T> v){
        if(v == AB.raiz())
            return false;

        if(!v.padre().hayDerecho())
            return false;

        return v.padre().derecho() == v;     
    }

    protected String graficaVertice(VerticeArbolBinario<T> v, int x, int y, int r){
        return SVGraph.creaCirculo(x, y, radioVertice, 
        "#F29325", "#F29325") + "\n";
    }

    protected String graficaTexto(VerticeArbolBinario<T> v, int x, int y){
        return SVGraph.creaTexto(v.toString(), "#000000", 
        tamLetra, x, y+1);       
    }
    
    @Override
    public int getAltura(){
        return alturaSVG;
    }
    
}
