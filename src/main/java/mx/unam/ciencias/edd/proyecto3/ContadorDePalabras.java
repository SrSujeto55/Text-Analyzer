package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase para buscar y contar el número de apariciones de una palabra en un archivo
 */
public class ContadorDePalabras {
    private Diccionario<String, Palabra> conteo;

    public ContadorDePalabras(Lista<Linea> ls){
        conteo = new Diccionario<>(ls.getElementos());
        for(Linea line : ls){
            String[] parts = line.getLinea().split(" ");

            for(String palabra : parts){
                if(conteo.contiene(palabra))
                    conteo.agrega(palabra, new Palabra(palabra, conteo.get(palabra).getFrecuencia() + 1));
                else
                    conteo.agrega(palabra, new Palabra(palabra, 1));
            }

        }
    }

    public Diccionario<String, Palabra> getConteo(){
        return conteo;
    }
}
