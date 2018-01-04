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
public class ConsolaNormal implements Consola {
    Scanner sca;
    public ConsolaNormal() {
        sca=new Scanner(System.in);
    }

    @Override
    public void imprimir(String mensaje) {
        Juego.CONSOLA.imprimir(mensaje);
    }

    @Override
    public void imprimir() {
        Juego.CONSOLA.imprimir();
    }

    @Override
    public String leer(String descripcion) {
        Juego.CONSOLA.imprimir(descripcion);
        return sca.nextLine();
    }

    @Override
    public void imprimirEnLinea(String mensaje) {
        System.out.print(mensaje);
    }

    @Override
    public String leerEnLinea(String descripcion) {
        System.out.print(descripcion);
        return sca.nextLine();
    }

    @Override
    public String leer() {
        return sca.nextLine();
    }

}
