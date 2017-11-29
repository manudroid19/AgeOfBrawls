/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Mapa;
import ageofbrawls.plataforma.Posicion;
import java.util.Objects;

/**
 *
 * @author Santiago
 */
public class Personaje {

    public final static int PAISANO = 1, SOLDADO = 2;
    private int tipo, salud, armadura, ataque, capRec, cantRecMadera, cantRecPiedra, cantRecComida;
    private Posicion posicion;
    private boolean muerto;
    private Civilizacion civilizacion;
    private String nombre;

    public Personaje(int tipo, Posicion posicion, String nombre, Civilizacion civilizacion) {
        if ((tipo == 1 || tipo == 2) && posicion != null && nombre != null) {
            this.tipo = tipo;
            this.posicion = new Posicion(posicion);
            this.nombre = nombre;
            this.civilizacion = civilizacion;
            if (tipo == Personaje.SOLDADO) {
                salud = 100;
                armadura = 200;
                ataque = 30;
                muerto = false;
                capRec = -1;
            } else {
                salud = 50;
                armadura = 100;
                ataque = -1;
                capRec = 50;
                muerto = false;
            }
        } else {
            System.out.println("Error seteando tipo");
        }
    }

    public int getTipo() {
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

    public String getNombre() {
        return nombre;
    }

    public boolean isMuerto() {
        return muerto;
    }

    public void setCantRecMadera(int valor) {
        if (valor >= 0 && this.tipo == Personaje.PAISANO) {
            cantRecMadera = valor;
        } else {
            System.out.println("Error: capacidad introducida errónea");
        }
    }

    public void setCantRecPiedra(int valor) {
        if (valor >= 0 && this.tipo == Personaje.PAISANO) {
            cantRecPiedra = valor;
        } else {
            System.out.println("Error: capacidad introducida errónea");
        }
    }

    public void setCantRecComida(int valor) {
        if (valor >= 0 && this.tipo == Personaje.PAISANO) {
            cantRecComida = valor;
        } else {
            System.out.println("Error: capacidad introducida errónea");
        }
    }

    public void setPosicion(Posicion posicion) {
        if (posicion != null) {
            this.posicion = new Posicion(posicion);
        } else {
            System.out.println("Error: posicion introducida errónea");
        }
    }

    public void describirPersonaje() {
        if (tipo == Personaje.SOLDADO) {
            System.out.println("Nombre: " + nombre);
            System.out.println("Salud :" + salud);
            System.out.println("Armadura :" + armadura);
            System.out.println("Ataque :" + ataque);
        } else {
            System.out.println("Nombre: " + nombre);
            System.out.println("Salud :" + salud);
            System.out.println("Capacidad de recolección: " + capRec);
            System.out.println("Cantidad de madera que transporta: " + cantRecMadera);
            System.out.println("Cantidad de comida que transporta: " + cantRecComida);
            System.out.println("Cantidad de piedra que transporta: " + cantRecPiedra);
            System.out.println("Cantidad de Recursos que lleva: " + (cantRecMadera + cantRecComida + cantRecPiedra));
        }

    }

    private void mover(Posicion posicion) {
        if (civilizacion.getMapa() == null || posicion == null) {
            System.out.println("Error en mover.");
            return;
        }
        for (int i = 0; i < civilizacion.getMapa().getCelda(this.posicion).getGrupos().size(); i++) {
            if (civilizacion.getMapa().getCelda(this.posicion).getGrupos().get(i).getPersonajes().contains(this)) {
                System.out.println("El personaje no puede moverse por si solo ya que pertenece a un grupo");
                return;
            }
        }
        if (civilizacion.getMapa().perteneceAMapa(posicion) && civilizacion.getMapa().getCelda(posicion).esCeldaLibre(false)) {
            civilizacion.getMapa().getCelda(this.posicion).removePersonaje(this);
            this.posicion = posicion;
            civilizacion.getMapa().getCelda(posicion).addPersonaje(this);
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
        for (int i = 0; i < civilizacion.getMapa().getCelda(this.posicion).getGrupos().size(); i++) {
            if (civilizacion.getMapa().getCelda(this.posicion).getGrupos().get(i).getPersonajes().contains(this)) {
                System.out.println("El personaje no puede recolectar por si solo ya que pertenece a un grupo");
                return;
            }
        }
        Posicion pos = posicion.getAdy(direccion);
        ContenedorRecurso contenedor = mapa.getCelda(pos).getContenedorRec();
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }
        if (this.tipo != Personaje.PAISANO) {
            System.out.println("Error: Un soldado no puede recolectar");
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
            System.out.println("Error en almacenar.");
            return;
        }
        for (int i = 0; i < civilizacion.getMapa().getCelda(this.posicion).getGrupos().size(); i++) {
            if (civilizacion.getMapa().getCelda(this.posicion).getGrupos().get(i).getPersonajes().contains(this)) {
                System.out.println("El personaje no puede almacenar por si solo ya que pertenece a un grupo");
                return;
            }
        }
        Posicion pos = posicion.getAdy(direccion);
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }
        if (this.tipo != Personaje.PAISANO) {
            System.out.println("Un soldado no puede recolectar");
            return;
        }
        if (mapa.getCelda(pos).getEdificio() == null || mapa.getCelda(pos).getEdificio().getTipo() != Edificio.CIUDADELA) {
            System.out.println("No se puede almacenar recursos en esa celda");
        }
        if (this.getCantRecTotal() <= 0) {
            System.out.println("El personaje no transporta recursos");
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
        for (int i = 0; i < civilizacion.getMapa().getCelda(this.posicion).getGrupos().size(); i++) {
            if (civilizacion.getMapa().getCelda(this.posicion).getGrupos().get(i).getPersonajes().contains(this)) {
                System.out.println("El personaje no puede moverse por si solo ya que pertenece a un grupo");
                return;
            }
        }
        if (tipo == Personaje.PAISANO) {
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

        } else {
            System.out.println("Error: Un soldado no puede construir edificios");
        }
    }

    public void reparar(Posicion pos) {
        if (pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos) || civilizacion.getMapa().getCelda(pos).getEdificio() == null || civilizacion.getMapa().getCelda(pos).getEdificio().getPs() == civilizacion.getMapa().getCelda(pos).getEdificio().getMaxVida()) {
            System.out.println("Nada que reparar.");
            return;
        }
        for (int i = 0; i < civilizacion.getMapa().getCelda(this.posicion).getGrupos().size(); i++) {
            if (civilizacion.getMapa().getCelda(this.posicion).getGrupos().get(i).getPersonajes().contains(this)) {
                System.out.println("El personaje no puede almacenar por si solo ya que pertenece a un grupo");
                return;
            }
        }
        if (tipo == Personaje.PAISANO) {

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
        } else {
            System.out.println("Un soldado no puede reparar edificios");
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.tipo;
        hash = 37 * hash + this.salud;
        hash = 37 * hash + this.armadura;
        hash = 37 * hash + this.ataque;
        hash = 37 * hash + this.capRec;
        hash = 37 * hash + this.cantRecMadera;
        hash = 37 * hash + this.cantRecComida;
        hash = 37 * hash + this.cantRecPiedra;
        hash = 37 * hash + Objects.hashCode(this.posicion);
        hash = 37 * hash + (this.muerto ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.nombre);
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
        final Personaje other = (Personaje) obj;
        if (this.tipo != other.tipo) {
            return false;
        }
        if (this.salud != other.salud) {
            return false;
        }
        if (this.armadura != other.armadura) {
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
        if (this.cantRecComida != other.cantRecComida) {
            return false;
        }
        if (this.cantRecPiedra != other.cantRecPiedra) {
            return false;
        }
        if (this.muerto != other.muerto) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.posicion, other.posicion)) {
            return false;
        }
        return true;
    }
}
