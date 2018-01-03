/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import java.util.Scanner;

/**
 *
 * @author Santiago
 */
public interface Comando {
    public void agrupar (String donde);
    public void almacenar(String personaje,String direccion);
    public void atacar(String personaje,String direccion);
    public void cambiar(String destino);
    public void cargar(String ruta);
    public void civilizacion();
    public void construir(String constructor, String tipo, String dir);
    public void crear(String edificio,String tipo);
    public void defender(String defensor,String direccion);
    public void desagrupar(String grupo);
    public void describir(String sujeto);
    public void desligar(String desligado, String grupo);
    public void guardar(String ruta);
    public void imprimirCabecera();
    public void imprimirMapa();
    public void listarCivilizaciones();
    public void listarEdificios();
    public void listarGrupos();
    public void listarPersonajes();
    public void manejar(String quien);
    public void mirar(String donde);
    public void mover(String persona, String direccion);
    public void recolectar(String recolector, String direccion);
    public void reparar(String reparador, String direccion);
    
}
