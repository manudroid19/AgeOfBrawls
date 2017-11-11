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

        System.out.println("Bienvenido a Age Of Brawls!!");
        System.out.println("Dispones inicialmente de 500 de piedra, madera y alimentos para levantar tu imperio.");
        System.out.println("Construir una casa cuesta 100 de piedra y madera, un cuartel 200 de piedra y madera, crear un paisano 50 de alimentos y crear un soldado 100 de alimentos.");
        System.out.println();
        mapa.imprimirCabecera();
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
            if (!orden.contains(" ") && !orden.equals("salir")) {
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
                    break;
                case "manejar":
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
                    System.out.println("Pulsa q para salir de este modo, a,s d, y w para desplazarte.");
                    String tecla = "";
                    while (!tecla.equals("q")) {
                        tecla = sca.nextLine();
                        switch (tecla) {
                            case "a":
                                person.mover(mapa, "oeste");
                                mapa.imprimirCabecera();
                                mapa.imprimir();
                                break;
                            case "s":
                                person.mover(mapa, "sur");
                                mapa.imprimirCabecera();
                                mapa.imprimir();
                                break;
                            case "d":
                                person.mover(mapa, "este");
                                mapa.imprimirCabecera();
                                mapa.imprimir();
                                break;
                            case "w":
                                person.mover(mapa, "norte");
                                mapa.imprimirCabecera();
                                mapa.imprimir();
                                break;
                        }
                    }
                    break;
                case "listar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    switch (comando[1].toLowerCase()) {
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
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String sujeto = comando[1];
                    if (mapa.getPersonajes().containsKey(sujeto)) {
                        Personaje personaje1 = mapa.getPersonajes().get(sujeto);
                        personaje1.describirPersonaje();
                    } else if (mapa.getEdificios().containsKey(sujeto)) {
                        Edificio edificio = mapa.getEdificios().get(sujeto);
                        edificio.describirEdificio();
                    } else if (mapa.getContenedoresRecurso().containsKey(sujeto)) {
                        ContenedorRecurso rec = mapa.getContenedoresRecurso().get(sujeto);
                        rec.describirContenedorRecurso();
                    } else {
                        System.out.println("Error: sujeto a describir no encontrado.");
                    }
                    break;
                case "mirar":
                    if (comando.length != 2 || comando[1].length() != 5 || comando[1].charAt(0) != '(' || comando[1].charAt(2) != ',' || comando[1].charAt(4) != ')') {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String sub3 = comando[1];
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
                    personaje.consEdif(tipo, dir, mapa);
                    break;
                case "reparar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String reparador = comando[1];
                    dir = comando[2];
                    personaje = mapa.getPersonajes().get(reparador);
                    if (personaje == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    personaje.reparar(personaje.getPosicion().get(dir),mapa);
                    break;
                case "crear":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String edificio = comando[1];
                    tipo = comando[2];
                    Edificio creador = mapa.getEdificios().get(edificio);
                    if (creador == null || (creador.getTipo()==Edificio.CUARTEL &&!tipo.equals("soldado")) || (creador.getTipo()==Edificio.CIUDADELA &&!tipo.equals("paisano"))) {
                        System.out.println("Comando erroneo. No se puede crear.");
                        break;
                    }
                    creador.crearPersonaje(mapa);
                    
                    break;
                    
                case "recolectar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String personaje2 = comando[1];
                    String direccion = comando[2];
                    Personaje personaje3= mapa.getPersonajes().get(personaje2);
                    if (personaje3 == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    personaje3.recolectar(mapa, direccion);
                    break;
                    
                case "almacenar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String personaje4 = comando[1];
                    String direccion2 = comando[2];
                    Personaje personaje5= mapa.getPersonajes().get(personaje4);
                    if (personaje5 == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    personaje5.almacenar(mapa, direccion2);
                    break;
                    
                default:
                    if (!orden.equals("salir") && !orden.equals("\n")) {
                        System.out.println("Error de sintaxis.");
                    }
            }

        }

    }

}
