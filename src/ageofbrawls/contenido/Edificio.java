/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

import ageofbrawls.plataforma.Civilizacion;
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
    public final static int CAPALOJ = 10;
    private Posicion posicion;
    private Civilizacion civilizacion;
    private int tipo;
    private int capacidadAlojamiento;
    private int capAloj;
    private int ataque;
    private int defensa;
    private int ps;
    private boolean destruido;
    private String nombre;

    public Edificio(int tipo, Posicion posicion, String nombre, Civilizacion civilizacion) {
        if (tipo > 0 && tipo < 4 && nombre != null && civilizacion != null) {
            this.tipo = tipo;
            this.civilizacion = civilizacion;
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
                    this.capAloj = 7;
                    this.ataque = 0;
                    this.defensa = 0;
                    break;
                case Edificio.CUARTEL:
                    this.ps = 500;
                    this.capAloj = 5;
                    this.ataque = 0;
                    this.defensa = 0;
                    break;
                case Edificio.CIUDADELA:
                    this.ps = 1000;
                    this.capAloj = 10;
                    this.ataque = 0;
                    this.defensa = 0;
                    break;
            }
        } else {
            System.out.println("Error seteando tipo");
        }
    }

    public Edificio(Posicion posicion, String nombre) {
        if (nombre != null) {
            this.tipo = CIUDADELA;
            if (posicion != null) {
                this.posicion = new Posicion(posicion);
            } else {
                this.posicion = new Posicion();
            }
            this.nombre = nombre;
            this.ps = 1000;
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

    public Civilizacion getCivilizacion() {
        return civilizacion;
    }

    public int getCapAloj() {
        return capacidadAlojamiento;
    }

    public int getCapAloj1() {
        return capAloj;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getCapAlmacen() {
        return civilizacion.getCapAlmacen();
    }

    public boolean estaDestruido() {
        return destruido;
    }

    public String getNombre() {
        return nombre;
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

    public void setCapAloj(int aloj, boolean relative) {
        if (relative) {
            if (capAloj + aloj < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            capAloj += aloj;
        } else {
            if (capAloj + aloj < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            capAloj = aloj;
        }
    }

    public void setAtaque(int atack, boolean relative) {
        if (relative) {
            if (ataque + atack < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            ataque += atack;
        } else {
            if (ataque + atack < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            ataque = atack;
        }
    }

    public void setDefensa(int defense, boolean relative) {
        if (relative) {
            if (defensa + defense < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            defensa += defense;
        } else {
            if (defensa + defense < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            defensa = defense;
        }
    }

    

    
    public void setTipo(int tipo) {
        if (tipo > 0 && tipo < 4) {
            this.tipo = tipo;
        } else {
            System.out.println("Error seteando tipo");
        }
    }

    public void crearPersonaje(Civilizacion civilizacion) {
        if (civilizacion == null) {
            System.out.println("Error.");
            return;
        }
        if (civilizacion.getPersonajes().size() >= civilizacion.contarEdificios(Edificio.CASA) * CAPALOJ) {
            System.out.println("Error: no hay suficiente espacio para crear.");
            return;
        }
        if (tipo == Edificio.CIUDADELA) {
            if (civilizacion.getAlimentos() < 50) {
                System.out.println("Error: no hay suficiente alimento disponible.");
                return;
            }
            Posicion pos = posicion.posicionAdyacenteLibre(civilizacion.getMapa());
            int i = 1;
            String nombrePers = "paisano1";
            while (civilizacion.getPersonajes().containsKey(nombrePers)) {
                nombrePers = nombrePers.replace("paisano" + i, "paisano" + (++i));
            }
            Personaje person = new Personaje(Personaje.PAISANO, pos, nombrePers, civilizacion);
            civilizacion.getMapa().getCelda(pos).addPersonaje(person);
            civilizacion.getPersonajes().put(person.getNombre(), person);
            civilizacion.getMapa().getCelda(pos).setOculto(civilizacion, false);
            civilizacion.setAlimentos(-50, true);
            civilizacion.makeAdyVisible(pos);
            System.out.println();
            civilizacion.getMapa().imprimirCabecera();
            civilizacion.getMapa().imprimir(civilizacion);
            System.out.println("Coste de creacion: 50 unidades de comida");
            System.out.println("Te quedan " + ((civilizacion.contarEdificios(Edificio.CASA) * CAPALOJ) - civilizacion.getPersonajes().size()) + " unidades de capacidad de alojamiento");
            System.out.println("Se ha creado " + person.getNombre() + " en la celda de " + pos);

        } else if (tipo == Edificio.CUARTEL) {
            if (civilizacion.getAlimentos() < 100) {
                System.out.println("Error: no hay suficiente alimento disponible.");
                return;
            }
            Posicion pos = posicion.posicionAdyacenteLibre(civilizacion.getMapa());
            int i = 1;
            String nombrePers = "soldado1";
            while (civilizacion.getPersonajes().containsKey(nombrePers)) {
                nombrePers = nombrePers.replace("soldado" + i, "soldado" + (++i));
            }
            Personaje person = new Personaje(Personaje.SOLDADO, pos, nombrePers, civilizacion);
            civilizacion.getMapa().getCelda(pos).addPersonaje(person);
            civilizacion.getPersonajes().put(person.getNombre(), person);
            civilizacion.getMapa().getCelda(pos).setOculto(civilizacion, false);
            civilizacion.setAlimentos(-100, true);
            civilizacion.makeAdyVisible(pos);
            System.out.println();
            civilizacion.getMapa().imprimirCabecera();
            civilizacion.getMapa().imprimir(civilizacion);
            System.out.println("Coste de creacion: 100 unidades de comida");
            System.out.println("Te quedan " + ((civilizacion.contarEdificios(Edificio.CASA) * CAPALOJ) - civilizacion.getPersonajes().size()) + " unidades de capacidad de alojamiento");
            System.out.println("Se ha creado " + person.getNombre() + " en la celda de " + pos);

        }

    }

    public String leerTipo() {
        switch (tipo) {
            case Edificio.CIUDADELA:
                return "ciudadela";
            case Edificio.CUARTEL:
                return "cuartel";
            case Edificio.CASA:
                return "casa";
            default:
                return "";
        }
    }

    public void describirEdificio() {
        System.out.println("Tipo: " + leerTipo());
        System.out.println("Salud: " + ps);
        System.out.println("Capacidad de Alojamiento para defenderlo " + capAloj);
        System.out.println("Capacidad de ataque: " + ataque);
        System.out.println("Capacidad de defensa: " + defensa);
        if (tipo == Edificio.CIUDADELA) {
            System.out.println("Recursos: " + civilizacion.getMadera() + " de madera, " + civilizacion.getPiedra() + " de piedra y " + civilizacion.getAlimentos() + " de alimentos");
        }
        System.out.println("Nombre: " + nombre);
        System.out.println("Civilizacion: " + civilizacion.getNombre());
    }

    public void danar(int dano) {
        if (dano > 0) {
            if (ps - dano > 0) {
                ps -= dano;
            } else {
                ps = 0;
                destruido = true;
                System.out.println("El edificio " + this.getNombre() + " ha sido destruido");
                civilizacion.getEdificios().remove(this.getNombre());
                if(tipo==CIUDADELA){
                    civilizacion.quitarCiudadela();
                }
                civilizacion.getMapa().getCelda(this.posicion).setTipoCont(0);
                System.out.println();
                civilizacion.getMapa().imprimirCabecera();
                civilizacion.getMapa().imprimir(civilizacion);
            }
        } else {
            System.out.println("Error dañando edificio.");
        }
    }

    public void reparar() {
        ps = getMaxVida();
    }

}
