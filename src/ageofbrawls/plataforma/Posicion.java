/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

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
        }else
            System.out.println("Error:posicion no valida.");
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

}
