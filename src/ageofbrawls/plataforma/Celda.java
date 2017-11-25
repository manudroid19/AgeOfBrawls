/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.contenido.Grupo;
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
    private boolean haygrupo;
    private ArrayList<Personaje> personajes;
    private ArrayList<Grupo> grupos;

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
        grupos = new ArrayList<>();
        haygrupo = false;
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

    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }

    public boolean isOculto(Civilizacion civilizacion) {
        return civilizacion.isOculto(posicion);
    }

    public boolean isHayGrupo() {
        return haygrupo;
    }

    public void setOculto(Civilizacion civilizacion, boolean oculto) {
        civilizacion.setOculto(posicion, oculto);
    }

    public void setTipoCont(int tipo) {
        if (tipo == PRADERA) {
            recurso = null;
        } else {
            if (recurso == null) {
                recurso = new ContenedorRecurso(tipo, 0);
            } else {
                recurso.setTipo(tipo);
            }
        }
    }

    public void setTipoCont(int tipo, int cantidad) {
        if (tipo == PRADERA) {
            recurso = null;
        } else {
            if (recurso == null) {
                recurso = new ContenedorRecurso(tipo, cantidad);
            } else {
                recurso.set(tipo, cantidad);
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

    public void addGrupo(Grupo grupo) {
        if (grupo != null) {
            grupos.add(grupo);
            if (this.getGrupos().size() == 1) {
                this.haygrupo = true;
            }
        }
    }

    public void removePersonaje(Personaje personaje) {
        if (personaje != null) {
            personajes.remove(personaje);
        }
    }

    public void removeGrupo(Grupo grupo) {
        if (grupo != null) {
            grupos.remove(grupo);
            if (grupos.isEmpty()) {
                this.haygrupo = false;
            }
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
            for (int i = 0; i < this.getPersonajes().size(); i++) {
                this.getPersonajes().get(i).describirPersonaje();//enseñamos la info de todos 
            }
        }
        if(this.getContenedorRec() !=null && this.isHayGrupo()){
            for(int i=0; i<this.getGrupos().size();i++){
                this.getGrupos().get(i).describirGrupo();
            }
        }

    }

    public void agrupar(Civilizacion civilizacion) {
        if (this.getPersonajes().size() > 1) {
            if (!this.haygrupo) {
                int i = 1;
                String nombreGrupo = "Grupo1";
                while (civilizacion.getGrupo().containsKey(nombreGrupo)) {
                    nombreGrupo = nombreGrupo.replace("grupo" + i, "grupo" + (++i));
                }
                Grupo group = new Grupo(this.getPersonajes(), this.getPosicion(), nombreGrupo, civilizacion);
                this.haygrupo = true;
                System.out.println("Se ha creado el" + group.getNombre() + "con los personajes: ");
                for (int j = 0; j < this.getPersonajes().size(); j++) {
                    System.out.println(this.getPersonajes().get(j).getNombre());
                }
                this.personajes.clear();

            } else {
                System.out.println("No se puede agrupar un grupo");
            }

        } else {
            System.out.println("No se puede crear un grupo con 1 o ningún personaje");

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
