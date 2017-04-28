/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author Juan
 */
public class BT2 extends RecursiveTask<Void>{
    static int forks_hilos=0;     //lleva la cuenta de cuantos se han hecho para asi no hacer mas 
    static boolean forks=false;
    //static Grafico grafico=new Grafico();;
    private int i;
    private int j;
    boolean[][]presencia_filas;
    boolean [][]presencia_columnas;
    int[]suma_filas;
    int[]suma_columnas;
    private Tablero t;
    String tab;
    Grafico grafico;
    private static ForkJoinPool mainPool;
    public BT2(int i,int j,Tablero t,boolean[][]presencia_filas,boolean[][]presencia_columnas,int[]suma_filas,int[]suma_columnas,String tab,Grafico grafico){
        this.i=i;
        this.j=j;
        this.presencia_filas=presencia_filas;
        this.presencia_columnas=presencia_columnas;
        this.suma_filas=suma_filas;
        this.suma_columnas=suma_columnas;
        this.tab=tab;
        this.t=(t);
        this.grafico=grafico;
        mainPool=new ForkJoinPool(forks_hilos);
    }
    @Override
    protected Void compute(){
        solve();
        return null;
    }
    public void solve(/*int i,int j,Tablero t,boolean[][]presencia_filas,boolean[][]presencia_columnas,int[]suma_filas,int[]suma_columnas,String tab,Grafico grafico*/) {
        //t=(Tablero)ThreadBT.deepClone(t);
        boolean condicion1,condicion2,condicion3,condicion4;
        casillaMatriz siguiente,abajo;
        if(j==14){
            j=0;
            i++;
            tab+="\n";
        }
        if(i==14){
            System.out.println("<<Solucion>>");
            //t.toString();
            System.out.println(tab);
            long fin=System.nanoTime();
            System.out.println(grafico.getStartTime()/1000000);
            System.out.println(fin/1000000);
            System.out.println((fin-grafico.getStartTime())/1000000);
            
            
            synchronized(grafico){
                grafico.imprimir();
            }
            
            
            
            return ;
        }
        casillaMatriz cm=t.getCasillaMatriz(i, j);
        
        if(cm.isNegra()){      //no se le tiene que poner un valor
            //System.out.println("Negra: "+i+" "+j);
            /*
            if(forks_hilos>0){
                if(forks){
                    //mientras no se tengan forks
                    solve(i,j+1,t);
                }
                else{
                    ThreadBT tbt=new ThreadBT(i,j+1,t);
                    forks_hilos--;
                    tbt.start();
                    //solve(i,j+1,t);
                }
            }
            else{
                solve(i,j+1,t);
            }
            */
            //t=(Tablero)ThreadBT.deepClone(t);
            //solve(i,j+1,t);
            //Tablero w=(Tablero)ThreadBT.deepClone(t);
            Arrays.fill(presencia_filas[i],false);
            Arrays.fill(presencia_columnas[j],false);
            suma_filas[i]=0;
            suma_columnas[j]=0;
            tab+="■ ";
            //System.out.println("Fila Columna "+i+" "+j+" es negra");
            //solve(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab,grafico);
            new BT2(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab,grafico).solve();
            
            
        }
        else if(cm.isTriangulo()){
            //System.out.println("Triangulo: "+i+" "+j);
            //solve(i,j+1,t);
            //Tablero w=(Tablero)ThreadBT.deepClone(t);
            Arrays.fill(presencia_filas[i],false);
            Arrays.fill(presencia_columnas[j],false);
            suma_filas[i]=0;
            suma_columnas[j]=0;
            //System.out.println("Fila Columna "+i+" "+j+" es triangulo ");
            tab+="\\ ";
            new BT2(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab,grafico).solve();
        }
        else{
            abajo=t.getAbajo(i, j);
            siguiente=t.getSiguiente(i, j);
            for(int k=1;k<10;k++){
                //System.out.println("Numero: "+i+" "+j+" "+k);
                
                condicion1=!isInAbajo(presencia_columnas,k,j);
                condicion2=!isInDerecha(presencia_filas,k,i);
                if(siguiente.isNumeral()){
                    condicion3=(t.getSuma(i, j).getObjetivoDerecha()>getSumaFila(suma_filas,i)+k);
                }
                else{
                    condicion3=((t.getSuma(i, j).getObjetivoDerecha()==getSumaFila(suma_filas,i)+k));
                }
                if(abajo.isNumeral()){
                    condicion4=(t.getSuma(i, j).getObjetivoAbajo()>getSumaColumna(suma_columnas,j)+k);
                }
                else{
                    condicion4=((t.getSuma(i, j).getObjetivoAbajo()==getSumaColumna(suma_columnas,j)+k));
                }
                //System.out.println("ID: "+t.getSuma(i, j).getDerecha()+" Numero: "+i+" "+j+" "+k+" Sumas: d/a "+t.getSuma(i, j).getObjetivoDerecha()+" "+t.getSuma(i, j).getObjetivoAbajo()+" "+t.getSuma(i, j).getActualDerecha()+" "+t.getSuma(i, j).getActualAbajo()+/*t.getSuma(i, j)+" "+info.getSumaHorizontal(i)+" "+k+*/"  Condiciones: "+condicion1+" "+condicion2+" "+condicion3+" "+condicion4);
                //si se cumplen las condiciones de poda
                
                //System.out.println("Suma: "+getSumaFila(suma_filas,i)+" "+getSumaColumna(suma_columnas,j));
                //System.out.println("Fila Columna "+i+" "+j+" es triangulo "+k);
                //System.out.println(condicion1+" "+condicion2+" "+condicion3+" "+condicion4);
                if(condicion1&&condicion2&&condicion3&&condicion4){
                    
                    synchronized(grafico){
                        grafico.addCasilla(i, j);
                    }
                    
                    presencia_filas[i][k-1]=true;
                    presencia_columnas[j][k-1]=true;
                    suma_filas[i]+=k;
                    suma_columnas[j]+=k;
                    t.setCasillaMatriz(i, j, k);
                    //System.out.println("Fila Columna "+i+" "+j+" es vacia");
                    //System.out.println(condicion1+" "+condicion2+" "+condicion3+" "+condicion4);
                    //t.toString();
                    //System.out.println("TABLERO: "+i+" "+j);
                    //t.toString();
                    //System.out.println(forks_hilos+" "+forks);
                    if((Thread.activeCount()<forks_hilos&&!forks)||forks_hilos>0){
                        if(forks){
                            //mientras no se tengan forks
                            //System.out.println("FORKS "+forks_hilos);
                            //ForkJoinPool.commonPool().invoke(new BT2(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab+Integer.toString(k)+" ",grafico));
                            mainPool.invoke(new BT2(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab+Integer.toString(k)+" ",grafico));
                            //forks_hilos--;
                            //new BT2(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab,grafico).solve();
                            //solve(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab,grafico);
                        }
                        else{
                            //System.out.println("NOFORKS "+forks_hilos);
                            //new Thread(() -> solve(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab+Integer.toString(k)+" ")).start();
                            new ThreadBT2(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab+Integer.toString(k)+" ",grafico).start();
                        }
                    }
                    else{
                        //t=(Tablero)ThreadBT.deepClone(t);
                        //solve(i,j+1,t);
                        new BT2(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab+Integer.toString(k)+" ",grafico).solve();
                        //solve(i,j+1,t,deepCopyPresencia(presencia_filas),deepCopyPresencia(presencia_columnas),deepCopySuma(suma_filas),deepCopySuma(suma_columnas),tab+Integer.toString(k)+" ",grafico);
                    }
                    
                    t.setCasillaMatriz(i, j, 0);
                    presencia_filas[i][k-1]=false;
                    presencia_columnas[j][k-1]=false;
                    suma_filas[i]-=k;
                    suma_columnas[j]-=k;
                }
                    
            }
        } 
    }
    
    public static void setFH(int num){
        forks_hilos=num;
    }
    public static void setForks(boolean t){
        forks=t;
    }
    public static int getSumaColumna(int[]suma_filas,int j){
        return suma_filas[j];
    }
    public static int getSumaFila(int[]suma_filas,int j){
        return suma_filas[j];
    }
    
    public static boolean isInAbajo(boolean[][]presencia_filas,int num,int col){
        return presencia_filas[col][num-1];
    }
    public static boolean isInDerecha(boolean[][]presencia_columnas,int num,int fil){
        return presencia_columnas[fil][num-1];
    }
    public static boolean[][]deepCopyPresencia(boolean[][]arreglo){
        boolean[][]salida=new boolean[14][9];
        for(int i=0;i<14;i++){
            for(int j=0;j<9;j++){
                salida[i][j]=arreglo[i][j];
            }
        }
        return salida;
    }
    public static int[]deepCopySuma(int[]sumas){
        int[]salida=new int[14];
        for(int i=0;i<14;i++){
            salida[i]=sumas[i];
        }
        return salida;
    }
}

class ThreadBT2 extends Thread{
    private int i;
    private int j;
    boolean[][]presencia_filas;
    boolean [][]presencia_columnas;
    int[]suma_filas;
    int[]suma_columnas;
    private Tablero t;
    String tab;
    Grafico grafico;
    public ThreadBT2(int i,int j,Tablero t,boolean[][]presencia_filas,boolean[][]presencia_columnas,int[]suma_filas,int[]suma_columnas,String tab,Grafico grafico){
        this.i=i;
        this.j=j;
        this.presencia_filas=presencia_filas;
        this.presencia_columnas=presencia_columnas;
        this.suma_filas=suma_filas;
        this.suma_columnas=suma_columnas;
        this.tab=tab;
        this.t=(t);
        this.grafico=grafico;
    }
    @Override
    public void run(){
        new BT2(i, j, t,presencia_filas,presencia_columnas,suma_filas,suma_columnas,tab,grafico).solve();
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
