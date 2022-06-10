package mx.unam.ciencias.edd.proyecto3.GraficadorSVG;
/**
 * Clase abstracta para las gráficas visuales
 */
public abstract class GraficadorGraficas<T> extends GrafEstructura<T> {
    @Override
    protected boolean esVacia() {
        return false;
    }

    @Override
    protected String graficaT() {
        return null;
    }

    @Override
    protected String graficaTCuerpoOnly() {
        return null;
    }
}
