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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author mprad
 */
public class Mapa {

    private ArrayList<ArrayList<Celda>> mapa;
    private int filas;
    private int columnas;
    private HashMap<String, Personaje> personajes;
    private HashMap<String, Edificio> edificios;
    private HashMap<String, ContenedorRecurso> recursosVisibles;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN

    public Mapa(int filas, int columnas) {
        if (filas > 0 && columnas > 0) {
            mapa = new ArrayList<>();
            edificios = new HashMap<>();
            personajes = new HashMap<>();
            recursosVisibles = new HashMap<>();

            for (int i = 0; i < filas; i++) {
                ArrayList<Celda> b = new ArrayList<>();
                for (int j = 0; j < columnas; j++) {
                    b.add(j, new Celda(i, j, true));
                }
                mapa.add(i, b);
            }
            this.filas = filas;
            this.columnas = columnas;
        } else {
            System.out.println("Error construyendo Mapa.");
        }
    }

    public Mapa(int filas, boolean inicializar) { //Inicializo mapa cuadrado con elementos
        this(filas, filas);
        int columnas = filas;
        if (inicializar) {
            for (int i = 0; i < mapa.size(); i++) { //numero de filas, i recorriendo filas,i=y
                for (int j = 0; j < mapa.get(0).size(); j++) { //columnas, j j=x
                    if (i % 2 == 0 && j % 2 == 0) {
                        if (j > 1 && i % 4 == 0) {
                            if (getCelda(i, j - 2).getContenedorRec().getTipo() == ContenedorRecurso.PRADERA) {
                                this.makeBloqueRec(i, j);
                            }
                        } else if (j % 4 == 0 && i % 4 == 2) {
                            this.makeBloqueRec(i, j);
                        }
                    }
                }
            }

            this.makeAdyPrad((mapa.size() - 1) / 2, (mapa.size() - 1) / 2);
            Posicion posCiudadela = new Posicion((mapa.size() - 1) / 2, (mapa.size() - 1) / 2);
            String nombre = "Ciudadela-1";
            Edificio ciud = new Edificio(Edificio.CIUDADELA, posCiudadela, nombre);
            this.getCelda(posCiudadela).setEdificio(ciud);
//            int i=1;
//            while(!edificios.containsKey(nombre)){
//                nombre = nombre.replace("-"+i, "-"+(++i));
//            }
            edificios.put(nombre, ciud);
            Posicion posPaisano = edificios.get("Ciudadela-1").getPosicion().PosicionAdyacenteLibre(this);
            Personaje paisano1 = new Personaje(Personaje.PAISANO, posPaisano, "paisano1");
            personajes.put("paisano1", paisano1);
            this.getCelda(posPaisano).addPersonaje(paisano1);
            this.makeAdyVisible(posPaisano);
        }
    }

    public void listarPersonajes() {
        Set<Map.Entry<String, Personaje>> pers = personajes.entrySet();
        for (Map.Entry<String, Personaje> entry : pers) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().getPosicion());
        }
    }
    public boolean perteneceAMapa(Posicion posicion){
        return posicion.getX() < columnas && posicion.getY() < filas && posicion.getX() > -1 && posicion.getY() > -1;
    }

    public void listarEdificios() {
        Set<Map.Entry<String, Edificio>> pers = edificios.entrySet();
        for (Map.Entry<String, Edificio> entry : pers) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().getPosicion());

        }
    }

    public void makeAdyVisible(Posicion posicion) {
        int i = posicion.getX(), j = posicion.getY();
        for (int h = i - 1; h < i + 2; h++) {
            for (int k = j - 1; k < j + 2; k++) {
                Celda c = this.getCelda(h, k);
                if (c != null && c.isOculto() && (h == i || j == k || (c.getEdificio() != null && c.getEdificio().getTipo() == Edificio.CIUDADELA))) {
                    c.setOculto(false);
                    if (c.getContenedorRec().getTipo() != ContenedorRecurso.PRADERA) {
                        c.getContenedorRec().setNombre(c.getContenedorRec() + Integer.toString(c.getContenedorRec().getContador()));
                        recursosVisibles.put(c.getContenedorRec().getNombre(), c.getContenedorRec());
                    }
                }
            }
        }
    }

    private void makeAdyPrad(int i, int j) { //Hacer todas las celdas asyacentes pradera
        for (int h = i - 1; h < i + 2; h++) {
            for (int k = j - 1; k < j + 2; k++) {
                this.getCelda(h, k).getContenedorRec().set(ContenedorRecurso.PRADERA, 0);
            }
        }
    }

    private void makeBloqueRec(int i, int j) {//Hacer un bloque de 4 celdas de recursos a partir de la celda dada
        ArrayList<Integer> bloque = new ArrayList<>(4);
        for (int k = 0; k < 4; k++) {
            bloque.add(k);
        }
        Collections.shuffle(bloque); //Cada int es un recurso, aleatorizo orden
        Random rt = new Random();
        int[] cantidad = new int[]{rt.nextInt(100) + 1, rt.nextInt(100) + 1, rt.nextInt(100) + 1, rt.nextInt(100) + 1};
        Posicion posicion = new Posicion(i, j);
        getCelda(posicion).getContenedorRec().set(bloque.get(0), cantidad[0]);
        getCelda(posicion.get(Posicion.ESTE)).getContenedorRec().set(bloque.get(1), cantidad[1]);
        getCelda(posicion.get(Posicion.SUR)).getContenedorRec().set(bloque.get(2), cantidad[2]);
        getCelda(posicion.get(Posicion.SURESTE)).getContenedorRec().set(bloque.get(3), cantidad[3]);
    }

    public Mapa() {
        this(10, true);
    }

    public Celda getCelda(int x, int y) {
        if (x < columnas && y < filas && x > -1 && y > -1) {
            return mapa.get(y).get(x);
        }
        return null;
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
    public int contarEdificios(int tipo){
        int n=0;
        if(tipo>0 && tipo<4){
        Collection<Edificio> edifs= this.getEdificios().values();
        for(Edificio ed: edifs){
           if(ed.getTipo()==tipo){
               n++;
           }
        }
        return n;
        }else{
            System.out.println("Error: tipo incorrecto.");
        }
        return -1;
    }

    public Celda getCelda(Posicion posicion) {
        if (posicion.getX() < columnas && posicion.getY() < filas && posicion.getX() > -1 && posicion.getY() > -1) {
            return mapa.get(posicion.getY()).get(posicion.getX());
        }
        return null;
    }

    public void imprimir() {
        System.out.print("\r  │");
        for (int i = 0; i < columnas; i++) {
            System.out.print("C" + i + " │");
        }
        System.out.println();
        //Primera linea: numeracion de columna

        for (int i = 0; i < filas; i++) {

            System.out.print("  ");
            for (int j = 0; j < columnas; j++) {
                System.out.print("───");
            }
            System.out.println();
            //Linea de separacion entre filas

            System.out.print("F" + i); //Numeracion de fila
            boolean flagrec = false;
            for (int j = 0; j < columnas; j++) {
                System.out.print(ANSI_RESET + "│" + mapa.get(i).get(j).toString());
                if (mapa.get(i).get(j).getContenedorRec().getTipo() != ContenedorRecurso.PRADERA) {
                    flagrec = true;
                }
            }
            System.out.print(ANSI_RESET + "│");//Ultimo separador de fila
            if (flagrec) {
                for (int j = 0; j < columnas; j++) {
                    if (mapa.get(i).get(j).getContenedorRec().getTipo() != ContenedorRecurso.PRADERA && !mapa.get(i).get(j).isOculto()) {
                        System.out.print(mapa.get(i).get(j).getContenedorRec().getNombre() + " ");
                    }
                }
            }
            System.out.println();
            //Linea de mapa
        }
        System.out.println();
    }
}
