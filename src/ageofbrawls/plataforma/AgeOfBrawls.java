/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import java.util.Scanner;

/**
 *
 * @author prada
 */
public class AgeOfBrawls {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = new Mapa();
        mapa.inicializar();
        mapa.imprimir();
        Scanner sca = new Scanner(System.in);
        String orden ="";
        while(!"salir".equals(orden)){
            orden = sca.nextLine();
            String comando = orden.substring(0,orden.indexOf(" "));
            switch(comando){
                case "mover":
                    String sub = orden.substring(orden.indexOf(" "),orden.length());
                    String quien = sub.substring(sub.indexOf(" "), sub.indexOf(" "));
                    break;
            }
        }
    }
     
}