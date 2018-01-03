/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.edificio;

import ageofbrawls.contenido.Personajes.Paisano;
import ageofbrawls.contenido.Personajes.Personaje;
import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Posicion;

/**
 *
 * @author Santiago
 */
public class Ciudadela extends Edificio {

    public Ciudadela(Posicion posicion, String nombre, Civilizacion civilizacion) {
        super(posicion, nombre, civilizacion, 1000, 10);
    }
    
    
    @Override
    public void crearPersonaje(Civilizacion civilizacion){
        if (civilizacion == null) {
            System.out.println("Error.");
            return;
        }
        if (civilizacion.getPersonajes().size() >= civilizacion.contarEdificios(Casa.class) * Casa.CAPALOJ) {
            System.out.println("Error: no hay suficiente espacio para crear.");
            return;
        }
        if (civilizacion.getAlimentos() < 50) {
                System.out.println("Error: no hay suficiente alimento disponible.");
                return;
            }
            Posicion pos = super.getPosicion().posicionAdyacenteLibre(civilizacion.getMapa());
            if (pos == super.getPosicion()) {
                System.out.println("No hay celdas adyacentes libres.");
                return;
            }
            int i = 1;
            String nombrePers = "paisano1";
            while (civilizacion.getPersonajes().containsKey(nombrePers)) {
                nombrePers = nombrePers.replace("paisano" + i, "paisano" + (++i));
            }
            Personaje person = new Paisano(pos, nombrePers, civilizacion);
            civilizacion.getMapa().getCelda(pos).addPersonaje(person);
            civilizacion.getPersonajes().put(person.getNombre(), person);
            civilizacion.getMapa().getCelda(pos).setOculto(civilizacion, false);
            civilizacion.setAlimentos(-50, true);
            civilizacion.makeAdyVisible(pos);
            System.out.println();
            civilizacion.getMapa().imprimirCabecera();
            civilizacion.getMapa().imprimir(civilizacion);
            System.out.println("Coste de creacion: 50 unidades de comida");
            System.out.println("Te quedan " + ((civilizacion.contarEdificios(Casa.class) * Casa.CAPALOJ) - civilizacion.getPersonajes().size()) + " unidades de capacidad de alojamiento");
            System.out.println("Se ha creado " + person.getNombre() + " en la celda de " + pos);
        
    }

    @Override
    public void describirEdificio() {
        System.out.println("Tipo: " + this.toString());
        super.describirEdificio();
        System.out.println("Recursos: " + getCivilizacion().getMadera() + " de madera, " + getCivilizacion().getPiedra() + " de piedra y " + getCivilizacion().getAlimentos() + " de alimentos");
    }

    public void danar(int dano) {
        super.danar(dano);
        if(dano>0 && this.getPs()-dano<=0){
            getCivilizacion().quitarCiudadela();
        }

    }

    @Override
    public int getMaxVida() {
        return 1000;
    }

    @Override
    public String toString() {
        return "ciudadela";
    }

}
