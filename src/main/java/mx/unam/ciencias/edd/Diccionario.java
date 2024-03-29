package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
            // Aquí va su código.
            this.llave = llave;
            this.valor = valor;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador() {
            // Aquí va su código.
            indice = -1;
            mueveIterador();
        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            // Aquí va su código.
            return iterador != null;
        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
            // Aquí va su código.
            if(iterador == null)
                throw new NoSuchElementException();

            Entrada siguienteE = iterador.next(); 

            if(!iterador.hasNext())
                mueveIterador();
   
            return siguienteE;
        }

        /* Mueve el iterador a la siguiente entrada válida. */
        private void mueveIterador() {
            // Aquí va su código.
            while(++indice < entradas.length){
                if(entradas[indice] != null){
                    iterador = entradas[indice].iterator();
                    return;
                }
            }
            iterador = null;
        }

    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override public K next() {
            // Aquí va su código.
            return siguiente().llave;
        }
    }

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador
        implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override public V next() {
            // Aquí va su código.
            return siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
        // Aquí va su código.
        this.dispersor = dispersor;
        capacidad = capacidad < MINIMA_CAPACIDAD ? MINIMA_CAPACIDAD : capacidad;
        capacidad = potenciaCercana(capacidad * 2);
        entradas = nuevoArreglo(capacidad);
    }

    private int potenciaCercana(int capacidad) {
        int potencia = (int) Math.ceil(Math.log(capacidad)/Math.log(2));
        return (int) Math.pow(2, potencia);
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) {
        // Aquí va su código.
        if(llave == null || valor == null)
            throw new IllegalArgumentException();

        int indice = dispersor.dispersa(llave) & (entradas.length - 1);

        if(entradas[indice] == null)
            entradas[indice] = new Lista<Entrada>();

        Entrada colision = hayColision(indice, llave);

        if(colision != null)
            colision.valor = valor;

        else{
            entradas[indice].agregaFinal(new Entrada(llave, valor));
            elementos++;
        }

        if(carga() >= MAXIMA_CARGA)
            doblaCapacidad();
    }

    /**
     * Metodo para verificar si hay alguna colision de diccionario en la lista
     * @param indice el indice del arreglo donde buscaremos colision
     * @param llave la llave a buscar
     * @return la entrada con llave repetida o bien null si no se encuentra colision
     */
    private Entrada hayColision(int indice, K llave){
        for(Entrada e : entradas[indice]){
            if(e.llave.equals(llave))
                return e;
        }
        return null;
    }


    /**
     * Metodo para doblar la capacidad del arreglo y ademas copiar cada elemento del antiguo
     * arrelgo al nuevo, aplicandoles la nueva mascara correspodiente (nuevoArreglo.lengh - 1)
     */
    private void doblaCapacidad() {
        Lista<Entrada>[] nuevoArreglo = nuevoArreglo(entradas.length * 2);
        Iterador i = new Iterador();

        while(i.hasNext()){
            Entrada e = i.siguiente();
            int indLlave = dispersor.dispersa(e.llave) & (nuevoArreglo.length - 1);

            if(nuevoArreglo[indLlave] == null)
                nuevoArreglo[indLlave] = new Lista<Entrada>();

            nuevoArreglo[indLlave].agregaFinal(e);
        }

        entradas = nuevoArreglo;
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
        // Aquí va su código.
        if(llave == null)
            throw new IllegalArgumentException();

        int indice = dispersor.dispersa(llave) & (entradas.length - 1);

        if(entradas[indice] == null)
            throw new NoSuchElementException();

        else{
            for(Entrada e : entradas[indice]){
                if(e.llave.equals(llave))
                    return e.valor;
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <code>true</code> si la llave está en el diccionario,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(K llave) {
        // Aquí va su código.
        int indice;

        if(llave == null)
            return false;
        indice = dispersor.dispersa(llave) & (entradas.length - 1);

        if(entradas[indice] == null)
            return false;

        else{
            for(Entrada e : entradas[indice]){
                if(e.llave.equals(llave))
                    return true;
            }
            return false;
        }
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        // Aquí va su código.
        if(llave == null)
            throw new IllegalArgumentException();
        
        int indice = dispersor.dispersa(llave) & (entradas.length - 1);
        Entrada eliminable = buscaEntrada(indice, llave);
        
        if(entradas[indice] == null)
            throw new NoSuchElementException();

        if(eliminable != null){
            entradas[indice].elimina(eliminable);
            elementos--;

            if (entradas[indice].getLongitud() == 0)
                entradas[indice] = null;
        }
        else{
            //Excepcion si no lo encontramos
            throw new NoSuchElementException();
        }
    }

    /**
     * Metodo para buscar la entrada dentro de la lista con el indice dado, en caso
     * de no encontrarla devuelve null
     * @param indice el indice del arreglo donde se encuentra la Lista a buscar
     * @param llave la llave del Elemento a eliminar
     * @return el elemento a eliminar o null si este no se encuentra en la lista
     */
    private Entrada buscaEntrada(int indice, K llave){
        for(Entrada e : entradas[indice]){
            if(e.llave.equals(llave))
                return e;
        }
        return null;
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        // Aquí va su código.
        int longitud = 0;

        for(Lista<Entrada> ls : entradas){
            if(ls != null){
                longitud += ls.getElementos(); 
            }
        }

        return longitud == 0 ? longitud : longitud - 1;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        // Aquí va su código.
        int maxLong = 0;

        for(Lista<Entrada> ls : entradas){
            if(ls != null){
                if(ls.getElementos() > maxLong)
                    maxLong = ls.getElementos();
            }
        }
        return maxLong - 1;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        // Aquí va su código.
        return Double.valueOf(elementos) / entradas.length;
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        // Aquí va su código.
        return elementos==0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        // Aquí va su código.
        entradas = nuevoArreglo(entradas.length);
        elementos = 0;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(esVacia())
            return "{}";
        
        String s = "{ ";
        Iterador i = new Iterador();
        while(i.hasNext()){
            Entrada a = i.siguiente();
            // s += "'" + a.llave.toString() + "': ";
            // s += "'" + a.valor.toString() + "', ";
            s += String.format("'%s': '%s', ", a.llave.toString(), a.valor.toString());
        }
        s += "}";
        return s;
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Diccionario<K, V> d =
            (Diccionario<K, V>)o;
        // Aquí va su código.
        if(elementos != d.elementos)
            return false;

        Iterador i = new Iterador();

        //Ciclo para comparar diccionarios, si sale de este, entonces no le queda de otra mas que
        //ser igual
        while(i.hasNext()){
            Entrada entry = i.siguiente();

            if(!d.contiene(entry.llave) || !d.get(entry.llave).equals(entry.valor))
                return false;
        }

        return true;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
