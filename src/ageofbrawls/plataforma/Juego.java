package ageofbrawls.plataforma;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.contenido.Personajes.Grupo;
import ageofbrawls.contenido.Personajes.Paisano;
import ageofbrawls.contenido.Personajes.Personaje;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mprad
 */
public class Juego implements Comando {

    Civilizacion activa;
    Mapa mapa;
    static ConsolaNormal consola;

    public Juego() {
        mapa = new Mapa(20, true);
        Civilizacion romana = new Civilizacion(mapa, "Romana", new Posicion((mapa.getFilas() - 1), 0));
        Civilizacion griega = new Civilizacion(mapa, "Griega", new Posicion(0, 0));
        mapa.addCivilizacion(romana.getNombre(), romana);
        mapa.addCivilizacion(griega.getNombre(), griega);
        activa = romana;
    }

    @Override
    public void cambiar(String destino) {
        Civilizacion civ = mapa.getCivilizaciones().get(destino);
        if (civ == null) {
            System.out.println("No existe la civilizacion");
            return;
        }
        activa = civ;
        mapa.imprimirCabecera();
        mapa.imprimir(activa);
        System.out.println("Has cambiado a la civilización " + activa.getNombre());
    }

    @Override
    public void civilizacion() {
        System.out.println("La civilización activa es: " + activa.getNombre());
    }

    @Override
    public void guardar(String ruta) {
        try {
            Loader loader = new Loader(mapa, ruta, true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AgeOfBrawls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void cargar(String ruta) {
        try {
            Loader loader = new Loader(mapa, ruta);
            activa = mapa.getCivilizaciones().get(mapa.getCivilizaciones().keySet().toArray()[0]);
            mapa.imprimirCabecera();
            mapa.imprimir(activa);
            System.out.println("Archivos cargados.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AgeOfBrawls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void atacar(String atacante, String direccion) {
        if (activa.getPersonajes().containsKey(atacante)) {
            Personaje personaje2 = activa.getPersonajes().get(atacante);
            personaje2.atacar(direccion);
        } else if (activa.getGrupos().containsKey(atacante)) {
            Grupo grupo1 = activa.getGrupos().get(atacante);
            grupo1.atacar(direccion);
        } else {
            System.out.println("Error: sujeto a atacar no encontrado.");
        }
    }

    @Override
    public void defender(String defensor, String direccion) {
        if (activa.getPersonajes().containsKey(defensor)) {
            Personaje personaje2 = activa.getPersonajes().get(defensor);
            personaje2.defender(direccion);
        } else if (activa.getGrupos().containsKey(defensor)) {
            Grupo grupo1 = activa.getGrupos().get(defensor);
            grupo1.defender(direccion);
        } else {
            System.out.println("Error: sujeto a defender no encontrado.");
        }

    }

    @Override
    public void desagrupar(String grupo) {
        if (!activa.getGrupos().containsKey(grupo)) {
            System.out.println("El grupo no existe");
            return;
        }
        activa.getGrupos().get(grupo).desagrupar();
    }

    @Override
    public void desligar(String desligado, String grupo) {
        if (!activa.getGrupos().containsKey(grupo)) {
            System.out.println("El grupo no existe");
            return;
        }
        if (!activa.getPersonajes().containsKey(desligado)) {
            System.out.println("El personaje no existe");
            return;
        }
        Personaje pers = activa.getPersonajes().get(desligado);
        if (!activa.getGrupos().get(grupo).getPersonajes().contains(pers)) {
            System.out.println("El personaje no pertenece a este grupo");
            return;
        }
        activa.getGrupos().get(grupo).desligar(pers);
    }

    @Override
    public void agrupar(String donde) {
        Posicion posAgrupar = new Posicion(donde);
        if (posAgrupar.getX() == -1) {
            System.out.println("Error de sintaxis.");
            return;
        }
        Celda celda1 = mapa.getCelda(posAgrupar);
        if (celda1 == null || celda1.isOculto(activa)) {
            System.out.println("Esta celda no existe o aun no es visible!");
            return;
        }
        celda1.agrupar(activa);
    }

    @Override
    public void manejar(String quien, Scanner sca) {
        if (!activa.getPersonajes().containsKey(quien) && !activa.getGrupos().containsKey(quien)) {
            System.out.println("El personaje/grupo no existe");
            return;
        }
        System.out.println("Pulsa q para salir de este modo, a,s d, y w para desplazarte.");
        String tecla = "";
        while (!tecla.equals("q")) {
            tecla = sca.nextLine();
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
            System.out.println("Error: almacenador no encontrado.");
        }
    }

    @Override
    public void recolectar(String persona, String direccion) {
        if (activa.getPersonajes().containsKey(persona)) {
            Personaje personaje2 = activa.getPersonajes().get(persona);
            ((Paisano) personaje2).recolectar(direccion);
        } else if (activa.getGrupos().containsKey(persona)) {
            Grupo grupo1 = activa.getGrupos().get(persona);
            grupo1.recolectar(direccion);
        } else {
            System.out.println("Error: sujeto a moverse no encontrado.");
        }
    }

    @Override
    public void crear(String edificio, String tipo) {
        Edificio creador = activa.getEdificios().get(edificio);
        if (creador == null || (creador.getTipo() == Edificio.CUARTEL && !tipo.equals("soldado")) || (creador.getTipo() == Edificio.CIUDADELA && !tipo.equals("paisano"))) {
            System.out.println("Comando erroneo. No se puede crear.");
            return;
        }
        creador.crearPersonaje(activa);
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
            System.out.println("Error: reparador no encontrado.");
        }
    }

    @Override
    public void construir(String constructor, String tipo, String dir) {
        if (activa.getPersonajes().containsKey(constructor)) {
            Personaje personaje1 = activa.getPersonajes().get(constructor);
            personaje1.construir(tipo, dir);
        } else if (activa.getGrupos().containsKey(constructor)) {
            Grupo grupo1 = activa.getGrupos().get(constructor);
            grupo1.construir(tipo, dir);
        } else {
            System.out.println("Error: constructor no encontrado.");
        }
    }

    @Override
    public void mirar(String donde) {
        Posicion posMirar = new Posicion(donde);
        if (posMirar.getX() == -1) {
            System.out.println("Error de sintaxis.");
            return;
        }
        Celda celda = mapa.getCelda(posMirar);
        if (celda == null || celda.isOculto(activa)) {
            System.out.println("Esta celda no existe o aun no es visible!");
            return;
        }
        System.out.println("Celda de tipo " + celda.leerTipoCont());
        if (celda.getEdificio() != null) {
            System.out.println("Hay un edificio: ");
            celda.getEdificio().describirEdificio();
        }
        if (!celda.getPersonajes().isEmpty()) {
            System.out.println("Personajes: ");
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
            ContenedorRecurso rec = activa.getContenedoresRecurso().get(sujeto);
            rec.describirContenedorRecurso();
        } else if (activa.getGrupos().containsKey(sujeto)) {
            Grupo grupo1 = activa.getGrupos().get(sujeto);
            grupo1.describir();
        } else {
            System.out.println("Error: sujeto a describir no encontrado.");
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
    public void mover(String quien, String donde) {
        Personaje personaje = activa.getPersonajes().get(quien);
        if (personaje == null) {
            Grupo grupo = activa.getGrupos().get(quien);
            if (grupo == null) {
                System.out.println("El personaje o grupo no existe");
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
