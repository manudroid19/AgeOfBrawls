/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.Recursos;

/**
 *
 * @author Santiago
 */
public abstract class Recurso {

    private int cantidad;

    public Recurso(int cantidad) {
        if (cantidad >= 0) {

            this.cantidad = cantidad;
        } else {
            this.cantidad = 0;
        }

    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad = cantidad;
        } else {
            this.cantidad = 0;
        }
    }


@Override
        public String toString() {
        return "recurso";
    }

}
