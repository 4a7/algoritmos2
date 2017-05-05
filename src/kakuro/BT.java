package kakuro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author Juan
 */
public class BT{
    int forks_hilos=0;     //lleva la cuenta de cuantos se han hecho para asi no hacer mas 
    boolean forks=false;
    Grafico grafico=new Grafico();
    int[] sumaFila=new int[14];
    int[] sumaCol=new int[14];
    int[] objetivoFila=new int[14];
    int[] objetivoCol=new int[14];
    boolean[][] presenciaFila=new boolean[14][9];
    boolean[][] presenciaCol=new boolean[14][9];
    
    boolean abajo,siguiente;
    int ix,iy;
    public BT(int ix,int iy){
        this.ix=ix;
        this.iy=iy;
    }
    public boolean solve(int i,int j,boolean[][]negras,boolean[][]blancas,boolean[][]instrucciones,int[][]instruccionDerecha,int[][]instruccionAbajo){
        //System.out.println("gniblos "+i+" "+j);
        boolean a=false;
        //t=(Tablero)ThreadBT.deepClone(t);
        boolean condicion1,condicion2,condicion3,condicion4;
        //System.out.println(i+" "+ix+" "+j+" "+iy);
        if(j==14){
            j=0;
            i++;
        }
        if(/*(j==iy-1 && i==ix)||(j==13&&i==ix-1&&iy==0)*/(j==iy+1&&i==ix)||(iy==13&&j==0&&ix+1==i)/**//*i==ix&&j==iy*/){
            //System.out.println(i+" "+j);
            return true;
        }
        
        if(i==14){
            
            
            return true;
        }
        //casillaMatriz cm=t.getCasillaMatriz(i, j);
        
        if(negras[i][j]){      //no se le tiene que poner un valor
            sumaFila[i]=0;
            sumaCol[j]=0;
            for(int k=0;k<9;k++){
                presenciaFila[i][k]=false;
                presenciaCol[j][k]=false;
            }
           
            a=a||solve(i,j+1,negras,blancas,instrucciones,instruccionDerecha,instruccionAbajo);
            
        }
        else if(instrucciones[i][j]){
            sumaFila[i]=0;
            sumaCol[j]=0;
            objetivoFila[i]=instruccionDerecha[i][j];
            objetivoCol[j]=instruccionAbajo[i][j];
            for(int k=0;k<9;k++){
                presenciaFila[i][k]=false;
                presenciaCol[j][k]=false;
            }
            a=a||solve(i,j+1,negras,blancas,instrucciones,instruccionDerecha,instruccionAbajo);
        }
        else if (blancas[i][j]){
            
            if(i<ix||(i==ix-1&&j<iy)){
                abajo = blancas[i+1][j];
               
            }else{
                //System.out.println("at");
                abajo=true;
            }
            if(j<13){
                siguiente=blancas[i][j+1];
            }
            else{
                siguiente=false;
            }
            if(i==ix&&j==iy){
                //System.out.println("st");
                siguiente=true;
            }
            
            /*
            
            if(j<iy){
                siguiente = blancas[i][j+1];
            }else{
                siguiente=true;
      
            }
            */
            
            //System.out.println("objetivos "+objetivoFila[i]+" "+objetivoCol[j]);
            
            for(int k=0;k<9;k++){
                        //System.out.println("Numero: "+i+" "+j+" "+k);

                condicion1=!(presenciaCol[j][k]);
                condicion2=!(presenciaFila[i][k]);
                if(siguiente){
                    condicion3=(objetivoFila[i]>sumaFila[i]+k+1);
                }
                else{
                    condicion3=(objetivoFila[i]==sumaFila[i]+k+1);
                }
                if(abajo){
                    condicion4=(objetivoCol[j]>sumaCol[j]+k+1);
                }
                else{
                    condicion4=(objetivoCol[j]==sumaCol[j]+k+1);
                  
                }
                //System.out.println("ID: "+t.getSuma(i, j).getDerecha()+" Numero: "+i+" "+j+" "+k+" Sumas: d/a "+t.getSuma(i, j).getObjetivoDerecha()+" "+t.getSuma(i, j).getObjetivoAbajo()+" "+t.getSuma(i, j).getActualDerecha()+" "+t.getSuma(i, j).getActualAbajo()+/*t.getSuma(i, j)+" "+info.getSumaHorizontal(i)+" "+k+*/"  Condiciones: "+condicion1+" "+condicion2+" "+condicion3+" "+condicion4);
                //si se cumplen las condiciones de poda
                if(condicion1&&condicion2&&condicion3&&condicion4){
                    sumaCol[j]+=k+1;
                    sumaFila[i]+=k+1;
                    presenciaFila[i][k]=true;
                    presenciaCol[j][k]=true;
                    //t.setCasillaMatriz(i, j, k);
                    //System.out.println("TABLERO: "+i+" "+j);
                    //t.toString();
                    //System.out.println(forks_hilos+" "+forks);

                    a=a||solve(i,j+1,negras,blancas,instrucciones,instruccionDerecha,instruccionAbajo);

                    //t.setCasillaMatriz(i, j, 0);
                    sumaCol[j]-=k+1;
                    sumaFila[i]-=k+1;
                    presenciaFila[i][k]=false;
                    presenciaCol[j][k]=false;
                }

            }
            
        }
        else{
            System.out.println("juajuajua********"+i+" "+j+" "+ix+" "+iy);
            //return true;
        }
        return a;
    }
    
    /*public static void setFH(int num){
        forks_hilos=num;
    }
    public static void setForks(boolean t){
        forks=t;
    }
    public static void setMaxXY(int x,int y){
        ix=x-1;
        iy=y-1;
    }
}
class ThreadBT extends Thread{
    private int i;
    private int j;
    private Tablero t;
    public ThreadBT(int i,int j,Tablero t){
        this.i=i;
        this.j=j;
        
        this.t=(Tablero)deepClone(t);
    }
    @Override
    public void run(){
        //System.out.println("Solving "+i+" "+j);
        BT.solve(i, j, t);
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
        
      }
    
    */
}