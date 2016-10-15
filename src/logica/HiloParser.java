package logica;

import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import logica.parser.XmlParser;
import logica.parser.XmlTree;
import vista.MainFrame;

/**
 *
 * @author Guerino
 */
public class HiloParser extends Thread{
    private XmlParser parserXML;
    private String filePath;
    private JTextArea txtArea;   
    private JLabel txtInfo;
    private MainFrame padre;
    private HiloInfo hiloInfo;
    
    
    public HiloParser(MainFrame padre, String filePath, JTextArea txtArea, JLabel txtInfo) {
        this.filePath = filePath;
        this.txtArea = txtArea;        
        this.txtInfo = txtInfo;
        this.padre = padre;
        this.hiloInfo = new HiloInfo(txtInfo);
     }

    @Override
    public void run() {
        try {           
            //Cursor de espera
            padre.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            this.txtArea.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            padre.habilitarCtrls(false);
            
            long tiempo = System.currentTimeMillis();
            hiloInfo.start();
            parserXML = new XmlParser(this.filePath);
            XmlTree xmlTree = parserXML.parse();
            
            if(xmlTree != null ) {
                //Cargar el arbol xml
                padre.inicializarArbol(xmlTree);
                this.txtArea.setText(parserXML.getXml());
                padre.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                this.txtArea.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                double total = (double)(System.currentTimeMillis() - tiempo)/(double)1000;
                hiloInfo.interrupt();
                
                if(parserXML.isIsValidDocument())
                    this.txtInfo.setText(" Analizado en: " + total + " segundos - XML bien formado | Documento valido.");
                else                    
                    this.txtInfo.setText(" Analizado en: " + total + " segundos - XML bien formado.");
                
                padre.setjButtonGuardarEnabled(false);
                padre.habilitarCtrls(true);
            }else{
                padre.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                this.txtArea.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                padre.habilitarCtrls(true);
                hiloInfo.interrupt();
            }
            
        } catch (Exception e) {
        }        
    }   
    
}
