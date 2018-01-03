/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.Personajes;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.plataforma.Celda;
import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Mapa;
import ageofbrawls.plataforma.Posicion;
import ageofbrawls.contenido.Personajes.Soldados.Caballero;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Santiago
 */
public class Grupo extends Personaje {

    private ArrayList<Personaje> personajes;
    private boolean haySoldado;

    public Grupo(ArrayList<Personaje> personajes, Posicion posicion, String nombre, Civilizacion civilizacion) {
        super(posicion, nombre, civilizacion);
        if (posicion != null && nombre != null && personajes != null && civilizacion != null) {
            this.personajes = new ArrayList<>(personajes);
            for (int i = 0; i < this.personajes.size(); i++) {
                personajes.get(i).setGrupo(this);
                if (this.personajes.get(i) instanceof Soldado) {
                    this.haySoldado = true;
                }
            }
        } else {
            System.out.println("Error creando grupo");
        }
    }

    @Override
    public int getSalud() {
        int salud = 0;
        for (Personaje p : personajes) {
            salud += p.getSalud();
        }
        return salud;
    }

    @Override
    public int getDefensa() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getDefensa();
        }
        return valor;
    }

    @Override
    public int danhoAtaque() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.danhoAtaque();
        }
        return valor;
    }

    @Override
    public int getCapRec() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getCapRec();
        }
        return valor;
    }

    public int capacidadMovimiento() {
        for (Personaje p : personajes) {
            if (!(p instanceof Caballero)) {
                return 1;
            }
        }
        return 2;
    }

    @Override
    public int getCantRecMadera() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getCantRecMadera();
        }
        return valor;
    }

    public boolean getHaySoldado() {
        return haySoldado;
    }

    @Override
    public int getCantRecPiedra() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getCantRecPiedra();
        }
        return valor;
    }

    @Override
    public int getCantRecComida() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getCantRecComida();
        }
        return valor;
    }

    @Override
    public int getCantRecTotal() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getCantRecTotal();
        }
        return valor;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    @Override
    public void setGrupo(Grupo grupo) {
        System.out.println("Error");
    }

    @Override
    public void setPosicion(Posicion posicion) {
        super.setPosicion(posicion);
        actualizarPosiciones();
    }

    @Override
    public void vaciarCantRecComida() {
        for (Personaje p : personajes) {
            if (p instanceof Paisano) {
                ((Paisano) p).setCantRecComida(0);
            }
        }
    }

    @Override
    public void vaciarCantRecMadera() {
        for (Personaje p : personajes) {
            if (p instanceof Paisano) {
                ((Paisano) p).setCantRecMadera(0);
            }
        }
    }

    @Override
    public void vaciarCantRecPiedra() {
        for (Personaje p : personajes) {
            if (p instanceof Paisano) {
                ((Paisano) p).setCantRecPiedra(0);
            }
        }
    }

    public boolean estaFormadoPor(Class clase) {
        for (Personaje p : personajes) {
            if (!clase.isInstance(p)) {
                return false;
            }
        }
        return true;
    }

    public void desligar(Personaje personaje) {
        if (personaje == null) {
            System.out.println("El personaje no existe");
            return;
        }
        if (!this.getPersonajes().contains(personaje)) {
            System.out.println("El personaje no pertenece al grupo seleccionado");
            return;
        }
        Civilizacion civilizacion = getCivilizacion();
        Posicion posicion = getPosicion();
        this.getPersonajes().remove(personaje);
        personaje.setGrupo(null);
        civilizacion.getMapa().getCelda(posicion).getPersonajes().add(personaje);
        revisarVacio();
        civilizacion.getMapa().imprimirCabecera();
        civilizacion.getMapa().imprimir(civilizacion);
        System.out.println(personaje.getNombre() + " desligado de " + getNombre());

    }

    public void desagrupar() {
        Civilizacion civilizacion = getCivilizacion();
        Posicion posicion = getPosicion();
        for (Personaje p : personajes) {
            civilizacion.getMapa().getCelda(posicion).getPersonajes().add(p);
            p.setGrupo(null);
        }
        civilizacion.getMapa().getCelda(posicion).removeGrupo(this);
        getPersonajes().clear();
        civilizacion.getGrupos().remove(getNombre());
        civilizacion.getMapa().imprimirCabecera();
        civilizacion.getMapa().imprimir(civilizacion);
        System.out.println(getNombre() + " desagrupado.");
    }

    @Override
    public void describir() {
        System.out.println("Nombre del grupo: " + getNombre());
        System.out.println("Civilizacion: " + getCivilizacion().getNombre());
        System.out.println("Armadura :" + getDefensa());
        if (this.haySoldado) {
            System.out.println("Ataque :" + danhoAtaque());
        }
        if (!this.haySoldado) {
            System.out.println("Capacidad de recoleccion del grupo:" + getCapRec());
            System.out.println("Cantidad de madera que transporta: " + getCantRecMadera());
            System.out.println("Cantidad de comida que transporta: " + getCantRecComida());
            System.out.println("Cantidad de piedra que transporta: " + getCantRecPiedra());
            System.out.println("Cantidad de recursos que lleva: " + (getCantRecTotal()));
        }
        System.out.println("En este grupo están los siguientes personajes: ");
        for (Personaje p : personajes) {
            p.describir();
        }
    }

    @Override
    public void mover(String direccion) {
        try {
            Posicion posicion = getPosicion().getAdy(direccion);
            Mapa mapa = getCivilizacion().getMapa();
            Celda celdaAntigua = mapa.getCelda(getPosicion());

            moverGenerico(posicion, personajes.size());
            celdaAntigua.removeGrupo(this);
            actualizarPosiciones();
            mapa.getCelda(posicion).addGrupo(this);

            System.out.println();
            mapa.imprimirCabecera();
            mapa.imprimir(getCivilizacion());
        } catch (Exception ex) {
            //Logger.getLogger(Grupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recolectar(String direccion) {
        Mapa mapa = getCivilizacion().getMapa();
        if (direccion == null) {
            System.out.println("Error en recolectar.");
            return;
        }
        if (this.haySoldado) {
            System.out.println("Como hay un soldado en el grupo, este grupo no puede recolectar");
            return;
        }
        Posicion posicion = getPosicion();
        Civilizacion civilizacion = getCivilizacion();
        Posicion pos = posicion.getAdy(direccion);
        ContenedorRecurso contenedor = mapa.getCelda(pos).getContenedorRec();
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("No se puede recolectar desde un edificio");
            return;
        }
        if (this.getCantRecTotal() == getCapRec()) {
            System.out.println(this.getNombre() + " no puede recolectar más");
            return;
        }
        if (contenedor == null) {
            System.out.println("Error: La celda destino no es un contenedor de recursos.");
            return;
        }
        int recolectando = Math.min(getCapRec() - this.getCantRecTotal(), contenedor.getRecurso().getCantidad()), tipoC = contenedor.getTipo();
        if (contenedor.getRecurso().getCantidad() - recolectando == 0) {
            mapa.getCelda(pos).setTipoCont(Celda.PRADERA);
        }
        contenedor.getRecurso().setCantidad(contenedor.getRecurso().getCantidad() - recolectando);
        if (mapa.getCelda(pos).getContenedorRec() == null) { //si se ha vuelto pradera, imprimo
            mapa.imprimir(civilizacion);
        }
        switch (tipoC) {
            case ContenedorRecurso.BOSQUE:
                System.out.println("Has recolectado " + recolectando + " unidades de madera");
                for (int i = 0; i < this.getPersonajes().size(); i++) {
                    if (this.getPersonajes().get(i) instanceof Paisano) {
                        int loQuePuedeRec = (this.getPersonajes().get(i).getCapRec() - this.getPersonajes().get(i).getCantRecTotal());
                        if (recolectando > loQuePuedeRec) {
                            ((Paisano) this.getPersonajes().get(i)).setCantRecMadera(this.getPersonajes().get(i).getCantRecMadera() + loQuePuedeRec);
                            recolectando = recolectando - loQuePuedeRec;
                        } else {
                            ((Paisano) this.getPersonajes().get(i)).setCantRecMadera(this.getPersonajes().get(i).getCantRecMadera() + recolectando);
                            recolectando = 0;
                        }

                    }
                }
                break;
            case ContenedorRecurso.ARBUSTO:
                System.out.println("Has recolectado " + recolectando + " unidades de comida");
                for (int i = 0; i < this.getPersonajes().size(); i++) {
                    if (this.getPersonajes().get(i) instanceof Paisano) {
                        int recolect = (this.getPersonajes().get(i).getCapRec() - this.getPersonajes().get(i).getCantRecTotal());
                        if (recolectando > recolect) {
                            ((Paisano) this.getPersonajes().get(i)).setCantRecComida(this.getPersonajes().get(i).getCantRecComida() + recolect);
                            recolectando = recolectando - recolect;
                        } else {
                            ((Paisano) this.getPersonajes().get(i)).setCantRecComida(this.getPersonajes().get(i).getCantRecComida() + recolectando);
                            recolectando = 0;
                        }

                    }
                }
                break;
            case ContenedorRecurso.CANTERA:
                System.out.println("Has recolectado " + recolectando + " unidades de piedra");
                for (int i = 0; i < this.getPersonajes().size(); i++) {
                    if (recolectando == 0) {
                        return;
                    }

                    if (this.getPersonajes().get(i) instanceof Paisano) {
                        int recolect = (this.getPersonajes().get(i).getCapRec() - this.getPersonajes().get(i).getCantRecTotal());
                        if (recolectando > recolect) {
                            ((Paisano) this.getPersonajes().get(i)).setCantRecPiedra(this.getPersonajes().get(i).getCantRecPiedra() + recolect);
                            recolectando = recolectando - recolect;
                        } else {
                            ((Paisano) this.getPersonajes().get(i)).setCantRecPiedra(this.getPersonajes().get(i).getCantRecPiedra() + recolectando);
                            recolectando = 0;
                        }

                    }
                }
                break;
        }
    }

    public void almacenar(String direccion) {
        if (this.haySoldado) {
            System.out.println("Como hay un soldado en el grupo, este grupo no puede almacenar");
            return;
        }
        almacenarGenerico(direccion);
    }

    @Override
    public void construir(String tipoC, String dir) {
        if (tipoC == null || dir == null) {
            System.out.println("Error en consEdif.");
            return;
        }
        if (this.haySoldado) {
            System.out.println("Como hay un soldado en el grupo, este grupo no puede construir");
            return;
        }
        construirGenerico(tipoC, dir);
    }

    @Override
    public void reparar(Posicion pos) {
        if (this.haySoldado) {
            System.out.println("Como hay un soldado en el grupo, este grupo no puede recolectar");
            return;
        }
        repararGenerico(pos);
    }

    @Override
    public void defender(String direccion) {
        Posicion posicion = getPosicion();
        Civilizacion civilizacion = getCivilizacion();
        Posicion pos = posicion.getAdy(direccion);
        if (direccion == null || pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos) || civilizacion.getMapa().getCelda(pos).getEdificio() == null) {
            System.out.println("No hay edificio en la posición indicada.");
            return;
        }
        if (civilizacion != civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("El grupo no puede entrar en el edificio de la otra civilización");
            return;
        }
        if (civilizacion.getMapa().getCelda(pos).getEdificio().getCapAloj1() < this.getPersonajes().size()) {
            System.out.println("No se puede mover el grupo. El número " + this.getPersonajes().size() + "de componentes del grupo (" + this.getNombre() + ") supera la capacidad de alojamiento actual (" + civilizacion.getMapa().getCelda(pos).getEdificio().getCapAloj1() + ") de " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + ".");
            return;
        }

        civilizacion.getMapa().getCelda(posicion).removeGrupo(this);
        super.setPosicion(pos);
        actualizarPosiciones();
        civilizacion.makeAdyVisible(pos);
        civilizacion.getMapa().getCelda(pos).addGrupo(this);
        civilizacion.getMapa().getCelda(pos).getEdificio().setCapAloj(-(this.getPersonajes().size()), true);
        civilizacion.getMapa().imprimirCabecera();
        civilizacion.getMapa().imprimir(civilizacion);
        System.out.println("El " + this.getNombre() + " ha entrado en " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + " (capacidad restante " + civilizacion.getMapa().getCelda(pos).getEdificio().getCapAloj1() + ").");
        for (int i = 0; i < this.getPersonajes().size(); i++) {
            this.getPersonajes().get(i).recuperarVida();
        }
    }

    @Override
    public void atacar(String direccion) {
        if (!this.haySoldado) {
            System.out.println("El grupo no tiene soldados y no puede atacar");
            return;
        }
        atacarGenerico(direccion);
    }

    private void actualizarPosiciones() {
        for (Personaje p : personajes) {
            p.setPosicion(super.getPosicion());
        }
    }

    private void revisarVacio() {
        if (getPersonajes().size() == 1) {
            desligar(personajes.get(0));
        } else if (this.getPersonajes().isEmpty()) {
            Civilizacion civilizacion = getCivilizacion();
            Posicion posicion = getPosicion();
            civilizacion.getMapa().getCelda(posicion).getGrupos().remove(this);
            civilizacion.getGrupos().remove(getNombre());
            civilizacion.getMapa().getCelda(posicion).setHaygrupo(!civilizacion.getMapa().getCelda(posicion).getGrupos().isEmpty());
        }
    }

    @Override
    public String toString() {
        return "grupo";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.personajes);
        hash = 97 * hash + (this.haySoldado ? 1 : 0);
        return hash;
    }

    @Override
    public void recuperarVida() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void recuperar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
