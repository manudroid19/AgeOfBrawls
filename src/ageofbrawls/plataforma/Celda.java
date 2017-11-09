/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.contenido.Personaje;
import java.util.ArrayList;

/**
 *
 * @author Santiago
 */
public class Celda {

    private Edificio edificio;
    private boolean oculto;
    private ContenedorRecurso recurso;
    private Posicion posicion;
    ArrayList<Personaje> personajes;

    public Celda(int tipo, int cantidadRecurso, int edificio, Posicion posicion, String nombreEdificio) {
        this.posicion = new Posicion(posicion);//valida a posicion
        if (edificio == 0) {
            this.edificio = null;
        } else {
            this.edificio = new Edificio(edificio, posicion, nombreEdificio);//valida o edificio
        }
        recurso = new ContenedorRecurso(tipo, cantidadRecurso);//valida tipo e cantRecurso
        personajes = new ArrayList<>();
    }

    public Celda(int edificio, int i, int j, String nombreEdificio) {
        this(ContenedorRecurso.PRADERA, 0, edificio, new Posicion(i, j), nombreEdificio);
    }

    public Celda(int tipo, int cantidadRecurso, int i, int j) {
        this(tipo, cantidadRecurso, 0, new Posicion(i, j), null);
    }

    public Celda(int i, int j) {
        this(ContenedorRecurso.PRADERA, 0, 0, new Posicion(i, j), null);
    }

    public Celda(int i, int j, boolean oculto) {
        this(ContenedorRecurso.PRADERA, 0, 0, new Posicion(i, j), null);
        this.oculto = oculto;
    }

    public ContenedorRecurso getContenedorRec() {
        return recurso;
    }

    public Edificio getEdificio() {
        return edificio;
    }
    public void addPersonaje(Personaje personaje){
        if(personaje!=null){
            personajes.add(personaje);
        }
    }
    public void removePersonaje(Personaje personaje){
        if(personaje!=null){
            personajes.remove(personaje);
        }
    }

    public void setEdificio(Edificio edificio) {
        if (edificio != null) {
            this.recurso.set(ContenedorRecurso.PRADERA, 0);
            this.edificio = edificio;
        } else {
            System.out.println("Error:edificio es nulo");
        }
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    @Override
    public String toString() {
        if (this.oculto) {
            return "?";
        }
        if(!this.personajes.isEmpty()){
            return Mapa.ANSI_CYAN_BACKGROUND+"P";
        }
        switch (this.recurso.getTipo()) {
            case ContenedorRecurso.BOSQUE:
                return "B";
            case ContenedorRecurso.PRADERA:
                if (this.edificio == null) {
                    return Mapa.ANSI_GREEN_BACKGROUND + " ";
                } else if (this.edificio.getTipo() == Edificio.CIUDADELA) {
                    return "U";
                } else if (this.edificio.getTipo() == Edificio.CASA) {
                    return "K";
                }
            case ContenedorRecurso.CANTERA:
                return "C";
            case ContenedorRecurso.ARBUSTO:
                return "A";
            default:
                return " ";
        }
    }
}
