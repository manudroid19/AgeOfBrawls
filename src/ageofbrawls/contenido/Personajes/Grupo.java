/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.Personajes;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.contenido.Personajes.Personaje;
import ageofbrawls.plataforma.Celda;
import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Mapa;
import ageofbrawls.plataforma.Posicion;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Santiago
 */
public class Grupo extends Personaje {

    private ArrayList<Personaje> personajes;
    private Posicion posicion;
    private Civilizacion civilizacion;
    private String nombre;
    private boolean haySoldado;

    public Grupo(ArrayList<Personaje> personajes, Posicion posicion, String nombre, Civilizacion civilizacion) {
        super(posicion, nombre, civilizacion);
        if (posicion != null && nombre != null && personajes != null && civilizacion != null) {
            this.personajes = new ArrayList<>(personajes);
            this.posicion = new Posicion(posicion);
            this.nombre = nombre;
            this.civilizacion = civilizacion;
            for (int i = 0; i < (this.personajes.size()); i++) {
                personajes.get(i).setGrupo(this);
                if (this.personajes.get(i) instanceof Soldado) {
                    this.haySoldado = true;
                }
            }
        } else {
            System.out.println("Error creando grupo");
        }
    }

    public int getSalud() {
        int salud = 0;
        for (Personaje p : personajes) {
            salud += p.getSalud();
        }
        return salud;
    }

    public int getDefensa() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getDefensa();
        }
        return valor;
    }

    public int getAtaque() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getAtaque();
        }
        return valor;
    }

    public int getCapRec() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getCapRec();
        }
        return valor;
    }

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

    public int getCantRecPiedra() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getCantRecPiedra();
        }
        return valor;
    }

    public int getCantRecComida() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getCantRecComida();
        }
        return valor;
    }

    public int getCantRecTotal() {
        int valor = 0;
        for (Personaje p : personajes) {
            valor += p.getCantRecTotal();
        }
        return valor;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public Civilizacion getCivilizacion() {
        return civilizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void setGrupo(Grupo grupo) {
        System.out.println("Error");
    }

    public void setPosicion(Posicion posicion) {
        if (posicion != null) {
            this.posicion = new Posicion(posicion);
            actualizarPosiciones();
        } else {
            System.out.println("Error: posicion introducida errónea");
        }
    }

    public void vaciarCantRecComida() {
        for (Personaje p : personajes) {
            if (p instanceof Paisano) {
                ((Paisano) p).setCantRecComida(0);
            }
        }
    }

    public void vaciarCantRecMadera() {
        for (Personaje p : personajes) {
            if (p instanceof Paisano) {
                ((Paisano) p).setCantRecMadera(0);
            }
        }
    }

    public void vaciarCantRecPiedra() {
        for (Personaje p : personajes) {
            if (p instanceof Paisano) {
                ((Paisano) p).setCantRecPiedra(0);
            }
        }
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
        this.getPersonajes().remove(personaje);
        personaje.setGrupo(null);
        civilizacion.getMapa().getCelda(posicion).getPersonajes().add(personaje);
        revisarVacio();
        civilizacion.getMapa().imprimirCabecera();
        civilizacion.getMapa().imprimir(civilizacion);
        System.out.println(personaje.getNombre() + " desligado de " + nombre);

    }

    public void desagrupar() {
        for (Personaje p : personajes) {
            civilizacion.getMapa().getCelda(posicion).getPersonajes().add(p);
            p.setGrupo(null);
        }
        civilizacion.getMapa().getCelda(posicion).removeGrupo(this);
        getPersonajes().clear();
        civilizacion.getGrupos().remove(this.nombre);
        civilizacion.getMapa().imprimirCabecera();
        civilizacion.getMapa().imprimir(civilizacion);
        System.out.println(nombre + " desagrupado.");
    }

    public void describirGrupo() {

        System.out.println("Nombre del grupo: " + nombre);
        System.out.println("Civilizacion: " + civilizacion.getNombre());
        System.out.println("Armadura :" + getDefensa());
        if (this.haySoldado) {
            System.out.println("Ataque :" + getAtaque());
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
            p.describirPersonaje();
        }
    }

    public void mover(String direccion) {
        try {
            Posicion posicion = this.posicion.getAdy(direccion);
            Mapa mapa = civilizacion.getMapa();
            Celda celdaAntigua = mapa.getCelda(this.posicion);
            
            moverGenerico(posicion, personajes.size());
            celdaAntigua.removeGrupo(this);
            actualizarPosiciones();
            mapa.getCelda(posicion).addGrupo(this);
            
            System.out.println();
            mapa.imprimirCabecera();
            mapa.imprimir(civilizacion);
        } catch (Exception ex) {
            //Logger.getLogger(Grupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recolectar(Mapa mapa, String direccion) {
        if (mapa == null || direccion == null) {
            System.out.println("Error en recolectar.");
            return;
        }
        if (this.haySoldado) {
            System.out.println("Como hay un soldado en el grupo, este grupo no puede recolectar");
            return;
        }

        Posicion pos = posicion.getAdy(direccion);
        ContenedorRecurso contenedor = mapa.getCelda(pos).getContenedorRec();
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El grupo está en un edificio y por tanto no puede recolectar");
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
        int recolectando = Math.min(getCapRec() - this.getCantRecTotal(), contenedor.getCantidad()), tipoC = contenedor.getTipo();
        if (contenedor.getCantidad() - recolectando == 0) {
            mapa.getCelda(pos).setTipoCont(0);
        }
        contenedor.setCantidad(contenedor.getCantidad() - recolectando);
        if (mapa.getCelda(pos).getContenedorRec() == null) { //si se ha vuelto pradera, imprimo
            mapa.imprimir(civilizacion);
        }
        switch (tipoC) {
            case ContenedorRecurso.BOSQUE:
                System.out.println("Has recolectado " + recolectando + " unidades de madera");
                for (int i = 0; i < this.getPersonajes().size(); i++) {
                    if (recolectando == 0) {
                        return;
                    }
                    this.getPersonajes().get(i);

                    if (this.getPersonajes().get(i) instanceof Paisano) {
                        int recolect = (this.getPersonajes().get(i).getCapRec() - this.getPersonajes().get(i).getCantRecTotal());
                        if (recolectando > recolect) {
                            ((Paisano) this.getPersonajes().get(i)).setCantRecMadera(this.getPersonajes().get(i).getCantRecMadera() + recolect);
                            recolectando = recolectando - recolect;
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
                    if (recolectando == 0) {
                        return;
                    }

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

    public void almacenar(Mapa mapa, String direccion) {
        if (mapa == null || direccion == null) {
            System.out.println("Error en almacenar.");
            return;
        }
        if (this.haySoldado) {
            System.out.println("Como hay un soldado en el grupo, este grupo no puede almacenar");
            return;
        }

        Posicion pos = posicion.getAdy(direccion);
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El grupo está en un edificio y por tanto no puede almacenar");
            return;
        }

        if (mapa.getCelda(pos).getEdificio() == null || mapa.getCelda(pos).getEdificio().getTipo() != Edificio.CIUDADELA) {
            System.out.println("No se puede almacenar recursos en esa celda");
            return;
        }
        if (this.getCantRecTotal() <= 0) {
            System.out.println("El grupo no transporta recursos");
            return;
        }
        if (this.getCantRecMadera() > 0) {
            civilizacion.setMadera(this.getCantRecMadera(), true);
            System.out.println("Almacenadas " + this.getCantRecMadera() + " unidades de madera en la ciudadela");
            this.vaciarCantRecMadera();
        }
        if (this.getCantRecPiedra() > 0) {
            civilizacion.setPiedra(this.getCantRecPiedra(), true);
            System.out.println("Almacenadas " + this.getCantRecPiedra() + " unidades de piedra en la ciudadela");
            this.vaciarCantRecPiedra();
        }
        if (this.getCantRecComida() > 0) {
            civilizacion.setAlimentos(this.getCantRecComida(), true);
            System.out.println("Almacenadas " + this.getCantRecComida() + " unidades de alimento en la ciudadela");
            this.vaciarCantRecComida();
        }
    }

    public void consEdif(String tipoC, String dir, Civilizacion civilizacion) {
        if (civilizacion.getMapa() == null || tipoC == null || dir == null) {
            System.out.println("Error en consEdif.");
            return;
        }

        if (this.haySoldado) {
            System.out.println("Como hay un soldado en el grupo, este grupo no puede construir");
            return;
        }

        Posicion posConstruir = posicion.getAdy(dir);
        if (posConstruir.equals(posicion) || !civilizacion.getMapa().perteneceAMapa(posConstruir) || !civilizacion.getMapa().getCelda(posConstruir).esCeldaLibre(true)) { //direccion no valida
            System.out.println("Error: No se puede contruir en la celda de destino.");
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El grupo está en un edificio y por tanto no puede construir");
            return;
        }
        switch (tipoC) {
            case "casa":
                if (civilizacion.getMadera() < 100 || civilizacion.getPiedra() < 100) {
                    System.out.println("No se puede construir! Se necesitan 100 de madera y piedra y tienes " + civilizacion.getMadera() + " y " + civilizacion.getPiedra());
                    return;
                }
                civilizacion.setPiedra(-100, true);
                civilizacion.setMadera(-100, true);
                Edificio edif = new Edificio(Edificio.CASA, posConstruir, "casa" + (civilizacion.contarEdificios(Edificio.CASA) + 1), civilizacion);
                civilizacion.getMapa().getCelda(posConstruir).setEdificio(edif);
                System.out.println();
                civilizacion.getEdificios().put(edif.getNombre(), edif);
                civilizacion.getMapa().imprimirCabecera();
                civilizacion.getMapa().imprimir(civilizacion);
                System.out.println("Casa construida en " + posConstruir);
                System.out.println("Coste: 100 de madera, 100 de piedra.");
                break;
            case "cuartel":
                if (civilizacion.getMadera() < 200 || civilizacion.getPiedra() < 200) {
                    System.out.println("No se puede construir! Se necesitan 200 de madera y piedra y tienes " + civilizacion.getMadera() + " y " + civilizacion.getPiedra());
                    return;
                }
                civilizacion.setPiedra(-200, true);
                civilizacion.setMadera(-200, true);
                Edificio cuart = new Edificio(Edificio.CUARTEL, posConstruir, "cuartel" + (civilizacion.contarEdificios(Edificio.CUARTEL) + 1), civilizacion);
                civilizacion.getMapa().getCelda(posConstruir).setEdificio(cuart);
                civilizacion.getEdificios().put(cuart.getNombre(), cuart);
                System.out.println();
                civilizacion.getMapa().imprimirCabecera();
                civilizacion.getMapa().imprimir(civilizacion);
                System.out.println("Cuartel construido en " + posConstruir);
                System.out.println("Coste: 200 de madera, 200 de piedra.");
                break;
            default:
                System.out.println("Error: tipo de construccion incorrecta.");
        }
    }

    public void reparar(Posicion pos) {
        if (pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos) || civilizacion.getMapa().getCelda(pos).getEdificio() == null || civilizacion.getMapa().getCelda(pos).getEdificio().getPs() == civilizacion.getMapa().getCelda(pos).getEdificio().getMaxVida()) {
            System.out.println("Nada que reparar.");
            return;
        }

        if (this.haySoldado) {
            System.out.println("Como hay un soldado en el grupo, este grupo no puede recolectar");
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El personaje está en un edificio y por tanto no puede recolectar");
            return;
        }

        int puntosAReparar = civilizacion.getMapa().getCelda(pos).getEdificio().getMaxVida() - civilizacion.getMapa().getCelda(pos).getEdificio().getPs();
        int costeMadera = (int) (puntosAReparar * 0.4);
        int costePiedra = (int) (puntosAReparar * 0.5);
        if (civilizacion.getMadera() < costeMadera || civilizacion.getPiedra() < costePiedra) {
            System.out.println("No tienes suficientes recursos disponibles!");
            return;
        }
        civilizacion.setMadera(-costeMadera, true);
        civilizacion.setPiedra(-costePiedra, true);
        civilizacion.getMapa().getCelda(pos).getEdificio().reparar();
        System.out.println("Reparación completada.");
        System.out.println("Coste de la reparación: " + costeMadera + " unidades de madera y " + costePiedra + " unidades de piedra de la ciudadela.");
    }

    public void defender(String direccion) {
        Posicion pos = posicion.getAdy(direccion);
        if (direccion == null || pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos) || civilizacion.getMapa().getCelda(pos).getEdificio() == null) {
            System.out.println("No hay edificio en la posición indicada.");
            return;
        }
        if (this.civilizacion != civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("El grupo no puede entrar en el edificio de la otra civilización");
            return;
        }
        if (civilizacion.getMapa().getCelda(pos).getEdificio().getCapAloj1() < this.getPersonajes().size()) {
            System.out.println("No se puede mover el grupo. El número " + this.getPersonajes().size() + "de componentes del grupo (" + this.getNombre() + ") supera la capacidad de alojamiento actual (" + civilizacion.getMapa().getCelda(pos).getEdificio().getCapAloj1() + ") de " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + ".");
            return;
        }

        civilizacion.getMapa().getCelda(this.posicion).removeGrupo(this);
        this.posicion = pos;
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

    public void atacar(String direccion) {
        Posicion pos = posicion.getAdy(direccion);
        if (direccion == null || pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos)) {
            System.out.println("No es una celda válida.");
            return;
        }

        if (!this.haySoldado) {
            System.out.println("El grupo no tiene soldados y no puede atacar");
            return;
        }
        if (civilizacion.getMapa().getCelda(pos).getEdificio() == null && !civilizacion.getMapa().getCelda(pos).isHayGrupo() && civilizacion.getMapa().getCelda(pos).getPersonajes().isEmpty()) {
            System.out.println("En esa celda no hay nada a lo que se le pueda atacar");
            return;
        }
        if (civilizacion.getMapa().getCelda(pos).getEdificio() != null && this.civilizacion == civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("El grupo de soldados no puede atacar a un edificio de su propia civilización");
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
        if (!pers.isEmpty()) {
            System.out.println("Has inflingido " + PuntosAQuitar + " a los miembros de la civilización (" + pers.get(0).getCivilizacion().getNombre() + ").");
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

        if (PuntosAQuitarACadaUno == 0 && civilizacion.getMapa().getCelda(pos).getEdificio() != null && this.civilizacion != civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("Has inflingido " + PuntosAQuitar + " al edificio " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + " de la civilizacion (" + civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion().getNombre() + ").");
            civilizacion.getMapa().getCelda(pos).getEdificio().danar(PuntosAQuitar);
        }

    }

    private void actualizarPosiciones() {
        for (Personaje p : personajes) {
            p.setPosicion(posicion);
        }
    }

    public void revisarVacio() {
        if (getPersonajes().size() == 1) {
            desligar(personajes.get(0));
        } else if (this.getPersonajes().isEmpty()) {
            civilizacion.getMapa().getCelda(posicion).getGrupos().remove(this);
            civilizacion.getGrupos().remove(this.nombre);
            civilizacion.getMapa().getCelda(posicion).setHaygrupo(!civilizacion.getMapa().getCelda(posicion).getGrupos().isEmpty());
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.personajes);
        hash = 97 * hash + Objects.hashCode(this.posicion);
        hash = 97 * hash + Objects.hashCode(this.civilizacion);
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + (this.haySoldado ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grupo other = (Grupo) obj;
        if (this.haySoldado != other.haySoldado) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.personajes, other.personajes)) {
            return false;
        }
        if (!Objects.equals(this.posicion, other.posicion)) {
            return false;
        }
        if (!Objects.equals(this.civilizacion, other.civilizacion)) {
            return false;
        }
        return true;
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
