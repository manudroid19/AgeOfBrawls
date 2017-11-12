/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

import ageofbrawls.plataforma.Mapa;
import ageofbrawls.plataforma.Posicion;

/**
 *
 * @author Santiago
 */
public class Edificio {

    public final static int CIUDADELA = 1;
    public final static int CUARTEL = 2;
    public final static int CASA = 3;
    public final static int CAPALMACEN = 100000;
    public final static int CAPALOJ = 10;
    private Posicion posicion;
    private int tipo;
    private int capacidadAlojamiento;
    private int capAlmacen;
    private int ps;
    private boolean destruido;
    private String nombre;
    private int madera, piedra, alimentos;

    public Edificio(int tipo, Posicion posicion, String nombre) {
        if (tipo > 0 && tipo < 4 && nombre != null) {
            this.tipo = tipo;
            if (posicion != null) {
                this.posicion = new Posicion(posicion);
            } else {
                this.posicion = new Posicion();
            }
            this.nombre = nombre;
            switch (tipo) {
                case Edificio.CASA:
                    this.ps = 200;
                    this.capacidadAlojamiento = Edificio.CAPALOJ;
                    break;
                case Edificio.CUARTEL:
                    this.ps = 500;
                    break;
                case Edificio.CIUDADELA:
                    this.ps = 1000;
                    this.capAlmacen = Edificio.CAPALMACEN;
                    madera = 500;
                    piedra = 500;
                    alimentos = 500;
                    break;
            }
        } else {
            System.out.println("Error seteando tipo");
        }
    }

    public Posicion getPosicion() {
        return new Posicion(posicion);
    }

    public int getTipo() {
        return tipo;
    }

    public int getPs() {
        return ps;
    }

    public int getCapAloj() {
        return capacidadAlojamiento;
    }

    public int getCapAlmacen() {
        return capAlmacen;
    }

    public boolean estaDestruido() {
        return destruido;
    }

    public String getNombre() {
        return nombre;
    }

    public int getMadera() {
        return this.madera;
    }

    public int getPiedra() {
        return this.piedra;
    }

    public int getAlimentos() {
        return this.alimentos;
    }

    public int getMaxVida() {
        switch (tipo) {
            case Edificio.CASA:
                return 200;
            case Edificio.CUARTEL:
                return 500;
            case Edificio.CIUDADELA:
                return 1000;
        }
        return -1;
    }

    public void setPiedra(int cant, boolean relative) {
        if (relative) {
            if (piedra + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            piedra += cant;
        } else {
            if (piedra + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            piedra = cant;
        }
    }

    public void setAlimentos(int cant, boolean relative) {
        if (relative) {
            if (alimentos + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            alimentos += cant;
        } else {
            if (alimentos + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            alimentos = cant;
        }
    }

    public void setMadera(int cant, boolean relative) {
        if (relative) {
            if (madera + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            madera += cant;
        } else {
            if (madera + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            madera = cant;
        }
    }

    public void setTipo(int tipo) {
        if (tipo > 0 && tipo < 4) {
            this.tipo = tipo;
        } else {
            System.out.println("Error seteando tipo");
        }
    }

    public void crearPersonaje(Mapa mapa) {
        if (mapa == null) {
            System.out.println("Error.");
            return;
        }
        if (mapa.getPersonajes().size() >= mapa.contarEdificios(Edificio.CASA) * CAPALOJ) {
            System.out.println("Error: no hay suficiente espacio para crear.");
            return;
        }
        if (tipo == Edificio.CIUDADELA) {
            if (mapa.getEdificios().get("ciudadela1").getAlimentos() < 50) {
                System.out.println("Error: no hay suficiente alimento disponible.");
                return;
            }
            Posicion pos = posicion.posicionAdyacenteLibre(mapa);
            int i = 1;
            String nombrePers = "paisano1";
            while (mapa.getPersonajes().containsKey(nombrePers)) {
                nombrePers = nombrePers.replace("paisano" + i, "paisano" + (++i));
            }
            Personaje person = new Personaje(Personaje.PAISANO, pos, nombrePers);
            mapa.getCelda(pos).addPersonaje(person);
            mapa.getPersonajes().put(person.getNombre(), person);
            mapa.getCelda(pos).setOculto(false);
            mapa.getEdificios().get("ciudadela1").setAlimentos(-50, true);
            mapa.makeAdyVisible(pos);
            System.out.println();
            mapa.imprimirCabecera();
            mapa.imprimir();
            System.out.println("Coste de creacion: 50 unidades de comida");
            System.out.println("Te quedan " + ((mapa.contarEdificios(Edificio.CASA) * CAPALOJ) - mapa.getPersonajes().size()) + " unidades de capacidad de alojamiento");
            System.out.println("Se ha creado " + person.getNombre() + " en la celda de " + pos);

        } else if (tipo == Edificio.CUARTEL) {
            if (mapa.getEdificios().get("ciudadela1").getAlimentos() < 100) {
                System.out.println("Error: no hay suficiente alimento disponible.");
                return;
            }
            Posicion pos = posicion.posicionAdyacenteLibre(mapa);
            int i = 1;
            String nombrePers = "soldado1";
            while (mapa.getPersonajes().containsKey(nombrePers)) {
                nombrePers = nombrePers.replace("soldado" + i, "soldado" + (++i));
            }
            Personaje person = new Personaje(Personaje.SOLDADO, pos, nombrePers);
            mapa.getCelda(pos).addPersonaje(person);
            mapa.getPersonajes().put(person.getNombre(), person);
            mapa.getCelda(pos).setOculto(false);
            mapa.getEdificios().get("ciudadela1").setAlimentos(-100, true);
            mapa.makeAdyVisible(pos);
            System.out.println();
            mapa.imprimirCabecera();
            mapa.imprimir();
            System.out.println("Coste de creacion: 100 unidades de comida");
            System.out.println("Te quedan " + ((mapa.contarEdificios(Edificio.CASA) * CAPALOJ) - mapa.getPersonajes().size()) + " unidades de capacidad de alojamiento");
            System.out.println("Se ha creado " + person.getNombre() + " en la celda de " + pos);

        }

    }

    public void describirEdificio() {
        switch (tipo) {
            case Edificio.CIUDADELA:
                System.out.println("Tipo: CIUDADELA");
                System.out.println("Salud: " + ps);
                System.out.println("Recursos: " + madera + " de madera, " + piedra + " de piedra y " + alimentos + " de alimentos");
                System.out.println("Nombre: " + nombre);
                break;

            case Edificio.CUARTEL:
                System.out.println("Tipo: CUARTEL");
                System.out.println("Salud: " + ps);
                System.out.println("Nombre: " + nombre);
                break;

            case Edificio.CASA:
                System.out.println("Tipo: CASA");
                System.out.println("Salud: " + ps);
                System.out.println("Nombre: " + nombre);
                break;

        }
    }

    public void danar(int dano) {
        if (dano > 0) {
            if (ps - dano > 0) {
                ps -= dano;
            } else {
                ps = 0;
                destruido = true;
            }
        } else {
            System.out.println("Error dañando edificio.");
        }
    }

    public void reparar() {
        ps = getMaxVida();
    }

}
