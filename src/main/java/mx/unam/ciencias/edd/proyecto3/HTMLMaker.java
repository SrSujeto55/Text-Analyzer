package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.GraficadorSVG.Graficador;
/**
 * Clase para crear los archivos HTML para cada archivo con la plantilla designada
 */
public class HTMLMaker {
    private Lista<String> rutasIndice;

    /**
     * Método para crear o revisar si existe el directorio, al final del método tenemos asegurado que 
     * existe un directorio en la ruta específicada
     * @param ruta_nombre la ruta donde debe estar el directorio, Si existe un directorio ahí, no hacemos nada
     * Si no existe dicho directorio, lo creamos en la ruta específicada
     */
    public void creaDirectorioHTML(String ruta_nombre){
        File directorio = new File(ruta_nombre);

        if(!directorio.exists()){
            System.out.println("No se econotró el directorio" + ruta_nombre + "Se creo el directorio en la ruta específicada");
            directorio.mkdir();
        }
    }

    public void creaDirectorioDefault(){
        File directorio = new File("/src/main/java/mx/unam/ciencias/edd/proyecto3/HTML");
        directorio.mkdir();
    }

    public void creaHTMLArchivo(String ruta, String nombreArchivo, Graficador graficador){
        String ruta_FinalFinal = ruta + "/" + nombreArchivo + ".html";
        rutasIndice.agregaFinal(ruta_FinalFinal);

        try {
            FileOutputStream fileOut = new FileOutputStream(ruta_FinalFinal);
            OutputStreamWriter osOut = new OutputStreamWriter(fileOut);
            BufferedWriter out = new BufferedWriter(osOut);
            out.write(HTMLStuff.inicializaHTML());
            out.write(HTMLStuff.nombrePestaña(nombreArchivo));
            out.write(HTMLStuff.CCS());
            out.write(HTMLStuff.body(true));
            out.write(HTMLStuff.nombreArchivo(nombreArchivo));
            out.write(HTMLStuff.secPalabras());

            for(Palabra word : graficador.getallWords()){
                out.write(HTMLStuff.palabra(word.getPalabra(), word.getFrecuencia()));
            }

            out.write(graficador.getGraficapastel());
            out.write(HTMLStuff.secPastel());

            out.write(graficador.getGraficaBarras());
            out.write(HTMLStuff.secBarras());

            out.write(graficador.getGraficaRojinegro());
            out.write(HTMLStuff.secRojinegro());

            out.write(graficador.getGraficaAVL());
            out.write(HTMLStuff.secAVL());

            out.write(HTMLStuff.body(false));
            out.write("</html>");

            out.close();
        } catch (IOException ioe) {
            System.err.println("Algo salió mal al intentar escribir el archivo HTML de: " + nombreArchivo);
            System.exit(1);
        }
    }
    public void creaHTMLIndex(String ruta){

        try {
            FileOutputStream fileOut = new FileOutputStream(ruta + "/Index.html");
            OutputStreamWriter osOut = new OutputStreamWriter(fileOut);
            BufferedWriter out = new BufferedWriter(osOut);
            out.write(HTMLStuff.inicializaHTML());
            out.write(HTMLStuff.nombrePestaña("Indices"));
            out.write(HTMLStuff.CCS());
            out.write(HTMLStuff.body(true));
            out.write(HTMLStuff.nombreArchivo("Indece HTML's"));
            out.write("<br>");
            for(String path : rutasIndice){
                String[] parts = path.split("/");
                out.write(HTMLStuff.link(path, parts[parts.length -1]));
            }
            out.write(HTMLStuff.body(false));
            out.write("</html>");
            out.close();
        }catch(IOException ioe){
            System.err.println("Algo salió mal al intentar escribir el archivo HTML de Indces");
            System.exit(1);
        }
    }
}
