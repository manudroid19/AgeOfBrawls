package ageofbrawls.plataforma;

import ageofbrawls.contenido.contenedor.Contenedor;
import ageofbrawls.contenido.edificio.Edificio;
import ageofbrawls.contenido.Personajes.Grupo;
import ageofbrawls.contenido.Personajes.Paisano;
import ageofbrawls.contenido.Personajes.Personaje;
import ageofbrawls.contenido.Personajes.Soldado;
import ageofbrawls.contenido.Personajes.Soldados.Legionario;
import ageofbrawls.contenido.Recursos.Comida;
import ageofbrawls.contenido.Recursos.Madera;
import ageofbrawls.contenido.Recursos.Piedra;
import ageofbrawls.contenido.Recursos.Recurso;
import ageofbrawls.contenido.contenedor.Arbusto;
import ageofbrawls.contenido.contenedor.Bosque;
import ageofbrawls.contenido.contenedor.Cantera;
import ageofbrawls.contenido.edificio.Casa;
import ageofbrawls.contenido.edificio.Ciudadela;
import ageofbrawls.contenido.edificio.Cuartel;
import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaPersonaje;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentosInternos;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionDireccionNoValida;
import ageofbrawls.z.excepciones.Recursos.ExcepcionCorrespondenciaRecursos;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteArchivo;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteCivilizacion;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteMapa;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExistePosicion;
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
public class CargadorArchivo implements CargadorJuego{

    Mapa mapa;
    String dir;

    public CargadorArchivo(String dir) {
        this.dir = dir;
    }

    @Override
    public Juego cargarJuego() throws ExcepcionNoExisteArchivo, ExcepcionArgumentosInternos, ExcepcionCorrespondenciaRecursos, ExcepcionAccionRestringidaPersonaje, ExcepcionDireccionNoValida, ExcepcionNoExistePosicion, ExcepcionNoExisteMapa, ExcepcionNoExisteCivilizacion {
        Juego juego = new Juego();
        this.mapa = juego.getMapa();
        this.mapa.clear();
        String[] aLeer = new String[]{"mapa", "personajes", "edificios"};
        File files[] = new File[3];
        for (int i = 0; i < 3; i++) {
            files[i] = new File(dir + File.separator + aLeer[i] + ".csv");
            if (!files[i].exists()) {
                throw new ExcepcionNoExisteArchivo("En el directorio especificado no están todos los archivos");
            }
        }
        cargarMapa(files[0]);
        cargarPersonajes(files[1]);
        cargarEdificios(files[2]);
        return juego;
    }

    public CargadorArchivo(Mapa mapa, String dir, boolean save) throws ExcepcionNoExisteArchivo {
        try {
            this.mapa = mapa;
            String[] aLeer = new String[]{"mapa", "personajes", "edificios"};
            File files[] = new File[3];
            for (int i = 0; i < 3; i++) {
                files[i] = new File(dir + File.separator + aLeer[i] + ".csv");
                files[i].createNewFile();
                if (!files[i].canWrite()) {
                    throw new ExcepcionNoExisteArchivo("No se puede guardar " + files[i].getPath());
                }
            }
            ArrayList<String> lineas = new ArrayList<>(), edificios = new ArrayList<>();
            for (Celda celda : mapa.getCeldas()) {
                String a;
                if (celda.getContenedorRec().getRecurso() == null) {
                    a = "";
                } else {
                    a = celda.getContenedorRec().getNombre() + ";" + celda.getContenedorRec().getRecurso().getCantidad();
                }
                lineas.add(celda.getPosicion().getX() + "," + celda.getPosicion().getY() + ";" + celda.leerTipoCont() + ";" + a);
            }
            Files.write(files[0].toPath(), lineas);
            lineas.clear();
            for (Civilizacion civ : mapa.getCivilizaciones().values()) {
                for (Personaje p : civ.getPersonajes().values()) {
                    lineas.add(p.getPosicion().getY() + "," + p.getPosicion().getX() + ";" + p.toString() + ";" + p.getNombre() + ";" + p.danhoAtaque() + ";" + p.getDefensa() + ";" + p.getSalud() + ";" + p.getCapRec() + ";" + p.leerGrupo() + ";" + p.getCivilizacion().getNombre());
                }
                for (Edificio e : civ.getEdificios().values()) {
                    edificios.add(e.getPosicion().getY() + "," + e.getPosicion().getX() + ";" + e.getNombre() + ";" + e.getCivilizacion().getNombre());
                }
            }
            Files.write(files[1].toPath(), lineas);
            Files.write(files[2].toPath(), edificios);
        } catch (IOException ex) {
            throw new ExcepcionNoExisteArchivo(ex.getMessage());
        }

    }

    private void cargarMapa(File file) throws ExcepcionArgumentosInternos, ExcepcionNoExisteArchivo, ExcepcionCorrespondenciaRecursos {
        ArrayList<String[]> datos = leer(file);
        for (String[] linea : datos) {
            Posicion pos = new Posicion("(" + linea[0] + ")");
            if (linea.length == 2) {
                if ("pradera".toLowerCase().equals(linea[1])) {
                    mapa.getCelda(pos).hacerPradera();
                }
            } else {
                switch (linea[1].toLowerCase()) {
                    case "bosque":
                        Recurso recurso = new Madera(Integer.parseInt(linea[3]));
                        crearRecurso(pos, 1, linea[2], recurso);
                        break;
                    case "arbusto":
                        Recurso recurso1 = new Comida(Integer.parseInt(linea[3]));
                        crearRecurso(pos, 2, linea[2], recurso1);
                        break;
                    case "cantera":
                        Recurso recurso2 = new Piedra(Integer.parseInt(linea[3]));
                        crearRecurso(pos, 3, linea[2], recurso2);
                        break;
                }
            }
        }
    }

    private void cargarPersonajes(File file) throws ExcepcionNoExisteArchivo, ExcepcionArgumentosInternos, ExcepcionAccionRestringidaPersonaje, ExcepcionNoExistePosicion, ExcepcionNoExisteMapa, ExcepcionNoExisteCivilizacion {
        ArrayList<String[]> datos = leer(file);
        for (String[] linea : datos) {
            Posicion pos = new Posicion("(" + linea[0] + ")");

            switch (linea[1].toLowerCase()) {
                case "paisano":
                    crearPersonaje(pos, 0, linea[2], Integer.parseInt(linea[3]), Integer.parseInt(linea[4]), Integer.parseInt(linea[5]), Integer.parseInt(linea[6]), linea[7], linea[8]);
                    break;
                case "soldado":
                    crearPersonaje(pos, 1, linea[2], Integer.parseInt(linea[3]), Integer.parseInt(linea[4]), Integer.parseInt(linea[5]), Integer.parseInt(linea[6]), linea[7], linea[8]);
                    break;
            }

        }
    }

    private void cargarEdificios(File file) throws ExcepcionArgumentosInternos, ExcepcionNoExisteArchivo, ExcepcionNoExistePosicion, ExcepcionNoExisteMapa, ExcepcionNoExisteCivilizacion {
        ArrayList<String[]> datos = leer(file);
        for (String[] linea : datos) {
            Posicion pos = new Posicion("(" + linea[0] + ")");
            if (linea.length == 4) {
                if (mapa.getCelda(pos).getEdificio() != null) {
                    System.out.println("Sobreescribiendo edificio: " + mapa.getCelda(pos).getEdificio().getNombre());
                }
                switch (linea[1].toLowerCase()) {
                    case "casa":
                        crearEdificio(pos, 3, linea[2], linea[3]);
                        break;
                    case "cuartel":
                        crearEdificio(pos, 2, linea[2], linea[3]);
                        break;
                    case "ciudadela":
                        crearEdificio(pos, 1, linea[2], linea[3]);
                        break;
                }
            }

        }
    }

    private void crearEdificio(Posicion pos, int tipo, String nombre, String civilizacion) throws ExcepcionArgumentosInternos, ExcepcionNoExistePosicion, ExcepcionNoExisteMapa, ExcepcionNoExisteCivilizacion {
        if (!mapa.getCivilizaciones().containsKey(civilizacion)) {
            mapa.addCivilizacion(civilizacion, new Civilizacion(mapa, civilizacion));
        }
        Civilizacion current = mapa.getCivilizaciones().get(civilizacion);
        Edificio edificio;
        if (tipo == 1) {
            Ciudadela ciud = new Ciudadela(pos, nombre, current);
            mapa.getCelda(pos).setEdificio(ciud);
            current.getEdificios().put(nombre, ciud);
            current.makeAdyVisible(pos);
            current.anadirCiudadela();

        }
        if (tipo == 2) {
            Cuartel cuart = new Cuartel(pos, nombre, current);
            mapa.getCelda(pos).setEdificio(cuart);
            current.getEdificios().put(nombre, cuart);
            current.makeAdyVisible(pos);

        }
        if (tipo == 3) {
            Casa casa = new Casa(pos, nombre, current);
            mapa.getCelda(pos).setEdificio(casa);
            current.getEdificios().put(nombre, casa);
            current.makeAdyVisible(pos);

        }

    }

    private void crearPersonaje(Posicion pos, int tipo, String nombre, int ataque, int defensa, int salud, int capacidad, String grupo, String civilizacion) throws ExcepcionArgumentosInternos, ExcepcionAccionRestringidaPersonaje, ExcepcionNoExistePosicion, ExcepcionNoExisteMapa, ExcepcionNoExisteCivilizacion {
        if (!mapa.getCivilizaciones().containsKey(civilizacion)) {
            mapa.addCivilizacion(civilizacion, new Civilizacion(mapa, civilizacion));
        }
        Civilizacion current = mapa.getCivilizaciones().get(civilizacion);
        Personaje personaje;
        if (tipo == 0) {
            personaje = new Paisano(pos, nombre, current);
            personaje.setSalud(salud, false);
            personaje.setDefensa(defensa);
            ((Paisano) personaje).setCapRec(capacidad);

        } else {
            personaje = new Legionario(pos, nombre, current);
            personaje.setSalud(salud, false);
            personaje.setDefensa(defensa);
            ((Soldado) personaje).setAtaque(ataque);
        }
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

    private void crearRecurso(Posicion pos, int tipo, String nombre, Recurso recurso) throws ExcepcionCorrespondenciaRecursos {
        if (tipo == 1) {
            Bosque bos = new Bosque(recurso, nombre);
        }
        if (tipo == 2) {
            Arbusto arb = new Arbusto(recurso, nombre);
        }
        if (tipo == 3) {
            Cantera cant = new Cantera(recurso, nombre);
        }
    }

    private ArrayList<String[]> leer(File file) throws ExcepcionNoExisteArchivo {
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
            throw new ExcepcionNoExisteArchivo("No se puede leer " + file.getName());
        }
        return lineas;
    }
}
