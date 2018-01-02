/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido.Personajes;

import ageofbrawls.contenido.ContenedorRecurso;
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

    public void describir() {
        super.describir();
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
        Posicion posicion = getPosicion();
        Civilizacion civilizacion = getCivilizacion();
        Posicion pos = posicion.getAdy(direccion);
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
        if (getGrupo() != null) {
            System.out.println("El personaje no puede almacenar por si solo ya que pertenece a un grupo");
            return;
        }
        almacenarGenerico(mapa, direccion);
    }

    @Override
    protected void vaciarCantRecMadera() {
        setCantRecMadera(0);
    }
    @Override
    protected void vaciarCantRecComida() {
        setCantRecComida(0);
    }
    @Override
    protected void vaciarCantRecPiedra() {
        setCantRecPiedra(0);
    }
    
    @Override
    public void construir(String tipoC, String dir) {
        if (tipoC == null || dir == null) {
            System.out.println("Error en consEdif.");
            return;
        }
        Grupo grupo = super.getGrupo();
        if (grupo != null) {
            System.out.println("El personaje no puede construir por si solo ya que pertenece a un grupo");
            return;
        }
        construirGenerico(tipoC, dir);
    }

    public void recuperarVida() {
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
        if (getGrupo() != null) {
            System.out.println("El personaje no puede reparar por si solo ya que pertenece a un grupo");
            return;
        }
        repararGenerico(pos);
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
