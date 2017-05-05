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
    public  void main(String[] args) {
        System.out.println("hola");
        // TODO code application logic here
        /*
        System.out.println(Thread.activeCount());
        Informacion info =new Informacion();
        Tablero t=new Tablero();
        //BT2 bt2=new BT2();
        //esta pone el numero de forks o hilos que se quieren
        //BT2.setFH(5);
        //esta se pone true si se quieren forks
        //funciona en conjunto con la anterior, si con BT.setFH se pone mas de 1 hilo, entonces el programa 
        //usara hilos o forks, para saber cual usar usa BT2.setForks, si es verdadera entonces se usan forks, sino entonces hilos
        //BT2.setForks(true);
        t.readTablero("C:\\users\\juan\\desktop\\kakuro1.txt");//el path del tablero
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
        
 
        //Ventana v=new Ventana();
        
        //new BT2(0, 0, t,presencia_filas,presencia_columnas,suma_filas,suma_columnas,"",grafico,"",v).solve();
       //new BT2().solve(0, 0, t,presencia_filas,presencia_columnas,suma_filas,suma_columnas,"",grafico);
        
        System.out.println("que");
        System.out.println(Thread.activeCount());
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
        boolean [][] negras=new boolean[14][14];
        boolean [][] blancas=new boolean[14][14];
        boolean [][] instrucciones=new boolean[14][14];
        int[][] instruccionderecha=new int[14][14];
        int[][] instruccionabajo=new int[14][14];
        Grafico g=new Grafico();
        
        BTSolve.setForks(true);
       BTSolve.setForks_hilos(2);
        //new BTSolve(0,0,negras,blancas,instrucciones,instruccionderecha,instruccionabajo,g).solve();
    } 
}
