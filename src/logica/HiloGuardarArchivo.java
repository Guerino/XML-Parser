package logica;

import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Guerino
 */
public class HiloGuardarArchivo extends Thread{
    private String strContArchivo;
    private String strNombreArchivo;
    private JLabel jlabel;
    private JButton jButtonGuardar;

    public HiloGuardarArchivo(JLabel label, JButton jButtonGuardar,String fileContent, String fileName) {
        this.strContArchivo = fileContent;
        this.strNombreArchivo = fileName;
        this.jlabel = label;
        this.jButtonGuardar = jButtonGuardar;
    }

    @Override
    public void run() {        
         try {            
            this.jlabel.setText(" Guardando archivo...");
            jButtonGuardar.setEnabled(false);
            Thread.sleep(100); 
            //Buffer de escritura a disco
            BufferedWriter bwriter = new BufferedWriter(new FileWriter(this.strNombreArchivo));            
            bwriter.write(this.strContArchivo);
            bwriter.close();

            this.jlabel.setText(" Listo");          
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, this.strNombreArchivo + " no se pudo guardar.", "Guardar archivo", JOptionPane.WARNING_MESSAGE);
        } 
    }
    
    
    
}
