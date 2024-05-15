package logica;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Poligono {
    private List<Linea> lineasPoligono;

    public Poligono(List<Linea> lineasPoligono) {
        this.lineasPoligono = lineasPoligono;
    }

    public List<Point> getPuntosPoligono() {
        List<Point> puntosPoligono = new ArrayList<>();
        for (Linea linea : lineasPoligono) {
            puntosPoligono.add(linea.getPunto1());
            puntosPoligono.add(linea.getPunto2());
        }
        return puntosPoligono;
    }
}
