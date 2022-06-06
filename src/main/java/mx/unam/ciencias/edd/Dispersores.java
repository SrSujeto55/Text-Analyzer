package mx.unam.ciencias.edd;

import static org.junit.Assert.assertArrayEquals;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        // Aquí va su código.
        int r = 0;
        int resi = 4 - llave.length%4;
        
        for(int i = 0; i < llave.length + resi; i+=4){
            r ^= combinaB(toInt(llave, i), toInt(llave, i+1), 
            toInt(llave, i+2), toInt(llave, i+3));
        }
        return r;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        // Aquí va su código.
        boolean ejecucion = true;
        int i = 0;
        int a = 0x9E3779B9;
        int b = 0x9E3779B9;
        int c = 0xFFFFFFFF;

        while (ejecucion) {
            a += combinaB(toInt(llave, i+3), toInt(llave, i+2),
            toInt(llave, i+1), toInt(llave, i));
            i += 4;

            b += combinaB(toInt(llave, i+3), toInt(llave, i+2),
            toInt(llave, i+1), toInt(llave, i));
            i += 4;

            if (llave.length - i >= 4){
                c += combinaB(toInt(llave, i+3), toInt(llave, i+2),
                toInt(llave, i+1), toInt(llave, i));
                i += 4;
            }
                
            else {
                ejecucion = false;
                c += llave.length;
                c += combinaB(toInt(llave, i+2), toInt(llave, i+1),
                toInt(llave, i), 0);
            }

                //fase 1
                a += -b - c;
                a ^= (c >>> 13);
                b += -c - a;
                b ^= (a << 8);
                c += -a - b;
                c ^= (b >>> 13);

                //fase 2
                a += -b - c;
                a ^= (c >>> 12);
                b += -c - a;
                b ^= (a << 16);
                c += -a - b;
                c ^= (b >>> 5);

                //fase 3
                a += -b - c;
                a ^= (c >>> 3);
                b += -c - a;
                b ^= (a << 10);
                c += -a - b;
                c ^= (b >>> 15);
        }

        return c;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        // Aquí va su código.
        int h = 5381;

        for (int i = 0; i < llave.length; i++)
            h += (h << 5) + (llave[i] & 0xFF);

        return h;
    }

    /**
     * Metodo auxiliar para combinar 4 bytes en un entero de 32 bits en formato Big endian
     * @param a
     * @param b
     * @param c
     * @param d
     * @return entero de 32 bits
     */
    private static int combinaB(int a, int b, int c, int d){
        return (a << 24) | (b << 16) |
                (c << 8) | d;
    }

    private static int toInt(byte[] llave, int p){
        if(p < llave.length)
            return (llave[p] & 0xFF);
        else
            return 0;
    }


}
