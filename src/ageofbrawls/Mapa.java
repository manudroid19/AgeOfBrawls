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
    private int filas;
    private int columnas;
    
    public Mapa(int filas, int columnas){
        mapa = new ArrayList<>();
        for(int i=0;i<filas;i++){
            ArrayList<Celda> b = new ArrayList<>();
            for (int j=0;j<columnas;j++){
                b.add(j,new Celda());
            }
            mapa.add(i,b);
        }
        this.filas = filas;
        this.columnas = columnas;
    }
    public void inicializar(){
        for(int i =0;i<mapa.size();i++)
            for(int j=0;j<mapa.get(0).size();j++){
                if(i%2==0 && j%2==0){
                    this.putCelda(new Celda(ContenedorRecurso.BOSQUE,0),i,j);
                }else
                    this.putCelda(new Celda(),i,j);
            }
        
    }
    private void makeBloquePrad(int i, int j){
        
    }
    public Mapa(){
        this(10,10);
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
            System.out.print("|");
            for(int j=0;j<columnas;j++){
                System.out.print(mapa.get(i).get(j));
            }
            System.out.print("|");
            System.out.println();

        }
        
        
        
        for(int i=0;i<columnas+2;i++)
            System.out.print("_");
        System.out.println();
    }
}
