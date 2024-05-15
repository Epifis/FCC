package interfaz;

import controlador.Controlador;
import logica.Linea;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class InterfazApp extends JFrame implements ActionListener, MouseListener {

    private JButton[][] celdas;
    private JPanel panelGrilla, panelBotones;
    private Controlador controlador;
    private List<Linea> aristas;
    private boolean asignandoVertices = false;
    private boolean asignandoAristas = false;
    private Point puntoInicialArista;

    public InterfazApp(Controlador controlador) {
        this.controlador = controlador;
        setTitle("Interfaz con Grilla y Botones");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int[][] grilla = controlador.getLogica().getGrilla();
        int filas = grilla.length;
        int columnas = grilla[0].length;

        getContentPane().removeAll();

        panelGrilla = new JPanel(new GridLayout(filas, columnas));
        celdas = new JButton[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new JButton("");
                celdas[i][j].addActionListener(this);
                celdas[i][j].addMouseListener(this);
                panelGrilla.add(celdas[i][j]);
            }
        }

        panelBotones = new JPanel(new FlowLayout());
        JButton asignarVerticesBtn = new JButton("Asignar Vértices");
        JButton asignarAristasBtn = new JButton("Asignar Aristas");
        JButton mostrarDibujoBtn = new JButton("Mostrar Dibujo");
        JButton limpiarDibujoBtn = new JButton("Limpiar Dibujo");
        asignarVerticesBtn.addActionListener(this);
        asignarAristasBtn.addActionListener(this);
        mostrarDibujoBtn.addActionListener(this);
        limpiarDibujoBtn.addActionListener(this);
        panelBotones.add(asignarVerticesBtn);
        panelBotones.add(asignarAristasBtn);
        panelBotones.add(mostrarDibujoBtn);
        panelBotones.add(limpiarDibujoBtn);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelGrilla, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        pack();
        setVisible(true);

        aristas = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Asignar Vértices":
                asignandoVertices = true;
                asignandoAristas = false;
                break;
            case "Asignar Aristas":
                asignandoVertices = false;
                asignandoAristas = true;
                break;
            case "Mostrar Dibujo":
                mostrarDibujoCompleto();
                break;
            case "Limpiar Dibujo":
                limpiarDibujo();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton boton = (JButton) e.getSource();
        int fila = -1;
        int columna = -1;
        int filas = celdas.length;
        int columnas = celdas[0].length;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (boton == celdas[i][j]) {
                    fila = i;
                    columna = j;
                    break;
                }
            }
        }
        if (asignandoVertices) {
            if (fila != -1 && columna != -1) {
                controlador.procesarCelda(new Point(fila, columna), null);
                celdas[fila][columna].setText("Vértice");
                System.out.println("El punto del vértice creado es" + fila + ", " + columna);
            }
        } else if (asignandoAristas) {
            //hay que diferenciar y guardar cada poligono en una matriz para implementar la logica del coloreado, en logica, metodo buscarPoligonos
            if (puntoInicialArista == null) {
                puntoInicialArista = new Point(fila, columna);
                celdas[fila][columna].setForeground(Color.BLUE);
                System.out.println("El punto inicial está en el vértice " + puntoInicialArista.x + ", " + puntoInicialArista.y);
            } else {
                Point puntoFinalArista = new Point(fila, columna);
                aristas.add(new Linea(puntoInicialArista, puntoFinalArista));
                puntoInicialArista = null;
                celdas[fila][columna].setForeground(Color.RED);
                System.out.println("Se asignó la línea con puntos " + aristas.get(aristas.size() - 1).getPunto1().x + " en x, "
                        + aristas.get(aristas.size() - 1).getPunto1().y + " en y, y puntos " + aristas.get(aristas.size() - 1).getPunto2().x
                        + " en x, " + aristas.get(aristas.size() - 1).getPunto2().y);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void mostrarDibujoCompleto() {
        JFrame ventanaDibujoCompleto = new JFrame("Dibujo Completo");
        ventanaDibujoCompleto.setSize(400, 400);
        ventanaDibujoCompleto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelDibujo;
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar las líneas
                for (Linea linea : aristas) {
                    Point punto1 = linea.getPunto1();
                    Point punto2 = linea.getPunto2();
                    g.setColor(Color.RED);
                    //Esto es para organizar el dibujo en las medidas del panel
                    g.drawLine(punto1.y * getWidth() / celdas[0].length + getWidth() / (2 * celdas[0].length),
                            punto1.x * getHeight() / celdas.length + getHeight() / (2 * celdas.length),
                            punto2.y * getWidth() / celdas[0].length + getWidth() / (2 * celdas[0].length),
                            punto2.x * getHeight() / celdas.length + getHeight() / (2 * celdas.length));

                }
                controlador.getLogica().encontrarPoligonos(aristas);
                if (controlador.getLogica().getPoligono() != null) {
                    List<Point> puntosPoligono = controlador.getLogica().getPoligono().getPuntosPoligono();
                    if (puntosPoligono != null && puntosPoligono.size() >= 2) {
                        System.out.println("Tamaño de la lista de puntos del polígono: " + puntosPoligono.size());
                        System.out.println("Coordenadas de los puntos del polígono:");
                        for (Point punto : puntosPoligono) {
                            System.out.println("(" + punto.x + ", " + punto.y + ")");
                        }

                        int[] xPoints = new int[puntosPoligono.size()];
                        int[] yPoints = new int[puntosPoligono.size()];

                        for (int i = 0; i < puntosPoligono.size(); i++) {
                            Point punto = puntosPoligono.get(i);
                            yPoints[i] = punto.x * getWidth() / (celdas.length);
                            xPoints[i] = punto.y * getHeight() / (celdas[0].length);

                        }
                        g.setColor(Color.DARK_GRAY);
                        g.fillPolygon(xPoints, yPoints, puntosPoligono.size());
//hay que arreglar como se guardan las medidas para rellenar el panel
                    } else {
                        System.out.println("No hay suficientes puntos en el polígono.");
                    }

                } else {
                    System.out.println("No se encontró ningún polígono.");
                }

            }
        };

        ventanaDibujoCompleto.add(panelDibujo);
        ventanaDibujoCompleto.setVisible(true);
    }

    private void limpiarDibujo() {
        aristas.clear();
        puntoInicialArista = null;
        for (JButton[] fila : celdas) {
            for (JButton boton : fila) {
                boton.setBackground(null);
                boton.setForeground(null);
            }
        }
        asignandoVertices = false;
                asignandoAristas = false;
        repaint();
    }
}
