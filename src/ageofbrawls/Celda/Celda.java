/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.Celda;

import ageofbrawls.Edificio;

/**
 *
 * @author Santiago
 */
public class Celda {
    private int cantidadRec;
    private Edificio edificio;
    private boolean oculto;
    public int getCantidadRec() {
        
        return cantidadRec;
    }
    public void setCantidadRec(int valor){
        if(valor>(-1)){
            cantidadRec=valor;
        }
        else
            throw new IllegalArgumentException();
        
    }
    
    @Override
    public String toString(){
        return " ";
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }
    
}
