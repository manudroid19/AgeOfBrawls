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
public class Edificio {
    //public final static int NINGUNO=0;
    public final static int CIUDADELA=1;
    public final static int CUARTEL=2;
    public final static int CASA=3;
    private int tipo;
    private int ps;
    private boolean estaDestruido;
    public Edificio(int tipo){
        if(tipo>=1&&tipo<=3)
            this.tipo=tipo;
    }
    public int getTipo(){
        return tipo;
    }
    
    
}
