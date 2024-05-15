package interfaz;

import controlador.Controlador;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        System.out.println("Ingrese el valor de filas deseadas");
        int filas = sc.nextInt();
        System.out.println("Ingrese el valor de columnas deseadas");
        int columnas = sc.nextInt();
        Controlador controlador = new Controlador(filas,columnas);
    }
}
