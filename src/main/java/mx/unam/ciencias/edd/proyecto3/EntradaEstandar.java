package mx.unam.ciencias.edd.proyecto3;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
/**
 * Clase para la entrada estandar del programa
 */
public class EntradaEstandar {
    /**La lista para agregar cada linea del archivo */
    private Lista<Linea> ls;


    /**
     * método para conseguir el conteo de palabras de un archivo
     * @param nombreArchivo El nombre del archivo que leermos con @lecuraArch
     * @return El conjunto que contiene el conteo de todas las palabras en el archivo recibido
     */
    public Diccionario<String, Palabra> getConteo(String nombreArchivo){
        ls = lecturaArch(nombreArchivo);
        ContadorDePalabras cont = new ContadorDePalabras(ls);
        return cont.getConteo();
    }

    /**
     * Método para leer un archivo
     * @param nombreArchivo el nombre del archivo a leer
     * @return la lista con lineas normalizadas como entrada de cada elemento
     */
    private Lista<Linea> lecturaArch(String nombreArchivo){
        Lista<Linea> list = new Lista<>();
        File doc = new File(nombreArchivo);
        BufferedReader in;
    
            if(doc.exists()){
                try{
                    in = new BufferedReader(new FileReader(doc));
                    try{
                        list = carga(in);
                    in.close();
                    }catch(IOException e){
                        throw new FileNotFoundException("Ocurrio un error al intentar leer el archivo");
                    }
                }catch(FileNotFoundException F){
                    System.err.printf("No se puede leer: \"%s\": no existe tal ruta o directorio.\n",
                    nombreArchivo);
                    System.exit(1);
                }
                return list;
            }
    
            try {
                FileInputStream fileIn = new FileInputStream(nombreArchivo);
                InputStreamReader isIn = new InputStreamReader(fileIn);
                in = new BufferedReader(isIn);
    
                list = carga(in);
                in.close();
            } catch (IOException IEe) {
                System.err.printf("No se puede leer: \"%s\": no existe tal ruta o directorio.\n",
                                  nombreArchivo);
                System.exit(1);
            }
              return list;
        }

        /**
         * Método para leer el archivo recibido con un BufferReader y agregar todo a una lista, cada entrada es un elemento de la lista
         * @param in BufferReader para leer el archivo
         * @return la lista con todas las lineas del archivo como elemento
         * @throws IOException
         */
        private static Lista<Linea> carga(BufferedReader in) 
        throws IOException{
            Lista<Linea> ls = new Lista<>();
            String line;
            while( (line = in.readLine()) != null){
                ls.agregaFinal(new Linea(line));
            }
            return ls;
        } 
}
