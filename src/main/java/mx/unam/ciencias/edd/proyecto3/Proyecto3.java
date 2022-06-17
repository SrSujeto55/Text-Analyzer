package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.GraficadorSVG.Graficador;
/**
 * Clase princiapal del proyecto
 */
public class Proyecto3 {

    public static String uso(){
        return "Uso: <Archivo(s)> / bandera: [-o <Nombre Directorio> | Sin bandera: ruta default]" + "\n" + 
        "Cuidado: Si después de poner la bandera -o no se especifica bien un directorio sino mas bien un archivo, este se sobreescribirá";
    }

    public static void main(String[] args) {
        if(args.length < 1){
            uso();
            System.exit(1);
        }
        
        String rutaFinal;
        String[] nombresArchivos;
        EntradaEstandar in = new EntradaEstandar();
        BuscadorDeBandera src = new BuscadorDeBandera();
        HTMLMaker htMK = new HTMLMaker();
        Cola<Diccionario<String, Palabra>> q_arch = new Cola<>();
        Lista<String> entradas = new Lista<>(); 
        
        entradas = src.buscaYLlena(args);
        nombresArchivos = new String[entradas.getElementos()];
            int j = 0;
            for(String entrada : entradas){
                q_arch.mete(in.getConteo(entrada));
                nombresArchivos[j++] = entrada;
            }
                
        int indiceDir = src.getindiceRuta();
        if(indiceDir == args.length){
             uso();
             System.exit(1);
        }
           

        if(indiceDir != -1){
            htMK.creaDirectorioHTML(args[indiceDir]);
            rutaFinal = args[indiceDir];
        }

        else{
            htMK.creaDirectorioDefault();
            rutaFinal = "/src/main/java/mx/unam/ciencias/edd/proyecto3/HTML";
        }
            
        int h = 0;
        while(!q_arch.esVacia()){
            Graficador gphMK = new Graficador(q_arch.saca());
            htMK.creaHTMLArchivo(rutaFinal, nombresArchivos[h++], gphMK);
        }
        htMK.creaHTMLIndex(rutaFinal);
    }
}
