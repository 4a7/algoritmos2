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
public class Informacion {
    
    private listaPresencia[][] filas;
    private listaPresencia[][] columnas;
    private Integer[][] sumasVerticales;
    private Integer [][] sumasHorizontales;
    private Integer[][] objetivosVerticales;
    private Integer[][] objetivosHorizontales;
    
    public Informacion(){
        filas=new listaPresencia[14][14];
        columnas=new listaPresencia[14][14];
        sumasVerticales=new Integer[14][14];
        sumasHorizontales=new Integer[14][14];
        objetivosVerticales=new Integer[14][14];
        objetivosHorizontales=new Integer[14][14];
        for(int i=0;i<14;i++){
            for(int j=0;j<14;j++){
                filas[i][j]=new listaPresencia();
                columnas[i][j]=new listaPresencia();
                sumasVerticales[i][j]=new Integer(0);
                sumasHorizontales[i][j]=new Integer(0);
                objetivosVerticales[i][j]=new Integer(0);
                objetivosHorizontales[i][j]=new Integer(0);
            }
        }
    }
    
    //marca un numero como presente
    public void addNumero(int fila,int columna,int numero){
        sumasVerticales[fila][columna]=sumasVerticales[fila][columna]+numero;
        sumasHorizontales[fila][columna]=sumasHorizontales[fila][columna]+numero;
        filas[fila][columna].setTrue(numero);
        columnas[fila][columna].setTrue(numero);
    }
    public void subtractNumero(int fila,int columna,int numero){
        sumasVerticales[fila][columna]=sumasVerticales[fila][columna]-numero;
        sumasHorizontales[fila][columna]=sumasHorizontales[fila][columna]-numero;
        filas[fila][columna].setFalse(numero);
        columnas[fila][columna].setFalse(numero);
    }
    
    public void reiniciarFila(int fila,int columna){
        sumasHorizontales[fila][columna]=0;
        filas[fila][columna].setAllFalse();
    }
    
    public void reiniciarColumna(int fila,int columna){
        sumasVerticales[fila][columna]=0;
        columnas[fila][columna].setAllFalse();
    }
    /*
    public boolean isColActive(int fila,int columna,int numero){
        if(sumasVerticales.get(numero)==0){
            return false;
        }
        return true;
    }
    
    public boolean isFilaActive(int numero){
        if(sumasHorizontales.get(numero)==0){
            return false;
        }
        return true;
    }
    */
    public void nuevoObjetivoHorizontal(int fila,int columna,int numero){
        objetivosHorizontales[fila][columna]=numero;
    }
    
    public void nuevoObjetivoVertical(int fila,int columna,int numero){
        objetivosVerticales[fila][columna]=numero;
    }
    
    public boolean isInColumn(int fila,int columna,int numero){
        return columnas[fila][columna].isTrue(numero);
        
    }
    
    public boolean isInFila(int fila,int columna,int numero){
        return filas[fila][columna].isTrue(numero);
        
    }
    
    public int getObjetivoVertical(int fila,int columna){
        return objetivosVerticales[fila][columna];
    }
    public int getSumaVertical(int fila,int columna){
        return sumasVerticales[fila][columna];
    }
    
    
    public int getObjetivoHorizontal(int fila,int columna){
        return objetivosHorizontales[fila][columna];
    }
    public int getSumaHorizontal(int fila,int columna){
        return sumasHorizontales[fila][columna];
    }
}
