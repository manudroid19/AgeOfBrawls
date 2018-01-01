package ageofbrawls.plataforma;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.contenido.Personajes.Grupo;
import ageofbrawls.contenido.Personajes.Personaje;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mprad
 */
public class Loader {

    Mapa mapa;

    public Loader(Mapa mapa, String dir) throws FileNotFoundException {
        this.mapa = mapa;
        this.mapa.clear();
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

    public Loader(Mapa mapa, String dir, boolean save) throws FileNotFoundException {
        try {
            this.mapa = mapa;
            String[] aLeer = new String[]{"mapa", "personajes", "edificios"};
            File files[] = new File[3];
            for (int i = 0; i < 3; i++) {
                files[i] = new File(dir + File.separator + aLeer[i] + ".csv");
                files[i].createNewFile();
                if (!files[i].canWrite()) {
                    throw new FileNotFoundException("No se puede guardar " + files[i].getPath());
                }
            }
            ArrayList<String> lineas = new ArrayList<>(), edificios = new ArrayList<>();
            for (Celda celda : mapa.getCeldas()) {
                String a;
                if (celda.getContenedorRec() == null) {
                    a = "";
                } else {
                    a = celda.getContenedorRec().getNombre() + ";" + celda.getContenedorRec().getCantidad();
                }
                lineas.add(celda.getPosicion().getX() + "," + celda.getPosicion().getY() + ";" + celda.leerTipoCont() + ";" + a);
            }
            Files.write(files[0].toPath(), lineas);
            lineas.clear();
            for (Civilizacion civ : mapa.getCivilizaciones().values()) {
                for (Personaje p : civ.getPersonajes().values()) {
                    lineas.add(p.getPosicion().getY() + "," + p.getPosicion().getX() + ";" + p.leerTipo() + ";" + p.getNombre() + ";" + p.getAtaque() + ";" + p.getDefensa() + ";" + p.getSalud() + ";" + p.getCapRec() + ";" + p.leerGrupo() + ";" + p.getCivilizacion().getNombre());
                }
                for (Edificio e : civ.getEdificios().values()) {
                    edificios.add(e.getPosicion().getY() + "," + e.getPosicion().getX() + ";" + e.leerTipo() + ";" + e.getNombre() + ";" + e.getCivilizacion().getNombre());
                }
            }
            Files.write(files[1].toPath(), lineas);
            Files.write(files[2].toPath(), edificios);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarMapa(File file) {
        ArrayList<String[]> datos = leer(file);
        for (String[] linea : datos) {
            Posicion pos = new Posicion("(" + linea[0] + ")");
            if (linea.length == 2) {
                if ("pradera".toLowerCase().equals(linea[1])) {
                    mapa.getCelda(pos).setTipoCont(Celda.PRADERA);
                }
            } else {
                switch (linea[1].toLowerCase()) {
                    case "bosque":
                        crearRecurso(pos, ContenedorRecurso.BOSQUE, linea[2], Integer.parseInt(linea[3]));
                        break;
                    case "arbusto":
                        crearRecurso(pos, ContenedorRecurso.ARBUSTO, linea[2], Integer.parseInt(linea[3]));
                        break;
                    case "cantera":
                        crearRecurso(pos, ContenedorRecurso.CANTERA, linea[2], Integer.parseInt(linea[3]));
                        break;
                }
            }
        }
    }

    private void cargarPersonajes(File file) {
        ArrayList<String[]> datos = leer(file);
        for (String[] linea : datos) {
            Posicion pos = new Posicion("(" + linea[0] + ")");

            switch (linea[1].toLowerCase()) {
                case "paisano":
                    crearPersonaje(pos, Personaje.PAISANO, linea[2], Integer.parseInt(linea[3]), Integer.parseInt(linea[4]), Integer.parseInt(linea[5]), Integer.parseInt(linea[6]), linea[7], linea[8]);
                    break;
                case "soldado":
                    crearPersonaje(pos, Personaje.SOLDADO, linea[2], Integer.parseInt(linea[3]), Integer.parseInt(linea[4]), Integer.parseInt(linea[5]), Integer.parseInt(linea[6]), linea[7], linea[8]);
                    break;
            }
            
        }
    }

    private void cargarEdificios(File file) {
        ArrayList<String[]> datos = leer(file);
        for (String[] linea : datos) {
            Posicion pos = new Posicion("(" + linea[0] + ")");
            if (linea.length == 4) {
                if (mapa.getCelda(pos).getEdificio() != null) {
                    System.out.println("Sobreescribiendo edificio: " + mapa.getCelda(pos).getEdificio().getNombre());
                }
                switch (linea[1].toLowerCase()) {
                    case "casa":
                        crearEdificio(pos, Edificio.CASA, linea[2], linea[3]);
                        break;
                    case "cuartel":
                        crearEdificio(pos, Edificio.CUARTEL, linea[2], linea[3]);
                        break;
                    case "ciudadela":
                        crearEdificio(pos, Edificio.CIUDADELA, linea[2], linea[3]);
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
        Edificio edificio = new Edificio(tipo, pos, nombre, current);
        mapa.getCelda(pos).setEdificio(edificio);
        current.getEdificios().put(nombre, edificio);
        current.makeAdyVisible(pos);
        if(edificio.getTipo()==Edificio.CIUDADELA){
            current.anadirCiudadela();
        }
    }

    private void crearPersonaje(Posicion pos, int tipo, String nombre, int ataque, int defensa, int salud, int capacidad, String grupo, String civilizacion) {
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
            personaje.setGrupo(group);
        }
        current.getPersonajes().put(nombre, personaje);
        current.makeAdyVisible(pos);
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
