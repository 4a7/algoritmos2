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
public class Triangulo {
    private int objetivo;
    private int actual;
    private listaPresencia presentes;
    public Triangulo(int objetivo){
        this.objetivo=objetivo;
        presentes=new listaPresencia();
        actual=0;
    }
    public Triangulo(){
        objetivo=0;
        actual=0;
        presentes=new listaPresencia();
    }
    public void setObjetivo(int objetivo){
        this.objetivo=objetivo;
    }
    public void setActual(int numero){
        this.actual=numero;
    }
    public int getObjetivo(){
        return objetivo;
    }
    public int getActual(){
        return actual;
    }
    public void setPresente(int numero){
        presentes.setTrue(numero);
    }
    public void setAusente(int numero){
        presentes.setFalse(numero);
    }
    public boolean isPresente(int numero){
        return presentes.isTrue(numero);
    }
    
    
}
