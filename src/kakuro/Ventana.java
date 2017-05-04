/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kakuro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Random;
import javax.swing.AbstractButton;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Juan
 */
public class Ventana extends javax.swing.JFrame {

    /**
     * Creates new form Ventana
     */
    int tamano_x=14;
    int tamano_y=14;
    String path;
    JLabel[][] numeros=new JLabel[tamano_x][tamano_y];
    private String archivo_interfaz="interfaz.txt";
    boolean forks=false;
    int forks_hilos=10;
    JRadioButtonMenuItem rbMenuItem;
    JMenu submenu;
    ButtonGroup group;
    public Ventana() {
        
        initComponents();
        panel.setLayout(new GridLayout(tamano_x, tamano_y));
        crearInterfaz();
        panel.validate();
        panel.repaint();
        path="C:\\users\\juan\\desktop\\interfaz.txt";
        //ponerTablero();
        
    }
    public void Solucionar(){
        String seleccionado="Secuencial";
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                seleccionado=button.getText();
            }
        }
        if(seleccionado.equals("Forks")){
            forks=true;
        }
        else if(seleccionado.equals("Hilos")){
            forks=false;
            
        }
        else{
            forks=false;
            forks_hilos=1;
        }
        
        if(forks_hilos<=0){
            forks_hilos=1;
        }
        
        
        
        
        
        
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "TXT Files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           path=chooser.getSelectedFile().getAbsolutePath();
        }
        
        
        
        
        Informacion info =new Informacion();
        Tablero t=new Tablero();
        //BT2 bt2=new BT2();
        //esta pone el numero de forks o hilos que se quieren
        
        //esta se pone true si se quieren forks
        //funciona en conjunto con la anterior, si con BT.setFH se pone mas de 1 hilo, entonces el programa 
        //usara hilos o forks, para saber cual usar usa BT2.setForks, si es verdadera entonces se usan forks, sino entonces hilos
        BT2.setForks(forks);
        if(forks_hilos>0){
            BT2.setFH(forks_hilos);
        }
        else{
            BT2.setFH(0);
        }
        t.readTablero(path);//el path del tablero
        t.toString();
        boolean[][]presencia_filas=new boolean[14][9];
        boolean[][]presencia_columnas=new boolean[14][9];
        int[]suma_filas=new int[14];
        int[]suma_columnas=new int[14];
        Arrays.fill(suma_filas, 0);
        Arrays.fill(suma_columnas, 0);
        for( int m=0;m<14;m++){
            Arrays.fill(presencia_filas[m], false);
            Arrays.fill(presencia_columnas[m], false);
            
        }
        Grafico grafico=new Grafico();
        //new BT().solve(0,0, t);
        Ventana v=this;
        v.setPathInterfaz();
        new Thread(new Runnable() {
            public void run() {
                 new BT2(0, 0, t,presencia_filas,presencia_columnas,suma_filas,suma_columnas,"",grafico,"",v).solve();
            }
       }).start();
 
        
        
    }
    
    public void Generar(){
        new Thread(new Runnable() {
            public void run() {
                 System.out.println("Generando");
            }
       }).start();
    }
    
    public void setPathInterfaz(){
        this.path=archivo_interfaz;
    }
    
    public void crearInterfaz(){
        JLabel label;
        //crea el menu
        JMenuBar menubar = new JMenuBar();
        JMenu opcionesGenerar = new JMenu("Generar");
        opcionesGenerar.setMnemonic(KeyEvent.VK_O);
        JMenu opcionesSolucionar = new JMenu("Solucionar");
        opcionesSolucionar.setMnemonic(KeyEvent.VK_O);
        JMenuItem generar = new JMenuItem("Generar");
        generar.setMnemonic(KeyEvent.VK_G);
        generar.setToolTipText("Generar un Kakuro");
        generar.addActionListener((ActionEvent event) -> {
            System.out.println("Generar");
            Solucionar();
        });
        
        
        JMenuItem solucionar = new JMenuItem("Solucionar");
        solucionar.setMnemonic(KeyEvent.VK_S);
        solucionar.setToolTipText("Solucionar un Kakuro");
        solucionar.addActionListener((ActionEvent event) -> {
            System.out.println("Solucionar");
            Solucionar();
        });

        JMenu opcionesParalelismo = new JMenu("Paralismo");
        opcionesGenerar.setMnemonic(KeyEvent.VK_O);
        
        
        group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Secuencial");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);
        opcionesParalelismo.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Hilos");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        opcionesParalelismo.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("Forks");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        opcionesParalelismo.add(rbMenuItem);
        
        //paralelismo.add(paralelismo_serial);
        opcionesParalelismo.addSeparator();
        
        submenu = new JMenu("# de hilos/forks: "+Integer.toString(forks_hilos));
        submenu.setMnemonic(KeyEvent.VK_S);
        
        JMenuItem menos100 = new JMenuItem("-100");
        menos100.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menos100.addActionListener((ActionEvent event) -> {
            forks_hilos-=100;
            if(forks_hilos<0){
                forks_hilos=0;
                
            }
            submenu.setText("# de hilos/forks: "+Integer.toString(forks_hilos));
            System.out.println(forks_hilos);
        });
        
        JMenuItem menos10 = new JMenuItem("-10");
        menos10.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menos10.addActionListener((ActionEvent event) -> {
            forks_hilos-=10;
            if(forks_hilos<0){
                forks_hilos=0;
            }
            submenu.setText("# de hilos/forks: "+Integer.toString(forks_hilos));
            System.out.println(forks_hilos);
        });
        JMenuItem menos1 = new JMenuItem("-1");
        menos1.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_3, ActionEvent.ALT_MASK));
        menos1.addActionListener((ActionEvent event) -> {
            forks_hilos-=1;
            if(forks_hilos<0){
                forks_hilos=0;
            }
            submenu.setText("# de hilos/forks: "+Integer.toString(forks_hilos));
            System.out.println(forks_hilos);
        });
        
        JMenuItem mas1 = new JMenuItem("+1");
        mas1.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_4, ActionEvent.ALT_MASK));
        mas1.addActionListener((ActionEvent event) -> {
            forks_hilos+=1;
            submenu.setText("# de hilos/forks: "+Integer.toString(forks_hilos));
            System.out.println(forks_hilos);
        });
        
        JMenuItem mas10 = new JMenuItem("+10");
        mas10.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_5, ActionEvent.ALT_MASK));
        mas10.addActionListener((ActionEvent event) -> {
            forks_hilos+=10;
            submenu.setText("# de hilos/forks: "+Integer.toString(forks_hilos));
            System.out.println(forks_hilos);
        });
        
        JMenuItem mas100 = new JMenuItem("+100");
        mas100.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_6, ActionEvent.ALT_MASK));
        mas100.addActionListener((ActionEvent event) -> {
            forks_hilos+=100;
            submenu.setText("# de hilos/forks: "+Integer.toString(forks_hilos));
            System.out.println(forks_hilos);
            
        });
        
        submenu.add(menos100);
        submenu.add(menos10);
        submenu.add(menos1);
        submenu.add(mas1);
        submenu.add(mas10);
        submenu.add(mas100);
        
        opcionesParalelismo.add(submenu);
        //opcionesSolucionar.addSeparator();
        
        
        
        opcionesSolucionar.add(solucionar);
        
        
        opcionesGenerar.add(generar);
        
        menubar.add(opcionesGenerar);
        menubar.add(opcionesSolucionar);
        menubar.add(opcionesParalelismo);
        setJMenuBar(menubar);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        
        for(int i=0;i<tamano_x;i++){
            for(int j=0;j<tamano_y;j++){
                Random rand = new Random(); 
                int value = rand.nextInt(50); 
                numeros[i][j]=new JLabel(" ",SwingConstants.CENTER);//■
                numeros[i][j].setOpaque(true);
                numeros[i][j].setBorder(border);
                numeros[i][j].setMinimumSize(new Dimension(5, 5));
                numeros[i][j].setMaximumSize(new Dimension(5, 5));
                if(value<25){
                    numeros[i][j].setBackground(Color.BLACK);
                    numeros[i][j].setForeground(Color.white);
                }
                else{
                    numeros[i][j].setBackground(Color.WHITE);
                    numeros[i][j].setForeground(Color.BLACK);
                }
                
                
                
                panel.add(numeros[i][j]);
            }
        }
        
    }
    
    public void ponerTablero(String tablero){
        String a;
        Border borderBlanco = BorderFactory.createLineBorder(Color.BLACK, 1);
        String contenido;
        int i,j;
        i=0;
        j=0;
        String[] splited = tablero.split("\\s+");
                for(int k=0;k<splited.length;k++){
                    a=splited[k];
                    //System.out.println(i+" "+j+" "+a+" "+a.equals("b"));
                    //pone la informacion en el tablero de kakuro que se resolvera
                    if(a.equals("e")){
                        contenido="";
                        if(!splited[k+1].equals("0")){
                            contenido+=splited[k+1];
                        }
                        contenido+="\\";
                        if(!splited[k+2].equals("0")){
                            contenido+=splited[k+2];
                        }
                        numeros[i][j].setBackground(Color.BLACK);
                        numeros[i][j].setForeground(Color.white);
                        numeros[i][j].setText(contenido);
                        numeros[i][j].setBorder(borderBlanco);
                        k+=2;
                    }
                    else if (a.equals("b")){
                        contenido="";
                        numeros[i][j].setBackground(Color.BLACK);
                        numeros[i][j].setForeground(Color.white);
                        numeros[i][j].setText(contenido);
                        numeros[i][j].setBorder(borderBlanco);
                    }
                    else{
                        contenido=a;
                        if(contenido.equals("0")){
                            contenido="";
                        }
                        numeros[i][j].setBackground(Color.WHITE);
                        numeros[i][j].setForeground(Color.BLACK);
                        numeros[i][j].setText(contenido);
                        numeros[i][j].setBorder(borderBlanco);
                        
                    }
                    
                    
                    j++;
                    if(j==14){
                        j=0;
                        i++;
                    }  
                }
    }
    
    public void ponerTablero(){
        int i,j;
        String a;
        String contenido;
        i=0;
        j=0;
        Border borderBlanco = BorderFactory.createLineBorder(Color.BLACK, 1);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null&&i<14) {
                String[] splited = line.split("\\s+");
                for(int k=0;k<splited.length;k++){
                    a=splited[k];
                    //System.out.println(i+" "+j+" "+a+" "+a.equals("b"));
                    //pone la informacion en el tablero de kakuro que se resolvera
                    if(a.equals("e")){
                        contenido="";
                        if(!splited[k+1].equals("0")){
                            contenido+=splited[k+1];
                        }
                        contenido+="\\";
                        if(!splited[k+2].equals("0")){
                            contenido+=splited[k+2];
                        }
                        numeros[i][j].setBackground(Color.BLACK);
                        numeros[i][j].setForeground(Color.white);
                        numeros[i][j].setText(contenido);
                        numeros[i][j].setBorder(borderBlanco);
                        k+=2;
                    }
                    else if (a.equals("b")){
                        contenido="";
                        numeros[i][j].setBackground(Color.BLACK);
                        numeros[i][j].setForeground(Color.white);
                        numeros[i][j].setText(contenido);
                        numeros[i][j].setBorder(borderBlanco);
                    }
                    else{
                        contenido=a;
                        if(contenido.equals("0")){
                            contenido="";
                        }
                        numeros[i][j].setBackground(Color.WHITE);
                        numeros[i][j].setForeground(Color.BLACK);
                        numeros[i][j].setText(contenido);
                        numeros[i][j].setBorder(borderBlanco);
                        
                    }
                    
                    
                    j++;
                    if(j==14){
                        j=0;
                        i++;
                    }  
                }
                line = br.readLine();
            }
            String everything = sb.toString();
        } 
        catch(Exception e){
            e.printStackTrace(System.out);
        }
        finally{
            panel.validate();
            panel.repaint();
        }
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kakuro BT");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        panel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 469, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
