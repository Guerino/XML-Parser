package logica;

import javax.swing.JLabel;

/**
 *
 * @author Guerino
 */
public class HiloInfo extends Thread{
    private JLabel jlabel;

    public HiloInfo(JLabel label) {
        this.jlabel = label;
    }

    @Override
    public void run() {
        try {
            while(true){
                Thread.sleep(80);
                this.jlabel.setText(" Procesando");
                
                Thread.sleep(600);
                this.jlabel.setText(" Procesando.");
                
                Thread.sleep(900);
                this.jlabel.setText(" Procesando..");
               
                Thread.sleep(1200);
                this.jlabel.setText(" Procesando...");
                Thread.sleep(1400);
            }
        } catch (Exception e) {
        }  
        
    }  
    
    
}
