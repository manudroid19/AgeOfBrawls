/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

/**
 *
 * @author mprad
 */
public class ContenedorRecurso {

    public final static int BOSQUE = 1;
    public final static int CANTERA = 2;
    public final static int ARBUSTO = 3;
    private int tipo;
    private int cantidad;
    private String nombre;
    
    public ContenedorRecurso(int tipo, int cantidad) {
        if (tipo >= 0 && tipo <= 3) {
            this.tipo = tipo;
            if (this.tipo != 0 && cantidad >= 0) {
                this.cantidad = cantidad;
            } else {
                this.cantidad = 0;
            }
        } else {
            System.out.println("Error: tipo de CR no valido.");
        }
    }

    public int getTipo() {
        return this.tipo;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setTipo(int tipo) {
        if (tipo >= 0 && tipo <= 3) {
            this.tipo = tipo;
        } else {
            System.out.println("Error: El tipo introducido no es válido");
        }

    }

    public void setCantidad(int cantidad) {
        if (cantidad >= 0) {
            this.cantidad = cantidad;
//            if (this.cantidad == 0) {
//                this.tipo = PRADERA;
//            }
        } else {
            System.out.println("Error: Cantidad introducida no es válida");
        }

    }

    public void setNombre(String nombre) {
        if (nombre != null) {
            this.nombre = nombre;
        }
    }

    public void set(int tipo, int cantidad) {
        if (tipo >= 0 && tipo <= 3) {
            this.tipo = tipo;
            if (this.tipo != 0 && cantidad >= 0) {
                this.cantidad = cantidad;
            } else {
                this.cantidad = 0;
            }
        } else {
            System.out.println("Error: tipo de CR no valido.");
        }

    }

    public void describirContenedorRecurso() {
        switch (tipo) {
            case ContenedorRecurso.BOSQUE:
                System.out.println("Contenedor de recurso");
                System.out.println("Cantidad de madera: " + cantidad);
                break;
            case ContenedorRecurso.CANTERA:
                System.out.println("Contenedor de recurso");
                System.out.println("Cantidad de piedra: " + cantidad);
                break;
            case ContenedorRecurso.ARBUSTO:
                System.out.println("Contenedor de recurso");
                System.out.println("Cantidad de comida: " + cantidad);
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
