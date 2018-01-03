/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.contenedor;

import ageofbrawls.contenido.Recursos.Recurso;

/**
 *
 * @author Santiago
 */
public final class Cantera extends Contenedor {
    final int cantidadInicial;

    public Cantera(Recurso recurso, String nombre) {
        super(recurso, nombre);
        cantidadInicial=recurso.getCantidad();
    }
    
    @Override
    public void describirContenedorRecurso() {
        super.describirContenedorRecurso();
        System.out.println("Cantidad de piedra: " + this.getRecurso().getCantidad());
    }
    
    @Override
    public Recurso procesar(){
       int cantidad= getRecurso().getCantidad();
       for(int i=0;i<5;i++){
       float Incremento= (cantidadInicial-cantidad)/cantidadInicial;
       float disminucion=((float)Incremento)*0.1f/0.2f;
       cantidad -= cantidad*(1-disminucion)/5;
       }
    
}
    
    @Override
    public boolean esTransitable() {
        return false;
    }
    
    @Override
    public String toString() {
        return "cantera";
    }
    
}
