/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mprad
 */
public class Posicion {

    public static final int ESTE = 0;
    public static final int NORTE = 1;
    public static final int OESTE = 2;
    public static final int SUR = 3;
    public static final int NORESTE = 4;
    public static final int SURESTE = 5;
    private int x;
    private int y;

    public Posicion(int x, int y) {
        if (x > -1 && y > -1) {
            this.x = x;
            this.y = y;
        } else {
            System.out.println("Error:posicion no valida.");
        }
    }

    public Posicion(Posicion posicion) {
        this(posicion.getX(), posicion.getY());
    }

    public Posicion() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Posicion PosicionAdyacenteLibre(Mapa mapa) {
        int i=x;
        int j=y;
        ArrayList<Posicion> candidatos = new ArrayList<>();
        for (int h = i - 1; h < i + 2; h++) {
            for (int k = j - 1; k < j + 2; k++) {
                Posicion pos= new Posicion(h,k);
                if(mapa.getCelda(pos).esCeldaLibre()){
                    candidatos.add(pos);
                }
            }
        }
        Collections.shuffle(candidatos);
        return candidatos.get(0);
    }

    public Posicion get(int pos) {
        switch (pos) {
            case Posicion.ESTE:
                return new Posicion(x + 1, y);
            case Posicion.OESTE:
                return new Posicion(x - 1, y);
            case Posicion.NORTE:
                return new Posicion(x, y - 1);
            case Posicion.SUR:
                return new Posicion(x, y + 1);
            case Posicion.NORESTE:
                return new Posicion(x + 1, y - 1);
            case Posicion.SURESTE:
                return new Posicion(x + 1, y + 1);
            default:
                System.out.println("Error: posicion no valida.");
                return this;
        }
    }

    @Override
    public String toString() {
        return "Posicion{" + "x=" + x + ", y=" + y + '}';
    }
        @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.x;
        hash = 89 * hash + this.y;
        return hash;
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
        final Posicion other = (Posicion) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
}
