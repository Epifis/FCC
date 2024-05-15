// Controlador.java
package controlador;

import interfaz.InterfazApp;
import logica.Logica;
import java.awt.Point;

public class Controlador {
    private Logica logica;
    private InterfazApp interfaz;

    public Controlador(int filas, int columnas) {
        this.logica = new Logica(filas, columnas);
        interfaz = new InterfazApp(this);
    }

    public void procesarAccionBoton1() {
        // Lógica para el botón 1
        System.out.println("Acción del Botón 1");
    }

    public void procesarAccionBoton2() {
        // Lógica para el botón 2
        System.out.println("Acción del Botón 2");
    }

    public void procesarCelda(Point punto1, Point punto2) {
    logica.dibujarLinea(punto1, punto2);
    interfaz.repaint(); // Actualizar la interfaz después de encontrar polígonos
}


    public Logica getLogica() {
        return logica;
    }
}
