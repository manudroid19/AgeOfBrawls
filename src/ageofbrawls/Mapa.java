/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
                    if((i==filas-2 && j==0)||(i==0&&j==columnas-2)||(i==0&&j==0)||(i==filas-2&&j==columnas-2))//si estamos en las esquinas
                        this.makeBloquePrad(i, j);
                    
                }else
                    this.putCelda(new Celda(),i,j);
            }
        
    }
    private void makeBloquePrad(int i, int j){
        this.putCelda(new Celda(), i, j);
        this.putCelda(new Celda(), i+1, j);
        this.putCelda(new Celda(), i, j+1);
        this.putCelda(new Celda(), i+1, j+1);
    }
    private void makeBloqueRec(int i, int j){
        int b=0;
        ArrayList<Integer> bloque=new ArrayList<>(4);
        for(int k=1;k<4;k++){
            bloque.add(k);
        }
        Collections.shuffle(bloque);
        Random rn = new Random();
        int n = rn.nextInt(3 - 2) + 1;
        
        for(int h=0;h<4;h++){
        Random rt = new Random();
        int a = rt.nextInt(100 - 1) + 1;
        b=a;
        }
        this.putCelda(new Celda(bloque.get(0),b), i, j);
        this.putCelda(new Celda(bloque.get(1),b), i+1, j);
        this.putCelda(new Celda(bloque.get(2),b), i, j+1);
        this.putCelda(new Celda(n,b), i+1, j+1);
        
    
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
