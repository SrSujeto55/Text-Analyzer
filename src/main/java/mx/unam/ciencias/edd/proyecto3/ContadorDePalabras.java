package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase para buscar y contar el número de apariciones de una palabra en un archivo
 */
public class ContadorDePalabras {
    private Conjunto<String> conteo;

    public ContadorDePalabras(Lista<Linea> ls){
        conteo = new Conjunto<>(ls.getElementos());
        for(Linea line : ls){
            String[] parts = line.getLinea().split(" ");
            for(String word : parts){
                conteo.agrega(word);
            }
        }
    }

    public Conjunto<String> getConteo(){
        return conteo;
    }
}
