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
public class Recurso {

    public final static int MADERA = 1;
    public final static int PIEDRA = 2;
    public final static int COMIDA = 3;
    private int tipoRecurso;
    private int cantidad;
    
    public Recurso(){
        
    }
    
    public Recurso(int tipoRecurso,int cantidad){
       if ((tipoRecurso > 0 && tipoRecurso <= 3) && cantidad>=0) {
            this.tipoRecurso = tipoRecurso;
            this.cantidad=cantidad;
        }
       else if((tipoRecurso > 0 && tipoRecurso <= 3) && cantidad<0) {
            this.tipoRecurso = tipoRecurso;
        }
       else
            System.out.println("Tipo de recurso no válido");
       
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

    public int getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(int tipoRecurso) {
        if (tipoRecurso > 0 && tipoRecurso <= 3) {
            this.tipoRecurso = tipoRecurso;
        } else {
            System.out.println("Tipo de recurso no válido");
            
        }
        
    }

}
