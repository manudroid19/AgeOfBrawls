/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

import ageofbrawls.contenido.Recursos.Recurso;

/**
 *
 * @author mprad
 */
public class ContenedorRecurso {

    public final static int BOSQUE = 1;
    public final static int CANTERA = 2;
    public final static int ARBUSTO = 3;
    private Recurso recurso;
    private int tipo;
    private String nombre;

    public ContenedorRecurso(int tipo, int cantidad) {
        recurso= new Recurso();
        if (tipo >= 0 && tipo <= 3) {
            this.tipo = tipo;
            if (this.tipo != 0 && cantidad >= 0) {
                this.recurso.setCantidad(cantidad);
            } else {
                this.recurso.setCantidad(cantidad);
            }
        } else {
            System.out.println("Error: tipo de CR no valido.");
        }
    }

    public int getTipo() {
        return this.tipo;
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

    public void setTipo(int tipo) {
        if (tipo >= 0 && tipo <= 3) {
            this.tipo = tipo;
        } else {
            System.out.println("Error: El tipo introducido no es válido");
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

    public void set(int tipo, int cantidad) {
        if (tipo >= 0 && tipo <= 3) {
            this.tipo = tipo;
            if (this.tipo != 0 && cantidad >= 0) {
                this.recurso.setCantidad(cantidad);
            } else {
                this.recurso.setCantidad(0);
            }
        } else {
            System.out.println("Error: tipo de CR no valido.");
        }

    }

    public void describirContenedorRecurso() {
        switch (tipo) {
            case ContenedorRecurso.BOSQUE:
                System.out.println("Contenedor de recurso");
                System.out.println("Cantidad de madera: " + this.recurso.getCantidad());
                break;
            case ContenedorRecurso.CANTERA:
                System.out.println("Contenedor de recurso");
                System.out.println("Cantidad de piedra: " + this.recurso.getCantidad());
                break;
            case ContenedorRecurso.ARBUSTO:
                System.out.println("Contenedor de recurso");
                System.out.println("Cantidad de comida: " + this.recurso.getCantidad());
                break;
        }

    }

    @Override
    public String toString() {
        switch (tipo) {
            case ContenedorRecurso.BOSQUE:
                return "bosque";
            case ContenedorRecurso.CANTERA:
                return "cantera";
            case ContenedorRecurso.ARBUSTO:
                return "arbusto";
            default:
                return "";
        }
    }
}
