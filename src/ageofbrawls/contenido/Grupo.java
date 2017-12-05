/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Mapa;
import ageofbrawls.plataforma.Posicion;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Santiago
 */
public class Grupo {

    private ArrayList<Personaje> personajes;
    private int salud, defensa, ataque, capRec, cantRecMadera, cantRecPiedra, cantRecComida;
    private Posicion posicion;
    private Civilizacion civilizacion;
    private String nombre;
    private boolean haySoldado;

    public Grupo(ArrayList<Personaje> personajes, Posicion posicion, String nombre, Civilizacion civilizacion) {

        if (posicion != null && nombre != null && personajes != null && civilizacion != null) {
            this.personajes = new ArrayList<>(personajes);
            this.posicion = new Posicion(posicion);
            this.nombre = nombre;
            this.civilizacion = civilizacion;
            for (int i = 0; i < (this.personajes.size()); i++) {
                this.personajes.get(i).setGrupo(this);
                if (this.personajes.get(i).getTipo() == Personaje.SOLDADO) {
                    this.haySoldado = true;
                    defensa += this.personajes.get(i).getDefensa();
                    ataque += this.personajes.get(i).getAtaque();

                } else {
                    defensa += this.personajes.get(i).getDefensa();
                    capRec += this.personajes.get(i).getCapRec();
                    cantRecMadera += this.personajes.get(i).getCantRecMadera();
                    cantRecPiedra += this.personajes.get(i).getCantRecPiedra();
                    cantRecComida += this.personajes.get(i).getCantRecComida();
                }
            }
        } else {
            System.out.println("Error creando grupo");
        }
    }

    public int getSalud() {
        return salud;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getCapRec() {
        return capRec;
    }

    public int getCantRecMadera() {
        return cantRecMadera;
    }

    public boolean getHaySoldado() {
        return haySoldado;
    }

    public int getCantRecPiedra() {
        return cantRecPiedra;
    }

    public int getCantRecComida() {
        return cantRecComida;
    }

    public int getCantRecTotal() {
        int sumaRec = 0;
        for (int i = 0; i < this.getPersonajes().size(); i++) {
            sumaRec += this.getPersonajes().get(i).getCantRecTotal();
        }
        return sumaRec;
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

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public void setCapRec(int capRec) {
        if (capRec >= 0) {
            this.capRec = capRec;
        } else {
            System.out.println("Error seteando la capacidad");
        }
    }

    public void setCantRecMadera(int cantRecMadera) {
        if (cantRecMadera >= 0) {
            this.cantRecMadera = cantRecMadera;
        } else {
            System.out.println("Error seteando la cantidad de madera recolectada");
        }

    }

    public void setCantRecPiedra(int cantRecPiedra) {
        if (cantRecPiedra >= 0) {
            this.cantRecPiedra = cantRecPiedra;
        } else {
            System.out.println("Error seteando la cantidad de piedra recolectada");
        }
    }

    public void setCantRecComida(int cantRecComida) {
        if (cantRecComida >= 0) {
            this.cantRecComida = cantRecComida;
        } else {
            System.out.println("Error seteando la cantidad de comida recolectada");
        }
    }

    public void setPosicion(Posicion posicion) {
        if (posicion != null) {
            this.posicion = new Posicion(posicion);
            actualizarPosiciones();
        } else {
            System.out.println("Error: posicion introducida errónea");
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
        System.out.println("Armadura :" + defensa);
        if (this.haySoldado) {
            System.out.println("Ataque :" + ataque);
        }
        if (!this.haySoldado) {
            System.out.println("Capacidad de recoleccion del grupo:" + capRec);
            System.out.println("Cantidad de madera que transporta: " + cantRecMadera);
            System.out.println("Cantidad de comida que transporta: " + cantRecComida);
            System.out.println("Cantidad de piedra que transporta: " + cantRecPiedra);
            System.out.println("Cantidad de recursos que lleva: " + (cantRecMadera + cantRecComida + cantRecPiedra));

        }
    }

    private void mover(Posicion posicion) {
        if (civilizacion.getMapa() == null || posicion == null) {
            System.out.println("Error en mover.");
            return;
        }
        if (civilizacion.getMapa().perteneceAMapa(posicion) && civilizacion.getMapa().getCelda(posicion).esCeldaLibre(false)) {
            civilizacion.getMapa().getCelda(this.posicion).removeGrupo(this);
            if (civilizacion.getMapa().getCelda(this.posicion).getEdificio() != null) {
                Edificio edif = civilizacion.getMapa().getCelda(this.posicion).getEdificio();
                edif.setCapAloj(this.personajes.size(), true);
                edif.setDefensa(-this.defensa, true);
                edif.setAtaque(-ataque, true);
            }
            this.posicion = posicion;
            actualizarPosiciones();
            civilizacion.getMapa().getCelda(posicion).addGrupo(this);
            civilizacion.makeAdyVisible(posicion);
            System.out.println();
            civilizacion.getMapa().imprimirCabecera();
            civilizacion.getMapa().imprimir(civilizacion);
            if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
                Edificio edif = civilizacion.getMapa().getCelda(this.posicion).getEdificio();
                edif.setCapAloj(this.personajes.size(), true);
                edif.setDefensa(-this.defensa, true);
                edif.setAtaque(-ataque, true);
            }
        } else {
            System.out.println("Error: No te puedes mover a esa celda.");
        }
    }

    public void mover(String direccion) {
        mover(posicion.getAdy(direccion)); //chequeos de nulo en getAdy y en mover
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

        if (this.getCantRecTotal() == this.capRec) {
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
                this.setCantRecMadera(this.getCantRecMadera() + recolectando);
                for (int i = 0; i < this.getPersonajes().size(); i++) {
                    if (recolectando == 0) {
                        return;
                    }
                    this.getPersonajes().get(i);

                    if (this.getPersonajes().get(i).getTipo() == Personaje.PAISANO) {
                        int recolect = (this.getPersonajes().get(i).getCapRec() - this.getPersonajes().get(i).getCantRecTotal());
                        if (recolectando > recolect) {
                            this.getPersonajes().get(i).setCantRecMadera(this.getPersonajes().get(i).getCantRecMadera() + recolect);
                            recolectando = recolectando - recolect;
                        } else {
                            this.getPersonajes().get(i).setCantRecMadera(this.getPersonajes().get(i).getCantRecMadera() + recolectando);
                            recolectando = 0;
                        }

                    }
                }
                break;
            case ContenedorRecurso.ARBUSTO:
                System.out.println("Has recolectado " + recolectando + " unidades de comida");
                this.setCantRecComida(this.getCantRecComida() + recolectando);
                for (int i = 0; i < this.getPersonajes().size(); i++) {
                    if (recolectando == 0) {
                        return;
                    }

                    if (this.getPersonajes().get(i).getTipo() == Personaje.PAISANO) {
                        int recolect = (this.getPersonajes().get(i).getCapRec() - this.getPersonajes().get(i).getCantRecTotal());
                        if (recolectando > recolect) {
                            this.getPersonajes().get(i).setCantRecComida(this.getPersonajes().get(i).getCantRecComida() + recolect);
                            recolectando = recolectando - recolect;
                        } else {
                            this.getPersonajes().get(i).setCantRecComida(this.getPersonajes().get(i).getCantRecComida() + recolectando);
                            recolectando = 0;
                        }

                    }
                }
                break;
            case ContenedorRecurso.CANTERA:
                System.out.println("Has recolectado " + recolectando + " unidades de piedra");
                this.setCantRecPiedra(this.getCantRecPiedra() + recolectando);
                for (int i = 0; i < this.getPersonajes().size(); i++) {
                    if (recolectando == 0) {
                        return;
                    }

                    if (this.getPersonajes().get(i).getTipo() == Personaje.PAISANO) {
                        int recolect = (this.getPersonajes().get(i).getCapRec() - this.getPersonajes().get(i).getCantRecTotal());
                        if (recolectando > recolect) {
                            this.getPersonajes().get(i).setCantRecPiedra(this.getPersonajes().get(i).getCantRecPiedra() + recolect);
                            recolectando = recolectando - recolect;
                        } else {
                            this.getPersonajes().get(i).setCantRecPiedra(this.getPersonajes().get(i).getCantRecPiedra() + recolectando);
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
        if (this.cantRecMadera > 0) {
            civilizacion.setMadera(this.cantRecMadera, true);
            System.out.println("Almacenadas " + this.cantRecMadera + " unidades de madera en la ciudadela");
            this.setCantRecMadera(0);
        }
        if (this.cantRecPiedra > 0) {
            civilizacion.setPiedra(this.cantRecPiedra, true);
            System.out.println("Almacenadas " + this.cantRecPiedra + " unidades de piedra en la ciudadela");
            this.setCantRecPiedra(0);
        }
        if (this.cantRecComida > 0) {
            civilizacion.setAlimentos(this.cantRecComida, true);
            System.out.println("Almacenadas " + this.cantRecComida + " unidades de alimento en la ciudadela");
            this.setCantRecComida(0);
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
        this.posicion=pos;
        civilizacion.getMapa().getCelda(pos).addGrupo(this);
        civilizacion.getMapa().getCelda(pos).getEdificio().setAtaque(this.getAtaque(), true);
        civilizacion.getMapa().getCelda(pos).getEdificio().setDefensa(this.getDefensa(), true);
        civilizacion.getMapa().getCelda(pos).getEdificio().setCapAloj(-(this.getPersonajes().size()), true);
        System.out.println("El " + this.getNombre() + " ha entrado en " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + " (capacidad restante " + civilizacion.getMapa().getCelda(pos).getEdificio().getCapAloj1() + ").");
        for (int i = 0; i < this.getPersonajes().size(); i++) {
            this.getPersonajes().get(i).recuperarVida();
        }
        civilizacion.makeAdyVisible(pos);
        System.out.println();
        civilizacion.getMapa().imprimirCabecera();
        civilizacion.getMapa().imprimir(civilizacion);

    }

    public void atacar(String direccion) {
        Posicion pos = posicion.getAdy(direccion);
        if (direccion == null || pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos) || civilizacion.getMapa().getCelda(pos).getEdificio() == null) {
            System.out.println("No hay edificio en la posición indicada.");
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
        System.out.println("Has inflingido " + PuntosAQuitar + " a los miembros de la civilización (" + pers.get(0).getCivilizacion() + ").");
        int PuntosAQuitarACadaUno;
        if (pers.isEmpty()) {
            PuntosAQuitarACadaUno = 0;
        } else {
            PuntosAQuitarACadaUno = (int) (PuntosAQuitar / pers.size());
        }
        for (int i = 0; i < pers.size(); i++) {
            Personaje atacado = pers.get(i);
            if (pers.get(i).getTipo() == Personaje.PAISANO) {
                atacado.setSalud(-(PuntosAQuitarACadaUno), true);
            } else {
                atacado.setSalud(-(int) ((double) PuntosAQuitarACadaUno * 0.5), true);
            }
            if (atacado.getSalud() <= 0) {
                System.out.println("El personaje: " + atacado.getNombre() + " ha muerto");
                if (atacado.getGrupo() != null) {
                    atacado.getGrupo().desligar(atacado);
                }
                civilizacion.getMapa().getCelda(pos).getPersonajes().remove(atacado);
                civilizacion.getPersonajes().remove(atacado.getNombre());
                atacado.getGrupo().setCantRecComida(atacado.getGrupo().getCantRecComida() - cantRecComida);
                atacado.getGrupo().setCantRecPiedra(atacado.getGrupo().getCantRecPiedra() - cantRecPiedra);
                atacado.getGrupo().setCantRecMadera(atacado.getGrupo().getCantRecMadera() - cantRecMadera);
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
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.personajes);
        hash = 97 * hash + this.salud;
        hash = 97 * hash + this.defensa;
        hash = 97 * hash + this.ataque;
        hash = 97 * hash + this.capRec;
        hash = 97 * hash + this.cantRecMadera;
        hash = 97 * hash + this.cantRecPiedra;
        hash = 97 * hash + this.cantRecComida;
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
        if (this.salud != other.salud) {
            return false;
        }
        if (this.defensa != other.defensa) {
            return false;
        }
        if (this.ataque != other.ataque) {
            return false;
        }
        if (this.capRec != other.capRec) {
            return false;
        }
        if (this.cantRecMadera != other.cantRecMadera) {
            return false;
        }
        if (this.cantRecPiedra != other.cantRecPiedra) {
            return false;
        }
        if (this.cantRecComida != other.cantRecComida) {
            return false;
        }
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
    
}
