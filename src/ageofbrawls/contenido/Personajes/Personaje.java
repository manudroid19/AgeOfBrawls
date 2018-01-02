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

    public Personaje(Posicion posicion, String nombre, Civilizacion civilizacion, int defensa, int salud) {
        this(posicion, nombre, civilizacion);//defensa salud
        this.defensa = defensa;
        this.salud = salud;
    }

    public int getSalud() {
        return salud;
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

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
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

    public void setPosicion(Posicion posicion) {
        if (posicion != null) {
            this.posicion = new Posicion(posicion);
        } else {
            System.out.println("Error: posicion introducida errónea");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public boolean isMuerto() {
        return muerto;
    }

    public void describir() {
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

    public void atacar(String direccion) {
        System.out.println("Error. No puede atacar");
    }

    protected void vaciarCantRecComida() {
        System.out.println("Error");
    }

    protected void vaciarCantRecPiedra() {
        System.out.println("Error");
    }

    protected void vaciarCantRecMadera() {
        System.out.println("Error");
    }

    public void recuperarVida(){
        System.out.println("Error");
    }

    public void recuperar(){
        System.out.println("Error");
    }

    public void recolectar(Mapa mapa, String direccion){
        System.out.println("Error");
    }

    public void almacenar(Mapa mapa, String direccion){
        System.out.println("Error");
    }

    public void construir(String tipoC, String dir){
        System.out.println("Error");
    }

    public void reparar(Posicion pos){
        System.out.println("Error");
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

    protected void construirGenerico(String tipo, String dir) {
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("No se puede construir desde un edificio");
            return;
        }
        Posicion posConstruir = posicion.getAdy(dir);
        if (posConstruir.equals(posicion) || !civilizacion.getMapa().perteneceAMapa(posConstruir) || !civilizacion.getMapa().getCelda(posConstruir).esCeldaLibre(true)) { //direccion no valida
            System.out.println("Error: No se puede contruir en la celda de destino.");
            return;
        }
        switch (tipo) {
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

    protected void repararGenerico(Posicion pos) {
        if (pos == null || !civilizacion.getMapa().perteneceAMapa(pos) || civilizacion.getMapa().getCelda(pos).getEdificio() == null || civilizacion.getMapa().getCelda(pos).getEdificio().getPs() == civilizacion.getMapa().getCelda(pos).getEdificio().getMaxVida()) {
            System.out.println("Nada que reparar.");
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("No se puede reparar desde un edificio");
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

    protected void atacarGenerico(String direccion) {
        Posicion pos = posicion.getAdy(direccion);
        if (pos == null || civilizacion.getMapa() == null || !civilizacion.getMapa().perteneceAMapa(pos)) {
            System.out.println("No se puede atacar a esa posición.");
            //return;
        }
        if (civilizacion.getMapa().getCelda(pos).getEdificio() == null && !civilizacion.getMapa().getCelda(pos).isHayGrupo() && civilizacion.getMapa().getCelda(pos).getPersonajes().isEmpty()) {
            System.out.println("En esa celda no hay nada a lo que se le pueda atacar");
            //return;
        }
        if (civilizacion.getMapa().getCelda(pos).getEdificio() != null && civilizacion == civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("No se puede atacar a un edificio de la misma civilización");
            //return;
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

        if (PuntosAQuitarACadaUno == 0 && civilizacion.getMapa().getCelda(pos).getEdificio() != null && civilizacion != civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion()) {
            System.out.println("Has inflingido " + PuntosAQuitar + " al edificio " + civilizacion.getMapa().getCelda(pos).getEdificio().getNombre() + " de la civilizacion (" + civilizacion.getMapa().getCelda(pos).getEdificio().getCivilizacion().getNombre() + ").");
            civilizacion.getMapa().getCelda(pos).getEdificio().danar(PuntosAQuitar);

        }
    }

    protected void almacenarGenerico(Mapa mapa, String direccion) {
        if (mapa == null || direccion == null) {
            System.out.println("Error en almacenar.");
            return;
        }
        Posicion posicion = getPosicion();
        Civilizacion civilizacion = getCivilizacion();
        Posicion pos = posicion.getAdy(direccion);
        if (pos.equals(posicion)) { //error con la posicion
            return;
        }
        if (civilizacion.getMapa().getCelda(posicion).getEdificio() != null) {
            System.out.println("No se puede almacenar desde un edificio");
            return;
        }
        if (mapa.getCelda(pos).getEdificio() == null || mapa.getCelda(pos).getEdificio().getTipo() != Edificio.CIUDADELA) {
            System.out.println("No se puede almacenar recursos en esa celda");
            return;
        }
        if (this.getCantRecTotal() <= 0) {
            System.out.println("No se transportan recursos");
            return;
        }
        if (!civilizacion.puedeAlmacenar(getCantRecComida() + getCantRecMadera() + getCantRecPiedra())) {
            System.out.println("No hay espacio suficiente en la ciudadela.");
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
