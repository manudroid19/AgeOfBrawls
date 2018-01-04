/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.contenedor;

import ageofbrawls.contenido.Recursos.Recurso;

/**
 *
 * @author mprad
 */
public abstract class Contenedor {

    private Recurso recurso;
    private String nombre;
    private boolean esTransitable;

    public Contenedor(Recurso recurso, String nombre) {

        if (nombre != null) {
            this.recurso = recurso;
            this.nombre = nombre;
        } else {
            System.out.println("Error: tipo de CR no valido.");
        }
    }

    public String getNombre() {
        if (nombre == null) {
            return "";
        }
        return nombre;
    }

    public Recurso getRecurso() {
        if (recurso != null) {
            return recurso;
        } else {
            return null;
        }
    }

    public void setNombre(String nombre) {
        if (nombre != null) {
            this.nombre = nombre;
        }
    }

    public void setRecurso(Recurso recurso) {
        if (recurso != null) {
            this.recurso = recurso;
        }
    }

    public void describirContenedorRecurso() {

        System.out.println("Contenedor de recurso");

    }
    
    public Recurso procesar(){
        
   
    }

    public boolean esTransitable() {
        return true;
    }

    @Override
    public String toString() {
        return "contenedorRecurso";
    }
}
