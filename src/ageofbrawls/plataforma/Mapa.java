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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author mprad
 */
public class Mapa {

    private ArrayList<ArrayList<Celda>> mapa;
    private int filas;
    private int columnas;
    HashMap<String,Personaje> personajes;
    HashMap<String , Edificio> edificios;
    

    public Mapa(int filas, int columnas) {
        if (filas > 0 && columnas > 0) {
            mapa = new ArrayList<>();
            for (int i = 0; i < filas; i++) {
                ArrayList<Celda> b = new ArrayList<>();
                for (int j = 0; j < columnas; j++) {
                    b.add(j, new Celda(i, j,true));
                }
                mapa.add(i, b);
            }
            this.filas = filas;
            this.columnas = columnas;
        } else {
            System.out.println("Error construyendo Mapa.");
        }
    }

    public Mapa(int filas, boolean inicializar) {
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
            Posicion ciudadela = new Posicion((mapa.size() - 1) / 2, (mapa.size() - 1) / 2);
            this.getCelda(ciudadela).setEdificio(new Edificio(Edificio.CIUDADELA));
        }
    }

    private void makeAdyPrad(int i, int j) {
        for (int h = i - 1; h < i + 2; h++) {
            for (int k = j - 1; k < j + 2; k++) {
                this.getCelda(h, k).getContenedorRec().set(ContenedorRecurso.PRADERA, 0);
            }
        }
    }

    private void makeBloqueRec(int i, int j) {
        ArrayList<Integer> bloque = new ArrayList<>(4);
        for (int k = 0; k < 4; k++) {
            bloque.add(k);
        }
        Collections.shuffle(bloque);
        Random rt = new Random();
        int[] cantidad = new int[]{rt.nextInt(100 - 1 + 1) + 1, rt.nextInt(100 - 1 + 1) + 1, rt.nextInt(100 - 1 + 1) + 1, rt.nextInt(100 - 1 + 1) + 1};
        Posicion posicion = new Posicion(i, j);
            getCelda(posicion).getContenedorRec().set(bloque.get(0), cantidad[0]);
            getCelda(posicion.get(Posicion.ESTE)).getContenedorRec().set(bloque.get(1), cantidad[1]);
            getCelda(posicion.get(Posicion.SUR)).getContenedorRec().set(bloque.get(2), cantidad[2]);
            getCelda(posicion.get(Posicion.SURESTE)).getContenedorRec().set(bloque.get(3), cantidad[3]);
    }

    public Mapa() {
        this(10, 10);
    }

    public Celda getCelda(int x, int y) {
        if (x < columnas && y < filas && x > -1 && y > -1) {
            return mapa.get(y).get(x);
        }
        System.out.println("getCelda devuelve null" + x + y);
        return null;
    }

    public Celda getCelda(Posicion posicion) {
        if (posicion.getX() < columnas && posicion.getY() < filas && posicion.getX() > -1 && posicion.getY() > -1) {
            return mapa.get(posicion.getY()).get(posicion.getX());
        }
        return null;
    }

    public boolean esCeldaVacia(int i, int j) {
        if (i < filas && j < columnas && i > -1 && j > -1) {
            return (mapa.get(j).get(i) == null); //expresion logica= true si celda==null
        }
        System.out.println("Error esCeldaVacia");
        return false;
    }

    public void imprimir() {
        System.out.print("    ");
        for (int i = 0; i < columnas; i++) {
            System.out.print(i + "  │");
        }
        System.out.println();

        for (int i = 0; i < filas; i++) {
            System.out.print(i);
            for (int j = 0; j < columnas; j++) {
                System.out.print(mapa.get(i).get(j).toString());
            }
            System.out.print("│");
            System.out.println();
            for (int j = 0; j < columnas; j++) {
                System.out.print("───");
            }
            System.out.println();

        }

        System.out.println();
    }
}
