/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.Personajes;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
import ageofbrawls.plataforma.Civilizacion;
import ageofbrawls.plataforma.Mapa;
import ageofbrawls.plataforma.Posicion;

/**
 *
 * @author mprad
 */
public class Paisano extends Personaje {

    int capRec, cantRecMadera, cantRecPiedra, cantRecComida;

    public Paisano(Posicion posicion, String nombre, Civilizacion civilizacion) {
        super(posicion, nombre, civilizacion, 100, 50);
        capRec = 50;
    }

    public void setCapRec(int capRec) {
        this.capRec = capRec;
    }
    

    public void setCantRecMadera(int valor) {
        if (valor >= 0) {
            cantRecMadera = valor;
        } else {
            System.out.println("Error: capacidad introducida errónea");
        }
    }

    public void setCantRecPiedra(int valor) {
        if (valor >= 0) {
            cantRecPiedra = valor;
        } else {
            System.out.println("Error: capacidad introducida errónea");
        }
    }

    public void setCantRecComida(int valor) {
        if (valor >= 0) {
            cantRecComida = valor;
        } else {
            System.out.println("Error: capacidad introducida errónea");
        }
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

    public void describirPersonaje() {
        super.describirPersonaje();
        System.out.println("Capacidad de recolección: " + capRec);
        System.out.println("Cantidad de madera que transporta: " + cantRecMadera);
        System.out.println("Cantidad de comida que transporta: " + cantRecComida);
        System.out.println("Cantidad de piedra que transporta: " + cantRecPiedra);
        System.out.println("Cantidad de Recursos que lleva: " + (cantRecMadera + cantRecComida + cantRecPiedra));
    }

    public void recolectar(Mapa mapa, String direccion) {
        if (mapa == null || direccion == null) {
            System.out.println("Error en recolectar.");
            return;
        }

        if (super.getGrupo() != null) {
            System.out.println("El personaje no puede recolectar por si solo ya que pertenece a un grupo");
            return;
        }
        Posicion posicion = super.getPosicion();
        Posicion pos = posicion.getAdy(direccion);
        Civilizacion civilizacion = super.getCivilizacion();
        ContenedorRecurso contenedor = mapa.getCelda(pos).getContenedorRec();
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El personaje está en un edificio y por tanto no puede recolectar");
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
        Civilizacion civilizacion = super.getCivilizacion();
        Posicion posicion = super.getPosicion();
        for (int i = 0; i < civilizacion.getMapa().getCelda(posicion).getGrupos().size(); i++) {
            if (civilizacion.getMapa().getCelda(posicion).getGrupos().get(i).getPersonajes().contains(this)) {
                System.out.println("El personaje no puede almacenar por si solo ya que pertenece a un grupo");
                return;
            }
        }
        Posicion pos = posicion.getAdy(direccion);
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El personaje está en un edificio y por tanto no puede almacenar recursos");
            return;
        }
        if (mapa.getCelda(pos).getEdificio() == null || mapa.getCelda(pos).getEdificio().getTipo() != Edificio.CIUDADELA) {
            System.out.println("No se puede almacenar recursos en esa celda");
            return;
        }
        if (this.getCantRecTotal() <= 0) {
            System.out.println("El personaje no transporta recursos");
            return;
        }
        if (!civilizacion.puedeAlmacenar(cantRecComida + cantRecMadera + cantRecPiedra)) {
            System.out.println("No hay espacio suficiente en la ciudadela.");
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
        Grupo grupo = super.getGrupo();
        Posicion posicion = super.getPosicion();
        if (grupo != null) {
            System.out.println("El personaje no puede construir por si solo ya que pertenece a un grupo");
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El personaje está en un edificio y por tanto no puede construir");
            return;
        }
        Posicion posConstruir = posicion.getAdy(dir);
        if (posConstruir.equals(posicion) || !civilizacion.getMapa().perteneceAMapa(posConstruir) || !civilizacion.getMapa().getCelda(posConstruir).esCeldaLibre(true)) { //direccion no valida
            System.out.println("Error: No se puede contruir en la celda de destino.");
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
            case "ciudadela":
                if (civilizacion.getMadera() < 500 || civilizacion.getPiedra() < 500) {
                    System.out.println("No se puede construir! Se necesitan 500 de madera y piedra y tienes " + civilizacion.getMadera() + " y " + civilizacion.getPiedra());
                    return;
                }
                civilizacion.setPiedra(-500, true);
                civilizacion.setMadera(-500, true);
                Edificio ciud = new Edificio(Edificio.CIUDADELA, posConstruir, "ciudadela" + (civilizacion.contarEdificios(Edificio.CIUDADELA) + 1), civilizacion);
                civilizacion.anadirCiudadela();
                civilizacion.getMapa().getCelda(posConstruir).setEdificio(ciud);
                civilizacion.getEdificios().put(ciud.getNombre(), ciud);
                System.out.println();
                civilizacion.getMapa().imprimirCabecera();
                civilizacion.getMapa().imprimir(civilizacion);
                System.out.println("Ciudadela construida en " + posConstruir);
                System.out.println("Coste: 500 de madera, 500 de piedra.");
                break;
            default:
                System.out.println("Error: tipo de construccion incorrecta.");
        }

    }
    public void recuperarVida(){
        Civilizacion civilizacion = super.getCivilizacion();
        int puntosARecuperar = 50 - this.getSalud();
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

    public void reparar(Posicion pos) {
        Civilizacion civilizacion = super.getCivilizacion();
        Grupo grupo = super.getGrupo();
        Posicion posicion = super.getPosicion();
        if (pos == null || !civilizacion.getMapa().perteneceAMapa(pos) || civilizacion.getMapa().getCelda(pos).getEdificio() == null || civilizacion.getMapa().getCelda(pos).getEdificio().getPs() == civilizacion.getMapa().getCelda(pos).getEdificio().getMaxVida()) {
            System.out.println("Nada que reparar.");
            return;
        }

        if (grupo != null) {
            System.out.println("El personaje no puede reparar por si solo ya que pertenece a un grupo");
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

    @Override
    public String toString() {
        return "paisano";
    }

    @Override
    public void recuperar() {
            super.setSalud(50, false);
    }
}
