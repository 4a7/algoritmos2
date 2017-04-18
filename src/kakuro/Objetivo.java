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
public class Objetivo {
    private Triangulo trianguloAbajo;
    private Triangulo trianguloDerecha;
    public Objetivo(int abajo,int derecha){
        trianguloAbajo=new Triangulo(abajo);
        trianguloDerecha=new Triangulo(derecha);
    }
    public Objetivo(Triangulo tAbajo,Triangulo tDerecha){
        trianguloAbajo=tAbajo;
        trianguloDerecha=tDerecha;
    }
    public Triangulo getAbajo(){
        return trianguloAbajo;
    }
    public Triangulo getDerecha(){
        return trianguloDerecha;
    }
    public void setObjetivoAbajo(int numero){
        trianguloAbajo.setObjetivo(numero);
    }
    public void setObjetivoDerecha(int numero){
        trianguloDerecha.setObjetivo(numero);
    }
    public int getActualAbajo(){
        return trianguloAbajo.getActual();
    }
    public int getActualDerecha(){
        return trianguloDerecha.getActual();
    }
    public void sumarActualAbajo(int numero){
        trianguloAbajo.setActual(numero+trianguloAbajo.getActual());
        trianguloAbajo.setPresente(numero);
    }
    public void sumarActualDerecha(int numero){
        trianguloDerecha.setActual(numero+trianguloDerecha.getActual());
        trianguloDerecha.setPresente(numero);
    }
    public void restarActualAbajo(int numero){
        
        //System.out.println("RESTAR1: "+trianguloAbajo.getActual()+" "+numero);
        trianguloAbajo.setActual(trianguloAbajo.getActual()-numero);
        trianguloAbajo.setAusente(numero);
        //System.out.println("RESTAR2: "+trianguloAbajo.getActual()+" "+numero);
    }
    public void restarActualDerecha(int numero){
        trianguloDerecha.setActual(trianguloDerecha.getActual()-numero);
        trianguloDerecha.setAusente(numero);
        //System.out.println("RESTAR: "+trianguloDerecha.getActual()+" "+numero);
    }
    public boolean isInAbajo(int numero){
        return trianguloAbajo.isPresente(numero);
    }
    
    public boolean isInDerecha(int numero){
        return trianguloDerecha.isPresente(numero);
    }
    public int getObjetivoAbajo(){
        return trianguloAbajo.getObjetivo();
    }
    public int getObjetivoDerecha(){
        return trianguloDerecha.getObjetivo();
    }
    
}
