/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuro;

import static java.awt.SystemColor.text;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import static kakuro.BTSolve.deepcopy;

/**
 *
 * @author Juan
 */
public class BTSolve extends RecursiveTask<Void>{
    static int forks_hilos=1;     //lleva la cuenta de cuantos se han hecho para asi no hacer mas 
    static boolean forks=false;
    static int soluciones=1;
    static boolean ya=false;
    //static Grafico grafico=new Grafico();;
    boolean [][] negras=new boolean[14][14];
    boolean [][] blancas;
    boolean [][] instrucciones;
    int[][] instruccionderecha;
    int[][] instruccionabajo;
    int i,j;
    Grafico g;
    Ventana v;
    private static ForkJoinPool mainPool;
    
    public BTSolve(){
        boolean [][] negras=new boolean[14][14];
        boolean [][] blancas=new boolean[14][14];
        boolean [][] instrucciones=new boolean[14][14];
        int[][] instruccionderecha=new int[14][14];
        int[][] instruccionabajo=new int[14][14];
        Grafico g=new Grafico();
        
        
        //solve(0,0,negras,blancas,instrucciones,instruccionderecha,instruccionabajo,g);
    }
    public BTSolve(int i,int j,boolean[][]negras,boolean[][]blancas,boolean[][]instrucciones,int[][]instruccionderecha,int[][]instruccionabajo,Grafico g,Ventana v){
        this.negras=negras;
        this.i=i;
        this.j=j;
        this.blancas=blancas;
        this.instrucciones=instrucciones;
        this.instruccionderecha=instruccionderecha;
        this.instruccionabajo=instruccionabajo;
        this.g=g;
        if(forks_hilos>1){
            mainPool=new ForkJoinPool(forks_hilos);
        }
        else{
            mainPool=new ForkJoinPool(1);
        }
        this.v=v;
        //solve(i,j,negras,blancas,instrucciones,instruccionderecha,instruccionabajo,g);
    }

    @Override
    protected Void compute(){
        solve();
        return null;
    }
    
    public void solve(){
        
        if(j==14){
            j=0;
            i++;
        }
        if(i==14){
            
            if(!ya){
                System.out.println("<<Generado>>");
                //imprimir el tablero
                //System.out.println();
                String tab=imprimirTablero(negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                v.setPathInterfaz();
                v.ponerTablero(tab);
            try(  PrintWriter out = new PrintWriter( "generado.txt" )  ){
                out.println( tab );
            }
            catch(Exception e){
                
            }
            }
            ya=true;
            soluciones--;
            return;
        }
        
        if(i==0){
            //si es la primera fila, limita los valores que se pueden poner en ese espacio
            if(j==0){
                //si es la primera columna
                negras[i][j]=true;
                blancas[i][j]=false;
                instrucciones[i][j]=false;
                if((Thread.activeCount()<forks_hilos&&!forks)||forks_hilos>1){
                        //System.out.println(forks_hilos);
                    if(forks){
                        mainPool.invoke(new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v));
                    }
                    else{
                        new ThreadBTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).run();
                    }
                }
                else{
                    new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).solve();
                }
                negras[i][j]=false;
                synchronized(g){
                    g.addCasilla(i, j);
                }
                
                
            
            
            }
            else{
                for(int k=0;k<2;k++){
                    //k=1, prueba con una negra
                    //k=2, prueba con una instruccion
                    if(k==1){
                        negras[i][j]=true;
                        blancas[i][j]=false;
                        instrucciones[i][j]=false;
                        synchronized(g){
                            g.addCasilla(i, j);
                        }
                        if((Thread.activeCount()<forks_hilos&&!forks)||forks_hilos>1){
                                //System.out.println(forks_hilos);
                            if(forks){
                                mainPool.invoke(new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v));
                            }
                            else{
                                new ThreadBTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).run();
                            }
                        }
                        else{
                            new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).solve();
                        }
                        negras[i][j]=false;
                    }
                    else if (k==0){
                        if(j<13&&i<13){
                        negras[i][j]=false;
                        blancas[i][j]=false;
                        instrucciones[i][j]=true;

                        instrucciones[i][j]=true;
                        boolean a=new BT(i,j).solve(0, 0, negras, blancas, instrucciones, instruccionderecha, instruccionabajo);
                        siguienteInstruccion(i,j,negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                        //System.out.println("Tablero: "+ix+" "+iy+" "+a);
                        //imprimirTablero(negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                        if(a){
                            synchronized(g){
                                g.addCasilla(i, j);
                            }
                            if((Thread.activeCount()<forks_hilos&&!forks)||forks_hilos>1){
                                    //System.out.println(forks_hilos);
                                if(forks){
                                    mainPool.invoke(new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v));
                                }
                                else{
                                    new ThreadBTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).run();
                                }
                            }
                            else{
                                new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).solve();
                            }
                        }
                        instrucciones[i][j]=false;
                        instruccionderecha[i][j]=0;
                        instruccionabajo[i][j]=0;

                       
                        
                        }
                    }
                }
            }
        }
        else{
            boolean blanca,negra,instruccion;
            blanca=negra=instruccion=true;
            if(j==0){
                
                blancas[i][j]=false;
                negras[i][j]=false;
                instrucciones[i][j]=true;
                //funcion que pone los numeros
                siguienteInstruccion(i,j,negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                boolean a=new BT(i,j).solve(0, 0, negras, blancas, instrucciones, instruccionderecha, instruccionabajo);
                //System.out.println("Tablero: "+ix+" "+iy+" "+a+instrucciones[i][j]);
                //imprimirTablero(negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                if(a){
                    synchronized(g){
                        g.addCasilla(i, j);
                    }
                    if((Thread.activeCount()<forks_hilos&&!forks)||forks_hilos>1){
                        //System.out.println(forks_hilos);
                            if(forks){
                                mainPool.invoke(new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v));
                            }
                            else{
                                new ThreadBTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).run();
                            }
                        }
                        else{
                            new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).solve();
                        }
                }
                //solve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo));
                instrucciones[i][j]=false;
                instruccionderecha[i][j]=0;
                instruccionabajo[i][j]=0;
                //chequea con una negra
                negras[i][j]=true;
                blancas[i][j]=false;
                instrucciones[i][j]=false;
                synchronized(g){
                    g.addCasilla(i, j);
                }
                if((Thread.activeCount()<forks_hilos&&!forks)||forks_hilos>1){
                        //System.out.println(forks_hilos);
                    if(forks){
                        mainPool.invoke(new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v));
                    }
                    else{
                        new ThreadBTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).run();
                    }
                }
                else{
                    new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).solve();
                }
                negras[i][j]=false;
                blancas[i][j]=false;
                instrucciones[i][j]=false;
                //chequea con una instruccion
                
                
                
                
                
            }
            else{
                //la de la izquierda
                if(negras[i][j-1]||(instruccionderecha[i][j-1]==0&&instrucciones[i][j-1])){
                    //si la anterior es negra
                    blanca=false;
                    
                }
                if (instrucciones[i][j-1]&&instruccionderecha[i][j-1]!=0){
                    
                    negra=false;
                    instruccion=false;
                }
                
                //la de arriba
                if(negras[i-1][j]||(instruccionabajo[i-1][j]==0&&instrucciones[i-1][j])){
                    
                    blanca=false;
                }
                if (instrucciones[i-1][j]&&instruccionabajo[i-1][j]!=0){
                    negra=false;
                    instruccion=false;
                    
                }
                if(blanca){
                    //System.out.println("blanca "+i+" "+j);
                    blancas[i][j]=true;
                    instrucciones[i][j]=false;
                    negras[i][j]=false;
                    
                    boolean a=new BT(i,j).solve(0, 0,(negras), (blancas), instrucciones, instruccionderecha, instruccionabajo);
                    //System.out.println("Tablero: "+ix+" "+iy+" "+a);
                    //imprimirTablero(negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                    
                    if(a){
                        synchronized(g){
                            g.addCasilla(i, j);
                        }
                        if((Thread.activeCount()<forks_hilos&&!forks)||forks_hilos>1){
                        //System.out.println(forks_hilos);
                            if(forks){
                                mainPool.invoke(new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v));
                            }
                            else{
                                new ThreadBTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).run();
                            }
                        }
                        else{
                            new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).solve();
                        }
                    }
                    blancas[i][j]=false;
                    //instruccionderecha[i][j]=0;
                    //instruccionabajo[i][j]=0;
                }
                if(instruccion&&j<13&&i<13){
                    instrucciones[i][j]=true;
                    blancas[i][j]=false;
                    negras[i][j]=false;
                    //funcion que pone los numeros4

                    instrucciones[i][j]=true;
                    boolean a=new BT(i,j).solve(0, 0, negras, blancas, instrucciones, instruccionderecha, instruccionabajo);
                    siguienteInstruccion(i,j,negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                    //System.out.println("Tablero: "+ix+" "+iy+" "+a);
                    //imprimirTablero(negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                    if(a){
                        synchronized(g){
                            g.addCasilla(i, j);
                        }
                        if((Thread.activeCount()<forks_hilos&&!forks)||forks_hilos>1){
                        //System.out.println(forks_hilos);
                            if(forks){
                                mainPool.invoke(new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v));
                            }
                            else{
                                new ThreadBTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).run();
                            }
                        }
                        else{
                            new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).solve();
                        }
                    }
                    instrucciones[i][j]=false;
                    instruccionderecha[i][j]=0;
                    instruccionabajo[i][j]=0;

                    instrucciones[i][j]=false;
                    instruccionderecha[i][j]=0;
                    instruccionabajo[i][j]=0;
                }
                if(negra){
                    blancas[i][j]=false;
                    instrucciones[i][j]=false;
                    negras[i][j]=true;

                    boolean a=new BT(i,j).solve(0, 0, negras, blancas, instrucciones, instruccionderecha, instruccionabajo);

                    //System.out.println("Tablero: "+ix+" "+iy+" "+a);
                    //imprimirTablero(negras,blancas, instrucciones, instruccionderecha, instruccionabajo);
                    if(a){
                        synchronized(g){
                            g.addCasilla(i, j);
                        }
                        if((Thread.activeCount()<forks_hilos&&!forks)||forks_hilos>1){
                        //System.out.println(forks_hilos);
                            if(forks){
                                mainPool.invoke(new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v));
                            }
                            else{
                                new ThreadBTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).run();
                            }
                        }
                        else{
                            new BTSolve(i,j+1,deepcopy(negras),deepcopy(blancas),deepcopy(instrucciones),deepcopy(instruccionderecha),deepcopy(instruccionabajo),g,v).solve();
                        }
                    }
                    negras[i][j]=false;
                }
                
                
                
                
                
                
                
                    
                
                
            }
            
            
            
            
            
            
            
            
            
            
            
        }
        
        
        
        
        
    }
    
    public void siguienteInstruccion(int i,int j,boolean[][]negras,boolean[][]blancas,boolean[][]instrucciones,int[][]instruccionderecha,int[][]instruccionabajo){
        //la casilla que va a poner es la de la derecha
        Random rand = new Random(); 
        int primeracondicion = rand.nextInt(50);
        int segundacondicion = rand.nextInt(50);// (int )(Math.random() * 45 + 3);
        //System.out.println(primeracondicion+" "+segundacondicion);
        boolean primera=primeracondicion<3;
        boolean segunda=segundacondicion<3;
        //System.out.println(primera+" "+segunda);
        int terceracondicion = (int )(Math.random() * 45 + 3);
        int cuartacondicion=(int) (Math.random()*45+3);
        negras[i][j]=false;
        blancas[i][j]=false;
        instrucciones[i][j]=true;
        instruccionderecha[i][j]=segundacondicion;
        instruccionabajo[i][j]=terceracondicion;
        if(primera||j>10||i==0){
            instruccionderecha[i][j]=0;
        }
        if(segunda||i>10||j==0){
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
    public String imprimirTablero(boolean[][]negras,boolean[][]blancas,boolean[][]instrucciones,int[][]instruccionderecha,int[][]instruccionabajo){
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
        
        return arch;
    }

    public static void setForks_hilos(int forks_hilos) {
        BTSolve.forks_hilos = forks_hilos;
    }

    public static void setForks(boolean forks) {
        BTSolve.forks = forks;
    }
    
    
    
    
}

class ThreadBTSolve extends Thread{
    boolean [][] negras;
    boolean [][] blancas;
    boolean [][] instrucciones;
    int[][] instruccionderecha;
    int[][] instruccionabajo;
    int i,j;
    String arch;
    String tab;
    Grafico grafico;
    Ventana v;
    public ThreadBTSolve(int i,int j,boolean[][]negras,boolean[][]blancas,boolean[][]instrucciones,int[][]instruccionderecha,int[][]instruccionabajo,Grafico g,Ventana v){
        this.i=i;
        this.j=j;
        this.negras=negras;
        this.blancas=blancas;
        this.instrucciones=instrucciones;
        this.instruccionderecha=instruccionderecha;
        this.instruccionabajo=instruccionabajo;
        grafico=g;
        this.v=v;
    }
    @Override
    public void run(){
        new BTSolve(i, j, negras,blancas,instrucciones,instruccionderecha,instruccionabajo,grafico,v).solve();
    }
    
    public static Object deepClone(Object object) {
        
        try {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          ObjectOutputStream oos = new ObjectOutputStream(baos);
          oos.writeObject(object);
          ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
          ObjectInputStream ois = new ObjectInputStream(bais);
          return ois.readObject();
        }
        catch (Exception e) {
          e.printStackTrace();
          return object;
        }
        
        /*
        XStream x = new XStream(new StaxDriver());
        Object myClone = x.fromXML(x.toXML(object));
        return myClone;
        */
      }
    
    
}

