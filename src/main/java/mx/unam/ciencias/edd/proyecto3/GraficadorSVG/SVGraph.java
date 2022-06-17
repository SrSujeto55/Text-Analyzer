package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;

/**
 * Clase abstracta para definir el comportamiento que toda estructura deberia
 * poder realizar al graficarse 
 */
public  class SVGraph {

    /**
     * Constructor vacio y privado para evitar la instancicion de la clase
     */
    private SVGraph(){}

    /**
     * Metodo para declarar el uso de XML
     * @return
     */
    public static String declaracionXML() {
        return "<?xml version='1.0' encoding='UTF-8' ?>";
    }

    /**
     * Metodo para establecer el formato de medidas del SVG
     * @param x el ancho del archivo
     * @param y al largo del archivo
     * @return el String referente a las dimensiones del archivo
     */
    public static String empienzaSVG(int x, int y, String etiqueta) {
        return String.format("<svg " + etiqueta + " width='%d' height='%d'> \n <g>", x, y);
    }

    /**
     * Metodo para terminar el SVG
     * @return La cadena para terminar el SVG
     */
    public static String finalizaSVG(){
        return "</g> \n </svg>";
    }

    /**
     * Metodo estatico para crear circulos con SVG
     * @param cx coordenada x de centro de circulo
     * @param cy coordenada y de centro de circulo
     * @param r radio del circulo
     * @param Ccontorno color del contorno
     * @param Crelleno color del relleno
     * @return la cadena en formato SVG para crear un circulo
     */
    public static String creaCirculo(int cx, int cy, int r, String Ccontorno, String Crelleno){
        return String.format("<circle cx='%d' cy='%d' r='%d' stroke='%s' stroke-width='3' fill='%s' />", 
        cx, cy, r, Ccontorno, Crelleno);
    }

    /**
     * Metodo estatico para crear circulos con SVG (Exclusivo de graficas)
     * @param cx coordenada x del centro del circulo
     * @param cy coordenada y del centro del circulo
     * @param r radio del circulo
     * @param color color del circulo
     * @return la string representativa del SVG del circulo
     */
    public static String creaCirculoG(double cx, double cy, int r, String color ){
        return String.format("<circle cx='%f' cy='%f' r='%d' fill='%s' />", 
        cx, cy, r, color);
    }

    /**
     * Metodo estatico para crear lineas con SVG
     * @param xIni coordenada x inicial de la linea
     * @param yIni coordenada y inciial de la linea
     * @param xFin coordenada x final de la linea
     * @param yFin coordenada y final de la linea
     * @param color color de la linea
     * @returnla cadena en formato SVG para crear una linea
     */
    public static String creaLinea(int xIni, int yIni, int xFin, int yFin, int tamano, String color){
        return String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='%s' stroke-width='%d' />" ,
        xIni, yIni, xFin, yFin, color, tamano) + "\n";
    }

    /**
     * Metodo estatico para crear lineas con SVG (Exclusivo de la graficas)
     * @param xIni coordenada x inicial de la linea
     * @param yIni coordenada y inciial de la linea
     * @param xFin coordenada x final de la linea
     * @param yFin coordenada y final de la linea
     * @param color color de la linea
     * @returnla cadena en formato SVG para crear una linea
     */
    public static String creaLineaG(double xIni, double yIni, double xFin, double yFin, int tamano, String color){
        return String.format("<line x1='%f' y1='%f' x2='%f' y2='%f' stroke='%s' stroke-width='%d' />" ,
        xIni, yIni, xFin, yFin, color, tamano) + "\n";
    }

    /**
     * Metodo para crear flechas(Solo horizontales) con SVG
     * @param xIni coordenada x inicial de la flecha
     * @param yIni coordenada y inciial de la flecha
     * @param xFin coordenada x final con direccion de la flecha
     * @param color color de la flecha
     * @param der True si la flech apunta a la derecha, false si apunta a la izquierda
     * @return El string necesario para crear una flecha
     */
    public static String creaFlecha(int xIni, int yIni, int xFin, String color, boolean der){
        String s = String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='%s' stroke-width='2' />" ,
        xIni, yIni, xFin, yIni, color) + "\n";

        s += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='%s' stroke-width='1.2' />" ,
        xFin, yIni+1, xFin-2, yIni-3, color) + "\n" + 
        String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='%s' stroke-width='1.2' />" ,
        xFin, yIni-1, xFin-2, yIni+3, color) + "\n";

        s += String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='%s' stroke-width='1.2' />" ,
        xIni, yIni+1, xIni+2, yIni-3, color) + "\n" + 
        String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='%s' stroke-width='1.2' />" ,
        xIni, yIni-1, xIni+2, yIni+3, color) + "\n";

        return s;
    }

    /**
     * Metodo estatico para crear un rectanculo en SVG
     * @param XPixelC Coordenada x del pixel superior izquierdo que inicia el rectanculo
     * @param YPixelC Coordenada y del pixel superior izquierdo que inicia el rectanculo
     * @param ancho ancho del rectangulo
     * @param alto alto del rectangulo
     * @param Ccontorno Color del contorno del rectangulo
     * @param Color Color del rectangulo
     * @return cadena en formato SVG para crear un rectangulo
     */
    public static String creaRectacngulo(int XPixelC, int YPixelC, int ancho, int alto, String Ccontorno, String Color){
        return String.format("<rect x='%d' y='%d' width='%d' height='%d' stroke='%s' stroke-width='0.5' fill='%s' />",
        XPixelC, YPixelC, ancho, alto, Ccontorno, Color) + "\n";
    }

    /**
     * Metodo estatico para crear texto en SVG
     * @param txt el texto
     * @param Color color del texto
     * @param size tamano del texto
     * @param CX coordenada X del centro del texto
     * @param CY coordenada Y del centro del texto
     * @return cadena en formato SVG para crear texto
     */
    public static String creaTexto(String txt, String Color, int size, int CX, int CY){
        return String.format("<text fill='%s' font-family='sans-serif' font-size='%d' x='%d' y='%d' \n text-anchor='middle'>%s</text>", 
        Color, size, CX, CY, txt) + "\n";
    }

    /**
     * Metodo estatico para crear texto en SVG *(Exclusivo de Graficas)
     * @param txt el texto
     * @param Color color del texto
     * @param size tamano del texto
     * @param CX coordenada X del centro del texto
     * @param CY coordenada Y del centro del texto
     * @return cadena en formato SVG para crear texto
     */
    public static String creaTextoG(String txt, String Color, int size, double CX, double CY){
        return String.format("<text fill='%s' font-family='sans-serif' font-size='%d' x='%f' y='%f' \n text-anchor='middle'>%s</text>", 
        Color, size, CX, CY, txt) + "\n";
    }

}
