package ageofbrawls.contenido.Personajes;

import ageofbrawls.contenido.ContenedorRecurso;
import ageofbrawls.contenido.Edificio;
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
public abstract class Personaje {

    private int salud, defensa;
    private Posicion posicion;
    private boolean muerto;
    private Civilizacion civilizacion;
    private String nombre;
    private Grupo grupo;

    public Personaje(Posicion posicion, String nombre, Civilizacion civilizacion) {
        if (posicion != null && nombre != null) {
            this.posicion = new Posicion(posicion);
            this.nombre = nombre;
            this.civilizacion = civilizacion;
            this.grupo = null;
            salud = 10;
            defensa = 10;
            muerto = false;
        } else {
            System.out.println("Error seteando tipo");
        }
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public Personaje(Posicion posicion, String nombre, Civilizacion civilizacion, int defensa, int salud) {
        this(posicion, nombre, civilizacion);//defensa salud
        this.defensa = defensa;
        this.salud = salud;
    }

    public int getSalud() {
        return salud;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getAtaque() {
        return 0;
    }

    public Civilizacion getCivilizacion() {
        return civilizacion;
    }

    public int getCapRec() {
        return 0;
    }

    public int getCantRecMadera() {
        return 0;
    }

    public int getCantRecPiedra() {
        return 0;
    }

    public int getCantRecComida() {
        return 0;
    }

    public int getCantRecTotal() {
        return 0;
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
                salud = 0;
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
        System.out.println("Nombre del personaje: " + nombre);
        System.out.println("Este personaje es un " + this.toString());
        System.out.println("Salud :" + salud);
        System.out.println("Civilizacion: " + this.civilizacion.getNombre());
        System.out.println("Armadura :" + defensa);
    }

    public String leerGrupo() {
        if (grupo == null) {
            return "";
        } else {
            return grupo.getNombre();
        }
    }
    public String leerTipo(){
        if(this instanceof Paisano){
            return "paisano";
        }else if(this instanceof Soldado){
            return "soldado";
        }else{
            return "leertipo unknown";
        }
    }

    protected void moverGenerico(Posicion posicion, int n) throws Exception {
        if (grupo != null && grupo.getPersonajes().contains(this)) {
            System.out.println("El personaje no puede moverse por si solo ya que pertenece a un grupo");
            throw new Exception();
        }
        Mapa mapa = civilizacion.getMapa();
        Celda celdaAntigua = mapa.getCelda(this.posicion);
        if (!mapa.perteneceAMapa(posicion) || !mapa.getCelda(posicion).esCeldaLibre(false)) {
            System.out.println("Error: No te puedes mover a esa celda.");
            throw new Exception();
        }
        if (celdaAntigua.getEdificio() != null) {
            Edificio edif = celdaAntigua.getEdificio();
            edif.setCapAloj(n, true);
        }
        this.posicion = posicion;
        civilizacion.makeAdyVisible(posicion);
    }

    public void mover(String direccion) {
        try {
            Posicion posicion = this.posicion.getAdy(direccion);
            Mapa mapa = civilizacion.getMapa();
            Celda celdaAntigua = mapa.getCelda(this.posicion);
            
            moverGenerico(posicion, 1);
            celdaAntigua.removePersonaje(this);
            mapa.getCelda(posicion).addPersonaje(this);
            
            System.out.println();
            mapa.imprimirCabecera();
            mapa.imprimir(civilizacion);
        } catch (Exception ex) {
            //Logger.getLogger(Personaje.class.getName()).log(Level.SEVERE, null, ex);
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

    public abstract void recuperarVida();

    public abstract void recuperar();

    public void atacar(String direccion) {
        System.out.println("Error. No puede atacar");
    }

    @Override
    public String toString() {
        return "personaje";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.salud;
        hash = 37 * hash + this.defensa;
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
        if (this.salud != other.salud) {
            return false;
        }
        if (this.defensa != other.defensa) {
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
