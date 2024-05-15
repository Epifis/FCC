package logica;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Logica {
    private int[][] grilla;
    private List<Linea> lineas;
    private List<Linea> lineasPoligono;
    private List<Poligono> poligonos;
    private Poligono poligono;

    public Logica(int filas, int columnas) {
        this.grilla = new int[filas][columnas];
        this.lineas = new ArrayList<>();
        this.poligonos = new ArrayList<>();
    }
    public Poligono getPoligono() {
        return poligono;
    }

    public int[][] getGrilla() {
        return grilla;
    }

    public void actualizarCelda(int fila, int columna, int valor) {
        grilla[fila][columna] = valor;
    }

    public void dibujarLinea(Point punto1, Point punto2) {
        Linea nuevaLinea = new Linea(punto1, punto2);
        if (!lineas.contains(nuevaLinea)) {
            lineas.add(nuevaLinea);
        }
    }

    public void limpiarLineas() {
        lineas.clear();
    }

    public List<Linea> getLineas() {
        return lineas;
    }
    public void encontrarPoligonos(List<Linea> lineas) {
        //hay que diferenciar y guardar cada poligono en una matriz para implementar la logica del coloreado
        poligonos.clear(); // Limpiar lista de polígonos anteriormente identificados
        System.out.println("Buscando polígonos...");
        // Iterar sobre todas las líneas para encontrar polígonos cerrados
        for (Linea linea : lineas) {
            List<Linea> poligonoActual = new ArrayList<>();
            poligonoActual.add(linea); // Iniciar con la línea actual

            for (Linea otraLinea : lineas) {
                if (!poligonoActual.contains(otraLinea) && linea.comparteVertice(otraLinea)) {
                    poligonoActual.add(otraLinea);
                    Linea ultimaLinea = poligonoActual.get(poligonoActual.size() - 1);
                    if (ultimaLinea.getPunto2().equals(linea.getPunto1())) {
                        // Se ha cerrado el polígono
                        poligonos.add(new Poligono(new ArrayList<>(poligonoActual)));
                        break;
                    }
                }
            }
        }

        // Crear un nuevo polígono y asignarlo
        if (!poligonos.isEmpty()) {
            poligono = poligonos.get(0); // Suponiendo que deseas utilizar el primer polígono encontrado
        }
    }



    public List<Poligono> getPoligonos() {
        return poligonos;
    }

    public List<Linea> getLineasPoligono() {
        return lineasPoligono;
    }
}
