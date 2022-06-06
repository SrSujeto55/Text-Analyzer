package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            if(hasNext()){
                return arbol[indice++];
            }
            throw new NoSuchElementException();
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
            return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
        arbol = nuevoArreglo(161);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        // Aquí va su código.
        elementos = n;
        arbol = nuevoArreglo(n);
        int i = 0;
        for(T e : iterable){
            arbol[i] = e;
            arbol[i].setIndice(i);
            i++;
        }

        for(int k = (elementos-1)/2; k >= 0; k--){
            acomodaAbajo(k);
        }

    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elementos == arbol.length){
            T[] newA = nuevoArreglo(arbol.length*2);
            for(int i = 0; i < arbol.length;i++){
                newA[i] = arbol[i];
            }
            arbol = newA;
        }
        arbol[elementos] = elemento;
        arbol[elementos].setIndice(elementos);

        acomodaArriba(elementos);
        elementos++;
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        // Aquí va su código.
        if(esVacia())
            throw new IllegalStateException();

        T e = arbol[0];
        intercambia(arbol[0], arbol[elementos - 1]);
        elementos--;

        arbol[elementos].setIndice(-1);
        arbol[elementos] = null;
        acomodaAbajo(0);
        return e;
    }

    private void intercambia(T a, T b){
        int A = a.getIndice();
        int B = b.getIndice();

        arbol[A] = b;
        arbol[A].setIndice(A);
        arbol[B] = a;
        arbol[B].setIndice(B);
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            return;
        
        int ind = elemento.getIndice();
        
        if(ind < 0 || ind >= elementos)
            return;

        intercambia(arbol[ind], arbol[elementos - 1]);
        elementos--;
        arbol[elementos].setIndice(-1);
        arbol[elementos] = null;
        
        reordena(arbol[ind]);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        for(T e : arbol){
            if(e.equals(elemento))
                return true;
        }
        return false;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        elementos = 0;
        for(T e : arbol)
            e = null;  
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            return;
        
        int ind = elemento.getIndice();
        acomodaArriba(ind);
        acomodaAbajo(ind);
    }

    /* Acomoda hacia abajo (min-heapify); ve si el nodo actual (i) es mayor que
     * alguno de sus hijos (2*i+1, 2*i+2). Si así es, reemplaza el nodo con el
     * hijo menor, y recursivamente hace lo mismo con el hijo menor (que tiene
     * el valor del que era su padre). */
    private void acomodaAbajo(int i) {
        // Aquí va su código.
        int Menor = menor(((2*i)+1), ((2*i)+2));
        int LSon = (2*i) + 1;
        int DSon = (2*i) + 2;
       
        if(LSon >= elementos && DSon >= elementos)  
            return;

         Menor = menor(Menor, i);

        if(Menor != i){
            T aux = arbol[i];

            arbol[i] = arbol[Menor];
            arbol[i].setIndice(i);

            arbol[Menor] = aux;
            arbol[Menor].setIndice(Menor);
            
            acomodaAbajo(Menor);
        }
    }

    /**
     * Regresa el menor de los hijos
     * @param L el hijo izquierdo
     * @param D el hijo derecho
     * @return el hijo menor
     */
    private int menor(int L, int D){
        if(D >= elementos)
            return L;

        if(arbol[L].compareTo(arbol[D]) < 0)
            return L;
        
        else
            return D;   
    }

    private void acomodaArriba(int i){
        int pa = (i-1)/2;
        int min = i;

        if(pa >= 0 && arbol[pa].compareTo(arbol[i]) > 0)
            min = pa;

        if(min != i){
            T aux = arbol[i];

            arbol[i] = arbol[pa];
            arbol[i].setIndice(i);

            arbol[pa] = aux;
            arbol[pa].setIndice(pa);

            acomodaArriba(min);
        } 
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
        if(i < 0 || i >= elementos)
            throw new NoSuchElementException();

        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
        String s = "";
        for(int i = 0; i < elementos;i++){
            s += arbol[i] + ", ";
        }
        return s;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        // Aquí va su código.
        if(monticulo.elementos != elementos)
            return false;
        for(int i = 0; i<elementos; i++){
            if(!monticulo.get(i).equals(arbol[i]))
                return false;
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.
        Lista<Adaptador<T>> l1 = new Lista<Adaptador<T>>();
        Lista<T> l2 = new Lista<T>();

        for(T e : coleccion){
            Adaptador<T> adap = new Adaptador<T>(e);
            l1.agregaFinal(adap);
        }

        MonticuloMinimo<Adaptador<T>> mont = new MonticuloMinimo<Adaptador<T>>(l1);

        while(!mont.esVacia())
            l2.agregaFinal(mont.elimina().elemento);
        
        return l2;
    }
}
