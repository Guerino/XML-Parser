package logica.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Nodo Atributo
 * @author Guerino
 */
public class XmlAttribute {
    private String name;
    private String value;
    
    public XmlAttribute() {
        this.name = "";
        this.value = "";        
    }

    public XmlAttribute(String name, String value) {
        this.name = name;
        this.value = value;        
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;        
    }
    
    /**
    * Metodo que se encarga de separar los atributos
    * @param strXml
    * @return List<XmlAttribute>
    */   
    public List<XmlAttribute> splitAttribute(String strXml) {
        XmlAttribute attr = new XmlAttribute();
        List<XmlAttribute> atrBuilder = new ArrayList<XmlAttribute>();
        //Borramos el espacion del comienso
        strXml = strXml.trim() + " ";//Le sumo un espacio al final para detectar la ultima comilla
            
        String str = new String();
        int cont = 0;
        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i < strXml.length(); i++) {
            char c = strXml.charAt(i);
            
            if (c == '"' && cont < 2 ) {
               //no guardo las comillas
                    cont++;                       
            }else{
                str += String.valueOf(c);
                if(cont == 2){
                    //Agregamos la etiqueta a la lista              
                    data.add(str);//de 10!                    
                    str = "";
                    cont = 0;
                }
            }           
        }       
      
        if(data.size() > 0){
            String[] attribute;
            for(int i =0; i < data.size(); i++) {
                //Ahora separamos por "=", a la izq tenemos el nombre
                //y a la dcha el valor             
                attribute = data.get(i).split("=");                 
                //name=value
                atrBuilder.add(new XmlAttribute(attribute[0].trim(), attribute[1].trim()));//de 10!       
            }
       }
       return atrBuilder;
   }
    
    @Override
    public String toString() {
        return this.name + "=\"" + this.value + "\" ";	
    }    
}
