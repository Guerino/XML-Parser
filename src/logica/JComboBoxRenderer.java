/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
/**
 *
 * @author Guerino
 */
public class JComboBoxRenderer extends JLabel implements ListCellRenderer {
    private ImageIcon icon;

    public JComboBoxRenderer() {        
        this.icon = new ImageIcon(getClass().getResource("/resources/xml_file24x24.png"));
        setOpaque(true);
    }
    
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        this.setFont(list.getFont());
        this.setText((String)value);
        this.setIcon(icon);
        
        if(isSelected){            
            this.setBackground(list.getSelectionBackground());
            this.setForeground(list.getSelectionForeground());
                             
        }else{
            
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());              
                 
        }     
        
        return this;        
    }   
    
}
