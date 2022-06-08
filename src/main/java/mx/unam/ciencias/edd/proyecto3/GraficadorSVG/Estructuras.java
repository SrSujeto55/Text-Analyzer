package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;
/**
 * Clase enumeracion para cosiderar todas las estructuras graficables
 */

public enum Estructuras {
    /**Arbol Binario Rojinegro */
    AB_ROJINEGRO,
    /**Arbol BInario AVL */
    AB_AVL,
    /**Valor invalido para una estructura no graficable, no seguir si se 
     * recibe este valor
     */
    INVALIDO;

    /**
     * Metodo que recibe una representacion en cadena de la estructura y la convierte a la
     * enumeracion correspondiente, regresa INVALIDO si la representacion en cadena no corresponde 
     * a alguna de las Estructuras de la enumeracion
     * @param estructura La representacion en cadena de la estructura
     * @return La enumeracion correspondiente a la Estructura
     */
    public static Estructuras getEnum(String estructura){
        switch(estructura){
            case "ArbolRojinegro": return AB_ROJINEGRO;
            case "ArbolAVL": return AB_AVL;
            default: return INVALIDO;
        }
    }


}
