/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;

/**
 *
 * @author Juan
 */
public class Tablero {
    
    
    private casillaMatriz[][] tablero;
    private Objetivo[][] sumas;
    
    public Tablero(){
        tablero=new casillaMatriz[14][14];
        sumas=new Objetivo[14][14];
    }
    
    public void readTablero(String path){
        int i,j;
        Triangulo abajo,derecha;
        String a;
        i=0;
        j=0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null&&i<14) {
                String[] splited = line.split("\\s+");
                for(int k=0;k<splited.length;k++){
                    a=splited[k];
                    //System.out.println(i+" "+j+" "+a+" "+a.equals("b"));
                    //pone la informacion en el tablero de kakuro que se resolvera
                    if(a.equals("e")){
                        tablero[i][j]=new casillaMatriz(Integer.parseInt(splited[k+1]),Integer.parseInt(splited[k+2]));
                        
                    }
                    else if (a.equals("b")){
                        tablero[i][j]=new casillaMatriz();
                    }
                    else{
                        tablero[i][j]=new casillaMatriz(Integer.parseInt(a));
                    }
                    
                    //pone la informacion de las sumas en el tablero
                    if(tablero[i][j].isTriangulo()){
                        sumas[i][j]=new Objetivo(Integer.parseInt(splited[k+1]),Integer.parseInt(splited[k+2]));
                        k+=2;
                    }
                    else if (tablero[i][j].isNegra()){
                        sumas[i][j]=new Objetivo(0,0);
                    }
                    else{
                        abajo=sumas[i-1][j].getAbajo();
                        derecha=sumas[i][j-1].getDerecha();
                        sumas[i][j]=new Objetivo(abajo,derecha);
                    }
                    j++;
                    if(j==14){
                        j=0;
                        i++;
                    }  
                }
                line = br.readLine();
            }
            String everything = sb.toString();
        } 
        catch(Exception e){
            e.printStackTrace(System.out);
        }
        
    }

    public casillaMatriz getCasillaMatriz(int i,int j) {
        return tablero[i][j];
    }
    
    public casillaMatriz getSiguiente(int i,int j){
        if(j<13){
            return tablero[i][j+1];
        }
        else{
            return new casillaMatriz();     //la siguiente casilla no existe, se le da una vacia
        }
    }
    
    //la casilla que esta abajo de la actual
    public casillaMatriz getAbajo(int i,int j){
        if(i<13){
            return tablero[i+1][j];
        }
        else{
            return new casillaMatriz();
        }
    }
    
    public void setCasillaMatriz(int i,int j,int numero){
        tablero[i][j].setValuesCasilla(numero);
    }
    
    
    public String toString(){
        String salida="";
        for(int i=0;i<14;i++){
            for(int j=0;j<14;j++){
                if(tablero[i][j].isNumeral()){
                    salida+=String.valueOf(tablero[i][j].getNumero())+" ";
                }
                else if(tablero[i][j].isNegra()){
                    salida+="■ ";
                }
                else{
                    salida+="\\ ";
                }
            }
            salida+="\n";
        }
        System.out.println(salida);
        return salida;
    }
    
    public Objetivo getSuma(int i,int j){
        return sumas[i][j];
    }
    
    
}
