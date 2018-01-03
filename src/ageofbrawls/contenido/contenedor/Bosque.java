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
public final class Bosque extends Contenedor {

    public Bosque(Recurso recurso, String nombre) {
        super(recurso, nombre);
    }
    
    public void describirContenedorRecurso() {
        super.describirContenedorRecurso();
        System.out.println("Cantidad de madera: " + this.getRecurso().getCantidad());
    }
    
    @Override
    public boolean esTransitable() {
        return false;
    }
    
    @Override
    public String toString() {
        return "bosque";
    }
    
}
