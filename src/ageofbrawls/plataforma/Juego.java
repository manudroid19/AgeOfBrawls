package ageofbrawls.plataforma;

import ageofbrawls.contenido.contenedor.Contenedor;
import ageofbrawls.contenido.edificio.Edificio;
import ageofbrawls.contenido.Personajes.Grupo;
import ageofbrawls.contenido.Personajes.Paisano;
import ageofbrawls.contenido.Personajes.Personaje;
import ageofbrawls.contenido.edificio.Ciudadela;
import ageofbrawls.contenido.edificio.Cuartel;
import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringida;
import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaEdificio;
import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaPersonaje;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentos;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentosInternos;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.EscasezRecursosCreacion;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.ExcepcionEspacioInsuficiente;
import ageofbrawls.z.excepciones.Recursos.ExcepcionCorrespondenciaRecursos;
import ageofbrawls.z.excepciones.Sintaxis.ExcepcionComando;
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
    public void cargar(String ruta) throws ExcepcionNoExisteArchivo, ExcepcionArgumentosInternos, ExcepcionCorrespondenciaRecursos, ExcepcionAccionRestringidaPersonaje {
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
    public void defender(String defensor, String direccion) throws ExcepcionNoExisteSujeto, ExcepcionArgumentosInternos {
        if (activa.getPersonajes().containsKey(defensor)) {
            Personaje personaje2 = activa.getPersonajes().get(defensor);
            personaje2.defender(direccion);
        } else if (activa.getGrupos().containsKey(defensor)) {
            Grupo grupo1 = activa.getGrupos().get(defensor);
            grupo1.defender(direccion);
        } else {
            throw new ExcepcionNoExisteSujeto("Error: sujeto a defender no encontrado.");
        }

    }

    @Override
    public void desagrupar(String grupo) throws ExcepcionAccionRestringidaPersonaje, ExcepcionNoExisteGrupo {
        if (!activa.getGrupos().containsKey(grupo)) {
            throw new ExcepcionNoExisteGrupo("El grupo no existe");
        }
        activa.getGrupos().get(grupo).desagrupar();
    }

    @Override
    public void desligar(String desligado, String grupo) throws ExcepcionArgumentosInternos, ExcepcionAccionRestringidaPersonaje, ExcepcionNoExisteGrupo, ExcepcionNoExistePersonaje {
        if (!activa.getGrupos().containsKey(grupo)) {
            throw new ExcepcionNoExisteGrupo("El grupo no existe");
            
        }
        if (!activa.getPersonajes().containsKey(desligado)) {
           throw new ExcepcionNoExistePersonaje("El personaje no existe");
            
        }
        Personaje pers = activa.getPersonajes().get(desligado);
        if (!activa.getGrupos().get(grupo).getPersonajes().contains(pers)) {
            throw new ExcepcionNoExisteGrupo("El personaje no pertenece a este grupo");
            
        }
        activa.getGrupos().get(grupo).desligar(pers);
    }

    @Override
    public void agrupar(String donde) throws ExcepcionArgumentosInternos, ExcepcionAccionRestringidaPersonaje {
        Posicion posAgrupar = new Posicion(donde);
        if (posAgrupar.getX() == -1) {
            throw new ExcepcionArgumentosInternos("Error de sintaxis.");
            
        }
        Celda celda1 = mapa.getCelda(posAgrupar);
        if (celda1 == null || celda1.isOculto(activa)) {
            throw new ExcepcionAccionRestringidaPersonaje("Esta celda no existe o aun no es visible!");
            
        }
        celda1.agrupar(activa);
    }

    @Override
    public void manejar(String quien) throws ExcepcionAccionRestringidaPersonaje, ExcepcionArgumentosInternos, ExcepcionNoExisteSujeto {
        if (!activa.getPersonajes().containsKey(quien) && !activa.getGrupos().containsKey(quien)) {
            throw new ExcepcionNoExisteSujeto("El personaje/grupo no existe");
            
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
    public void almacenar(String almacenador, String direccion) throws ExcepcionNoExisteSujeto{
        if (activa.getPersonajes().containsKey(almacenador)) {
            Personaje personaje2 = activa.getPersonajes().get(almacenador);
            ((Paisano) personaje2).almacenar(direccion);
        } else if (activa.getGrupos().containsKey(almacenador)) {
            Grupo grupo1 = activa.getGrupos().get(almacenador);
            grupo1.almacenar(direccion);
        } else {
            throw new ExcepcionNoExisteSujeto("Error: almacenador no encontrado.");
        }
    }

    @Override
    public void recolectar(String persona, String direccion) throws ExcepcionNoExisteSujeto,ExcepcionArgumentosInternos {
        if (activa.getPersonajes().containsKey(persona)) {
            Personaje personaje2 = activa.getPersonajes().get(persona);
            ((Paisano) personaje2).recolectar(direccion);
        } else if (activa.getGrupos().containsKey(persona)) {
            Grupo grupo1 = activa.getGrupos().get(persona);
            grupo1.recolectar(direccion);
        } else {
            throw new ExcepcionNoExisteSujeto("Error: sujeto a moverse no encontrado.");
        }
    }

    @Override
    public void crear(String edificio, String tipo) throws ExcepcionAccionRestringidaEdificio, ExcepcionEspacioInsuficiente, EscasezRecursosCreacion, ExcepcionNoExistePosicion, ExcepcionArgumentosInternos{
        Edificio creador = activa.getEdificios().get(edificio);
        if (creador == null || (creador instanceof Cuartel && !tipo.equals("soldado")) || (creador instanceof Ciudadela && !tipo.equals("paisano"))) {
            throw new ExcepcionArgumentosInternos("Comando erroneo. No se puede crear.");
            
        }
        creador.crearPersonaje();
    }

    @Override
    public void reparar(String reparador, String dir) throws ExcepcionNoExisteSujeto {
        if (activa.getPersonajes().containsKey(reparador)) {
            Personaje personaje2 = activa.getPersonajes().get(reparador);
            ((Paisano) personaje2).reparar(personaje2.getPosicion().getAdy(dir));
        } else if (activa.getGrupos().containsKey(reparador)) {
            Grupo grupo1 = activa.getGrupos().get(reparador);
            grupo1.reparar(grupo1.getPosicion().getAdy(dir));
        } else {
            throw new ExcepcionNoExisteSujeto("Error: reparador no encontrado.");
        }
    }

    @Override
    public void construir(String constructor, String tipo, String dir) throws ExcepcionArgumentosInternos, ExcepcionNoExisteSujeto {
        if (activa.getPersonajes().containsKey(constructor)) {
            Personaje personaje1 = activa.getPersonajes().get(constructor);
            personaje1.construir(tipo, dir);
        } else if (activa.getGrupos().containsKey(constructor)) {
            Grupo grupo1 = activa.getGrupos().get(constructor);
            grupo1.construir(tipo, dir);
        } else {
            throw new ExcepcionNoExisteSujeto("Error: constructor no encontrado.");
        }
    }

    @Override
    public void mirar(String donde) throws ExcepcionAccionRestringidaPersonaje, ExcepcionArgumentosInternos {
        Posicion posMirar = new Posicion(donde);
        if (posMirar.getX() == -1) {
            throw new ExcepcionArgumentosInternos("Error de sintaxis.");
            
        }
        Celda celda = mapa.getCelda(posMirar);
        if (celda == null || celda.isOculto(activa)) {
            throw new ExcepcionAccionRestringidaPersonaje("Esta celda no existe o aun no es visible!");
            
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
        if (celda.getContenedorRec().getRecurso() != null) {
            celda.getContenedorRec().describirContenedorRecurso();
        }
        if (!celda.getGrupos().isEmpty()) {
            for (Grupo gr : celda.getGrupos()) {
                gr.describir();
            }
        }
    }

    @Override
    public void describir(String sujeto) throws ExcepcionNoExisteSujeto {
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
            throw new ExcepcionNoExisteSujeto("Error: sujeto a describir no encontrado.");
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
    public void mover(String quien, String donde) throws ExcepcionAccionRestringidaPersonaje, ExcepcionArgumentosInternos, ExcepcionNoExisteSujeto {
        Personaje personaje = activa.getPersonajes().get(quien);
        if (personaje == null) {
            Grupo grupo = activa.getGrupos().get(quien);
            if (grupo == null) {
                throw new ExcepcionNoExisteSujeto("El personaje o grupo no existe");
                
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
