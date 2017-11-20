/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.contenido.Personaje;
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

        Mapa mapa = new Mapa(20, true);
        Civilizacion romana = new Civilizacion(mapa, "Romana", new Posicion((mapa.getFilas() - 1), 0));
        Civilizacion griega = new Civilizacion(mapa, "Griega", new Posicion(0, 0));
        mapa.addCivilizacion(romana.getNombre(), romana);
        mapa.addCivilizacion(griega.getNombre(), griega);
        Civilizacion activa = romana;
        Scanner sca = new Scanner(System.in);

        System.out.println("Bienvenido a Age Of Brawls!!");
        System.out.println("Por ahora es un vasto territorio inexplorado que solo habita tu fiel paisano \"paisano1\" desde su bastión \"ciudadela1\".");
        System.out.println("Dispones inicialmente de 500 de piedra, madera y alimentos para levantar tu imperio.");
        System.out.println("Construir una casa cuesta 100 de piedra y madera, un cuartel 200 de piedra y madera, crear un paisano 50 de alimentos y crear un soldado 100 de alimentos.");
        System.out.println();
        mapa.imprimirCabecera();

        String orden = "";
        mapa.imprimir(activa);
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
                    String quien = comando[1];
                    String donde = comando[2];
                    Personaje personaje = activa.getPersonajes().get(quien);
                    if (personaje == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    personaje.mover(donde);
                    break;
                case "listar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    switch (comando[1].toLowerCase()) {
                        case "personajes":
                            activa.listarPersonajes();
                            break;
                        case "edificios":
                            activa.listarEdificios();
                            break;
                        case "civilizaciones":
                            mapa.listarCivilizaciones();
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
                    if (activa.getPersonajes().containsKey(sujeto)) {
                        Personaje personaje1 = activa.getPersonajes().get(sujeto);
                        personaje1.describirPersonaje();
                    } else if (activa.getEdificios().containsKey(sujeto)) {
                        Edificio edificio = activa.getEdificios().get(sujeto);
                        edificio.describirEdificio();
                    } else if (activa.getContenedoresRecurso().containsKey(sujeto)) {
                        ContenedorRecurso rec = activa.getContenedoresRecurso().get(sujeto);
                        rec.describirContenedorRecurso();
                    } else {
                        System.out.println("Error: sujeto a describir no encontrado.");
                    }
                    break;
                case "mirar":
                    if (comando.length != 2 || comando[1].length() >= 5 || comando[1].charAt(0) != '(') {
                        System.out.println("Error de sintaxis..");
                        break;
                    }
                    String sub3 = comando[1];
                    int x = Integer.parseInt(sub3.substring(1, sub3.indexOf(",")));
                    int y = Integer.parseInt(sub3.substring(sub3.indexOf(",") + 1, sub3.length() - 1));
                    Celda celda = mapa.getCelda(new Posicion(y, x));
                    if (celda == null || celda.isOculto(activa)) {
                        System.out.println("Esta celda no existe o aun no es visible!");
                        break;
                    }
                    System.out.println("Celda de tipo " + celda.leerTipoCont());
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
                    if (celda.getContenedorRec() != null) {
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
                    personaje = activa.getPersonajes().get(constructor);
                    if (personaje == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    personaje.consEdif(tipo, dir, activa);
                    break;
                case "reparar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String reparador = comando[1];
                    dir = comando[2];
                    personaje = activa.getPersonajes().get(reparador);
                    if (personaje == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    personaje.reparar(personaje.getPosicion().getAdy(dir));
                    break;
                case "crear":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String edificio = comando[1];
                    tipo = comando[2];
                    Edificio creador = activa.getEdificios().get(edificio);
                    if (creador == null || (creador.getTipo() == Edificio.CUARTEL && !tipo.equals("soldado")) || (creador.getTipo() == Edificio.CIUDADELA && !tipo.equals("paisano"))) {
                        System.out.println("Comando erroneo. No se puede crear.");
                        break;
                    }
                    creador.crearPersonaje(activa);

                    break;

                case "recolectar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String persona = comando[1];
                    String direccion = comando[2];
                    personaje = activa.getPersonajes().get(persona);
                    if (personaje == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    personaje.recolectar(mapa, direccion);
                    break;

                case "almacenar":
                    if (comando.length != 3) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    String personaje4 = comando[1];
                    String direccion2 = comando[2];
                    Personaje personaje5 = activa.getPersonajes().get(personaje4);
                    if (personaje5 == null) {
                        System.out.println("El personaje no existe");
                        break;
                    }
                    personaje5.almacenar(mapa, direccion2);
                    break;
                case "manejar":
                    if (comando.length != 2) {
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    quien = comando[1];
                    Personaje person = activa.getPersonajes().get(quien);
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
                                person.mover("oeste");
                                mapa.imprimirCabecera();
                                mapa.imprimir(activa);
                                break;
                            case "s":
                                person.mover("sur");
                                mapa.imprimirCabecera();
                                mapa.imprimir(activa);
                                break;
                            case "d":
                                person.mover("este");
                                mapa.imprimirCabecera();
                                mapa.imprimir(activa);
                                break;
                            case "w":
                                person.mover("norte");
                                mapa.imprimirCabecera();
                                mapa.imprimir(activa);
                                break;
                        }
                    }
                    break;
                case "cambiar":
                    if(comando.length !=2){
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    Civilizacion civ = mapa.getCivilizaciones().get(comando[1]);
                    if(civ==null){
                        System.out.println("No existe la civilizacion");
                        break;
                    }
                    activa = civ;
                    System.out.println("Has cambiado a la civilización "+activa.getNombre());
                        
                    break;
                case "civilizacion":
                    System.out.println("La civilización activa es: "+activa.getNombre());
                    break;
                case "imprimir":
                    if(comando.length !=2 || !comando[1].equals("mapa")){
                        System.out.println("Error de sintaxis.");
                        break;
                    }
                    mapa.imprimirCabecera();
                    mapa.imprimir(activa);
                    break;
                default:
                    if (!orden.equals("salir") && !orden.equals("\n")) {
                        System.out.println("Error de sintaxis.");
                    }
            }

        }

    }

}
