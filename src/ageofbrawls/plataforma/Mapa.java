/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author mprad
 */
public class Mapa {

    private ArrayList<ArrayList<Celda>> mapa; //Este atributo y los 2 siguientes no tienen getter puesto que, por definicion, solo los metodos de la clase los modifica.
    private int filas;
    private int columnas;
    private HashMap<String, Civilizacion> civilizaciones;
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
            civilizaciones = new HashMap<>();
            for (int i = 0; i < filas; i++) {
                ArrayList<Celda> b = new ArrayList<>();
                for (int j = 0; j < columnas; j++) {
                    b.add(j, new Celda(i, j));
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
        if (inicializar) {
            for (int i = 0; i < mapa.size(); i++) { //numero de filas, i recorriendo filas,i=y
                for (int j = 0; j < mapa.get(0).size(); j++) { //columnas, j j=x
                    if (i % 2 == 0 && j % 2 == 0) {
                        if (j > 1 && i % 4 == 0) {
                            if (getCelda(i, j - 2).getContenedorRec() == null) {
                                this.makeBloqueRec(i, j);
                            }
                        } else if (j % 4 == 0 && i % 4 == 2) {
                            this.makeBloqueRec(i, j);
                        }
                    }
                }
            }

        }
    }

    public Mapa() {
        this(10, true);
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public Celda getCelda(int x, int y) {
        if (x < columnas && y < filas && x > -1 && y > -1) {
            return mapa.get(y).get(x);
        }
        return null;
    }

    public Celda getCelda(Posicion posicion) {
        if (posicion.getX() < columnas && posicion.getY() < filas && posicion.getX() > -1 && posicion.getY() > -1) {
            return mapa.get(posicion.getY()).get(posicion.getX());
        }
        return null;
    }

    public HashMap<String, Civilizacion> getCivilizaciones() {
        return civilizaciones;
    }

    public boolean perteneceAMapa(Posicion posicion) {
        if (posicion == null) {
            return false;
        }
        return posicion.getX() < columnas && posicion.getY() < filas && posicion.getX() > -1 && posicion.getY() > -1;
    }

    public void addCivilizacion(String nombre, Civilizacion civilizacion) {
        if (civilizacion == null || civilizaciones.containsKey(nombre)) {
            System.out.println("Error añadiendo civilizacion");
            return;
        }
        civilizaciones.put(nombre, civilizacion);
    }

    void makeAdyPrad(Posicion posicion) { //Hacer todas las celdas asyacentes pradera
        if (posicion == null) {
            System.out.println("Error.");
            return;
        }
        int i = posicion.getX();
        int j = posicion.getY();
        for (int h = i - 1; h < i + 2; h++) {
            for (int k = j - 1; k < j + 2; k++) {
                if (this.getCelda(h, k) != null) {
                    this.getCelda(h, k).setTipoCont(Celda.PRADERA);
                }
            }
        }
    }
    public ArrayList<Celda> getCeldas(){
        ArrayList<Celda> celdas = new ArrayList<>();
        for(ArrayList<Celda> ar : mapa){
            for(Celda celda : ar){
                celdas.add(celda);
            }
        }
        return celdas;
    }
    public void listarCivilizaciones() {
        Set<String> civs = civilizaciones.keySet();
        for (String civ : civs) {
            System.out.println(civ);
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
        getCelda(posicion).setTipoCont(bloque.get(0), cantidad[0]);
        getCelda(posicion.getAdy(Posicion.ESTE)).setTipoCont(bloque.get(1), cantidad[1]);
        getCelda(posicion.getAdy(Posicion.SUR)).setTipoCont(bloque.get(2), cantidad[2]);
        getCelda(posicion.getAdy(Posicion.SURESTE)).setTipoCont(bloque.get(3), cantidad[3]);
    }

    public void imprimir(Civilizacion activa) {
        System.out.print("\r   │");
        for (int i = 0; i < columnas; i++) {
            System.out.print("C" + i + ((i > 9) ? "" : " ") + "│");
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

            System.out.print("F" + i + ((i > 9) ? "" : " ")); //Numeracion de fila
            boolean flagrec = false;
            for (int j = 0; j < columnas; j++) {
                String celda;
                if (activa.isOculto(mapa.get(i).get(j).getPosicion())) {
                    celda = " ? ";
                } else {
                    celda = mapa.get(i).get(j).toString();
                }
                System.out.print(ANSI_RESET + "│" + celda);
                if (mapa.get(i).get(j).getContenedorRec() != null) {
                    flagrec = true;
                }
            }
            System.out.print(ANSI_RESET + "│");//Ultimo separador de fila
            if (flagrec) {
                for (int j = 0; j < columnas; j++) {
                    if (mapa.get(i).get(j).getContenedorRec() != null && !mapa.get(i).get(j).isOculto(activa)) {
                        System.out.print(mapa.get(i).get(j).getContenedorRec().getNombre() + " ");
                    }
                }
            }
            System.out.println();
            //Linea de mapa
        }
        System.out.println();
    }

    public void imprimirCabecera() {
        System.out.println("Leyenda: Pradera transitable" + Mapa.ANSI_GREEN_BACKGROUND + "   " + Mapa.ANSI_RESET + " Ciudadela:" + Mapa.ANSI_PURPLE_BACKGROUND + " U " + Mapa.ANSI_RESET);
        System.out.println("Casa:" + Mapa.ANSI_PURPLE_BACKGROUND + " K " + Mapa.ANSI_RESET + "Cuartel:" + Mapa.ANSI_PURPLE_BACKGROUND + " Z " + Mapa.ANSI_RESET + "Bosque:" + Mapa.ANSI_CYAN_BACKGROUND + " B " + Mapa.ANSI_RESET + "Cantera:" + Mapa.ANSI_BLUE_BACKGROUND + Mapa.ANSI_WHITE + " C " + Mapa.ANSI_RESET + "Arbusto:" + Mapa.ANSI_YELLOW_BACKGROUND + " A " + Mapa.ANSI_RESET);
        System.out.println("Casa con personajes o grupos:" + Mapa.ANSI_PURPLE_BACKGROUND + " K*" + Mapa.ANSI_RESET + "Cuartel con personajes o grupos:" + Mapa.ANSI_PURPLE_BACKGROUND + " Z*" +Mapa.ANSI_RESET + "Ciudadela con personajes o grupos:" + Mapa.ANSI_PURPLE_BACKGROUND + " U*" + Mapa.ANSI_RESET);
        System.out.println("En las praderas transitables puede haber paisanos, (" + Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P " + Mapa.ANSI_RESET + "), soldados(" + Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " S " + Mapa.ANSI_RESET + ") o varios personajes(" + Mapa.ANSI_WHITE + Mapa.ANSI_RED_BACKGROUND + " P*" + Mapa.ANSI_RESET + ").");
        System.out.println("Los nombres de los contenedores de recursos aparecer\u00e1n al lado de su fila.");
        System.out.println();
        System.out.println("Los comandos disponibles son: \n\rmover [nombre personaje o nombre grupo] [direccion: norte, sur, este o oeste]");
        System.out.println("manejar [personaje] (permite manejar el personaje usando ASDW)");
        System.out.println("listar [personajes,edificios,grupos o civilizaciones]");
        System.out.println("describir [nombre de personaje,edificio,contenedor de recurso o grupo]");
        System.out.println("mirar (fila,columna)");
        System.out.println("construir [paisano o grupo] [casa o cuartel] [direccion: norte, sur, este o oeste]");
        System.out.println("crear [cuartel o ciudadela] [soldado o paisano]");
        System.out.println("reparar [paisano o grupo] [direccion edificio]");
        System.out.println("recolectar [paisano o grupo] [direccion Contenedor Recursos]");
        System.out.println("almacenar [paisano o grupo] [direccion Ciudadela]");
        System.out.println("cambiar [civilizacion]");
        System.out.println("civilizacion");
        System.out.println("imprimir [mapa]");
        System.out.println("agrupar (fila,columna)");
        System.out.println("salir");
        System.out.println();
    }
}
