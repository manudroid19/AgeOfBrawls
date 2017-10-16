/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls;

import java.util.ArrayList;

/**
 *
 * @author mprad
 */
public class Mapa {
    private ArrayList<ArrayList<Celda>> mapa;
    int filas;
    int columnas;
    
    public Mapa(int filas, int columnas){
        mapa = new ArrayList();
        for(int i=0;i<filas;i++){
            ArrayList<Celda> b = new ArrayList(columnas);
            mapa.add(b);
        }
        this.filas = filas;
        this.columnas = columnas;
    }
    public Mapa(){
        this(5,5);
    }
    public void putCelda(Celda celda, int i, int j){
        ArrayList<Celda> fila = mapa.get(i);
        fila.set(j, celda);
        mapa.set(i, fila);
    }
    public Celda getCelda(int i, int j){
        return mapa.get(i).get(j);
    }
    public boolean esCeldaVacia (int i, int j){
        return (mapa.get(i).get(j) == null); //expresion logica= true si celda==null
    }
    public void imprimir(){
        for(int i=0;i<columnas+2;i++)
            System.out.print("_");
        System.out.println();
        
        for(int i=0;i<filas;i++){
            System.out.println("|");
            for(int j=0;j<columnas;j++){
                System.out.println(mapa.get(i).get(j));
            }
            System.out.println("|");

        }
        
        
        
        for(int i=0;i<columnas+2;i++)
            System.out.print("_");
    }
}
