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
        if(edificio==0){
            this.edificio=null;
        }else{
            this.edificio = new Edificio(edificio);
        }
        recurso = new ContenedorRecurso(tipo,cantidadRecurso);
    }
    public Celda(int edificio){
        this(ContenedorRecurso.PRADERA,0,edificio);
    }
    public Celda(int tipo, int cantidadRecurso){
        this(tipo,cantidadRecurso,0);
    }
    public Celda(){
        this(ContenedorRecurso.PRADERA,0,0);
    }
    public ContenedorRecurso getRecurso() {
        return recurso;
    }
    
    
    @Override
    public String toString(){
        switch (this.recurso.getTipo()) {
            case ContenedorRecurso.BOSQUE:
                return "B";
            case ContenedorRecurso.PRADERA:
                return " ";
            case ContenedorRecurso.CANTERA:
                return "C";
            case ContenedorRecurso.ARBUSTO:
                    return "A";
            default:
                return " ";
        }
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }
    
}
