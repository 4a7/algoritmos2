/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuro;

import java.util.ArrayList;

/**
 *
 * @author Juan
 */
public class Grafico {
    private ArrayList<CasillaGrafico>grafico;
    public Grafico(){
        grafico=new ArrayList<CasillaGrafico>();
    }
    public void addCasilla(int i,int j){
        int numero=(i*14)+j;
        long tiempo=System.nanoTime();
        grafico.add(new CasillaGrafico(tiempo,numero));
    }
    public void imprimir(){
        String salida="";
        for(CasillaGrafico c:grafico){
            System.out.println(/*c.getTiempo()+" "+*/c.getCasillas());
        }
    }
}
