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
    private Posicion posicion;
    
    public Celda(int tipo, int cantidadRecurso,int edificio,int i, int j){
        posicion = new Posicion(i,j);
        if(edificio==0){
            this.edificio=null;
        }else{
            this.edificio = new Edificio(edificio);
        }
        recurso = new ContenedorRecurso(tipo,cantidadRecurso);
    }
    public Celda(int edificio,int i,int j){
        this(ContenedorRecurso.PRADERA,0,edificio,i,j);
    }
    public Celda(int tipo, int cantidadRecurso,int i, int j){
        this(tipo,cantidadRecurso,0,i,j);
    }
    public Celda(int i,int j){
        this(ContenedorRecurso.PRADERA,0,0,i,j);
    }
    
    public ContenedorRecurso getContenedorRec() {
        return recurso;
    }
    public void setEdificio(Edificio edificio){
        this.recurso.set(ContenedorRecurso.PRADERA, 0);
        this.edificio = edificio;
    }
    
    @Override
    public String toString(){
        switch (this.recurso.getTipo()) {
            case ContenedorRecurso.BOSQUE:
                return " │ B";
            case ContenedorRecurso.PRADERA:
                if(this.edificio==null)
                    return " │  ";
                else if(this.edificio.getTipo()==Edificio.CIUDADELA)
                    return " │ U";
            case ContenedorRecurso.CANTERA:
                return " │ C";
            case ContenedorRecurso.ARBUSTO:
                    return " │ A";
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
