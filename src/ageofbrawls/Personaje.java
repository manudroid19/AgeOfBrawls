/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls;

/**
 *
 * @author Santiago
 */
public class Personaje {
    public final static int PAISANO=1;
    public final static int SOLDADO=2;
    private int tipo;
    private int salud;
    private int armadura;
    private int ataque;
    private int capRec;
    private int cantRec;
    private boolean estaMuerto;
    
    public Personaje(int tipo){
        if(tipo>=1 && tipo<=2)
            this.tipo=tipo;
    }
    public int getTipo(){
        return tipo;
    }

    public int getSalud() {
        return salud;
    }

    public int getArmadura() {
        return armadura;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getCapRec() {
        return capRec;
    }
    public void setCapRec(int valor){
        if(valor>0){
            capRec=valor;
        }
        else
            System.out.println("Capacidad introducida errónea");
    }
    public void CrearSoldado(){
        tipo=2;
        salud=100;
        armadura=200;
        ataque=30;
        estaMuerto=false;
    }
    public void CrearPaisano(){
        tipo=1;
        salud=50;
        armadura=100;
        ataque=0;
        capRec=40;
        estaMuerto=false;
    }
    public void describirPersonaje(){
        
    }
  
    public void recolectar(Personaje personaje){
        if(personaje.getTipo()==1){
 
            
            
        }
        else
            System.out.println("Un soldado no puede recolectar");
    }
    public void consEdif(Personaje personaje){
         if(personaje.getTipo()==1){
            
            
            
        }
        else
            System.out.println("Un soldado no puede construir edificios");
    } 
    
    public void almacenar(Personaje personaje){
        if(personaje.getTipo()==1){
            
            
            
        }
        else
            System.out.println("Un soldado no puede almacenar recursos ");
    }
    
}
