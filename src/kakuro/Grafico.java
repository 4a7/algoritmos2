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
        long tiempo=(System.nanoTime())/1000000;
        grafico.add(new CasillaGrafico(tiempo,numero));
    }
    public void imprimir(){
        long primero=grafico.get(0).getTiempo();
        String salida="";
        for(CasillaGrafico c:grafico){
            System.out.println(c.getTiempo()-primero);
        }
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println("\n\n\n\n\n\n\n\n");
        for(CasillaGrafico c:grafico){
            System.out.println(c.getCasillas());
        }
    }
    
    public long getStartTime(){
        return grafico.get(0).getTiempo();
    }
}
