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

    public static final int PRADERA = 0;
    private Edificio edificio;
    private ContenedorRecurso recurso;
    private Posicion posicion;
    private ArrayList<Personaje> personajes;

    public Celda(int tipo, int cantidadRecurso, int edificio, Posicion posicion, String nombreEdificio) {
        if (posicion != null) {
            this.posicion = new Posicion(posicion);
        } else {
            this.posicion = new Posicion();
        }
        if (edificio == 0) {
            this.edificio = null;
        } else {
            this.edificio = new Edificio(edificio, posicion, nombreEdificio);//valida o edificio e o string
        }
        if (tipo == PRADERA) {
            recurso = null;
        } else {
            recurso = new ContenedorRecurso(tipo, cantidadRecurso);//valida tipo e cantRecurso
        }
        personajes = new ArrayList<>();
    }

    public Celda(int i, int j) {
        this(PRADERA, 0, 0, new Posicion(i, j), null);
    }

    public ContenedorRecurso getContenedorRec() {
        return recurso;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public boolean isOculto(Civilizacion civilizacion) {
        return civilizacion.isOculto(posicion);
    }

    public void setOculto(Civilizacion civilizacion, boolean oculto) {
        civilizacion.setOculto(posicion,oculto);
    }

    public void setTipoCont(int tipo) {
        if (tipo == PRADERA) {
            recurso = null;
        } else {
            if(recurso==null){
                recurso = new ContenedorRecurso(tipo, 0);
            }else{
                recurso.setTipo(tipo);
            }
        }
    }
    
    public void setTipoCont(int tipo, int cantidad) {
        if (tipo == PRADERA) {
            recurso = null;
        } else {
            if(recurso==null){
                recurso = new ContenedorRecurso(tipo, cantidad);
            }else{
                recurso.set(tipo,cantidad);
            }
        }
    }

    public void setEdificio(Edificio edificio) {
        if (edificio != null) {
            recurso = null;
            this.edificio = edificio;
        } else {
            System.out.println("Error:edificio es nulo");
        }
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

    public String leerTipoCont() {
        if (recurso == null) {
            return "pradera";
        }
        switch (this.recurso.getTipo()) {
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

    public void mirar() {
        if (this.getEdificio() != null) {
            this.getEdificio().describirEdificio();//En caso de que en la celda haya un edificio, lo describimos
        } else if (this.getContenedorRec() != null) {
            this.getContenedorRec().describirContenedorRecurso();//En caso de estar en un contenedor de Recursos, imprimimos su descripción
        }
        if (this.getContenedorRec() != null && this.getPersonajes() != null) {
            this.getPersonajes().get(0).describirPersonaje();//enseñamos la info del 0, ya que es el único de momento que se puede alamcenar en la celda
        }

    }

    public boolean esCeldaLibre(boolean personaje) {
        if (getContenedorRec() == null && getEdificio() == null) {
            if (personaje) {
                return personajes.isEmpty();
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (!this.personajes.isEmpty()) {
            if (this.personajes.size() == 1) {
                if (this.personajes.get(0).getTipo() == Personaje.PAISANO) {
                    return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P ";
                } else {
                    return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " S ";
                }
            } else {
                return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P*";
            }
        }
        if (this.recurso == null) {
            if (this.edificio == null) {
                return Mapa.ANSI_GREEN_BACKGROUND + "   ";
            } else if (this.edificio.getTipo() == Edificio.CIUDADELA) {
                return Mapa.ANSI_PURPLE_BACKGROUND + " U ";
            } else if (this.edificio.getTipo() == Edificio.CASA) {
                return Mapa.ANSI_PURPLE_BACKGROUND + " K ";
            } else if (this.edificio.getTipo() == Edificio.CUARTEL) {
                return Mapa.ANSI_PURPLE_BACKGROUND + " Z ";
            }
        }
        switch (this.recurso.getTipo()) {
            case ContenedorRecurso.BOSQUE:
                return Mapa.ANSI_CYAN_BACKGROUND + " B ";
            case ContenedorRecurso.CANTERA:
                return Mapa.ANSI_BLUE_BACKGROUND + Mapa.ANSI_WHITE + " C ";
            case ContenedorRecurso.ARBUSTO:
                return Mapa.ANSI_YELLOW_BACKGROUND + " A ";
            default:
                return " ";
        }
    }
}
