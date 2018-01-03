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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author mprad
 */
public class Civilizacion {

    private HashMap<String, Personaje> personajes;
    private HashMap<String, Edificio> edificios;
    private HashMap<String, Contenedor> recursosVisibles;
    private HashMap<String, Grupo> grupos;
    private ArrayList<ArrayList<Boolean>> oculto;
    private String nombre;
    private Mapa mapa;
    private int bosques = 1, arbustos = 1, canteras = 1; //contadores
    private int madera, piedra, alimentos;
    private int capAlmacen, contCiudadelas=0;
    public final static int CAPALMACEN = 3000;

    public Civilizacion(Mapa mapa, String nombre, Posicion posCiudadela) {
        edificios = new HashMap<>();
        personajes = new HashMap<>();
        recursosVisibles = new HashMap<>();
        grupos = new HashMap<>();
        oculto = new ArrayList<>();
        this.capAlmacen = CAPALMACEN;
        madera = 500;
        piedra = 500;
        alimentos = 500;
        for (int i = 0; i < mapa.getFilas(); i++) {
            ArrayList<Boolean> fila = new ArrayList<>();
            for (int j = 0; j < mapa.getColumnas(); j++) {
                fila.add(true);
            }
            oculto.add(fila);

        }
        this.nombre = nombre;
        this.mapa = mapa;
        if (posCiudadela != null) {
            mapa.makeAdyPrad(posCiudadela);
            String nomCiud = "ciudadela1";
            Edificio ciud = new Edificio(Edificio.CIUDADELA, posCiudadela, nomCiud, this);
            anadirCiudadela();
            mapa.getCelda(posCiudadela).setEdificio(ciud);
            edificios.put(nomCiud, ciud);
            Posicion posPaisano = edificios.get("ciudadela1").getPosicion().posicionAdyacenteLibre(mapa);
            Personaje paisano1 = new Paisano(posPaisano, "paisano1", this);
            personajes.put(paisano1.getNombre(), paisano1);
            mapa.getCelda(posPaisano).addPersonaje(paisano1);
            this.makeAdyVisible(posPaisano);
        }
    }

    public Civilizacion(Mapa mapa, String nombre) {
        this(mapa, nombre, null);
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
    public int getCapAlmacen() {
        return capAlmacen;
    }
    public int getMadera() {
        return this.madera;
    }

    public int getPiedra() {
        return this.piedra;
    }

    public int getAlimentos() {
        return this.alimentos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getContador(int tipo) {
        switch (tipo) {
            case Contenedor.BOSQUE:
                return bosques++;
            case Contenedor.CANTERA:
                return canteras++;
            case Contenedor.ARBUSTO:
                return arbustos++;
            default:
                return -1;
        }
    }

    public HashMap<String, Contenedor> getContenedoresRecurso() {
        return recursosVisibles;
    }

    public HashMap<String, Grupo> getGrupos() {
        return grupos;
    }

    public boolean isOculto(Posicion posicion) {
        if (posicion == null) {
            return false;
        }
        return oculto.get(posicion.getY()).get(posicion.getX());
    }
public void setPiedra(int cant, boolean relative) {
        if (relative) {
            if (piedra + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            piedra += cant;
        } else {
            if (piedra + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            piedra = cant;
        }
    }
public void setAlimentos(int cant, boolean relative) {
        if (relative) {
            if (alimentos + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            alimentos += cant;
        } else {
            if (alimentos + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            alimentos = cant;
        }
    }

    public void setMadera(int cant, boolean relative) {
        if (relative) {
            if (madera + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            madera += cant;
        } else {
            if (madera + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            madera = cant;
        }
    }
    public void setOculto(Posicion pos, boolean oculto) {
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
    public boolean puedeAlmacenar(int cantidad){
        return (madera+piedra+alimentos+cantidad)<=capAlmacen;
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
                    c.setOculto(this, false);
                    if (c.getContenedorRec() != null) {
                        if (c.getContenedorRec().getNombre() == null || "".equals(c.getContenedorRec().getNombre())) {
                            int n = getContador(c.getContenedorRec().getTipo());
                            while (recursosVisibles.containsKey(c.getContenedorRec().toString() + n)) {
                                n = getContador(c.getContenedorRec().getTipo());
                            }
                            c.getContenedorRec().setNombre(c.getContenedorRec().toString() + n);
                        }
                        recursosVisibles.put(c.getContenedorRec().getNombre(), c.getContenedorRec());
                    }
                }
            }
        }
    }
    public void anadirCiudadela(){
        contCiudadelas++;
    }
    public void quitarCiudadela(){
        contCiudadelas--;
        if(contCiudadelas==0){
            System.out.println("La última ciudadela de "+nombre+ " ha sido destruida. Game Over.");
            System.exit(0);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Civilizacion other = (Civilizacion) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

}
