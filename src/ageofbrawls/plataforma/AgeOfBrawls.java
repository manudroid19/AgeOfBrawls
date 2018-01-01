
package ageofbrawls.plataforma;

import java.util.Scanner;

/**
 *
 * @author prada
 */
public class AgeOfBrawls {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Juego juego = new Juego();
        Scanner sca = new Scanner(System.in);

        System.out.println("Bienvenido a Age Of Brawls!!");
        System.out.println("Por ahora es un vasto territorio inexplorado que solo habita tu fiel paisano \"paisano1\" desde su bastión \"ciudadela1\".");
        System.out.println("Dispones inicialmente de 500 de piedra, madera y alimentos para levantar tu imperio.");
        System.out.println("Construir una casa cuesta 100 de piedra y madera, un cuartel 200 de piedra y madera, crear un paisano 50 de alimentos y crear un soldado 100 de alimentos.");
        System.out.println();
        juego.imprimirCabecera();

        String orden = "";
        juego.imprimirMapa();
        while (!"salir".equals(orden)) {
            System.out.print("Introduce orden: ");
            orden = sca.nextLine();
            if (!orden.contains(" ") && !(orden.equals("salir") || orden.equals("civilizacion"))) {
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
                    juego.mover(comando[1], comando[2]);//quien, donde
                    break;

                case "listar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    switch (comando[1].toLowerCase()) {
                        case "personajes":
                            juego.listarPersonajes();
                            break;
                        case "grupos":
                            juego.listarGrupos();
                            break;
                        case "edificios":
                            juego.listarEdificios();
                            break;
                        case "civilizaciones":
                            juego.listarCivilizaciones();
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
                    juego.describir(comando[1]);
                    break;
                case "mirar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.mirar(comando[1]);
                    break;
                case "construir":
                    if (comando.length != 4) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.construir(comando[1], comando[2], comando[3]);//constructor, tipo de edificio, direccion
                    break;
                case "reparar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.reparar(comando[1], comando[2]);//reparador, direccion
                    break;

                case "crear":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.crear(comando[1], comando[2]); //edificio creador, tipo de edificio
                    break;

                case "recolectar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.recolectar(comando[1], comando[2]);//persona, direccion
                    break;

                case "almacenar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.almacenar(comando[1], comando[2]); //quien almacena, direccion
                    break;
                case "manejar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.manejar(comando[1], sca);
                    break;
                case "cambiar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.cambiar(comando[1]);
                    break;
                case "civilizacion":
                    juego.civilizacion();
                    break;
                case "imprimir":
                    if (comando.length != 2 || !comando[1].equals("mapa")) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.imprimirCabecera();
                    juego.imprimirMapa();
                    break;
                case "agrupar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.agrupar(comando[1]);
                    break;

                case "desligar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis");
                        break;
                    }
                    juego.desligar(comando[1],comando[2]); //personaje desligado, grupo
                    break;

                case "desagrupar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis");
                    }
                    juego.desagrupar(comando[1]);
                    break;

                case "defender":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.defender(comando[1],comando[2]);
                    break;

                case "atacar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    juego.atacar(comando[1], comando[2]);
                    break;

                case "cargar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis");
                    }
                    juego.cargar(comando[1]);
                    break;
                case "guardar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis");
                    }
                    juego.guardar(comando[1]);
                    break;
                default:
                    if (!orden.equals("salir") && !orden.equals("\n")) {
                        System.out.println("Error de sintaxis.");
                    }
            }

        }

    }

}
