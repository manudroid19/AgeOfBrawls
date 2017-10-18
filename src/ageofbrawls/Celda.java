/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls;

import ageofbrawls.Edificio;

/**
 *
 * @author Santiago
 */
public class Celda {
    private Edificio edificio;
    private boolean oculto;
    private ContenedorRecurso recurso;
    
    public Celda(int tipo, int cantidadRecurso,int edificio){
        edificio = new Edificio();
        recurso = new ContenedorRecurso(tipo,cantidadRecurso);
    }
    public Celda(int edificio){
        this(0,0,edificio);
    }
    public Celda(int tipo, int cantidadRecurso){
        this(tipo,cantidadRecurso,0);
    }
    public ContenedorRecurso getRecurso() {
        return recurso;
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
