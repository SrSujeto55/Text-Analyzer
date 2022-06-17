package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
/**
 * Clase para buscar la banderoa -o en el programa
 */
public class BuscadorDeBandera{
    private boolean skipOne;
    /**Booleando que nos dice si tiene la bandera -o */
    private boolean contieneBandera;
    /**Indice que nos indica donde se encuentra la ruta del directorio*/
    private int index;

    private Lista<String> nomArchivos;

    public BuscadorDeBandera(){
        nomArchivos = new Lista<>();
        skipOne = false;
        contieneBandera = false;
    }

    /**
     * Constructor de la clase, cuando se instancia con un arreglo, busca en cada entrada la bandera
     * "-o", si no la encuentra inicializa la variable global como false
     * @param args El arreglo donde se va buscar la bandera
     */
    public Lista<String> buscaYLlena(String[] args){
        int i = 0;
        for(String str : args){
            if(str.equals("-o")){
                 contieneBandera = true;
                 index = ++i;
                 skipOne = true;
                 continue;
            }

            if(skipOne){
                i++;
                skipOne = false;
                continue;
            }
            String[] parts = str.split(".");
            nomArchivos.agregaFinal(parts[0]);
            i++;
        }
        return nomArchivos;
    }

    /**
     * Método que nos regresa el índice que contiene la ruta de la carpeta a guardar los HTML
     * En caso de no tener la bandera -o, regresaremos null, en cuyo caso representa que no hay directorio especificado y trabajaremos
     * con uno default dentro del programa.
     * @return
     */
    public int getindiceRuta(){
        return contieneBandera ? index : -1;
    }


}
