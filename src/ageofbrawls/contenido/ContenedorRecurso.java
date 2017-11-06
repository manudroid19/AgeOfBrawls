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
    public final static int PRADERA = 0;
    public final static int BOSQUE = 1;
    public final static int CANTERA = 2;
    public final static int ARBUSTO = 3;
    private int tipo;
    private int cantidad;
    
    public ContenedorRecurso(int tipo, int cantidad){
        if(tipo>=0&&tipo<=3)
            this.tipo=tipo;
        else
            this.tipo=0;
        if(this.tipo!=0 && cantidad>=0)
            this.cantidad=cantidad;
        else
            this.cantidad=0;
            
    }
    public ContenedorRecurso(){
        this(ContenedorRecurso.PRADERA,0);
    }
    public int getTipo(){
        return this.tipo;
    }
    public int getCantidad(){
        return this.cantidad;
    }
    public void setTipo(int tipo){
        if(tipo>=0&&tipo<=3)
        this.tipo =tipo;
        
        else
            System.out.println("El tipo introducido no es válido");
        
    }
    public void setCantidad(int cantidad){
        if(cantidad>0)
            this.cantidad=cantidad;
        
        else
            System.out.println("Cantidad introducida no es válida");
        
    }
    public void set(int tipo, int cantidad){
        if(cantidad>0 && (tipo>=1 && tipo<=3)){
            this.cantidad=cantidad;
            this.tipo=tipo;
        }
        if(tipo==0){
            this.tipo=0;
           this.cantidad=0; 
        }
        
    }
}
