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
    @Override
    public void imprimir(String mensaje){
        System.out.println(mensaje);
    }
    
    @Override
    public String leer(String descripcion){
        System.out.println(descripcion); 
        Scanner sca= new Scanner(System.in);
        String orden="";
        orden=sca.nextLine();
        return orden;
    }
    
    
}
