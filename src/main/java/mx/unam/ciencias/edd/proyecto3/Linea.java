package mx.unam.ciencias.edd.proyecto3;

import java.text.Normalizer;

public class Linea{
    /**String de clase, la misma que será modificada para eliminar caracteres especiales */
    private String ln;

    /**
     * Constructor de clase que normaliza la linea recibida
     * @param ln la linea a normalizar
     */
    public Linea(String ln){
        this.ln = ln;
        ln = Normalizer.normalize(ln,Normalizer.Form.NFKD);
        ln = ln.replaceAll("[^\\p{IsAlphabethic}\\s]", "");
        ln = ln.toLowerCase();
        ln = ln.trim();
    }

    /**
     * metodo para acceder al atributo ln
     * @return La linea normalizada
     */
    public String getLinea(){
        return ln;
    }
    
}
