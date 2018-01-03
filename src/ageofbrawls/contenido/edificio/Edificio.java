/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.edificio;

import ageofbrawls.contenido.edificio.Casa;
import ageofbrawls.contenido.Personajes.Grupo;
import ageofbrawls.contenido.Personajes.Paisano;
import ageofbrawls.contenido.Personajes.Personaje;
import ageofbrawls.contenido.Personajes.Soldado;
import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Posicion;
import java.util.ArrayList;

/**
 *
 * @author Santiago
 */
public abstract class Edificio {

    private Posicion posicion;
    private Civilizacion civilizacion;
    private int capAlojDef;
    private int ps;
    private boolean destruido;
    private String nombre;

    public Edificio(Posicion posicion, String nombre, Civilizacion civilizacion) {
        if (nombre != null && civilizacion != null) {

            this.civilizacion = civilizacion;
            if (posicion != null) {
                this.posicion = new Posicion(posicion);
            } else {
                this.posicion = new Posicion();
            }
            this.nombre = nombre;

        } else {
            System.out.println("Error seteando tipo");
        }
    }

    public Edificio(Posicion posicion, String nombre, Civilizacion civilizacion, int salud, int capAloj) {
        this(posicion, nombre, civilizacion);
        this.ps = salud;
        this.capAlojDef = capAloj;

    }

    public Posicion getPosicion() {
        return new Posicion(posicion);
    }

    public int getPs() {
        return ps;
    }

    public Civilizacion getCivilizacion() {
        return civilizacion;
    }

    public int getCapAloj1() {
        return capAlojDef;
    }

    public int getAtaque() {
        int ataque = 0;
        ArrayList<Personaje> pers = (ArrayList<Personaje>) civilizacion.getMapa().getCelda(posicion).getPersonajes().clone();
        for (Grupo g : civilizacion.getMapa().getCelda(posicion).getGrupos()) {
            pers.addAll((ArrayList<Personaje>) g.getPersonajes().clone());
        }
        for (Personaje p : pers) {
            ataque += p.danhoAtaque();
        }
        return ataque;
    }

    public int getDefensa() {
        int defensa = 0;
        ArrayList<Personaje> pers = (ArrayList<Personaje>) civilizacion.getMapa().getCelda(posicion).getPersonajes().clone();
        for (Grupo g : civilizacion.getMapa().getCelda(posicion).getGrupos()) {
            pers.addAll((ArrayList<Personaje>) g.getPersonajes().clone());
        }
        for (Personaje p : pers) {
            defensa += p.getDefensa();
        }
        return defensa;
    }

    public int getCapAlmacen() {
        return civilizacion.getCapAlmacen();
    }

    public boolean estaDestruido() {
        return destruido;
    }

    public String getNombre() {
        return nombre;
    }

    public int getMaxVida() {
        return -1;
    }

    public void setCapAloj(int aloj, boolean relative) {
        if (relative) {
            if (capAlojDef + aloj < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            capAlojDef += aloj;
        } else {
            if (capAlojDef + aloj < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            capAlojDef = aloj;
        }
    }

    public void setPs(int valor) {
        if (valor < 0) {
            System.out.println("Setteo incorrecto");
        } else {
            this.ps = valor;
        }
    }
    
     public void crearPersonaje(Civilizacion civilizacion){
         System.out.println("Error");
     }
     
     public void atacar(Personaje[] personajes){
         
         
         
     }

    public String toString() {
        return "edificio";
    }

    public void describirEdificio() {
        System.out.println("Salud: " + ps);
        System.out.println("Capacidad de Alojamiento para defenderlo " + capAlojDef);
        System.out.println("Capacidad de ataque: " + getAtaque());
        System.out.println("Capacidad de defensa: " + getDefensa());
        System.out.println("Nombre: " + nombre);
        System.out.println("Civilizacion: " + civilizacion.getNombre());
    }

    public void danar(int dano) {
        if (dano > 0) {
            if (ps - dano > 0) {
                ps -= dano;
            } else {
                ps = 0;
                destruido = true;
                civilizacion.getMapa().getCelda(this.posicion).setTipoCont(0);
                civilizacion.getMapa().getCelda(posicion).setEdificio(null);
                civilizacion.getEdificios().remove(this.getNombre());
                civilizacion.getMapa().imprimirCabecera();
                civilizacion.getMapa().imprimir(civilizacion);
                System.out.println("El edificio " + this.getNombre() + " ha sido destruido");

            }
        } else {
            System.out.println("Error dañando edificio.");
        }
    }

    public void reparar() {
        ps = getMaxVida();
    }

}
