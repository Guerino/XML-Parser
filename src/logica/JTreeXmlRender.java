package logica;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Guerino
 */
public class JTreeXmlRender extends DefaultTreeCellRenderer{
    private ImageIcon iconTag;
    private String tagName;
    private String attribute;
    private String text;
    
    public JTreeXmlRender() {
        this.iconTag = new ImageIcon(this.getClass().getResource("/resources/tagg.png"));
        this.setLeafIcon(iconTag);
        this.setOpenIcon(iconTag);
        this.setClosedIcon(iconTag);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
         if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
                String strXml = (String) ((DefaultMutableTreeNode) value).getUserObject();
                      //book category="CHILDREN"  lang="es"
                     int space = strXml.indexOf(" ");
                     if( space > 0){ //Etiqueta con algo mas
                         tagName = strXml.substring(0, space).trim();                             
                         if(strXml.indexOf(":") > 0){//Si tiene texto
                            text = strXml.substring(strXml.indexOf(":")+1, strXml.length());
                            if(strXml.indexOf("=") > 0)//Si tiene atributos
                                attribute = strXml.substring(space+1,strXml.indexOf(":"));                        
                         }
                         else //Solo atributos
                             attribute = strXml.substring(space+1, strXml.length());
                         
                     }else //Si solo es una etiqueta
                         tagName = strXml.trim();
                    
                if(sel){
                    String strHtml = "<html>";                
                    strHtml += "<FONT COLOR=WHITE>" + this.tagName.trim() + "</FONT>";
                    strHtml += "<FONT COLOR=WHITE>&nbsp;" + this.attribute + "</FONT>";
                    strHtml += "<FONT COLOR=WHITE>" + this.text + "</FONT>";              
                    strHtml +=  "</html>";
                    
                    this.setText(strHtml.trim());//de 10!
                    
                }else{                    
                     String stHtml = "<html>";                
                     stHtml += "<FONT COLOR=BLUE>" +  this.tagName.trim() + "</FONT>";
                     stHtml += "<FONT COLOR=GREEN>&nbsp;" + this.attribute + "</FONT>";
                     stHtml += "<FONT COLOR=RED>" + this.text + "</FONT>";              
                     stHtml +=  "</html>";
                     
                     this.setText(stHtml.trim());                     
                }                

                //Si no hago esto se siguen concatenando con cada repintado
                this.tagName = "";
                this.attribute = "";
                this.text = "";
         }//end if ((value != null)
         
        return this;
    }    
    
}