/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import ageofbrawls.contenido.Edificio;
import ageofbrawls.contenido.Personaje;
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
        String jugador = "a";

        System.out.print("Bienvenido a Age Of Brawls!!\n\rIntroduce un nombre para tu primer jugador: ");
        jugador = sca.nextLine();
        mapa.inicializar(jugador);
        System.out.println();
        System.out.println("Leyenda: Pradera transitable" + Mapa.ANSI_GREEN_BACKGROUND + "   " + Mapa.ANSI_RESET + " Ciudadela:" + Mapa.ANSI_PURPLE_BACKGROUND + " U " + Mapa.ANSI_RESET);
        System.out.println("Casa:" + Mapa.ANSI_PURPLE_BACKGROUND + " K " + Mapa.ANSI_RESET + "Bosque:" + Mapa.ANSI_CYAN_BACKGROUND + " B " + Mapa.ANSI_RESET + "Cantera:" + Mapa.ANSI_BLUE_BACKGROUND + Mapa.ANSI_WHITE + " C " + Mapa.ANSI_RESET + "Arbusto:" + Mapa.ANSI_YELLOW_BACKGROUND + " A " + Mapa.ANSI_RESET);
        System.out.println("En las praderas transitables puede haber personajes, en \n\rcaso lo verás como " + Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P " + Mapa.ANSI_RESET + "y se te indicará en la fila sus nombres.");
        System.out.println("Los comandos disponibles son: \n\rmover [nombre personaje] [direccion: norte, sur, este o oeste]");
        System.out.println("manejar [personaje] (permite manejar el personaje usando ASDF o las flechas de control)");
        System.out.println("listar [personajes o edificios]");

        System.out.println();
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(AgeOfBrawls.class.getName()).log(Level.SEVERE, null, ex);
        }
        String orden = "";
        while (!"salir".equals(orden)) {
            mapa.imprimir();
            System.out.print("Introduce orden: ");
            orden = sca.nextLine();
            if (orden.contains(" ")) {
                String comando = orden.substring(0, orden.indexOf(" "));
                switch (comando.toLowerCase()) {
                    case "mover":
                        String sub = orden.substring(orden.indexOf(" ") + 1, orden.length());
                        String quien = sub.substring(0, sub.indexOf(" "));
                        String donde = sub.substring(sub.indexOf(" ") + 1, sub.length());
                        Personaje personaje = mapa.getPersonajes().get(quien);
                        personaje.mover(mapa, donde);
                        break;
                    case "manejar":
                        System.out.println("Entra q para salir de este modo, a,s d, y w para desplazarte.");
                        String subc = orden.substring(orden.indexOf(" ") + 1, orden.length());
                        Personaje person = mapa.getPersonajes().get(subc);
                        String tecla = "";
                        while (!tecla.equals("q")) {
                            tecla = sca.next();
                            switch (tecla) {
                                case "a":
                                    person.mover(mapa, "oeste");
                                    mapa.imprimir();
                                    break;
                                case "s":
                                    person.mover(mapa, "sur");
                                    mapa.imprimir();
                                    break;
                                case "d":
                                    person.mover(mapa, "este");
                                    mapa.imprimir();
                                    break;
                                case "w":
                                    person.mover(mapa, "norte");
                                    mapa.imprimir();
                                    break;
                            }
                        }

                        break;
                        
                    case "listar":
                        String sub1 = orden.substring(orden.indexOf(" ") + 1, orden.length());
                        if(sub1.equals("personajes")){
                          mapa.listarPersonajes();  
                        }
                        else if(sub1.equals("edificios")){
                            mapa.listarEdificios();
                        }
                        break;
                        
                    case "describir":
                        String sub2 = orden.substring(orden.indexOf(" ") + 1, orden.length());
                        if(mapa.getPersonajes().containsKey(sub2)){
                        Personaje personaje1 = mapa.getPersonajes().get(sub2);
                        personaje1.describirPersonaje();
                        }
                        else if(mapa.getEdificios().containsKey(sub2)){
                        Edificio edificio= mapa.getEdificios().get(sub2);
                        edificio.describirEdificio();
                        }
                        //else
                            
                }
            }
        }

    }

}
