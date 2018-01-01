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

    public void describirPersonaje() {
        super.describirPersonaje();
        System.out.println("Ataque :" + ataque);
    }

    public void atacar(String direccion) {
        Posicion posicion = super.getPosicion();
        Civilizacion civilizacion = super.getCivilizacion();

        Posicion pos = posicion.getAdy(direccion);
        if (direccion == null || pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos)) {
            System.out.println("No se puede atacar a esa posición.");
            return;
        }
        if (super.getGrupo() != null) {
            System.out.println("El personaje no puede atacar ya que pertenece a un grupo");
            return;
        }

        if (civilizacion.getMapa().getCelda(pos).getEdificio() == null && !civilizacion.getMapa().getCelda(pos).isHayGrupo() && civilizacion.getMapa().getCelda(pos).getPersonajes().isEmpty()) {
            System.out.println("En esa celda no hay nada a lo que se le pueda atacar");
            return;
        }
        if (civilizacion.getMapa().getCelda(pos).getEdificio() != null && civilizacion == civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("El personaje no puede atacar a un edificio de su propia civilización");
            return;
        }
        int PuntosAQuitar = this.getAtaque();

        ArrayList<Personaje> pers = new ArrayList<>();
        if (!civilizacion.getMapa().getCelda(pos).getGrupos().isEmpty()) {
            for (int i = 0; i < civilizacion.getMapa().getCelda(pos).getGrupos().size(); i++) {
                for (int j = 0; j < civilizacion.getMapa().getCelda(pos).getGrupos().get(i).getPersonajes().size(); j++) {
                    if (civilizacion.getMapa().getCelda(pos).getGrupos().get(i).getPersonajes().get(j).getCivilizacion() != civilizacion) {
                        pers.add(civilizacion.getMapa().getCelda(pos).getGrupos().get(i).getPersonajes().get(j));
                    }
                }
            }

        }
        for (int i = 0; i < civilizacion.getMapa().getCelda(pos).getPersonajes().size(); i++) {
            if (civilizacion.getMapa().getCelda(pos).getPersonajes().get(i).getCivilizacion() != civilizacion) {
                pers.add(civilizacion.getMapa().getCelda(pos).getPersonajes().get(i));
            }
        }
        int PuntosAQuitarACadaUno;
        if (pers.isEmpty()) {
            PuntosAQuitarACadaUno = 0;
        } else {
            PuntosAQuitarACadaUno = (int) (PuntosAQuitar / pers.size());
        }
        for (int i = 0; i < pers.size(); i++) {
            Personaje atacado = pers.get(i);
            int quitados;
            if (pers.get(i) instanceof Paisano) {
                quitados = (PuntosAQuitarACadaUno);
            } else {
                quitados = (int) ((double) PuntosAQuitarACadaUno * 0.5);
            }
            atacado.setSalud(-quitados, true);
            if (atacado.getSalud() <= 0) {
                if (atacado.getGrupo() != null) {
                    atacado.getGrupo().desligar(atacado);
                }
                civilizacion.getMapa().getCelda(pos).getPersonajes().remove(atacado);
                civilizacion.getPersonajes().remove(atacado.getNombre());
                civilizacion.getMapa().imprimirCabecera();
                civilizacion.getMapa().imprimir(civilizacion);
                System.out.println("Has inflingido " + quitados + " de daño al personaje " + atacado.getNombre() + " de la celda " + pos.toStringMapa() + " (civ " + pers.get(0).getCivilizacion().getNombre() + ").");
                System.out.println("El personaje: " + atacado.getNombre() + " ha muerto");
            } else {
                System.out.println("Has inflingido " + quitados + " de daño al personaje " + atacado.getNombre() + " de la celda " + pos.toStringMapa() + " (civ " + pers.get(0).getCivilizacion().getNombre() + ").");
            }
        }

        if (PuntosAQuitarACadaUno == 0 && civilizacion.getMapa().getCelda(pos).getEdificio() != null && civilizacion != civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("Has inflingido " + PuntosAQuitar + " al edificio " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + " de la civilizacion (" + civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion().getNombre() + ").");
            civilizacion.getMapa().getCelda(pos).getEdificio().danar(PuntosAQuitar);

        }

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
