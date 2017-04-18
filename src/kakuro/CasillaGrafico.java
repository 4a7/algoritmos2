/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuro;

/**
 *
 * @author Juan
 */
public class CasillaGrafico {
    long tiempo;
    int casillas;
    public CasillaGrafico(long tiempo,int numero){
        this.tiempo=tiempo;
        this.casillas=numero;
    }

    public long getTiempo() {
        return tiempo;
    }

    public int getCasillas() {
        return casillas;
    }
    
}
