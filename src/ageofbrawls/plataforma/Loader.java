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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author mprad
 */
public class Loader {

    Mapa mapa;

    public Loader(Mapa mapa, String dir) throws FileNotFoundException {
        this.mapa = mapa;
        String[] aLeer = new String[]{"mapa", "personajes", "edificios"};
        File files[] = new File[3];
        for (int i = 0; i < 3; i++) {
            files[i] = new File(dir + File.separator + aLeer[i] + ".csv");
            if (!files[i].exists()) {
                throw new FileNotFoundException("No directorio especificado non están todos os arquivos");
            }
        }
        cargarMapa(files[0]);
        cargarPersonajes(files[1]);
        cargarEdificios(files[2]);
    }
    public Loader (Mapa mapa, String dir, boolean save) throws FileNotFoundException{
        this.mapa = mapa;
        String[] aLeer = new String[]{"mapa", "personajes", "edificios"};
        File files[] = new File[3];
        for (int i = 0; i < 3; i++) {
            files[i] = new File(dir + File.separator + aLeer[i] + ".csv");
            if (!files[i].exists() || !files[i].canWrite()) {
                throw new FileNotFoundException("No directorio especificado non están todos os arquivos");
            }
        }
        for(Celda celda : mapa.getCeldas()){
            
        }
    }

    private void cargarMapa(File file) {
        ArrayList<String[]> datos = leer(file);
        for (String[] linea : datos) {
            Posicion pos = new Posicion("(" + linea[0] + ")");
            if (linea.length == 2) {
                if ("Pradera".equals(linea[1])) {
                    mapa.getCelda(pos).setTipoCont(Celda.PRADERA);
                }
            } else if (linea.length == 5) {
                switch (linea[1]) {
                    case "Bosque":
                        crearRecurso(pos, ContenedorRecurso.BOSQUE, linea[2], Integer.parseInt(linea[4]));
                        break;
                    case "Arbusto":
                        crearRecurso(pos, ContenedorRecurso.ARBUSTO, linea[2], Integer.parseInt(linea[4]));
                        break;
                    case "Cantera":
                        crearRecurso(pos, ContenedorRecurso.CANTERA, linea[2], Integer.parseInt(linea[4]));
                        break;

                }
            }

        }
    }

    private void cargarPersonajes(File file) {
        ArrayList<String[]> datos = leer(file);
        for (String[] linea : datos) {
            Posicion pos = new Posicion("(" + linea[0] + ")");
            if (linea.length == 10) {
                switch (linea[1]) {
                    case "Paisano":
                        crearPersonaje(pos, Personaje.PAISANO, linea[3], linea[9], Integer.parseInt(linea[4]), Integer.parseInt(linea[5]), Integer.parseInt(linea[6]), Integer.parseInt(linea[7]), linea[8]);
                        break;
                    case "Soldado":
                        crearPersonaje(pos, Personaje.SOLDADO, linea[3], linea[9], Integer.parseInt(linea[4]), Integer.parseInt(linea[5]), Integer.parseInt(linea[6]), Integer.parseInt(linea[7]), linea[8]);
                        break;
                }
            }

        }
    }

    private void cargarEdificios(File file) {
        ArrayList<String[]> datos = leer(file);
        for (String[] linea : datos) {
            Posicion pos = new Posicion("(" + linea[0] + ")");
            if (linea.length == 5) {
                if(mapa.getCelda(pos).getEdificio()!=null){
                    System.out.println("Sobreescribiendo edificio: "+mapa.getCelda(pos).getEdificio().getNombre());
                }
                switch (linea[1]) {
                    case "Casa":
                        crearEdificio(pos, Edificio.CASA, linea[2], linea[4]);
                        break;
                    case "Cuartel":
                        crearEdificio(pos, Edificio.CUARTEL, linea[2], linea[4]);
                        break;
                }
            }

        }
    }
    
    private void crearEdificio(Posicion pos, int tipo, String nombre, String civilizacion) {
        if (!mapa.getCivilizaciones().containsKey(civilizacion)) {
            mapa.addCivilizacion(civilizacion, new Civilizacion(mapa, civilizacion));
        }
        Civilizacion current = mapa.getCivilizaciones().get(civilizacion);
        Edificio edificio = new Edificio(tipo,pos,nombre,current);
        mapa.getCelda(pos).setEdificio(edificio);
        current.getEdificios().put(nombre, edificio);
    }

    private void crearPersonaje(Posicion pos, int tipo, String nombre, String civilizacion, int ataque, int defensa, int salud, int capacidad,  String grupo) {
        if (!mapa.getCivilizaciones().containsKey(civilizacion)) {
            mapa.addCivilizacion(civilizacion, new Civilizacion(mapa, civilizacion));
        }
        Civilizacion current = mapa.getCivilizaciones().get(civilizacion);
        Personaje personaje = new Personaje(tipo, pos, nombre, current, ataque, defensa, capacidad, salud);
        if (grupo == null || "".equals(grupo)) {
            mapa.getCelda(pos).addPersonaje(personaje);
        } else {
            Grupo group;
            if (current.getGrupos().containsKey(grupo)) {
                group = current.getGrupos().get(grupo);
                group.getPersonajes().add(personaje);
            } else {
                group = new Grupo(new ArrayList<>(Arrays.asList(personaje)), pos, grupo, current);
                mapa.getCelda(pos).addGrupo(group);
                current.getGrupos().put(grupo, group);
            }
            
        }
        current.getPersonajes().put(nombre, personaje);
    }

    private void crearRecurso(Posicion pos, int tipo, String nombre, int cantidad) {
        mapa.getCelda(pos).setTipoCont(tipo, cantidad);
        mapa.getCelda(pos).getContenedorRec().setNombre(nombre);
    }

    private ArrayList<String[]> leer(File file) {
        ArrayList<String[]> lineas = new ArrayList<>();
        try {
            Scanner lector = new Scanner(file);
            String linea;
            while (lector.hasNext()) {
                linea = lector.nextLine();
                if (linea.charAt(0) != '#') {
                    lineas.add(linea.split(";"));
                }
            }
        } catch (FileNotFoundException excep) {
            System.out.println("ERROR --> Cargar ficheiro");
        }
        return lineas;
    }
}
