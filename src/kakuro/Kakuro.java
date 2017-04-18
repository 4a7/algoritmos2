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
public class Kakuro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Informacion info =new Informacion();
        Tablero t=new Tablero();
        //BT.setFH(10);
        t.readTablero("C:\\users\\juan\\desktop\\kakuro1.txt");
        t.toString();
        BT.solve(0, 0, t);
        //System.out.println(t.toString());
    }
    
}
