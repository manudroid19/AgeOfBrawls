/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.contenido.Personaje;
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
    private String nombre;
    private Mapa mapa;

    public Civilizacion(Mapa mapa, String nombre) {
        edificios = new HashMap<>();
        personajes = new HashMap<>();
        recursosVisibles = new HashMap<>();
        Posicion posCiudadela = new Posicion((mapa.getFilas() - 1) / 2, (mapa.getFilas() - 1) / 2);
        mapa.makeAdyPrad(posCiudadela);
        String nomCiud = "ciudadela1";
        Edificio ciud = new Edificio(Edificio.CIUDADELA, posCiudadela, nomCiud);
        mapa.getCelda(posCiudadela).setEdificio(ciud);
        edificios.put(nomCiud, ciud);
        Posicion posPaisano = edificios.get("ciudadela1").getPosicion().posicionAdyacenteLibre(mapa);
        Personaje paisano1 = new Personaje(Personaje.PAISANO, posPaisano, "paisano1");
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

    public HashMap<String, ContenedorRecurso> getContenedoresRecurso() {
        return recursosVisibles;
    }
    public void listarPersonajes() {
        Set<Map.Entry<String, Personaje>> pers = personajes.entrySet();
        for (Map.Entry<String, Personaje> entry : pers) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().getPosicion());
        }
    }

    public void listarEdificios() {
        Set<Map.Entry<String, Edificio>> pers = edificios.entrySet();
        for (Map.Entry<String, Edificio> entry : pers) {
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
                if (c != null && c.isOculto() && (h == i || j == k || (c.getEdificio() != null && c.getEdificio().getTipo() == Edificio.CIUDADELA))) {
                    c.setOculto(false);
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
