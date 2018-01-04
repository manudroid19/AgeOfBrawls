/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import ageofbrawls.contenido.contenedor.Contenedor;
import ageofbrawls.contenido.edificio.Edificio;
import ageofbrawls.contenido.Personajes.Grupo;
import ageofbrawls.contenido.Personajes.Paisano;
import ageofbrawls.contenido.Personajes.Personaje;
import ageofbrawls.contenido.Recursos.Recurso;
import ageofbrawls.contenido.contenedor.Arbusto;
import ageofbrawls.contenido.contenedor.Bosque;
import ageofbrawls.contenido.contenedor.Cantera;
import ageofbrawls.contenido.edificio.Casa;
import ageofbrawls.contenido.edificio.Ciudadela;
import ageofbrawls.contenido.edificio.Cuartel;
import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaPersonaje;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentosInternos;
import java.util.ArrayList;

/**
 *
 * @author Santiago
 */
public class Celda {

    private Edificio edificio;
    private Contenedor contenedor;
    private Posicion posicion;
    private boolean haygrupo;
    private ArrayList<Personaje> personajes;
    private ArrayList<Grupo> grupos;

    public Celda(Contenedor contenedor, Edificio edificio, Posicion posicion, Civilizacion civilizacion) throws ExcepcionArgumentosInternos {
        if (posicion != null) {
            this.posicion = new Posicion(posicion);
        } else {
            throw new ExcepcionArgumentosInternos("Posicion de creación no valida.");
        }
        if (edificio == null) {
            this.edificio = null;
        } else {
            this.edificio = edificio;
        }
        this.contenedor = new Contenedor(contenedor);
        personajes = new ArrayList<>();
        grupos = new ArrayList<>();
        haygrupo = false;
    }

    public Celda(int i, int j) throws ExcepcionArgumentosInternos {
        this(new Contenedor(), null, new Posicion(i, j), null);
    }

    public Contenedor getContenedorRec() {
        return contenedor;
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
    public void hacerPradera(){
        contenedor=new Contenedor();
    }
    public void setContenedorRecursos(Contenedor contenedor){
        if(contenedor==null){
            hacerPradera();
        }else{
            this.contenedor=contenedor;
        }
    }
    public void setOculto(Civilizacion civilizacion, boolean oculto) {
        civilizacion.setOculto(posicion, oculto);
    }

    public void setHaygrupo(boolean haygrupo) {
        this.haygrupo = haygrupo;
    }

    public void setEdificio(Edificio edificio) {
        if (edificio != null) {
            hacerPradera();
            this.edificio = edificio;
        } else {
            this.edificio = null;
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
        if (contenedor.getRecurso() == null) {
            return "pradera";
        }
        if (contenedor instanceof Bosque) {

            return "bosque";
        } else if (contenedor instanceof Cantera) {
            return "cantera";
        } else if (contenedor instanceof Arbusto) {
            return "arbusto";
        } else {
            return null;
        }
    }

//    public void mirar() {
//        if (this.getEdificio() != null) {
//            this.getEdificio().describirEdificio();//En caso de que en la celda haya un edificio, lo describimos
//        } else if (this.getContenedorRec() != null) {
//            this.getContenedorRec().describirContenedorRecurso();//En caso de estar en un contenedor de Recursos, imprimimos su descripción
//        }
//        if (this.getContenedorRec() != null && this.getPersonajes() != null) {
//            for (int i = 0; i < this.getPersonajes().size(); i++) {
//                this.getPersonajes().get(i).describirPersonaje();//enseñamos la info de todos 
//            }
//        }
//        if (this.getContenedorRec() != null && this.isHayGrupo()) {
//            for (int i = 0; i < this.getGrupos().size(); i++) {
//                this.getGrupos().get(i).describirGrupo();
//            }
//        }
//
//    }
    public void agrupar(Civilizacion civilizacion) throws ExcepcionArgumentosInternos, ExcepcionAccionRestringidaPersonaje {
        if ((this.getPersonajes().isEmpty() && this.getGrupos().size() <= 1) || (this.getPersonajes().size() <= 1) && !this.haygrupo) {
            throw new ExcepcionAccionRestringidaPersonaje("No se puede crear un grupo en esta situación");
            
        }
        ArrayList<Personaje> pers = new ArrayList<>();
        if (haygrupo) {
            for (int i = 0; i < this.getGrupos().size(); i++) {
                for (int j = 0; j < this.getGrupos().get(i).getPersonajes().size(); j++) {
                    pers.add(this.getGrupos().get(i).getPersonajes().get(j));
                }
            }

        }
        for (int i = 0; i < this.getPersonajes().size(); i++) {
            pers.add(this.getPersonajes().get(i));
        }
        for (int i = 1; i < pers.size(); i++) {
            if (!pers.get(i).getCivilizacion().equals(pers.get(i - 1).getCivilizacion())) {
                throw new ExcepcionAccionRestringidaPersonaje("No puedes agrupar personajes de distintas civilizaciones!");
                
            }
        }
        int k = 1;
        String nombreGrupo = "grupo1";
        while (civilizacion.getGrupos().containsKey(nombreGrupo)) {
            nombreGrupo = nombreGrupo.replace("grupo" + k, "grupo" + (++k));
        }

        Grupo group = new Grupo(pers, this.getPosicion().inversa(), nombreGrupo, civilizacion);
        this.haygrupo = true;
        this.personajes.clear();
        this.grupos.clear();
        this.addGrupo(group);
        civilizacion.getGrupos().put(nombreGrupo, group);
        civilizacion.getMapa().imprimirCabecera();
        civilizacion.getMapa().imprimir(civilizacion);
        Juego.CONSOLA.imprimir("Se ha creado el " + group.getNombre() + " con los personajes: ");
        for (Personaje person : group.getPersonajes()) {
            Juego.CONSOLA.imprimir(person.getNombre());
        }
    }

    public boolean esCeldaLibre(boolean personaje) {
        if (getContenedorRec().getRecurso() == null && getEdificio() == null) {
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
            if ((this.personajes.size() == 1 && !this.haygrupo) && this.edificio == null) {
                if (this.personajes.get(0) instanceof Paisano) {
                    return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P ";
                } else {
                    return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " S ";
                }
            } else if ((this.personajes.size() > 1 && !this.haygrupo) && this.edificio == null) {
                return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P*";
            } else if (this.edificio != null) {
                if (this.edificio instanceof Ciudadela) {
                    return Mapa.ANSI_PURPLE_BACKGROUND + " U*";

                } else if (this.edificio instanceof Casa) {
                    return Mapa.ANSI_PURPLE_BACKGROUND + " K*";

                } else if (this.edificio instanceof Cuartel) {
                    return Mapa.ANSI_PURPLE_BACKGROUND + " Z*";
                }

            }
        }
        if (this.haygrupo && this.edificio == null) {
            if (this.grupos.size() == 1) {
                return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " G ";
            } else if (((this.getPersonajes().size() >= 1 && this.grupos.size() >= 1) || this.grupos.size() > 1) && this.edificio == null) {
                return Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " G*";
            }
        }
        if (this.haygrupo && this.edificio != null) {
            if (this.edificio instanceof Ciudadela) {
                return Mapa.ANSI_PURPLE_BACKGROUND + " U*";

            } else if (this.edificio instanceof Casa) {
                return Mapa.ANSI_PURPLE_BACKGROUND + " K*";

            } else if (this.edificio instanceof Cuartel) {
                return Mapa.ANSI_PURPLE_BACKGROUND + " Z*";
            }
        }

        if (this.contenedor.getRecurso() == null) {
            if (this.edificio == null) {
                return Mapa.ANSI_GREEN_BACKGROUND + "   ";
            } else if (this.edificio instanceof Ciudadela) {
                return Mapa.ANSI_PURPLE_BACKGROUND + " U*";

            } else if (this.edificio instanceof Casa) {
                return Mapa.ANSI_PURPLE_BACKGROUND + " K*";

            } else if (this.edificio instanceof Cuartel) {
                return Mapa.ANSI_PURPLE_BACKGROUND + " Z*";
            }
        }
        if (contenedor instanceof Bosque) {
            return Mapa.ANSI_CYAN_BACKGROUND + " B ";
        } else if (contenedor instanceof Cantera) {
            return Mapa.ANSI_BLUE_BACKGROUND + Mapa.ANSI_WHITE + " C ";
        } else if (contenedor instanceof Arbusto) {
            return Mapa.ANSI_YELLOW_BACKGROUND + " A ";
        } else {
            return " ";
        }
    }

}
