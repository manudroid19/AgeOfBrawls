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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mprad
 */
public class Civilizacion {

    private HashMap<String, Personaje> personajes;
    private HashMap<String, Edificio> edificios;
    private HashMap<String, ContenedorRecurso> recursosVisibles;
    private HashMap<String, Grupo> grupos;
    private ArrayList<ArrayList<Boolean>> oculto;
    private String nombre;
    private Mapa mapa;

    public Civilizacion(Mapa mapa, String nombre, Posicion posCiudadela) {
        edificios = new HashMap<>();
        personajes = new HashMap<>();
        recursosVisibles = new HashMap<>();
        grupos=new HashMap<>();
        oculto = new ArrayList<>();
        for(int i=0;i<mapa.getFilas();i++){
            ArrayList<Boolean> fila = new ArrayList<>();
            for(int j=0;j<mapa.getColumnas();j++){
                fila.add(true);
            }
            oculto.add(fila);
            
        }
        this.nombre = nombre;
        this.mapa=mapa;
        mapa.makeAdyPrad(posCiudadela);
        String nomCiud = "ciudadela1";
        Edificio ciud = new Edificio(Edificio.CIUDADELA, posCiudadela, nomCiud);
        mapa.getCelda(posCiudadela).setEdificio(ciud);
        edificios.put(nomCiud, ciud);
        Posicion posPaisano = edificios.get("ciudadela1").getPosicion().posicionAdyacenteLibre(mapa);
        Personaje paisano1 = new Personaje(Personaje.PAISANO, posPaisano, "paisano1",this);
        personajes.put(paisano1.getNombre(), paisano1);
        mapa.getCelda(posPaisano).addPersonaje(paisano1);
        this.makeAdyVisible(posPaisano);
    }

    public HashMap<String, Personaje> getPersonajes() {
        return personajes;
    }

    public HashMap<String, Edificio> getEdificios() {
        return edificios;
    }

    public Mapa getMapa() {
        return mapa;
    }
    public String getNombre(){
        return nombre;
    }

    public HashMap<String, ContenedorRecurso> getContenedoresRecurso() {
        return recursosVisibles;
    }
    
    public HashMap<String, Grupo> getGrupo() {
        return grupos;
    }
    
    public boolean isOculto(Posicion posicion){
        if(posicion==null){
            return false;
        }
        return oculto.get(posicion.getY()).get(posicion.getX());
    }
    
    public void setOculto(Posicion pos, boolean oculto){
        ArrayList fila = this.oculto.get(pos.getY());
        fila.set(pos.getX(), oculto);
        this.oculto.set(pos.getY(), fila);
    }

    public void listarPersonajes() {
        Set<Map.Entry<String, Personaje>> pers = personajes.entrySet();
        for (Map.Entry<String, Personaje> entry : pers) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().getPosicion());
        }
    }

    public void listarEdificios() {
        Set<Map.Entry<String, Edificio>> edif = edificios.entrySet();
        for (Map.Entry<String, Edificio> entry : edif) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().getPosicion());

        }
    }
    
    public void listarGrupos() {
        Set<Map.Entry<String, Grupo>> group = grupos.entrySet();
        for (Map.Entry<String, Grupo> entry : group) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().getPosicion());

        }
    }
    public void makeAdyVisible(Posicion posicion) {
        if (posicion == null) {
            return;
        }
        int i = posicion.getX(), j = posicion.getY();
        for (int h = i - 1; h < i + 2; h++) {
            for (int k = j - 1; k < j + 2; k++) {
                Celda c = mapa.getCelda(h, k);
                if (c != null && c.isOculto(this) && (h == i || j == k || (c.getEdificio() != null && c.getEdificio().getTipo() == Edificio.CIUDADELA))) {
                    c.setOculto(this,false);
                    if (c.getContenedorRec() != null) {
                        c.getContenedorRec().setNombre(c.getContenedorRec() + Integer.toString(c.getContenedorRec().getContador()));
                        recursosVisibles.put(c.getContenedorRec().getNombre(), c.getContenedorRec());
                    }
                }
            }
        }
    }

    public int contarEdificios(int tipo) {
        int n = 0;
        if (tipo > 0 && tipo < 4) {
            Collection<Edificio> edifs = this.getEdificios().values();
            for (Edificio ed : edifs) {
                if (ed.getTipo() == tipo) {
                    n++;
                }
            }
            return n;
        } else {
            System.out.println("Error: tipo incorrecto.");
        }
        return -1;
    }

}
