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

/**
 *
 * @author Santiago
 */
public class Grupo {

    private ArrayList<Personaje> personajes;
    private int salud, armadura, ataque, capRec, cantRecMadera, cantRecPiedra, cantRecComida;
    private Posicion posicion;
    private Civilizacion civilizacion;
    private String nombre;

    public Grupo(ArrayList<Personaje> personajes, Posicion posicion, String nombre, Civilizacion civilizacion) {

        if (posicion != null && nombre != null && personajes != null && civilizacion != null) {
            this.personajes = new ArrayList<>(personajes);
            this.posicion = new Posicion(posicion);
            this.nombre = nombre;
            this.civilizacion = civilizacion;
            for (int i = 0; i < (this.personajes.size()); i++) {
                if (this.personajes.get(i).getTipo() == Personaje.SOLDADO) {
                    this.setCapRec(0);
                    this.setCantRecComida(0);
                    this.setCantRecMadera(0);
                    this.setCantRecPiedra(0);
                    armadura += this.personajes.get(i).getArmadura();
                    ataque += this.personajes.get(i).getAtaque();

                } else {
                    armadura += this.personajes.get(i).getArmadura();
                    capRec += this.personajes.get(i).getCapRec();
                    cantRecMadera += this.personajes.get(i).getCantRecMadera();
                    cantRecPiedra += this.personajes.get(i).getCantRecPiedra();
                    cantRecComida += this.personajes.get(i).getCantRecComida();
                }
            }
        } else {
            System.out.println("Error creandgo grupo");
        }
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

    public int getCantRecMadera() {
        return cantRecMadera;
    }

    public int getCantRecPiedra() {
        return cantRecPiedra;
    }

    public int getCantRecComida() {
        return cantRecComida;
    }

    public int getCantRecTotal() {
        return cantRecComida + cantRecMadera + cantRecPiedra;
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
        } else {
            System.out.println("Error: posicion introducida errónea");
        }
    }

    public void desligar(Personaje personaje) {
        if (personaje != null) {
            if (this.getPersonajes().contains(personaje)) {
                this.getPersonajes().remove(personaje);
                civilizacion.getMapa().getCelda(posicion).getPersonajes().add(personaje);
            } else {
                System.out.println("El personaje no pertenece al grupo seleccionado");
            }
        } else {
            System.out.println("El personaje no existe");
        }
    }

    public void desagrupar() {
        for(Personaje p : personajes){
            civilizacion.getMapa().getCelda(posicion).getPersonajes().add(p);
        }
        civilizacion.getMapa().getCelda(posicion).removeGrupo(this);
        getPersonajes().clear();
    }

    public void describirGrupo() {
        
        System.out.println("Nombre:" + nombre);
        System.out.println("Salud :" + salud);
        System.out.println("Armadura :" + armadura);
        System.out.println("Ataque :" + ataque);
        System.out.println("Capacidad de recoleccion del grupo:" + capRec);
        System.out.println("Cantidad de madera que transporta: " + cantRecMadera);
        System.out.println("Cantidad de comida que transporta: " + cantRecComida);
        System.out.println("Cantidad de piedra que transporta: " + cantRecPiedra);
        System.out.println("Cantidad de recursos que lleva: " + (cantRecMadera + cantRecComida + cantRecPiedra));

    }

    private void mover(Posicion posicion) {
        if (civilizacion.getMapa() == null || posicion == null) {
            System.out.println("Error en mover.");
            return;
        }
        if (civilizacion.getMapa().perteneceAMapa(posicion) && civilizacion.getMapa().getCelda(posicion).esCeldaLibre(false)) {
            civilizacion.getMapa().getCelda(this.posicion).removeGrupo(this);
            this.posicion = posicion;
            civilizacion.getMapa().getCelda(posicion).addGrupo(this);
            civilizacion.makeAdyVisible(posicion);
            System.out.println();
            civilizacion.getMapa().imprimirCabecera();
            civilizacion.getMapa().imprimir(civilizacion);
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
        for (int i = 0; i < this.personajes.size(); i++) {
            if (this.personajes.get(i).getTipo() == Personaje.SOLDADO) {
                System.out.println("Como hay un soldado en el grupo, este grupo no puede recolectar");
                return;
            }
        }
        Posicion pos = posicion.getAdy(direccion);
        ContenedorRecurso contenedor = mapa.getCelda(pos).getContenedorRec();
        if (pos.equals(posicion)) { //error con la posicion
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
                setCantRecMadera(getCantRecMadera() + recolectando);
                break;
            case ContenedorRecurso.ARBUSTO:
                System.out.println("Has recolectado " + recolectando + " unidades de comida");
                setCantRecComida(getCantRecComida() + recolectando);
                break;
            case ContenedorRecurso.CANTERA:
                System.out.println("Has recolectado " + recolectando + " unidades de piedra");
                setCantRecPiedra(getCantRecPiedra() + recolectando);
                break;
        }
    }

    public void almacenar(Mapa mapa, String direccion) {
        if (mapa == null || direccion == null) {
            System.out.println("Error en almcenar.");
            return;
        }
        for (int i = 0; i < this.personajes.size(); i++) {
            if (this.personajes.get(i).getTipo() == Personaje.SOLDADO) {
                System.out.println("Como hay un soldado en el grupo, este grupo no puede almacenar");
            }
        }
        Posicion pos = posicion.getAdy(direccion);
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }

        if (mapa.getCelda(pos).getEdificio() == null || mapa.getCelda(pos).getEdificio().getTipo() != Edificio.CIUDADELA) {
            System.out.println("No se puede almacenar recursos en esa celda");
        }
        if (this.getCantRecTotal() <= 0) {
            System.out.println("El grupo no transporta recursos");
        }
        if (this.cantRecMadera > 0) {
            mapa.getCelda(pos).getEdificio().setMadera(this.cantRecMadera, true);
            System.out.println("Almacenadas " + this.cantRecMadera + " unidades de madera en la ciudadela");
            this.setCantRecMadera(0);
        }
        if (this.cantRecPiedra > 0) {
            mapa.getCelda(pos).getEdificio().setPiedra(this.cantRecPiedra, true);
            System.out.println("Almacenadas " + this.cantRecPiedra + " unidades de piedra en la ciudadela");
            this.setCantRecPiedra(0);
        }
        if (this.cantRecComida > 0) {
            mapa.getCelda(pos).getEdificio().setAlimentos(this.cantRecComida, true);
            System.out.println("Almacenadas " + this.cantRecComida + " unidades de alimento en la ciudadela");
            this.setCantRecComida(0);
        }
    }

    public void consEdif(String tipoC, String dir, Civilizacion civilizacion) {
        if (civilizacion.getMapa() == null || tipoC == null || dir == null) {
            System.out.println("Error en consEdif.");
            return;
        }
        for (int i = 0; i < this.personajes.size(); i++) {
            if (this.personajes.get(i).getTipo() == Personaje.SOLDADO) {
                System.out.println("Como hay un soldado en el grupo, este grupo no puede construir edificios");
            }
        }
        Posicion posConstruir = posicion.getAdy(dir);
        if (posConstruir.equals(posicion) || !civilizacion.getMapa().perteneceAMapa(posConstruir) || !civilizacion.getMapa().getCelda(posConstruir).esCeldaLibre(true)) { //direccion no valida
            System.out.println("Error: No se puede contruir en la celda de destino.");
            return;
        }
        switch (tipoC) {
            case "casa":
                if (civilizacion.getEdificios().get("ciudadela1").getMadera() < 100 || civilizacion.getEdificios().get("ciudadela1").getPiedra() < 100) {
                    System.out.println("No se puede construir! Se necesitan 100 de madera y piedra y tienes " + civilizacion.getEdificios().get("ciudadela1").getMadera() + " y " + civilizacion.getEdificios().get("ciudadela1").getPiedra());
                    return;
                }
                civilizacion.getEdificios().get("ciudadela1").setPiedra(-100, true);
                civilizacion.getEdificios().get("ciudadela1").setMadera(-100, true);
                Edificio edif = new Edificio(Edificio.CASA, posConstruir, "casa" + (civilizacion.contarEdificios(Edificio.CASA) + 1));
                civilizacion.getMapa().getCelda(posConstruir).setEdificio(edif);
                System.out.println();
                civilizacion.getEdificios().put(edif.getNombre(), edif);
                civilizacion.getMapa().imprimirCabecera();
                civilizacion.getMapa().imprimir(civilizacion);
                System.out.println("Casa construida en " + posConstruir);
                System.out.println("Coste: 100 de madera, 100 de piedra.");
                break;
            case "cuartel":
                if (civilizacion.getEdificios().get("ciudadela1").getMadera() < 200 || civilizacion.getEdificios().get("ciudadela1").getPiedra() < 200) {
                    System.out.println("No se puede construir! Se necesitan 200 de madera y piedra y tienes " + civilizacion.getEdificios().get("ciudadela1").getMadera() + " y " + civilizacion.getEdificios().get("ciudadela1").getPiedra());
                    return;
                }
                civilizacion.getEdificios().get("ciudadela1").setPiedra(-200, true);
                civilizacion.getEdificios().get("ciudadela1").setMadera(-200, true);
                Edificio cuart = new Edificio(Edificio.CUARTEL, posConstruir, "cuartel" + (civilizacion.contarEdificios(Edificio.CUARTEL) + 1));
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
        for (int i = 0; i < this.personajes.size(); i++) {
            if (this.personajes.get(i).getTipo() == Personaje.SOLDADO) {
                System.out.println("Como hay un soldado en el grupo, este grupo no puede reparar");
            }
        }
        int puntosAReparar = civilizacion.getMapa().getCelda(pos).getEdificio().getMaxVida() - civilizacion.getMapa().getCelda(pos).getEdificio().getPs();
        int costeMadera = (int) (puntosAReparar * 0.4);
        int costePiedra = (int) (puntosAReparar * 0.5);
        if (civilizacion.getEdificios().get("ciudadela1").getMadera() < costeMadera || civilizacion.getEdificios().get("ciudadela1").getPiedra() < costePiedra) {
            System.out.println("No tienes suficientes recursos disponibles!");
            return;
        }
        civilizacion.getEdificios().get("ciudadela1").setMadera(-costeMadera, true);
        civilizacion.getEdificios().get("ciudadela1").setPiedra(-costePiedra, true);
        civilizacion.getMapa().getCelda(pos).getEdificio().reparar();
        System.out.println("Reparación completada.");
        System.out.println("Coste de la reparación: " + costeMadera + " unidades de madera y " + costePiedra + " unidades de piedra de la ciudadela.");
    }
}
