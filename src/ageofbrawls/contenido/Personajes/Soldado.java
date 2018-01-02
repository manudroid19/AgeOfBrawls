/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.Personajes;

import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Posicion;
import java.util.ArrayList;

/**
 *
 * @author mprad
 */
public class Soldado extends Personaje {

    private int ataque;

    public Soldado(Posicion posicion, String nombre, Civilizacion civilizacion) {
        super(posicion, nombre, civilizacion, 200, 100);//defensa salud
        ataque = 70;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void describir() {
        super.describir();
        System.out.println("Ataque :" + ataque);
    }

    public void atacar(String direccion) {
        if (super.getGrupo() != null) {
            System.out.println("El personaje no puede atacar ya que pertenece a un grupo");
            return;
        }
        atacarGenerico(direccion);
    }

    @Override
    public String toString() {
        return "soldado";
    }

    @Override
    public void recuperarVida() {
        Civilizacion civilizacion = super.getCivilizacion();
        int puntosARecuperar = 100 - this.getSalud();
        if (puntosARecuperar == 0) {
            System.out.println("El personaje tiene toda la vida");
            return;
        }
        int costeAlimento = (int) (puntosARecuperar * 0.8);
        if (civilizacion.getAlimentos() < costeAlimento) {
            int puntosRecuperados = (int) (civilizacion.getAlimentos() / 0.8);
            super.setSalud(puntosRecuperados, true);
            civilizacion.setAlimentos(0, false);
            return;
        }
        civilizacion.setAlimentos(-costeAlimento, true);
        this.recuperar();
        System.out.println("Coste de la recuperación de la vida: " + costeAlimento + " unidades de alimento de la ciudadela.");
    }

    @Override
    public void recuperar() {
        super.setSalud(50, false);
    }
}
