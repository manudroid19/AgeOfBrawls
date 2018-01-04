/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.edificio;

import ageofbrawls.contenido.Personajes.Personaje;
import ageofbrawls.contenido.Personajes.Soldados.Arquero;
import ageofbrawls.contenido.Personajes.Soldados.Caballero;
import ageofbrawls.contenido.Personajes.Soldados.Legionario;
import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Juego;
import ageofbrawls.plataforma.Posicion;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentosInternos;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentosValoresIncorrectos;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.EscasezRecursosCreacion;
import ageofbrawls.z.excepciones.Recursos.EscasezRecursos.ExcepcionEspacioInsuficiente;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteMapa;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExistePosicion;

/**
 *
 * @author Santiago
 */
public final class Cuartel extends Edificio {

    public Cuartel(Posicion posicion, String nombre, Civilizacion civilizacion) throws ExcepcionArgumentosInternos {
        super(posicion, nombre, civilizacion, 500, 5);//salud, capaloj
    }

    @Override
    public void crearPersonaje(String tipo) throws ExcepcionEspacioInsuficiente, EscasezRecursosCreacion, ExcepcionNoExistePosicion, ExcepcionNoExisteMapa, ExcepcionArgumentosInternos, ExcepcionArgumentosValoresIncorrectos {
        Civilizacion civilizacion = super.getCivilizacion();
        if (civilizacion.getPersonajes().size() >= civilizacion.contarEdificios(Casa.class) * Casa.CAPALOJ) {
            throw new ExcepcionEspacioInsuficiente("No hay suficiente espacio para crear personajes", civilizacion.getPersonajes().size());
        }
        if (civilizacion.getAlimentos() < 100) {
            throw new EscasezRecursosCreacion("No hay suficientes recursos para crear un personaje.", 100 - civilizacion.getAlimentos());
        }
        Posicion pos = super.getPosicion().posicionAdyacenteLibre(civilizacion.getMapa());
        if (pos == super.getPosicion()) {
            throw new ExcepcionNoExistePosicion("No hay posiciones adyacentes libres");
        }
        int i = 1;
        String nombrePers = tipo.toLowerCase() + "1";
        while (civilizacion.getPersonajes().containsKey(nombrePers)) {
            nombrePers = nombrePers.replace(tipo.toLowerCase() + i, tipo.toLowerCase() + (++i));
        }
        Personaje person;
        switch (tipo.toLowerCase()) {
            case "legionario":
                person = new Legionario(pos, nombrePers, civilizacion);
                break;
            case "arquero":
                person = new Arquero(pos, nombrePers, civilizacion);
                break;
            case "caballero":
                person = new Caballero(pos, nombrePers, civilizacion);
                break;
            default:
                person = null;
                break;
        }
        civilizacion.getMapa().getCelda(pos).addPersonaje(person);
        civilizacion.getPersonajes().put(person.getNombre(), person);
        civilizacion.getMapa().getCelda(pos).setOculto(civilizacion, false);
        civilizacion.setAlimentos(-100, true);
        civilizacion.makeAdyVisible(pos);
        Juego.CONSOLA.imprimir();
        civilizacion.getMapa().imprimirCabecera();
        civilizacion.getMapa().imprimir(civilizacion);
        Juego.CONSOLA.imprimir("Coste de creacion: 100 unidades de comida");
        Juego.CONSOLA.imprimir("Te quedan " + ((civilizacion.contarEdificios(Casa.class) * Casa.CAPALOJ) - civilizacion.getPersonajes().size()) + " unidades de capacidad de alojamiento");
        Juego.CONSOLA.imprimir("Se ha creado " + person.getNombre() + " en la celda de " + pos);
    }

    @Override
    public int getMaxVida() {
        return 500;
    }

    @Override
    public String toString() {
        return "cuartel";
    }

}
