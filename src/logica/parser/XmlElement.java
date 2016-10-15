package logica.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Elemento
 * @author Guerino
 */
public class XmlElement {
    private String name;//Nombre de la etiqueta 1    
    private List<XmlAttribute> attribute;//Atributos 0..*
    private XmlText text; //Texto dentro de las etiquetas 0..1
  
    public XmlElement() {
        this.name = "";
        this.attribute = new ArrayList<XmlAttribute>();
        this.text = null;
    }

    public XmlElement(String name, XmlText text) {
        this.name = name;
        this.text = text;
        this.attribute = new ArrayList<XmlAttribute>();
    }
    
    public XmlElement(String name, XmlText text, List<XmlAttribute> attribute) {
        this.name = name;
        this.text = text;
        this.attribute = attribute;
    } 

    public List<XmlAttribute> getListAttribute() {
        if(attribute.size() > 0) return attribute;
        else
            return null;
    }

    public void setListAttribute(List<XmlAttribute> attribute) {
        this.attribute = attribute;
    }    

    public String getTagName() {
        return name;
    }

    public void setTagName(String name) {
        this.name = name;
    }

    public XmlText getXmlText() {
        return text;
    }

    public void setXmlText(XmlText text) {
        this.text = text;
    }

    @Override
    public String toString() {
        String str = name;        
        // Obtenemos un Iterador y recorremos la lista.
        Iterator iter = this.attribute.iterator();
        while (iter.hasNext()){
            XmlAttribute attr = (XmlAttribute)iter.next();
            str += " " + attr.toString();
        }
        
        if(text != null)
            str += ": " + text.getText();        
   
        return str;
    }

}
