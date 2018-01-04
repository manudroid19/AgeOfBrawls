package ageofbrawls.plataforma;

import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaEdificio;
import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaGrupo;
import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaPersonaje;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentosInternos;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentosValoresIncorrectos;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionDireccionNoValida;
import ageofbrawls.z.excepciones.ExcepcionJuego;
import ageofbrawls.z.excepciones.Movimiento.ExcepcionFueraDeLimites;
import ageofbrawls.z.excepciones.Movimiento.ExcepcionNoTransitable;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.EscasezRecursosConstruccion;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.EscasezRecursosCreacion;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.ExcepcionEscasezRecursos;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.ExcepcionEspacioInsuficiente;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.ExcepcionNadaQueRecolectar;
import ageofbrawls.z.excepciones.Recursos.ExcepcionCorrespondenciaRecursos;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteArchivo;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteCivilizacion;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteGrupo;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExistePosicion;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteSujeto;
import java.util.Scanner;
import ageofbrawls.z.excepciones.Recursos.ExcepcionCorrespondenciaRecursos;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteArchivo;
import static java.lang.System.exit;
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
        CargadorJuego cargador;
        String cargar = Juego.CONSOLA.leerEnLinea("Deseas cargar el juego (s) o empezar uno nuevo(n)? ");
        if (cargar.contains("s")) {
            String ruta = Juego.CONSOLA.leerEnLinea("Introduce la ruta de los archivos (mismo directorio=.) ");
            cargador = (CargadorJuego) new CargadorArchivo(ruta);
        } else {
            cargador = (CargadorJuego) new CargadorNuevo();
        }

        Comando comando=null;
        try {
            comando = cargador.cargarJuego();
        } catch (ExcepcionNoExisteArchivo | ExcepcionArgumentosInternos | ExcepcionCorrespondenciaRecursos | ExcepcionAccionRestringidaPersonaje ex) {
            Juego.CONSOLA.imprimir(ex.getMensaje());
            exit(0);
        }

        Juego.CONSOLA.imprimir("Bienvenido a Age Of Brawls!!");
        Juego.CONSOLA.imprimir("Por ahora es un vasto territorio inexplorado que solo habita tu fiel paisano \"paisano1\" desde su bastión \"ciudadela1\".");
        Juego.CONSOLA.imprimir("Dispones inicialmente de 500 de piedra, madera y alimentos para levantar tu imperio.");
        Juego.CONSOLA.imprimir("Construir una casa cuesta 100 de piedra y madera, un cuartel 200 de piedra y madera, crear un paisano 50 de alimentos y crear un soldado 100 de alimentos.");
        Juego.CONSOLA.imprimir();
        comando.imprimirCabecera();
        comando.imprimirMapa();

        String orden = "";
        while (!"salir".equals(orden)) {
            orden = Juego.CONSOLA.leerEnLinea("Introduce orden: ");
            if (!orden.contains(" ") && !(orden.equals("salir") || orden.equals("civilizacion"))) {
                Juego.CONSOLA.imprimir("Error de sintaxis.");
                continue;
            }
            String[] comandos = orden.split(" ");
            switch (comandos[0].toLowerCase()) {
                case "mover":
                    if (comandos.length != 3) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.mover(comandos[1], comandos[2]);//quien, donde
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionDireccionNoValida ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionNoTransitable ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionFueraDeLimites ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionNadaQueRecolectar ex) {
                    Juego.CONSOLA.imprimir(ex.getMessage());
                }
            }
                    break;

                case "describir":
                    if (comandos.length != 2) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.describir(comandos[1]);
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;
                case "mirar":
                    if (comandos.length != 2) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.mirar(comandos[1]);
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;
                case "construir":
                    if (comandos.length != 4) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.construir(comandos[1], comandos[2], comandos[3]);//constructor, tipo de edificio, direccion
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionDireccionNoValida ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (EscasezRecursosConstruccion ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaGrupo ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;
                case "reparar":
                    if (comandos.length != 3) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.reparar(comandos[1], comandos[2]);//reparador, direccion
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionEscasezRecursos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaGrupo ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;

                case "crear":
                    if (comandos.length != 3) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.crear(comandos[1], comandos[2]); //edificio creador, tipo de edificio
                } catch (ExcepcionAccionRestringidaEdificio ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionEspacioInsuficiente ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (EscasezRecursosCreacion ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionNoExistePosicion ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;

                case "recolectar":
                    if (comandos.length != 3) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    try {
                        comando.recolectar(comandos[1], comandos[2]);//persona, direccion
                    } catch (ExcepcionAccionRestringidaPersonaje ex) {
                        Juego.CONSOLA.imprimir(ex.getMensaje());
                    } catch (ExcepcionArgumentosValoresIncorrectos ex) {
                        Juego.CONSOLA.imprimir(ex.getMensaje());
                    } catch (ExcepcionNadaQueRecolectar ex) {
                        Juego.CONSOLA.imprimir(ex.getMensaje());
                    } catch (ExcepcionAccionRestringidaGrupo ex) {
                        Juego.CONSOLA.imprimir(ex.getMensaje());
                    }
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionDireccionNoValida ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;

                case "almacenar":
                    if (comandos.length != 3) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    try {
                        comando.almacenar(comandos[1], comandos[2]); //quien almacena, direccion
                    } catch (ExcepcionAccionRestringidaGrupo ex) {
                        Juego.CONSOLA.imprimir(ex.getMensaje());
                    } catch (ExcepcionArgumentosValoresIncorrectos ex) {
                        Juego.CONSOLA.imprimir(ex.getMensaje());
                    }
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionDireccionNoValida ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionEscasezRecursos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;
                case "manejar":
                    if (comandos.length != 2) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.manejar(comandos[1]);
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;
                case "cambiar":
                    if (comandos.length != 2) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.cambiar(comandos[1]);
                } catch (ExcepcionNoExisteCivilizacion ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;
                case "civilizacion":
                    comando.civilizacion();
                    break;
                case "imprimir":
                    if (comandos.length != 2 || !comandos[1].equals("mapa")) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
                    comando.imprimirCabecera();
                    comando.imprimirMapa();
                    break;
                case "agrupar":
                    if (comandos.length != 2) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.agrupar(comandos[1]);
                } catch (ExcepcionNoExistePosicion ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;

                case "desligar":
                    if (comandos.length != 3) {
                        Juego.CONSOLA.imprimir("Error de sintaxis");
                        break;
                    }
            {
                try {
                    comando.desligar(comandos[1], comandos[2]); //personaje desligado, grupo
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;

                case "desagrupar":
                    if (comandos.length != 2) {
                        Juego.CONSOLA.imprimir("Error de sintaxis");
                    }
            {
                try {
                    comando.desagrupar(comandos[1]);
                } catch (ExcepcionNoExisteGrupo ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;

                case "defender":
                    if (comandos.length != 3) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.defender(comandos[1], comandos[2]);
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionDireccionNoValida ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionEspacioInsuficiente ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaGrupo ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;

                case "atacar":
                    if (comandos.length != 3) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                        break;
                    }
            {
                try {
                    comando.atacar(comandos[1], comandos[2]);
                } catch (ExcepcionNoExisteSujeto ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionDireccionNoValida ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaGrupo ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;

                case "cargar":
                    if (comandos.length != 2) {
                        Juego.CONSOLA.imprimir("Error de sintaxis");
                    }
            {
                try {
                    comando.cargar(comandos[1]);
                } catch (ExcepcionNoExisteArchivo ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionArgumentosInternos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionCorrespondenciaRecursos ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                } catch (ExcepcionAccionRestringidaPersonaje ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;
                case "guardar":
                    if (comandos.length != 2) {
                        Juego.CONSOLA.imprimir("Error de sintaxis");
                    }
            {
                try {
                    comando.guardar(comandos[1]);
                } catch (ExcepcionNoExisteArchivo ex) {
                    Juego.CONSOLA.imprimir(ex.getMensaje());
                }
            }
                    break;
                case "listar":
                    if (comandos.length != 2) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
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
                            Juego.CONSOLA.imprimir("Comando no valido");
                            break;
                    }
                    break;
                default:
                    if (!orden.equals("salir") && !orden.equals("\n")) {
                        Juego.CONSOLA.imprimir("Error de sintaxis.");
                    }
            }

        }

    }

}
