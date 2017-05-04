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
public class BTSolve {
    
    boolean [][]negras;
    boolean [][]blancas;
    boolean [][]instrucciones;
    int [][]instruccionderecha;
    int [][] instruccionabajo;
    int i,j;
    
    public BTSolve(int i,int j,boolean[][]negras,boolean[][]blancas,boolean[][]instrucciones,int[][]instruccionderecha,int[][]instruccionabajo){
        
    }

    
    
    public void solve(){
        if(j==14){
            j=0;
            i++;
        }
        if(i==14){
            System.out.println("<<Generado>>");
            //imprimir el tablero
        }
        
        if(i==0){
            //si es la primera fila, limita los valores que se pueden poner en ese espacio
            if(j==0){
                //si es la primera columna
                negras[i][j]=true;
                blancas[i][j]=false;
                instrucciones[i][j]=false;
                solve(i,j+1,deepCopy(negras),deepCopy(blancas),deepCopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
            }
            else{
                //primer fila, no primera columna
                for(int k=0;k<2;k++){
                    //k=1, prueba con una negra
                    //k=2, prueba con una instruccion
                    if(k==0){
                        negras[i][j]=true;
                        blancas[i][j]=false;
                        instrucciones[i][j]=false;
                        solve(i,j+1,deepCopy(negras),deepCopy(blancas),deepCopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                    }
                    else if (k==1){
                        negras[i][j]=false;
                        blancas[i][j]=false;
                        instrucciones[i][j]=true;
                        instruccionderecha[i][j]=0;
                        for(int w=3;w<46;w++){
                            instruccionabajo[i][j]=w;
                            solve(i,j+1,deepCopy(negras),deepCopy(blancas),deepCopy(instrucciones),deepCopy(instruccionesderecha),deepcopy(instruccionabajo));
                            instruccionabajo[i][j]=0;
                        }
                        
                        
                    }
                }
            }
        }
        
        else{
            //no es la primer fila
            if(j==0){
                //si es la primera columna
                negras[i][j]=true;
                blancas[i][j]=false;
                instrucciones[i][j]=true;
                solve(i,j+1,deepCopy(negras),deepCopy(blancas),deepCopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
            }
            else{
                //no primera columna
                for(int k=0;k<2;k++){
                    //k=1, prueba con una negra
                    //k=2, prueba con una instruccion
                    if(k==0){
                        negras[i][j]=true;
                        blancas[i][j]=false;
                        instrucciones[i][j]=false;
                        solve(i,j+1,deepCopy(negras),deepCopy(blancas),deepCopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                    }
                    else if (k==1){
                        negras[i][j]=false;
                        blancas[i][j]=false;
                        instrucciones[i][j]=true;
                        instruccionderecha[i][j]=0;
                        for(int w=3;w<46;w++){
                            instruccionabajo[i][j]=w;
                            solve(i,j+1,deepCopy(negras),deepCopy(blancas),deepCopy(instrucciones),deepCopy(instruccionesderecha),deepcopy(instruccionabajo));
                            instruccionabajo[i][j]=0;
                        }
                        
                        
                    }
                }
            
            
            
            
            
            
            
            
            
            
            
            
            }
        
        
        
        
        
        }
    
    }
    public static boolean[][]deepCopy(boolean[][]arreglo){
        boolean[][]salida=new boolean[14][14];
        for(int i=0;i<14;i++){
            for(int j=0;j<14;j++){
                salida[i][j]=arreglo[i][j];
            }
        }
        return salida;
    }
    
     public static int[][]deepCopy(int[][]arreglo){
        int[][]salida=new int[14][14];
        for(int i=0;i<14;i++){
            for(int j=0;j<14;j++){
                salida[i][j]=arreglo[i][j];
            }
        }
        return salida;
    }
    
}
