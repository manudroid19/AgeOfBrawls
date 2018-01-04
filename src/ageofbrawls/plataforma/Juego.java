package ageofbrawls.plataforma;

import ageofbrawls.contenido.contenedor.Contenedor;
import ageofbrawls.contenido.edificio.Edificio;
import ageofbrawls.contenido.Personajes.Grupo;
import ageofbrawls.contenido.Personajes.Paisano;
import ageofbrawls.contenido.Personajes.Personaje;
import ageofbrawls.contenido.edificio.Ciudadela;
import ageofbrawls.contenido.edificio.Cuartel;
import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaEdificio;
import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaPersonaje;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentosInternos;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.EscasezRecursosCreacion;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.ExcepcionEspacioInsuficiente;
import ageofbrawls.z.excepciones.Recursos.ExcepcionCorrespondenciaRecursos;
import ageofbrawls.z.excepciones.noExiste.*;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mprad
 */
public class Juego implements Comando {

    Civilizacion activa;
    Mapa mapa;
    public static final Consola CONSOLA = (Consola) new ConsolaNormal();

    public Juego() throws ExcepcionArgumentosInternos, ExcepcionCorrespondenciaRecursos {
        mapa = new Mapa(20, true);
        Civilizacion romana = new Civilizacion(mapa, "Romana", new Posicion((mapa.getFilas() - 1), 0));
        Civilizacion griega = new Civilizacion(mapa, "Griega", new Posicion(0, 0));
        mapa.addCivilizacion(romana.getNombre(), romana);
        mapa.addCivilizacion(griega.getNombre(), griega);
        activa = romana;
    }

    @Override
    public void cambiar(String destino) throws ExcepcionNoExisteCivilizacion {
        Civilizacion civ = mapa.getCivilizaciones().get(destino);
        if (civ == null) {
            throw new ExcepcionNoExisteCivilizacion("No existe la civilizacion");
        }
        activa = civ;
        mapa.imprimirCabecera();
        mapa.imprimir(activa);
        Juego.CONSOLA.imprimir("Has cambiado a la civilización " + activa.getNombre());
    }

    @Override
    public void civilizacion() {
        Juego.CONSOLA.imprimir("La civilización activa es: " + activa.getNombre());
    }

    @Override
    public void guardar(String ruta) throws ExcepcionNoExisteArchivo {
        Loader loader = new Loader(mapa, ruta, true);

    }

    @Override
    public void cargar(String ruta) throws ExcepcionNoExisteArchivo {
            Loader loader = new Loader(mapa, ruta);
            activa = mapa.getCivilizaciones().get(mapa.getCivilizaciones().keySet().toArray()[0]);
            mapa.imprimirCabecera();
            mapa.imprimir(activa);
            Juego.CONSOLA.imprimir("Archivos cargados.");

    }

    @Override
    public void atacar(String atacante, String direccion) throws ExcepcionNoExisteSujeto, ExcepcionArgumentosInternos, ExcepcionAccionRestringidaPersonaje {
        if (activa.getPersonajes().containsKey(atacante)) {
            Personaje personaje2 = activa.getPersonajes().get(atacante);
            personaje2.atacar(direccion);
        } else if (activa.getGrupos().containsKey(atacante)) {
            Grupo grupo1 = activa.getGrupos().get(atacante);
            grupo1.atacar(direccion);
        } else {
            throw new ExcepcionNoExisteSujeto("Error: sujeto a atacar no encontrado.");
        }
    }

    @Override
    public void defender(String defensor, String direccion) throws ExcepcionArgumentosInternos {
        if (activa.getPersonajes().containsKey(defensor)) {
            Personaje personaje2 = activa.getPersonajes().get(defensor);
            personaje2.defender(direccion);
        } else if (activa.getGrupos().containsKey(defensor)) {
            Grupo grupo1 = activa.getGrupos().get(defensor);
            grupo1.defender(direccion);
        } else {
            Juego.CONSOLA.imprimir("Error: sujeto a defender no encontrado.");
        }

    }

    @Override
    public void desagrupar(String grupo) throws ExcepcionAccionRestringidaPersonaje {
        if (!activa.getGrupos().containsKey(grupo)) {
            Juego.CONSOLA.imprimir("El grupo no existe");
            return;
        }
        activa.getGrupos().get(grupo).desagrupar();
    }

    @Override
    public void desligar(String desligado, String grupo) throws ExcepcionArgumentosInternos, ExcepcionAccionRestringidaPersonaje {
        if (!activa.getGrupos().containsKey(grupo)) {
            Juego.CONSOLA.imprimir("El grupo no existe");
            return;
        }
        if (!activa.getPersonajes().containsKey(desligado)) {
            Juego.CONSOLA.imprimir("El personaje no existe");
            return;
        }
        Personaje pers = activa.getPersonajes().get(desligado);
        if (!activa.getGrupos().get(grupo).getPersonajes().contains(pers)) {
            Juego.CONSOLA.imprimir("El personaje no pertenece a este grupo");
            return;
        }
        activa.getGrupos().get(grupo).desligar(pers);
    }

    @Override
    public void agrupar(String donde) throws ExcepcionArgumentosInternos, ExcepcionAccionRestringidaPersonaje {
        Posicion posAgrupar = new Posicion(donde);
        if (posAgrupar.getX() == -1) {
            Juego.CONSOLA.imprimir("Error de sintaxis.");
            return;
        }
        Celda celda1 = mapa.getCelda(posAgrupar);
        if (celda1 == null || celda1.isOculto(activa)) {
            Juego.CONSOLA.imprimir("Esta celda no existe o aun no es visible!");
            return;
        }
        celda1.agrupar(activa);
    }

    @Override
    public void manejar(String quien) throws ExcepcionAccionRestringidaPersonaje, ExcepcionArgumentosInternos {
        if (!activa.getPersonajes().containsKey(quien) && !activa.getGrupos().containsKey(quien)) {
            Juego.CONSOLA.imprimir("El personaje/grupo no existe");
            return;
        }
        Juego.CONSOLA.imprimir("Pulsa q para salir de este modo, a,s d, y w para desplazarte.");
        String tecla = "";
        while (!tecla.equals("q")) {
            tecla = Juego.CONSOLA.leer();
            switch (tecla) {
                case "a":
                    mover(quien, "oeste");
                    mapa.imprimirCabecera();
                    mapa.imprimir(activa);
                    break;
                case "s":
                    mover(quien, "sur");
                    mapa.imprimirCabecera();
                    mapa.imprimir(activa);
                    break;
                case "d":
                    mover(quien, "este");
                    mapa.imprimirCabecera();
                    mapa.imprimir(activa);
                    break;
                case "w":
                    mover(quien, "norte");
                    mapa.imprimirCabecera();
                    mapa.imprimir(activa);
                    break;
            }
        }
    }

    @Override
    public void almacenar(String almacenador, String direccion) {
        if (activa.getPersonajes().containsKey(almacenador)) {
            Personaje personaje2 = activa.getPersonajes().get(almacenador);
            ((Paisano) personaje2).almacenar(direccion);
        } else if (activa.getGrupos().containsKey(almacenador)) {
            Grupo grupo1 = activa.getGrupos().get(almacenador);
            grupo1.almacenar(direccion);
        } else {
            Juego.CONSOLA.imprimir("Error: almacenador no encontrado.");
        }
    }

    @Override
    public void recolectar(String persona, String direccion) throws ExcepcionArgumentosInternos {
        if (activa.getPersonajes().containsKey(persona)) {
            Personaje personaje2 = activa.getPersonajes().get(persona);
            ((Paisano) personaje2).recolectar(direccion);
        } else if (activa.getGrupos().containsKey(persona)) {
            Grupo grupo1 = activa.getGrupos().get(persona);
            grupo1.recolectar(direccion);
        } else {
            Juego.CONSOLA.imprimir("Error: sujeto a moverse no encontrado.");
        }
    }

    @Override
    public void crear(String edificio, String tipo) throws ExcepcionAccionRestringidaEdificio, ExcepcionEspacioInsuficiente, EscasezRecursosCreacion, ExcepcionNoExistePosicion{
        Edificio creador = activa.getEdificios().get(edificio);
        if (creador == null || (creador instanceof Cuartel && !tipo.equals("soldado")) || (creador instanceof Ciudadela && !tipo.equals("paisano"))) {
            Juego.CONSOLA.imprimir("Comando erroneo. No se puede crear.");
            return;
        }
        creador.crearPersonaje();
    }

    @Override
    public void reparar(String reparador, String dir) {
        if (activa.getPersonajes().containsKey(reparador)) {
            Personaje personaje2 = activa.getPersonajes().get(reparador);
            ((Paisano) personaje2).reparar(personaje2.getPosicion().getAdy(dir));
        } else if (activa.getGrupos().containsKey(reparador)) {
            Grupo grupo1 = activa.getGrupos().get(reparador);
            grupo1.reparar(grupo1.getPosicion().getAdy(dir));
        } else {
            Juego.CONSOLA.imprimir("Error: reparador no encontrado.");
        }
    }

    @Override
    public void construir(String constructor, String tipo, String dir) throws ExcepcionArgumentosInternos {
        if (activa.getPersonajes().containsKey(constructor)) {
            Personaje personaje1 = activa.getPersonajes().get(constructor);
            personaje1.construir(tipo, dir);
        } else if (activa.getGrupos().containsKey(constructor)) {
            Grupo grupo1 = activa.getGrupos().get(constructor);
            grupo1.construir(tipo, dir);
        } else {
            Juego.CONSOLA.imprimir("Error: constructor no encontrado.");
        }
    }

    @Override
    public void mirar(String donde) {
        Posicion posMirar = new Posicion(donde);
        if (posMirar.getX() == -1) {
            Juego.CONSOLA.imprimir("Error de sintaxis.");
            return;
        }
        Celda celda = mapa.getCelda(posMirar);
        if (celda == null || celda.isOculto(activa)) {
            Juego.CONSOLA.imprimir("Esta celda no existe o aun no es visible!");
            return;
        }
        Juego.CONSOLA.imprimir("Celda de tipo " + celda.leerTipoCont());
        if (celda.getEdificio() != null) {
            Juego.CONSOLA.imprimir("Hay un edificio: ");
            celda.getEdificio().describirEdificio();
        }
        if (!celda.getPersonajes().isEmpty()) {
            Juego.CONSOLA.imprimir("Personajes: ");
            for (Personaje per : celda.getPersonajes()) {
                per.describir();
            }
        }
        if (celda.getContenedorRec() != null) {
            celda.getContenedorRec().describirContenedorRecurso();
        }
        if (!celda.getGrupos().isEmpty()) {
            for (Grupo gr : celda.getGrupos()) {
                gr.describir();
            }
        }
    }

    @Override
    public void describir(String sujeto) {
        if (activa.getPersonajes().containsKey(sujeto)) {
            Personaje personaje1 = activa.getPersonajes().get(sujeto);
            personaje1.describir();
        } else if (activa.getEdificios().containsKey(sujeto)) {
            Edificio edificio = activa.getEdificios().get(sujeto);
            edificio.describirEdificio();
        } else if (activa.getContenedoresRecurso().containsKey(sujeto)) {
            Contenedor rec = activa.getContenedoresRecurso().get(sujeto);
            rec.describirContenedorRecurso();
        } else if (activa.getGrupos().containsKey(sujeto)) {
            Grupo grupo1 = activa.getGrupos().get(sujeto);
            grupo1.describir();
        } else {
            Juego.CONSOLA.imprimir("Error: sujeto a describir no encontrado.");
        }
    }

    @Override
    public void listarPersonajes() {
        activa.listarPersonajes();
    }

    @Override
    public void listarGrupos() {
        activa.listarGrupos();
    }

    @Override
    public void listarEdificios() {
        activa.listarEdificios();
    }

    @Override
    public void listarCivilizaciones() {
        mapa.listarCivilizaciones();
    }

    @Override
    public void mover(String quien, String donde) throws ExcepcionAccionRestringidaPersonaje, ExcepcionArgumentosInternos {
        Personaje personaje = activa.getPersonajes().get(quien);
        if (personaje == null) {
            Grupo grupo = activa.getGrupos().get(quien);
            if (grupo == null) {
                Juego.CONSOLA.imprimir("El personaje o grupo no existe");
                return;
            }
            grupo.mover(donde);
        } else {
            personaje.mover(donde);
        }
    }

    @Override
    public void imprimirCabecera() {
        mapa.imprimirCabecera();
    }

    @Override
    public void imprimirMapa() {
        mapa.imprimir(activa);
    }
}
