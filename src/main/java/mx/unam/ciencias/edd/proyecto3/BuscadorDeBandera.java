package mx.unam.ciencias.edd.proyecto3;
/**
 * Clase para buscar la banderoa -o en el programa
 */
public class BuscadorDeBandera{

    private boolean contieneBandera;

    /**
     * Cosntructor privado sin parámetros para evitar la instanciación de la clase sin parámetros
     */
    private BuscadorDeBandera(){}

    /**
     * Constructor de la clase, cuando se instancia con un arreglo, busca en cada entrada la bandera
     * "-o", si no la encuentra inicializa la variable global como false
     * @param args El arreglo donde se va buscar la bandera
     */
    public BuscadorDeBandera(String[] args){
        for(String str : args){
            if(str.equals("-o")){
                 contieneBandera = true;
                 return;
            }
        }
        contieneBandera = false;
    }

    /**
     * Nos regresa la variable global contieneBandera
     * @return True si la bandera esta contenida, false en otro caso
     */
    public boolean contieneBandera(){
        return contieneBandera;
    }


}
