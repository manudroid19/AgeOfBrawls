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
        Comando comando = (Comando) juego;
        Scanner sca = new Scanner(System.in);

        System.out.println("Bienvenido a Age Of Brawls!!");
        System.out.println("Por ahora es un vasto territorio inexplorado que solo habita tu fiel paisano \"paisano1\" desde su bastión \"ciudadela1\".");
        System.out.println("Dispones inicialmente de 500 de piedra, madera y alimentos para levantar tu imperio.");
        System.out.println("Construir una casa cuesta 100 de piedra y madera, un cuartel 200 de piedra y madera, crear un paisano 50 de alimentos y crear un soldado 100 de alimentos.");
        System.out.println();
        comando.imprimirCabecera();

        String orden = "";
        comando.imprimirMapa();
        while (!"salir".equals(orden)) {
            System.out.print("Introduce orden: ");
            orden = sca.nextLine();
            if (!orden.contains(" ") && !(orden.equals("salir") || orden.equals("civilizacion"))) {
                System.out.println("Error de sintaxis.");
                continue;
            }
            String[] comandos = orden.split(" ");
            switch (comandos[0].toLowerCase()) {
                case "mover":
                    if (comandos.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.mover(comandos[1], comandos[2]);//quien, donde
                    break;

                case "describir":
                    if (comandos.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.describir(comandos[1]);
                    break;
                case "mirar":
                    if (comandos.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.mirar(comandos[1]);
                    break;
                case "construir":
                    if (comandos.length != 4) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.construir(comandos[1], comandos[2], comandos[3]);//constructor, tipo de edificio, direccion
                    break;
                case "reparar":
                    if (comandos.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.reparar(comandos[1], comandos[2]);//reparador, direccion
                    break;

                case "crear":
                    if (comandos.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.crear(comandos[1], comandos[2]); //edificio creador, tipo de edificio
                    break;

                case "recolectar":
                    if (comandos.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.recolectar(comandos[1], comandos[2]);//persona, direccion
                    break;

                case "almacenar":
                    if (comandos.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.almacenar(comandos[1], comandos[2]); //quien almacena, direccion
                    break;
                case "manejar":
                    if (comandos.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.manejar(comandos[1], sca);
                    break;
                case "cambiar":
                    if (comandos.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.cambiar(comandos[1]);
                    break;
                case "civilizacion":
                    comando.civilizacion();
                    break;
                case "imprimir":
                    if (comandos.length != 2 || !comandos[1].equals("mapa")) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.imprimirCabecera();
                    comando.imprimirMapa();
                    break;
                case "agrupar":
                    if (comandos.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.agrupar(comandos[1]);
                    break;

                case "desligar":
                    if (comandos.length != 3) {
                        System.out.println("Error de sintaxis");
                        break;
                    }
                    comando.desligar(comandos[1], comandos[2]); //personaje desligado, grupo
                    break;

                case "desagrupar":
                    if (comandos.length != 2) {
                        System.out.println("Error de sintaxis");
                    }
                    comando.desagrupar(comandos[1]);
                    break;

                case "defender":
                    if (comandos.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.defender(comandos[1], comandos[2]);
                    break;

                case "atacar":
                    if (comandos.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    comando.atacar(comandos[1], comandos[2]);
                    break;

                case "cargar":
                    if (comandos.length != 2) {
                        System.out.println("Error de sintaxis");
                    }
                    comando.cargar(comandos[1]);
                    break;
                case "guardar":
                    if (comandos.length != 2) {
                        System.out.println("Error de sintaxis");
                    }
                    comando.guardar(comandos[1]);
                    break;
                case "listar":
                    if (comandos.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    switch (comandos[1].toLowerCase()) {
                        case "personajes":
                            comando.listarPersonajes();
                            break;
                        case "grupos":
                            comando.listarGrupos();
                            break;
                        case "edificios":
                            comando.listarEdificios();
                            break;
                        case "civilizaciones":
                            comando.listarCivilizaciones();
                            break;
                        default:
                            System.out.println("Comando no valido");
                            break;
                    }
                    break;
                default:
                    if (!orden.equals("salir") && !orden.equals("\n")) {
                        System.out.println("Error de sintaxis.");
                    }
            }

        }

    }

}
