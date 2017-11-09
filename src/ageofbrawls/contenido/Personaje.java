/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

import ageofbrawls.plataforma.Mapa;
import ageofbrawls.plataforma.Posicion;
import java.util.Objects;

/**
 *
 * @author Santiago
 */
public class Personaje {

    public final static int PAISANO = 1, SOLDADO = 2;
    private int tipo, salud, armadura, ataque, capRec, cantRec;
    private Posicion posicion;
    private boolean estaMuerto;
    private String nombre;

    public Personaje(int tipo, Posicion posicion, String nombre) {
        if (tipo == 1 || tipo == 2) {
            this.tipo = tipo;
            this.posicion = posicion;
            this.nombre = nombre;
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

    private void mover(Mapa mapa, int direccion) {
        if (mapa.getCelda(posicion.get(direccion)).esCeldaLibre()) {
            mapa.getCelda(posicion).removePersonaje(this);
            posicion = posicion.get(direccion);
            mapa.getCelda(posicion).addPersonaje(this);
            mapa.makeAdyVisible(posicion);
        }else
            System.out.println("Error: No te puedes mover a esa celda.");
    }

    public void mover(Mapa mapa, String direccion) {
        switch (direccion.toLowerCase()) {
            case "norte":
                mover(mapa, Posicion.NORTE);
                break;
            case "sur":
                mover(mapa, Posicion.SUR);
                break;
            case "este":
                mover(mapa, Posicion.ESTE);
                break;
            case "oeste":
                mover(mapa, Posicion.OESTE);
                break;
            default:
                System.out.println("Error: direccion no valida.");
        }
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.tipo;
        hash = 37 * hash + this.salud;
        hash = 37 * hash + this.armadura;
        hash = 37 * hash + this.ataque;
        hash = 37 * hash + this.capRec;
        hash = 37 * hash + this.cantRec;
        hash = 37 * hash + Objects.hashCode(this.posicion);
        hash = 37 * hash + (this.estaMuerto ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.nombre);
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
        final Personaje other = (Personaje) obj;
        if (this.tipo != other.tipo) {
            return false;
        }
        if (this.salud != other.salud) {
            return false;
        }
        if (this.armadura != other.armadura) {
            return false;
        }
        if (this.ataque != other.ataque) {
            return false;
        }
        if (this.capRec != other.capRec) {
            return false;
        }
        if (this.cantRec != other.cantRec) {
            return false;
        }
        if (this.estaMuerto != other.estaMuerto) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.posicion, other.posicion)) {
            return false;
        }
        return true;
    }
}
