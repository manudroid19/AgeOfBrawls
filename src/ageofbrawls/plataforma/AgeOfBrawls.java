/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.contenido.Personaje;
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

        System.out.print("Bienvenido a Age Of Brawls!!");
        System.out.println();
        imprimirCabecera();
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(AgeOfBrawls.class.getName()).log(Level.SEVERE, null, ex);
        }
        String orden = "";
        mapa.imprimir();
        while (!"salir".equals(orden)) {
            System.out.print("Introduce orden: ");
            orden = sca.nextLine();
            if (!orden.contains(" ")) {
                System.out.println("Error de sintaxis.");
                continue;
            }
            String comando[] = orden.split(" ");
            switch (comando[0].toLowerCase()) {
                case "mover":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String quien = comando[1];
                    String donde = comando[2];
                    Personaje personaje = mapa.getPersonajes().get(quien);
                    if (personaje == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    personaje.mover(mapa, donde);
                    System.out.println();
                    imprimirCabecera();
                    mapa.imprimir();
                    break;
                case "manejar":
                    System.out.println("Pulsa q para salir de este modo, a,s d, y w para desplazarte.");
                    quien = comando[1];
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    Personaje person = mapa.getPersonajes().get(quien);
                    if (person == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    String tecla = "";
                    while (!tecla.equals("q")) {
                        tecla = sca.next();
                        switch (tecla) {
                            case "a":
                                person.mover(mapa, "oeste");
                                imprimirCabecera();
                                mapa.imprimir();
                                break;
                            case "s":
                                person.mover(mapa, "sur");
                                imprimirCabecera();
                                mapa.imprimir();
                                break;
                            case "d":
                                person.mover(mapa, "este");
                                imprimirCabecera();
                                mapa.imprimir();
                                break;
                            case "w":
                                person.mover(mapa, "norte");
                                imprimirCabecera();
                                mapa.imprimir();
                                break;
                        }
                    }
                    break;
                case "listar":
                    String sub1 = orden.substring(orden.indexOf(" ") + 1, orden.length());
                    switch (sub1.toLowerCase()) {
                        case "personajes":
                            mapa.listarPersonajes();
                            break;
                        case "edificios":
                            mapa.listarEdificios();
                            break;
                        default:
                            System.out.println("Comando no valido");
                            break;
                    }
                    break;

                case "describir":
                    String sub2 = orden.substring(orden.indexOf(" ") + 1, orden.length());
                    if (mapa.getPersonajes().containsKey(sub2)) {
                        Personaje personaje1 = mapa.getPersonajes().get(sub2);
                        personaje1.describirPersonaje();
                    } else if (mapa.getEdificios().containsKey(sub2)) {
                        Edificio edificio = mapa.getEdificios().get(sub2);
                        edificio.describirEdificio();
                    } else if (mapa.getContenedoresRecurso().containsKey(sub2)) {
                        ContenedorRecurso rec = mapa.getContenedoresRecurso().get(sub2);
                        rec.describirContenedorRecurso();
                    }
                    break;
                case "mirar":
                    String sub3 = orden.substring(orden.indexOf(" ") + 1, orden.length());
                    int x = Integer.parseInt(sub3.substring(1, sub3.indexOf(",")));
                    int y = Integer.parseInt(sub3.substring(sub3.indexOf(",") + 1, sub3.length() - 1));
                    Celda celda = mapa.getCelda(new Posicion(y, x));
                    if (celda == null || celda.isOculto()) {
                        System.out.println("Esta celda no existe o aun no es visible!");
                        break;
                    }
                    System.out.println("Celda de tipo " + celda.getTipo());
                    if (celda.getEdificio() != null) {
                        System.out.println("Hay un edificio: ");
                        celda.getEdificio().describirEdificio();
                    }
                    if (!celda.getPersonajes().isEmpty()) {
                        System.out.println("Personajes: ");
                        for (Personaje per : celda.getPersonajes()) {
                            per.describirPersonaje();
                        }
                    }
                    if (celda.getContenedorRec().getTipo() != ContenedorRecurso.PRADERA) {
                        celda.getContenedorRec().describirContenedorRecurso();
                    }
                    break;
                case "construir":
                    if (comando.length != 4) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String constructor = comando[1];
                    String tipo = comando[2];
                    String dir = comando[3];
                    personaje = mapa.getPersonajes().get(constructor);
                    if (personaje == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }

                    System.out.println();
                    imprimirCabecera();
                    mapa.imprimir();
                    break;
                default:
                    System.out.println("Error de sintaxis.");
            }

        }

    }

    public static void imprimirCabecera() {
        System.out.println("Leyenda: Pradera transitable" + Mapa.ANSI_GREEN_BACKGROUND + "   " + Mapa.ANSI_RESET + " Ciudadela:" + Mapa.ANSI_PURPLE_BACKGROUND + " U " + Mapa.ANSI_RESET);
        System.out.println("Casa:" + Mapa.ANSI_PURPLE_BACKGROUND + " K " + Mapa.ANSI_RESET + "Bosque:" + Mapa.ANSI_CYAN_BACKGROUND + " B " + Mapa.ANSI_RESET
                + "Cantera:" + Mapa.ANSI_BLUE_BACKGROUND + Mapa.ANSI_WHITE + " C " + Mapa.ANSI_RESET + "Arbusto:" + Mapa.ANSI_YELLOW_BACKGROUND + " A " + Mapa.ANSI_RESET);
        System.out.println("En las praderas transitables puede haber personajes, en \n\rcaso lo verás como " + Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P " + Mapa.ANSI_RESET);
        System.out.println("Los nombres de los contenedores de recursos aparecerán al lado de su fila.");
        System.out.println("Los comandos disponibles son: \n\rmover [nombre personaje] [direccion: norte, sur, este o oeste]");
        System.out.println("manejar [personaje] (permite manejar el personaje usando ASDF o las flechas de control)");
        System.out.println("listar [personajes o edificios]");
        System.out.println("describir [nombre de personaje,edificio o contenedor de recurso]");
        System.out.println("mirar (fila,columna)");
        System.out.println();
    }

}
