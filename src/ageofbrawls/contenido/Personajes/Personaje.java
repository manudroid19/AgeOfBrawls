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
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Santiago
 */
public class Personaje {

    public final static int PAISANO = 1, SOLDADO = 2;
    private int tipo, salud, defensa, ataque, capRec, cantRecMadera, cantRecPiedra, cantRecComida;
    private Posicion posicion;
    private boolean muerto;
    private Civilizacion civilizacion;
    private String nombre;
    private Grupo grupo;

    public Personaje(int tipo, Posicion posicion, String nombre, Civilizacion civilizacion) {
        if ((tipo == 1 || tipo == 2) && posicion != null && nombre != null) {
            this.tipo = tipo;
            this.posicion = new Posicion(posicion);
            this.nombre = nombre;
            this.civilizacion = civilizacion;
            this.grupo = null;
            if (tipo == Personaje.SOLDADO) {
                salud = 100;
                defensa = 200;
                ataque = 70;
                muerto = false;
                capRec = -1;
            } else {
                salud = 50;
                defensa = 100;
                ataque = 0;
                capRec = 50;
                muerto = false;
            }
        } else {
            System.out.println("Error seteando tipo");
        }
    }

    public Personaje(int tipo, Posicion posicion, String nombre, Civilizacion civilizacion, int ataque, int defensa, int capacidad, int salud) {
        this(tipo, posicion, nombre, civilizacion);
        this.ataque = ataque;
        this.defensa = defensa;
        this.capRec = capacidad;
        this.salud = salud;
    }

    public int getTipo() {
        return tipo;
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

    public Civilizacion getCivilizacion() {
        return civilizacion;
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

    public Grupo getGrupo() {
        return grupo;
    }

    public boolean isMuerto() {
        return muerto;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
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

    public void setSalud(int cant, boolean relative) {
        if (relative) {
            if (salud + cant < 0) {
                salud=0;
                return;
            }
            salud += cant;
        } else {
            if (salud + cant < 0) {
                System.out.println("error, seteo incorrecto");
                return;
            }
            salud = cant;
        }
    }

    public void describirPersonaje() {
        if (tipo == Personaje.SOLDADO) {
            System.out.println("Nombre del soldado: " + nombre);
            System.out.println("Salud :" + salud);
            System.out.println("Civilizacion: " + this.civilizacion.getNombre());
            System.out.println("Armadura :" + defensa);
            System.out.println("Ataque :" + ataque);
        } else {
            System.out.println("Nombre del paisano: " + nombre);
            System.out.println("Salud :" + salud);
            System.out.println("Civilizacion: " + this.civilizacion.getNombre());
            System.out.println("Capacidad de recolección: " + capRec);
            System.out.println("Cantidad de madera que transporta: " + cantRecMadera);
            System.out.println("Cantidad de comida que transporta: " + cantRecComida);
            System.out.println("Cantidad de piedra que transporta: " + cantRecPiedra);
            System.out.println("Cantidad de Recursos que lleva: " + (cantRecMadera + cantRecComida + cantRecPiedra));
        }

    }

    public String leerTipo() {
        if (tipo == Personaje.SOLDADO) {
            return "soldado";
        } else {
            return "paisano";
        }
    }

    public String leerGrupo() {
        if (grupo == null) {
            return "";
        } else {
            return grupo.getNombre();
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
            if (civilizacion.getMapa().getCelda(this.posicion).getEdificio() != null) {
                Edificio edif = civilizacion.getMapa().getCelda(this.posicion).getEdificio();
                edif.setCapAloj(1, true);
            }
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

        if (this.grupo != null) {
            System.out.println("El personaje no puede recolectar por si solo ya que pertenece a un grupo");
            return;
        }

        Posicion pos = posicion.getAdy(direccion);
        ContenedorRecurso contenedor = mapa.getCelda(pos).getContenedorRec();
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El personaje está en un edificio y por tanto no puede recolectar");
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
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El personaje está en un edificio y por tanto no puede almacenar recursos");
            return;
        }
        if (this.tipo != Personaje.PAISANO) {
            System.out.println("Un soldado no puede recolectar");
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
        if (this.grupo != null) {
            System.out.println("El personaje no puede moverse por si solo ya que pertenece a un grupo");
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("El personaje está en un edificio y por tanto no puede construir");
            return;
        }

        if (tipo == Personaje.PAISANO) {
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
                    Edificio ciud = new Edificio(Edificio.CIUDADELA, posConstruir, "ciudadela" + (civilizacion.contarEdificios(Edificio.CIUDADELA) + 1), this.civilizacion);
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

        } else {
            System.out.println("Error: Un soldado no puede construir edificios");
        }
    }

    public void defender(String direccion) {
        Posicion pos = posicion.getAdy(direccion);
        if (direccion == null || pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos) || civilizacion.getMapa().getCelda(pos).getEdificio() == null) {
            System.out.println("No hay edificio en la posición indicada.");
            return;
        }
        if (this.civilizacion != civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("El personaje no puede entrar en el edificio de la otra civilización");
            return;
        }

        if (this.grupo != null) {
            System.out.println("El personaje no puede defender por si solo el edificio ya que pertenece a un grupo");
            return;
        }

        if (civilizacion.getMapa().getCelda(pos).getEdificio().getCapAloj1() == 0) {
            System.out.println(civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + " ya está al máximo de su capacidad. El " + this.getNombre() + "no ha podido entrar en " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + " .");
            return;
        }

        civilizacion.getMapa().getCelda(this.posicion).removePersonaje(this);
        civilizacion.getMapa().getCelda(pos).addPersonaje(this);
        posicion = new Posicion(pos);
        civilizacion.getMapa().imprimirCabecera();
        civilizacion.getMapa().imprimir(civilizacion);
        civilizacion.getMapa().getCelda(pos).getEdificio().setCapAloj(-1, true);
        this.recuperarVida();
        System.out.println("El " + this.getNombre() + " ha entrado en " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + " (capacidad restante " + civilizacion.getMapa().getCelda(pos).getEdificio().getCapAloj1() + ").");

    }

    public void reparar(Posicion pos) {
        if (pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos) || civilizacion.getMapa().getCelda(pos).getEdificio() == null || civilizacion.getMapa().getCelda(pos).getEdificio().getPs() == civilizacion.getMapa().getCelda(pos).getEdificio().getMaxVida()) {
            System.out.println("Nada que reparar.");
            return;
        }

        if (this.grupo != null) {
            System.out.println("El personaje no puede almacenar por si solo ya que pertenece a un grupo");
            return;

        }
        if (tipo == Personaje.PAISANO) {

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
        } else {
            System.out.println("Un soldado no puede reparar edificios");
        }
    }

    public void recuperarVida() {

        if (this.getTipo() == Personaje.PAISANO) {
            int puntosARecuperar = 50 - this.getSalud();
            if (puntosARecuperar == 0) {
                System.out.println("El personaje tiene toda la vida");
                return;
            }
            int costeAlimento = (int) (puntosARecuperar * 0.8);
            if (civilizacion.getAlimentos() < costeAlimento) {
                int puntosRecuperados = (int) (civilizacion.getAlimentos() / 0.8);
                this.salud = (this.getSalud() + puntosRecuperados);
                civilizacion.setAlimentos(0, false);
                return;
            }
            civilizacion.setAlimentos(-costeAlimento, true);
            this.recuperar();
            System.out.println("Coste de la recuperación de la vida: " + costeAlimento + " unidades de alimento de la ciudadela.");
        } else {
            int puntosARecuperar = 100 - this.getSalud();
            if (puntosARecuperar == 0) {
                System.out.println("El personaje tiene toda la vida");
                return;
            }
            int costeAlimento = (int) (puntosARecuperar * 0.8);
            if (civilizacion.getAlimentos() < costeAlimento) {
                int puntosRecuperados = (int) (civilizacion.getAlimentos() / 0.8);
                this.salud = (this.getSalud() + puntosRecuperados);
                civilizacion.setAlimentos(0, false);
                return;
            }
            civilizacion.setAlimentos(-costeAlimento, true);
            this.recuperar();
            System.out.println("Coste de la recuperación de la vida: " + costeAlimento + " unidades de alimento de la ciudadela.");
        }
    }

    public void recuperar() {
        if (this.getTipo() == Personaje.PAISANO) {
            salud = 50;
        } else {
            salud = 100;
        }
    }

    public void atacar(String direccion) {
        Posicion pos = posicion.getAdy(direccion);
        if (direccion == null || pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos)) {
            System.out.println("No se puede atacar a esa posición.");
            return;
        }
        if (this.grupo != null) {
            System.out.println("El personaje no puede atacar ya que pertenece a un grupo");
            return;
        }
        if (this.tipo == Personaje.PAISANO) {
            System.out.println("Un paisano no puede atacar");
            return;
        }
        if (civilizacion.getMapa().getCelda(pos).getEdificio() == null && !civilizacion.getMapa().getCelda(pos).isHayGrupo() && civilizacion.getMapa().getCelda(pos).getPersonajes().isEmpty()) {
            System.out.println("En esa celda no hay nada a lo que se le pueda atacar");
            return;
        }
        if (civilizacion.getMapa().getCelda(pos).getEdificio() != null && this.civilizacion == civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
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
            if (pers.get(i).getTipo() == Personaje.PAISANO) {
                quitados = (PuntosAQuitarACadaUno);                
            } else {
                quitados=(int) ((double) PuntosAQuitarACadaUno * 0.5);
            }
            atacado.salud = atacado.salud -quitados;
            if (atacado.salud <= 0) {
                if (atacado.grupo != null) {
                    atacado.grupo.desligar(atacado);
                }
                civilizacion.getMapa().getCelda(pos).getPersonajes().remove(atacado);
                civilizacion.getPersonajes().remove(atacado.getNombre());
                civilizacion.getMapa().imprimirCabecera();
                civilizacion.getMapa().imprimir(civilizacion);
                System.out.println("Has inflingido " + quitados + " de daño al personaje "+atacado.getNombre()+" de la celda " + pos.toStringMapa() + " (civ " + pers.get(0).getCivilizacion().getNombre() + ").");
                System.out.println("El personaje: " + atacado.getNombre() + " ha muerto");
            } else {
                System.out.println("Has inflingido " + quitados + " de daño al personaje "+atacado.getNombre()+" de la celda " + pos.toStringMapa() + " (civ " + pers.get(0).getCivilizacion().getNombre() + ").");
            }
        }

        if (PuntosAQuitarACadaUno == 0 && civilizacion.getMapa().getCelda(pos).getEdificio() != null && this.civilizacion != civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("Has inflingido " + PuntosAQuitar + " al edificio " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + " de la civilizacion (" + civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion().getNombre() + ").");
            civilizacion.getMapa().getCelda(pos).getEdificio().danar(PuntosAQuitar);

        }

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.tipo;
        hash = 37 * hash + this.salud;
        hash = 37 * hash + this.defensa;
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
        if (this.muerto != other.muerto) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.posicion, other.posicion)) {
            return false;
        }
        if (!Objects.equals(this.civilizacion, other.civilizacion)) {
            return false;
        }
        if (!Objects.equals(this.grupo, other.grupo)) {
            return false;
        }
        return true;
    }

}