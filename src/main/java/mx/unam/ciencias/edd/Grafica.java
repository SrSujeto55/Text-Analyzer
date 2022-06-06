package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ResourceBundle.Control;

import mx.unam.ciencias.edd.ArbolBinario.Vertice;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            // Aquí va su código.
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        private T elemento;
        /* El color del vértice. */
        private Color color;
        /* La distancia del vértice. */
        private double distancia;
        /* El índice del vértice. */
        private int indice;
        /* El diccionario de vecinos del vértice. */
        private Diccionario<T, Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            vecinos = new Diccionario<>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
            if(distancia > vertice.distancia)
                return 1;
            else if(distancia < vertice.distancia)
                return -1;
            return 0;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            // Aquí va su código.
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            // Aquí va su código.
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
            return vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecino.vecinos;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
        vertices = new Diccionario<>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();
        
        if(contiene(elemento))
            throw new IllegalArgumentException();

        Vertice nuevo = new Vertice(elemento);

        vertices.agrega(elemento, nuevo);
    }

    //Metodo auxilir para ver si un elemento a esta contenido en la grafica
    private Vertice contieneAux(Diccionario<T, Vertice> ls, T a){
        for(Vertice n : ls){
            if(n.elemento.equals(a))
                return n;
        }  
        return null;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
        Vertice A = (Vertice)vertice(a);
        Vertice B = (Vertice)vertice(b); 
        if( a.equals(b)|| sonVecinos(a, b))
            throw new IllegalArgumentException();

        if(A == null || B == null)
            throw new NoSuchElementException();

        A.vecinos.agrega(b, new Vecino(B, 1));   
        B.vecinos.agrega(a, new Vecino(A, 1));

        aristas++;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException();

        if( a.equals(b) || sonVecinos(a, b) || peso < 0)
            throw new IllegalArgumentException();

        Vertice A = (Vertice)vertice(a);
        Vertice B = (Vertice)vertice(b); 
        

        A.vecinos.agrega(b, new Vecino(B, peso));   
        B.vecinos.agrega(a, new Vecino(A, peso));

        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException();

        if (a.equals(b) || !sonVecinos(a, b))
            throw new IllegalArgumentException();

        Vertice A = (Vertice) vertice(a);
        Vertice B = (Vertice) vertice(b);

        A.vecinos.elimina(b);
        B.vecinos.elimina(a);
        aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        for(Vertice n : vertices){
            if(n.elemento.equals(elemento))
                return true;
        }
        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        if (!contiene(elemento))
            throw new NoSuchElementException();

        Vertice e = (Vertice) vertice(elemento);
        for (Vertice v : vertices){
            for (Vecino ve : v.vecinos){
                if (ve.vecino.equals(e)) {
                    v.vecinos.elimina(ve.get());
                    aristas--;
                }
            }   
        }   
        vertices.elimina(elemento);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException();
        
            Vertice A = (Vertice)vertice(a);
            Vertice B = (Vertice)vertice(b);

            for(Vecino v : A.vecinos){
                if(v.vecino.equals(B))
                    return true;
            }
        return false;
    }   

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException();
        
        if(!sonVecinos(a, b))
            throw new IllegalArgumentException();

        Vertice A = (Vertice) vertice(a);
        Vertice B = (Vertice) vertice(b);

        for (Vecino ve : A.vecinos){
            if (ve.vecino.equals(B))
                return ve.peso;
        }
        return -1;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        // Aquí va su código.
        if(!sonVecinos(a, b) || peso <= 0)
            throw new IllegalArgumentException();
        
        if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException();
        
        Vertice A = (Vertice)vertice(a);
        Vertice B = (Vertice)vertice(b);

        for(Vecino v : A.vecinos){
            if(v.vecino.equals(B)){
                v.peso = peso;
                break;
            }    
        }

        for(Vecino v : B.vecinos){
            if(v.vecino.equals(A)){
                v.peso = peso;
                break;
            }    
        }

    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
        Vertice E = contieneAux(vertices, elemento);

        if(E == null)
            throw new NoSuchElementException();
        
        VerticeGrafica<T> Vertice = E;

        return Vertice;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.
        if(vertice == null || 
        (vertice.getClass() != Vertice.class && 
        vertice.getClass() != Vecino.class))
            throw new IllegalArgumentException();
        
        if(vertice.getClass() == Vertice.class){
            Vertice v = (Vertice)vertice;
            v.color = color;
        }

        if(vertice.getClass() == Vecino.class){ 
            Vecino v = (Vecino)vertice;
            v.vecino.color = color;
        }
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
        Cola<Vertice> q = new Cola<Vertice>();
        Iterador i = new Iterador();
        Conjunto<Vertice> rojo = new Conjunto<>();
        paraCadaVertice((n) -> rojo.agrega((Vertice) n));

        if(esVacia())
            return false;

        Vertice primero = (Vertice) vertice(i.next());

        q.mete(primero);
        rojo.elimina(primero);

        while(!q.esVacia()){
            Vertice v = q.saca();
            for(Vecino n : v.vecinos){
                if(rojo.contiene(n.vecino)){//n.vecino.color == Color.ROJO){
                   rojo.elimina(n.vecino);
                   q.mete(n.vecino); 
                }
            }
        }

        for(Vertice n : vertices){
            if(rojo.contiene(n))
                return false;
        }
        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        for(Vertice n : vertices){
            accion.actua(n);
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        Vertice A = (Vertice)vertice(elemento);
        Conjunto<Vertice> rojo = new Conjunto<>();
        
        if(A == null)
            throw new NoSuchElementException();
            
        paraCadaVertice((n) -> rojo.agrega((Vertice) n)); //Coloreamos todos los vertices de Rojo
        
        Cola<Vertice> q = new Cola<Vertice>();
        q.mete(A);
        rojo.elimina(A);

        while(!q.esVacia()){
            Vertice v = q.saca();
            accion.actua(v);

            for(Vecino n : v.vecinos){
                if(rojo.contiene(n.vecino)){
                    q.mete(n.vecino);
                    rojo.elimina(n.vecino);
                }
            }
        }

        paraCadaVertice((n) -> setColor(n, Color.NINGUNO));
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        Vertice B = (Vertice)vertice(elemento);
        Conjunto<Vertice> rojo = new Conjunto<>();
        
        if(B == null)
            throw new NoSuchElementException();
            
        paraCadaVertice((n) -> rojo.agrega((Vertice) n)); //Coloreamos todos los vertices de Rojo
        
        Pila<Vertice> p = new Pila<Vertice>();
        p.mete(B);
        rojo.elimina(B);

        while(!p.esVacia()){
            Vertice v = p.saca();
            accion.actua(v);

            for(Vecino n : v.vecinos){
                if(rojo.contiene(n.vecino)){
                    p.mete(n.vecino);
                    rojo.elimina(n.vecino);
                }
            }
        }

        paraCadaVertice((n) -> setColor(n, Color.NINGUNO));
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        // Aquí va su código.
        String s = "{";

        for(Vertice n : vertices)
            s += String.format("%s, ", n.elemento);
        
        s += "}, {";

        paraCadaVertice((n) -> setColor(n, Color.ROJO));
        for(Vertice v : vertices){
            for(Vecino n : v.vecinos){
                if(n.vecino.color == Color.ROJO)
                s += String.format("(%s, %s), ", v.elemento, n.vecino.elemento);
            }
            setColor(v, Color.NEGRO);
        }
        s += "}";
        return s;
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        // Aquí va su código.
        if(getElementos() != grafica.getElementos())
            return false;
        if(getAristas() != grafica.getAristas())
            return false;

        for(Vertice n : vertices){
            if(!grafica.contiene(n.elemento))
                return false;
        }

        for(Vertice n : vertices){
            for(Vecino v : n.vecinos){
                if(!grafica.sonVecinos(n.elemento, v.vecino.elemento))
                    return false;
            }
        }

        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
        if(!contiene(origen) || !contiene(destino))
            throw new NoSuchElementException();

        Vertice o = (Vertice) vertice(origen);
        Vertice d = (Vertice) vertice(destino);

        //Poner distancia infinita a todos los vertices
        paraCadaVertice((v) -> {
            Vertice h = (Vertice)v;
                h.distancia = Double.POSITIVE_INFINITY;
            }   
        );

        o.distancia = 0;
        monticuloMinimoTrayecto();
        return recreaTrayectoria(o, d);
    }

    private void monticuloMinimoTrayecto(){
        MonticuloMinimo<Vertice> mon = new MonticuloMinimo<>(vertices, vertices.getElementos());
        while(!mon.esVacia()){
            Vertice min = mon.elimina();

            for(Vecino v : min.vecinos){
                if(v.vecino.distancia == Double.POSITIVE_INFINITY || 
                    min.distancia + v.peso < v.vecino.distancia){
                        v.vecino.distancia = min.distancia+1;
                        mon.reordena(v.vecino);
                }
            }
        }
    }

    private Lista<VerticeGrafica<T>> recreaTrayectoria(Vertice origen, Vertice destino){
        Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
        
        if(destino.distancia != Double.POSITIVE_INFINITY){  
            Vertice itera = destino;
            while(itera != origen){
                for(Vecino v : itera.vecinos){
                    if(v.vecino.distancia == itera.distancia - 1){
                        trayectoria.agregaInicio(itera);
                        itera = v.vecino;
                        break;
                    }
                }
            }
            if(itera == origen)
                    trayectoria.agregaInicio(origen); 
        }   
        return trayectoria;
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
        if(!contiene(origen) || !contiene(destino))
            throw new NoSuchElementException();
        
            Vertice o = (Vertice) vertice(origen);
            Vertice d = (Vertice) vertice(destino);
        
        //Poner distancia infinita a todos los vertices
        paraCadaVertice((v) -> {
            Vertice h = (Vertice)v;
                h.distancia = Double.POSITIVE_INFINITY;
            }   
        );

        o.distancia = 0;
        monticuloMinimoTDijkstra();
        return recreaTrayectoriaDijkstra(o, d);

    }

    private void monticuloMinimoTDijkstra(){
        MonticuloMinimo<Vertice> mon = new MonticuloMinimo<>(vertices, vertices.getElementos());
        while(!mon.esVacia()){
            Vertice min = mon.elimina();

            for(Vecino v : min.vecinos){
                if(v.vecino.distancia == Double.POSITIVE_INFINITY || 
                    min.distancia + v.peso < v.vecino.distancia){
                        v.vecino.distancia = min.distancia+v.peso;
                        mon.reordena(v.vecino);
                }
            }
        }
    }

    private Lista<VerticeGrafica<T>> recreaTrayectoriaDijkstra(Vertice origen, Vertice destino){
        Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
        
        if(destino.distancia != Double.POSITIVE_INFINITY){  
            Vertice itera = destino;
            while(itera != origen){
                for(Vecino v : itera.vecinos){
                    if(v.vecino.distancia == itera.distancia - v.peso){
                        trayectoria.agregaInicio(itera);
                        itera = v.vecino;
                        break;
                    }
                }
            }
            if(itera == origen)
                    trayectoria.agregaInicio(origen); 
        }   
        return trayectoria;
    }


}