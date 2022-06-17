package mx.unam.ciencias.edd.proyecto3;
/**
 * Clase designada para realizar ciertas acciones de un HTML tales como escribir <html> entre otros
 * ATENCIÓN: es probable que leer esto provoque los siguientes síntomas:
 * Malestar
 * Mareo
 * Asco
 * Y una fuerte depresión
 * Pero es seguro que eso ya sucedió varias clases atrás
 */
public class HTMLStuff {

    /**
     * Inicializa el HTML como html y el <html>
     * @return la string con estos datos
     */
    public static String inicializaHTML(){
        return "<!DOCTYPE html>" + "\n" +
        "<html>" + "\n";
    }

    /**
     * Asigna un nombre a la pestaña del HTML
     * @param nombre el nombre que se le asignará
     * @return el String con estos datos
     */
    public static String nombrePestaña(String nombre){
        return "<head>" + "\n" +
        "<meta charset='utf-8'>" + "\n" +
        "<title>" + nombre + "</title>" + "\n" +
      "</head>" + "\n";
    }

    /**
     * Aquí se declaran todos los datos referentes al CCS como el color de fondo y algunas clases de color
     * @return el String con estos datos
     */
    public static String CCS(){
       return "<style type='text/css'>" + "\n" +
    "body {" + "\n" +
      "font-family: Georgia, 'Times New Roman'," + "\n" +
          "Times, serif;" + "\n" +
      "color: black;" + "\n" +
      "background-color: #1FAD8A" + "\n" +
    "}" + "\n" +
     "\n" +
    "div{" + "\n" +
      "background-color: #e64a2fe5;" + "\n" +
    "}" + "\n" +
    "\n" +
    "h1 {" + "\n" +
    "font-family: Arial, Helvetica, sans-serif }" + "\n" +
      "#pastel {" + "\n" +
        "position: relative;" + "\n" +
        "left: 50%;" + "\n" +
        "-webkit-transform: translateX(-20%);" + "\n" +
        "-ms-transform: translateX(-20%);" + "\n" +
        "transform: translateX(-50%);" + "\n" +
      "}" + "\n" + 
      "\n" +
      "#ABrojinegro {" + "\n" +
        "position: relative;" + "\n" +
        "left: 50%;" + "\n" +
        "-webkit-transform: translateX(-20%);" + "\n" +
        "-ms-transform: translateX(-20%);" + "\n" +
        "transform: translateX(-50%);" + "\n" +
      "}" + "\n" + 
      "\n" +
      "#ABAVL {" + "\n" +
        "position: relative;" + "\n" +
        "left: 50%;" + "\n" +
        "-webkit-transform: translateX(-20%);" + "\n" +
        "-ms-transform: translateX(-20%);" + "\n" +
        "transform: translateX(-50%);" + "\n" +
      "}" + "\n" + 
      "\n" +
      "#barras {" + "\n" +
        "position: relative;" + "\n" +
        "left: 50%;" + "\n" +
        "-webkit-transform: translateX(-20%);" + "\n" +
        "-ms-transform: translateX(-20%);" + "\n" +
        "transform: translateX(-50%);" + "\n" +
      "}" + "\n" + 
      "\n" +
  ".centrado{text-align:center;border:1px dotted #000; padding:32px;}" + "\n" +
  ".centradoPequeño{text-align:center;border:1px dotted #000; padding:9px;}" + "\n" +
  "</style>" + "\n";
    }

    /**
     * Le asigna un nombre para desplegar en el HTML
     * @param nombre el nombre del archivo analizado
     * @return el String de estos datos
     */
    public static String nombreArchivo(String nombre){
        return "<div>" + "\n" +
        "<h1 class='centrado'> Análisis de archivo: " + nombre + "\n" +
      "</div>" + "\n";
    }

    /**
     * Sección de palabras en HTML
     * @return Sección de palabras en HTML
     */
    public static String secPalabras(){
        return "<div>" + "\n" +
        "<h2 class='centradoPequeño'> Conteo de palabras</h2>" + "\n" +
      "</div>" + "\n";
    }
     /**
     * Sección de pastel en HTML
     * @return Sección de pastel en HTML
     */
    public static String secPastel(){
        return "<div>" + "\n" +
        "<h2 class='centradoPequeño'>Gráfica pastel</h2>" + "\n" +
      "</div>" + "\n";
    }

     /**
     * Sección de Barras en HTML
     * @return Sección de barras en HTML
     */
    public static String secBarras(){
        return "<div>" + "\n" +
        "<h2 class='centradoPequeño'>Gráfica barras</h2>" + "\n" +
      "</div>" + "\n";
    }

     /**
     * Sección de Rojinegro en HTML
     * @return Sección de Rojinegro en HTML
     */
    public static String secRojinegro(){
        return "<div>" + "\n" +
        "<h2 class='centradoPequeño'>Rojinegro Top 15</h2>" + "\n" +
      "</div>" + "\n";
    }

     /**
     * Sección de AVL en HTML
     * @return Sección de AVL en HTML
     */
    public static String secAVL(){
        return "<div>" + "\n" +
        "<h2 class='centradoPequeño'>AVL Top 15</h2>" + "\n" +
      "</div>" + "\n";
    }

    /**
     * La cantidad de palabras que fueron revisadas en el archivo
     * @param palabra La palabra a evaluar
     * @param apariciones el número de veces que aparece en el archivo
     * @return El string con estos datos
     */
    public static String palabra(String palabra, int apariciones){
        return "<h2 class='centradoPequeño'>Palabra:" + palabra + ":" + apariciones +  "apariciones</h2>" +
            "\n";
    }

    /**
     * Inicia o finaliza el cuerpo del HTML
     * @param start Booleano que nos indica si se empieza el body o falso si se termina
     * @return el String con estos datos
     */
    public static String body(boolean start){
        if(start)
            return "<body>" + "\n";
        else
            return "</body>" + "\n";
    }

    public static String link(String link, String nombre){
      return "<a class='centrado' href=" + link + ">" + nombre + "</a>";
    }

    /**
     * Super Easter egg 
     * por si me despiden, la última técnica
     * Utiliza este método si te despiden y agregalo a todos los HTML's para ser despedido de una forma LEGENDARIA
     * @return un RickRoll, mucho cuidado
     */
    public static String RickRoll(){
        return "<a href='https://www.youtube.com/watch?v=dQw4w9WgXcQ'>Definitivamente NO un rickRoll</a>";
    }
}
