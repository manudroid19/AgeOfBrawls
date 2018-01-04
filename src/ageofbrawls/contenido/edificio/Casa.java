/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.edificio;

import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Posicion;

/**
 *
 * @author Santiago
 */
public class Casa extends Edificio {

    public final static int CAPALOJ = 10;

    public Casa(Posicion posicion, String nombre, Civilizacion civilizacion) {
        super(posicion, nombre, civilizacion, 200, 7);

    }
    
    @Override
    public void describirEdificio() {
        System.out.println("Tipo: " +this.toString());
        super.describirEdificio();
        
    }
    
    @Override
    public int getMaxVida() {
        return 1000;
    }
    
    
    @Override
    public String toString(){
        return "casa";
    }

}
