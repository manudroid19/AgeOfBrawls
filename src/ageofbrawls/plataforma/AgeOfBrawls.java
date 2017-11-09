/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prada
 */
public class AgeOfBrawls {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Mapa mapa = new Mapa(10, true);
        Scanner sca = new Scanner(System.in);
        String jugador="";
        
        System.out.print("Bienvenido a Age Of Brawls!!\n\rIntroduce un nombre para tu primer jugador: ");
        jugador=sca.nextLine();
        mapa.inicializar(jugador);
        System.out.print("Todo listo! Pulsa intro para comenzar...");
        sca.nextLine();
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(AgeOfBrawls.class.getName()).log(Level.SEVERE, null, ex);
        }

        mapa.imprimir();
        String orden = "";
        while (!"salir".equals(orden)) {
            System.out.print("Introduce orden: ");
            orden = sca.nextLine();
            if (orden.contains(" ")) {
                String comando = orden.substring(0, orden.indexOf(" "));
                switch (comando) {
                    case "mover":
                        String sub = orden.substring(orden.indexOf(" ") + 1, orden.length());
                        String quien = sub.substring(0, sub.indexOf(" "));
                        String donde = sub.substring(sub.indexOf(" ") + 1, sub.length());
                        break;
                }
            }
        }
    }

}
