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
    private ArrayList<Personaje> personajes;

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

    public boolean esCeldaLibre(boolean personaje) {
        if (getContenedorRec().getTipo() == ContenedorRecurso.PRADERA && getEdificio() == null) {
            if (personaje) {
                return personajes.isEmpty();
            }
            return true;
        }
        return false;
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

    public String getTipo() {
        switch (this.recurso.getTipo()) {
            case ContenedorRecurso.PRADERA:
                return "pradera";
            case ContenedorRecurso.BOSQUE:
                return "bosque";
            case ContenedorRecurso.CANTERA:
                return "cantera";
            case ContenedorRecurso.ARBUSTO:
                return "arbusto";
            default:
                return null;
        }
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

    public void addPersonaje(Personaje personaje) {
        if (personaje != null) {
            personajes.add(personaje);
        }
    }

    public void removePersonaje(Personaje personaje) {
        if (personaje != null) {
            personajes.remove(personaje);
        }
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void setEdificio(Edificio edificio) {
        if (edificio != null) {
            this.recurso.set(ContenedorRecurso.PRADERA, 0);
            this.edificio = edificio;
        } else {
            System.out.println("Error:edificio es nulo");
        }
    }

    public void mirar() {
        if (this.getEdificio() != null) {
            this.getEdificio().describirEdificio();//En caso de que en la celda haya un edificio, lo describimos
        } else if (this.getContenedorRec().getTipo() != ContenedorRecurso.PRADERA) {
            this.getContenedorRec().describirContenedorRecurso();//En caso de estar en un contenedor de Recursos, imprimimos su descripción
        }
        if (this.getContenedorRec().getTipo() == ContenedorRecurso.PRADERA && this.getPersonajes() != null) {
            this.getPersonajes().get(0).describirPersonaje();//enseñamos la info del 0, ya que es el único de momento que se puede alamcenar en la celda
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
            return " ? ";
        }
        if (!this.personajes.isEmpty()) {
            if (this.personajes.size() == 1) {
                if (this.personajes.get(0).getTipo() == Personaje.PAISANO) {
                    return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P ";
                }else{
                    return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " S ";
                }
            }else{
                return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P*";
            }
        }
        switch (this.recurso.getTipo()) {
            case ContenedorRecurso.BOSQUE:
                return Mapa.ANSI_CYAN_BACKGROUND + " B ";
            case ContenedorRecurso.PRADERA:
                if (this.edificio == null) {
                    return Mapa.ANSI_GREEN_BACKGROUND + "   ";
                } else if (this.edificio.getTipo() == Edificio.CIUDADELA) {
                    return Mapa.ANSI_PURPLE_BACKGROUND + " U ";
                } else if (this.edificio.getTipo() == Edificio.CASA) {
                    return Mapa.ANSI_PURPLE_BACKGROUND + " K ";
                } else if (this.edificio.getTipo() == Edificio.CUARTEL) {
                    return Mapa.ANSI_PURPLE_BACKGROUND + " Z ";
                }
            case ContenedorRecurso.CANTERA:
                return Mapa.ANSI_BLUE_BACKGROUND + Mapa.ANSI_WHITE + " C ";
            case ContenedorRecurso.ARBUSTO:
                return Mapa.ANSI_YELLOW_BACKGROUND + " A ";
            default:
                return " ";
        }
    }
}
