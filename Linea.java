package logica;

import java.awt.Point;
import java.util.Objects;

public class Linea {
    private Point punto1;
    private Point punto2;

    public Linea(Point punto1, Point punto2) {
        this.punto1 = punto1 != null ? punto1 : new Point(0, 0); // Si punto1 es null, inicializarlo en (0, 0)
        this.punto2 = punto2 != null ? punto2 : new Point(0, 0); // Si punto2 es null, inicializarlo en (0, 0)
    }

    public Point getPunto1() {
        return punto1;
    }

    public Point getPunto2() {
        return punto2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Linea linea = (Linea) o;
        return Objects.equals(punto1, linea.punto1) && Objects.equals(punto2, linea.punto2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(punto1, punto2);
    }

    public boolean comparteVertice(Linea otraLinea) {
        return this.getPunto1().equals(otraLinea.getPunto1()) || this.getPunto1().equals(otraLinea.getPunto2()) ||
                this.getPunto2().equals(otraLinea.getPunto1()) || this.getPunto2().equals(otraLinea.getPunto2());
    }

    public boolean conectaCon(Linea otraLinea) {
        return this.getPunto1().equals(otraLinea.getPunto1()) || this.getPunto1().equals(otraLinea.getPunto2()) ||
                this.getPunto2().equals(otraLinea.getPunto1()) || this.getPunto2().equals(otraLinea.getPunto2());
    }
}
