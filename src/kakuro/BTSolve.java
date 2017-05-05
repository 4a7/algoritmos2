/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuro;

import java.util.Random;

/**
 *
 * @author Juan
 */
public class BTSolve {
    
    
    public BTSolve(){
        boolean [][] negras=new boolean[14][14];
        boolean [][] blancas=new boolean[14][14];
        boolean [][] instrucciones=new boolean[14][14];
        int[][] instruccionderecha=new int[14][14];
        int[][] instruccionabajo=new int[14][14];
        solve(0,0,negras,blancas,instrucciones,instruccionderecha,instruccionabajo);
    }

    
    
    public void solve(int i,int j,boolean[][]negras,boolean[][]blancas,boolean[][]instrucciones,int[][]instruccionderecha,int[][]instruccionabajo){
        if(j==14){
            j=0;
            i++;
        }
        if(i==14){
            System.out.println("<<Generado>>");
            imprimirTablero(negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
            //imprimir el tablero
            return;
        }
        
        if(i==0){
            //si es la primera fila, limita los valores que se pueden poner en ese espacio
            if(j==0){
                //si es la primera columna
                negras[i][j]=true;
                blancas[i][j]=false;
                instrucciones[i][j]=false;
                solve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
            }
            else{
                for(int k=0;k<2;k++){
                    //k=1, prueba con una negra
                    //k=2, prueba con una instruccion
                    if(k==0){
                        negras[i][j]=true;
                        blancas[i][j]=false;
                        instrucciones[i][j]=false;
                        solve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                    }
                    else if (k==1){
                        negras[i][j]=false;
                        blancas[i][j]=false;
                        instrucciones[i][j]=true;
                        instruccionderecha[i][j]=0;
                        
                        siguienteInstruccion(i,j,negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                        solve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                        
                        
                    }
                }
            }
        }
        else{
            boolean blanca,negra,instruccion;
            blanca=negra=instruccion=true;
            if(j==0){
                //chequea con una negra
                negras[i][j]=true;
                blancas[i][j]=false;
                instrucciones[i][j]=false;
                solve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                negras[i][j]=false;
                //chequea con una instruccion
                
                blancas[i][j]=false;
                blancas[i][j]=false;
                instrucciones[i][j]=true;
                //funcion que pone los numeros
                solve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                instrucciones[i][j]=false;
                
                
                
            }
            else{
                //la de la izquierda
                if(negras[i][j-1]||instruccionderecha[i][j-1]==0){
                    //si la anterior es negra
                    blanca=false;
                    
                }
                if (instrucciones[i][j-1]&&instruccionderecha[i][j-1]!=0){
                    
                    negra=false;
                    instruccion=false;
                }
                
                //la de arriba
                if(negras[i-1][j]||instruccionabajo[i][j-1]==0){
                    
                    blanca=false;
                }
                if (instrucciones[i-1][j]&&instruccionabajo[i-1][j]!=0){
                    negra=false;
                    instruccion=false;
                    
                }
                if(blanca){
                    blancas[i][j]=true;
                    instrucciones[i][j]=false;
                    negras[i][j]=false;
                    solve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                    blancas[i][j]=false;
                }
                if(negra){
                    blancas[i][j]=false;
                    instrucciones[i][j]=false;
                    negras[i][j]=true;
                    if(new BT(i,j).solve(0, 0, negras, blancas, instrucciones, instruccionderecha, instruccionabajo)){
                        solve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                    }
                    negras[i][j]=false;
                }
                if(instruccion){
                    instrucciones[i][j]=true;
                    blancas[i][j]=false;
                    negras[i][j]=false;
                    //funcion que pone los numeros
                    if(new BT(i,j).solve(0, 0, negras, blancas, instrucciones, instruccionderecha, instruccionabajo)){
                        siguienteInstruccion(i,j,negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                        solve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                    }
                    instrucciones[i][j]=false;
                }
                
                
                    
                
                
            }
            
            
            
            
            
            
            
            
            
            
            
        }
        
        
        
        
        
    }
    
    public void siguienteInstruccion(int i,int j,boolean[][]negras,boolean[][]blancas,boolean[][]instrucciones,int[][]instruccionderecha,int[][]instruccionabajo){
        //la casilla que va a poner es la de la derecha
        Random rand = new Random(); 
        int primeracondicion = rand.nextInt(50);
        int segundacondicion = rand.nextInt(50);// (int )(Math.random() * 45 + 3);
        boolean primera=primeracondicion<3;
        boolean segunda=segundacondicion<3;
        
        int terceracondicion = (int )(Math.random() * 45 + 3);
        int cuartacondicion=(int) (Math.random()*45+3);
        negras[i][j]=false;
        blancas[i][j]=false;
        instrucciones[i][j]=true;
        instruccionderecha[i][j]=segundacondicion;
        instruccionabajo[i][j]=terceracondicion;
        if(primera||j>10){
            instruccionderecha[i][j]=0;
        }
        if(segunda||i>10||(i==13&&j==13)){
            instruccionabajo[i][j]=0;
        }    
    }

    
    
    public static boolean[][]deepcopy(boolean[][]arreglo){
        boolean[][]salida=new boolean[14][14];
        for(int i=0;i<14;i++){
            for(int j=0;j<14;j++){
                salida[i][j]=arreglo[i][j];
            }
        }
        return salida;
    }
    
     public static int[][]deepcopy(int[][]arreglo){
        int[][]salida=new int[14][14];
        for(int i=0;i<14;i++){
            for(int j=0;j<14;j++){
                salida[i][j]=arreglo[i][j];
            }
        }
        return salida;
    }
    public void imprimirTablero(boolean[][]negras,boolean[][]blancas,boolean[][]instrucciones,int[][]instruccionderecha,int[][]instruccionabajo){
        String arch="";
        String tab="";
        String derecha,abajo;
        for(int i=0;i<14;i++){
            for(int j=0;j<14;j++){
                if(negras[i][j]){
                    tab+="■ ";
                    arch+="b ";
                }
                else  if(blancas[i][j]){
                    tab+="0 ";
                    arch+="0 ";
                }
                else if (instrucciones[i][j]){
                    tab+="\\ ";
                    arch+="e ";
                    derecha=Integer.toString(instruccionderecha[i][j]);
                    abajo=Integer.toString(instruccionabajo[i][j]);
                    arch+=abajo+" "+derecha+" ";
                }
                else {
                    tab+="* ";
                    arch+="* ";
                }
                
                
            }
            tab+="\n";
            arch+="\n";
        }
        System.out.println(tab);
        System.out.println("---------");
        System.out.println(arch);
        
    }
    
}
