/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls;

/**
 *
 * @author Santiago
 */
public class Personaje {
    public final static int PAISANO=1;
    public final static int SOLDADO=2;
    private int tipo;
    private int salud;
    private int armadura;
    private int ataque;
    private int capRec;
    private boolean estaMuerto;
    
    public Personaje(int tipo){
        if(tipo>=1&&tipo<=2)
            this.tipo=tipo;
    }
    public int getTipo(){
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
    
}
