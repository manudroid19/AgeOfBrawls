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
public class Edificio {

    //public final static int NINGUNO=0;
    public final static int CIUDADELA = 1;
    public final static int CUARTEL = 2;
    public final static int CASA = 3;
     private final static int CAPALMACEN=10000;
    private Posicion posicion;
    private int tipo;
    private int ps;
    private boolean destruido;
    private String nombre;

    public Edificio(int tipo, Posicion posicion, String nombre) {
        if (tipo > 0 && tipo < 4) {
            this.tipo = tipo;
            this.posicion = posicion;
            this.nombre = nombre;
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

    public void setTipo(int tipo) {
        if (tipo > 0 && tipo < 4) {
            this.tipo = tipo;
        }else{
            System.out.println("Error seteando tipo");
    }
    }
    
    public void describirEdificio(){
        switch(tipo){
            case 1:
                System.out.println("Tipo: CIUDADELA");
                System.out.println("PS: " +ps);
                System.out.println("Nome:" +nombre);
                break;
                
            case 2:
                System.out.println("Tipo: CUARTEL");
                System.out.println("PS: " +ps);
                System.out.println("Nome:" +nombre);
                break;
                
            case 3:
                System.out.println("Tipo: CASA");
                System.out.println("PS: " +ps);
                System.out.println("Nome:" +nombre);
                break;
                
        }
    }

    public boolean estaDestruido() {
        return destruido;
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
