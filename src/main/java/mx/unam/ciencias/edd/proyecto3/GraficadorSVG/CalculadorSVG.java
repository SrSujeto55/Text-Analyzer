package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

import java.util.NoSuchElementException;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.IteradorLista;
import mx.unam.ciencias.edd.proyecto3.Palabra;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase para realizar los cálculos necesarios para conocer los datos escenciales de las palabras más usadas en un archivo
 */
public class CalculadorSVG{
    /**Variable de clase para repreentar el número de apariciones que forman todas las palabras que no estan en el TOP N palabras frecuentes */
    private int ElemRestantes;
    private int total;
    /**Lista con las palabras mapeadas del Diccionario reibido por el constructor */
    private Lista<Palabra> frecuentes;
    

    /**
     * Constructor para la clase, lo único que hace es rellenar una lista con los elementos del diccionario
     * @param contador El diccionario que ccontiene las palabras con su índice de aparicción
     */
    public CalculadorSVG(Diccionario<String, Palabra> contador){
        for(Palabra word : contador)
            frecuentes.agregaFinal(word);
    }

    /**
     * Método para conseguir la lista con las palabras más frecuentes dentro de un rango x
     * @param rango El rango en el cual se tomaran las x palabras más frecuentes
     * @return La lista con las palabras más frecuentes dentro del rango
     * @throws NoSuchElementException si es que la lista tiene menos palabras que el rango recibido
     */
    public Lista<Palabra> getPalabrasFrecuentes(int rango) throws NoSuchElementException{
        if(frecuentes.getElementos() < rango)
            throw new NoSuchElementException();
        
        Lista<Palabra> topN = frecuentes.mergeSort((a, b) -> a.compareTo(b));

        IteradorLista<Palabra> it = topN.iteradorLista();
        int i = 0;
        while(i++ < rango)
            topN.agregaFinal(it.next());

        while(i++ < frecuentes.getElementos()){
            ElemRestantes += (it.next()).getFrecuencia();
        }
        
        return topN;
    }

    /**
     * Regresa la cantidad TOTAL de palabras leídas para sacar porcentajes
     * @return El número de palabras en la lista de frecuentes
     */
    public int getTotalPalabras(){
        for(Palabra word : frecuentes)
            total += word.getFrecuencia();
        return total;
    }
    
    /**
     * Regresa la cantidad de palabras que forman en total todas las palabras que no están en el top N
     * @return
     */
    public int getElementosRestantes(){
        return ElemRestantes;
    }
}
