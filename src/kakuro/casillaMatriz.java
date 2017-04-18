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
public class casillaMatriz {
    private int numero;
    private int abajo;
    private int derecha;
    private boolean negra;          //es una negra
    private boolean triangulo;      //es una instruccion
    private boolean numeral;        //es blanca
    
    public casillaMatriz(){
        negra=true;
        triangulo=false;
        numeral=false;
        numero=0;
    }
    
    public casillaMatriz(int numero){
        this.numero=numero;
        this.negra=false;
        numeral=true;
        triangulo=false;
    }
    public casillaMatriz(int abajo,int derecha){
        this.abajo=abajo;
        this.derecha=derecha;
        triangulo=true;
        numeral=false;
        negra=false;
    }
    
    public void setValuesCasilla(int numero){
        this.numero=numero;
        this.negra=false;
    }
    /*
    public void setValuesCasilla(int abajo,int derecha){
        this.abajo=abajo;
        this.derecha=derecha;
        triangulo=true;
        negra=false;
    }
    */
    public int getNumero() {
        return numero;
    }

    public int getAbajo() {
        return abajo;
    }

    public int getDerecha() {
        return derecha;
    }
    
    
    
    public boolean isNegra() {
        return negra;
    }

    public boolean isTriangulo() {
        return triangulo;
    }

    public boolean isNumeral() {
        return numeral;
    }
    
    public void imprimir(){
        if(negra){
            System.out.println("■");
        }
        else if(triangulo){
            System.out.println("△");
        }
        else{
            System.out.println(numero);
        }
    }
    
    

    
    
    
}
