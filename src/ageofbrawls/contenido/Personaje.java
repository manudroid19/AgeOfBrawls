/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

import ageofbrawls.plataforma.Posicion;

/**
 *
 * @author Santiago
 */
public class Personaje {

    public final static int PAISANO = 1, SOLDADO = 2;
    private int tipo, salud, armadura, ataque, capRec, cantRec;
    private Posicion posicion;
    private boolean estaMuerto;

    public Personaje(int tipo) {
        if (tipo == 1 || tipo == 2) {
            this.tipo = tipo;
            if (tipo == Personaje.SOLDADO) {
                salud = 100;
                armadura = 200;
                ataque = 30;
                estaMuerto = false;
                cantRec = -1;
                capRec = -1;
            } else {
                salud = 50;
                armadura = 100;
                ataque = -1;
                cantRec = 0;
                capRec = 40;
                estaMuerto = false;
            }
        } else {
            System.out.println("Error seteando tipo");
        }
    }

    public Personaje() {
        this(Personaje.PAISANO);
    }

    public int getTipo() {
        return tipo;
    }

    public int getSalud() {
        return salud;
    }

    public int getArmadura() {
        return armadura;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getCapRec() {
        return capRec;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setCapRec(int valor) {
        if (valor > 0 && this.tipo == Personaje.PAISANO) {
            capRec = valor;
        } else {
            System.out.println("Error: capacidad introducida errónea");
        }
    }

    public void setPosicion(Posicion posicion) {
        if (posicion != null) {
            this.posicion = posicion;
        } else {
            System.out.println("Error: posicion introducida errónea");
        }
    }

    public void describirPersonaje() {
        System.out.println("Salud :" + salud);
        System.out.println("Armadura :" + armadura);
        System.out.println("Ataque :" + ataque);
    }

    public void mover() {

    }

    public void recolectar() {
        if (tipo == Personaje.PAISANO) {

        } else {
            System.out.println("Error: Un soldado no puede recolectar");
        }
    }

    public void consEdif() {
        if (tipo == Personaje.PAISANO) {

        } else {
            System.out.println("Error: Un soldado no puede construir edificios");
        }
    }

    public void almacenar() {
        if (tipo == Personaje.PAISANO) {

        } else {
            System.out.println("Error: Un soldado no puede almacenar recursos ");
        }
    }

}
