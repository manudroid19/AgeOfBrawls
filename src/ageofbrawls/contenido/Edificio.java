/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.contenido;

/**
 *
 * @author Santiago
 */
public class Edificio {

    //public final static int NINGUNO=0;
    public final static int CIUDADELA = 1;
    public final static int CUARTEL = 2;
    public final static int CASA = 3;
    private int tipo;
    private int ps;
    private boolean destruido;

    public Edificio(int tipo) {
        if (tipo > 0 && tipo < 4) {
            this.tipo = tipo;
        }else{
            System.out.println("Error seteando tipo");
        }
    }

    public int getTipo() {
        return tipo;
    }

    public int getPs() {
        return ps;
    }

    public void setTipo(int tipo) {
        if (tipo > 0 && tipo < 4) {
            this.tipo = tipo;
        }else{
            System.out.println("Error seteando tipo");
    }
    }

    public boolean estaDestruido() {
        return destruido;
    }

    public void danar(int dano) {
        if (dano > 0) {
            if (ps - dano > 0) {
                ps -= dano;
            } else {
                ps = 0;
                destruido = true;
            }
        } else {
            System.out.println("Error dañando edificio.");
        }
    }

}
