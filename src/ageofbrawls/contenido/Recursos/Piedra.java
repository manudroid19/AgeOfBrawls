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
public final class Piedra extends Recurso {

    private final int cantidadInicial;

    public Piedra(int cantidad) {
        super(cantidad);
        cantidadInicial = cantidad;
    }
    public int getCantidadInicial(){
        return cantidadInicial;
    }

    @Override
    public String toString() {
        return "piedra";
    }

}
