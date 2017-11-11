/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

import ageofbrawls.plataforma.Mapa;
import ageofbrawls.plataforma.Posicion;

/**
 *
 * @author Santiago
 */
public class Edificio {

    //public final static int NINGUNO=0;
    public final static int CIUDADELA = 1;
    public final static int CUARTEL = 2;
    public final static int CASA = 3;
    public final static int CAPALMACEN=100000;
    public final static int CAPALOJ=10;
    private Posicion posicion;
    private int tipo;
    private int capacidadAlojamiento;
    private int capAlmacen;
    private int ps;
    private boolean destruido;
    private String nombre;
    private int madera,piedra, alimentos;

    public Edificio(int tipo, Posicion posicion, String nombre) {
        if (tipo > 0 && tipo < 4) {
            this.tipo = tipo;
            this.posicion = posicion;
            this.nombre = nombre;
        switch(tipo){
            case Edificio.CASA:
                this.ps=200;
                this.capacidadAlojamiento=Edificio.CAPALOJ;
                break;
            case Edificio.CUARTEL:
                this.ps=500;
                break;
            case Edificio.CIUDADELA:
                this.ps=1000;
                this.capAlmacen=Edificio.CAPALMACEN;
                madera=500;
                piedra =500;
                alimentos=500;
                break;
            
            
        }    
        }else{
            System.out.println("Error seteando tipo");
        }
    }
    public Posicion getPosicion(){
        return new Posicion(posicion);
    }
    
    public int getTipo() {
        return tipo;
    }

    public int getPs() {
        return ps;
    }
    public int getCapAloj(){
        return capacidadAlojamiento;
    }

    public void setTipo(int tipo) {
        if (tipo > 0 && tipo < 4) {
            this.tipo = tipo;
        }else{
            System.out.println("Error seteando tipo");
    }
    }
    
    public void crearPersonaje(Mapa mapa){
        if(tipo== Edificio.CIUDADELA){
            if(mapa.getPersonajes().size()<mapa.contarEdificios(Edificio.CASA)*Edificio.CAPALOJ){
                //if()
            }
            
            
        }
        else if(tipo==Edificio.CUARTEL){
            
        }
            
    }
    
    public void describirEdificio(){
        switch(tipo){
            case Edificio.CIUDADELA:
                System.out.println("Tipo: CIUDADELA");
                System.out.println("Salud: " +ps);
                System.out.println("Recursos: "+madera+ " de madera, "+piedra+" de piedra y "+alimentos+" de alimentos");
                System.out.println("Nombre: " +nombre);
                break;
                
            case Edificio.CUARTEL:
                System.out.println("Tipo: CUARTEL");
                System.out.println("Salud: " +ps);
                System.out.println("Nombre: " +nombre);
                break;
                
            case Edificio.CASA:
                System.out.println("Tipo: CASA");
                System.out.println("Salud: " +ps);
                System.out.println("Nombre: " +nombre);
                break;
                
        }
    }

    public boolean estaDestruido() {
        return destruido;
    }
    public int getMadera(){
        return this.madera;
    }
    public int getPiedra(){
        return this.piedra;
    }
    public void setPiedra(int cant, boolean relative){
        if(relative){
            if(piedra+cant<0){
                System.out.println("error, seteo incorrecto");
                return;
            }                
            piedra+=cant;
        }else{
            if(piedra+cant<0){
                System.out.println("error, seteo incorrecto");
                return;
            }
            piedra = cant;
        }
    }
    public void setMadera(int cant, boolean relative){
        if(relative){
            if(madera+cant<0){
                System.out.println("error, seteo incorrecto");
                return;
            }                
            madera+=cant;
        }else{
            if(madera+cant<0){
                System.out.println("error, seteo incorrecto");
                return;
            }
            madera = cant;
        }
    }

    public void danar(int dano) {
        if (dano > 0) {
            if (ps - dano > 0) {
                ps -= dano;
            } else {
                ps = 0;
                destruido = true;
            }
        } else {
            System.out.println("Error dañando edificio.");
        }
    }

}
