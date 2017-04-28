/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuro;

import java.util.Arrays;

/**
 *
 * @author Juan
 */
public class Kakuro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Informacion info =new Informacion();
        Tablero t=new Tablero();
        //BT2 bt2=new BT2();
        BT2.setFH(5);
        BT2.setForks(true);
        t.readTablero("C:\\users\\juan\\desktop\\kakuro1.txt");
        t.toString();
        boolean[][]presencia_filas=new boolean[14][9];
        boolean[][]presencia_columnas=new boolean[14][9];
        int[]suma_filas=new int[14];
        int[]suma_columnas=new int[14];
        Arrays.fill(suma_filas, 0);
        Arrays.fill(suma_columnas, 0);
        for( int m=0;m<14;m++){
            Arrays.fill(presencia_filas[m], false);
            Arrays.fill(presencia_columnas[m], false);
            
        }
        Grafico grafico=new Grafico();
        //new BT().solve(0,0, t);
        
 
        
        new BT2(0, 0, t,presencia_filas,presencia_columnas,suma_filas,suma_columnas,"",grafico).solve();
       //new BT2().solve(0, 0, t,presencia_filas,presencia_columnas,suma_filas,suma_columnas,"",grafico);
        
        //System.out.println(t.toString());
       /*
        int[]suma_filas=new int[14];
        for(int i=0;i<4;i++){
            suma_filas[i]=2*i;
        }
        for(int i=0;i<4;i++){
            System.out.println(suma_filas[i]);
        }
        a(suma_filas);
        for(int i=0;i<4;i++){
            System.out.println(suma_filas[i]);
        }
        */
    }
    
    public static void a (int[]a){
        for(int i=0;i<10;i++){
            a[i]=-45;
        }
    }

    
}
