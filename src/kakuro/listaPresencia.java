/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuro;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Juan
 */
//tiene la informacion de la presencia de un numero en una columna o fila
public class listaPresencia implements Serializable{
    private boolean [] lista={false,false,false,false,false,false,false,false,false};
    
    public void setAllFalse(){
        for(int i=0;i<9;i++){
            lista[i]=false;
        }
        //Arrays.fill(lista, false);
    }
    
    public void setTrue(int indice){
        lista[indice-1]=true;
    }
    public void setFalse(int indice){
        lista[indice-1]=false;
    }
    public boolean isTrue(int indice){
        return lista[indice-1];
    }
    
    
    public boolean isAllTrue(){
        boolean es=true;
        for(boolean b:lista){
            es=es&&b;
        }
        return es;
    }
    
}
