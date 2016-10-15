package logica.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guerino
 */
public class XmlDTDHandler {
    private boolean firtsNode;
    private boolean firtsElement;
    private String strTagRoot;
    private XmlNode nodeRoot;
    List<XmlNode> list;
    
    public XmlDTDHandler(XmlNode nodeRoot, String tagRoot) {
        this.firtsNode = true;
        this.firtsElement = true;
        this.strTagRoot = tagRoot;
        this.nodeRoot = nodeRoot;
        this.list = this.toList(nodeRoot);
    }  
    
    /**
     * Metodo encargado de validar el DTD contra el arbol de nodos
     * @param nodeRoot
     * @param tagRoot
     * @param elements
     * @throws XmlParseException 
     */
    public void startElement(String tagRoot, ArrayList<XmlDTDElement> elements) throws XmlParseException{
         //Valido la etiqueta raiz
         if (firtsElement) {
            firtsElement = false;
            //Pregunto por el elemento raiz.
            if (!nodeRoot.getElement().getTagName().equals(this.strTagRoot) ||
                   !nodeRoot.getElement().getTagName().equals(tagRoot))
                   throw new XmlParseException("DTD Error: no coincide el nombre de la etiqueta raiz con "
                          + "la especificada en el DTD\n o se ha la escrito mal en la etiqueta DOCTYPE." 
                          + "\nEtiqueta DOCTYPE: <" + this.strTagRoot +">"   
                          + "\nEtiqueta XML: <" + nodeRoot.getElement().getTagName() +">"
                          + "\nEtiqueta DTD: <" + tagRoot +">");   
        }       

        
          XmlNode node = new XmlNode();          
          for (XmlNode nodo : this.list) {
              if(nodo.getElement().getTagName().equals(tagRoot)){
                  node = nodo;
                  break;
              }        
          } 

          if(!elements.get(0).getElement().equals("PCDATA")){          
              //Valido que todos los hijos del Padre cumplan con la propiedad descrita en element
              for (XmlDTDElement elemento : elements) {
                    int count = 0;
                    //System.out.println(elemento);
                    //Obtengo la cardinalidad
                    switch (elemento.getCardinality()) {
                        //Una o mas veces
                        case '+':
                            // Recorro cada uno de los objetos de la lista
                            for (XmlNode hijo : node.getChildrenNode()) {//Cuento los nodos hijos segun su cardinalidad
                                if (hijo.getElement().getTagName().equals(elemento.getElement())) {
                                       count++;
                                }
                            }

                            //Si no existe al menos una etiqueta lanzo un error
                            if (count < 1) 
                                throw new XmlParseException("DTD Error: la etiqueta <"+tagRoot+ "> debe tener en el documento xml una o mas etiquetas hijas: "
                                        + "\n<" + elemento.getElement() +">, como se especifico en el DTD." );
                            break;

                   case '*':
                        //Cero o mas veces 
                       

                        break;

                    case '?':
                        //Opcional una o cero veces    
                        for (XmlNode hijo : node.getChildrenNode()) {//Cuento los nodos hijos segun su cardinalidad
                            if (hijo.getElement().getTagName().equals(elemento.getElement())) {
                                 count++;
                            }
                        }
                        //Si no existe algun lanzo un error
                        if (count > 1)
                            throw new XmlParseException("DTD Error: la etiqueta <"+tagRoot+ "> no debe tener en el documento xml mas de una etiqueta: "
                                    + "\n<" + elemento.getElement() +">, como se especifico en el DTD." );
                        break;


                    case '1':
                        //Sin cardinalidad, una vez
                        for (XmlNode hijo : node.getChildrenNode()) {//Cuento los nodos hijos segun su cardinalidad
                            if (hijo.getElement().getTagName().equals(elemento.getElement())) {
                                  count++;
                            }
                        }
                        //Si no existe algun lanzo un error
                        if (count != 1)
                            throw new XmlParseException("DTD Error: la etiqueta <"+tagRoot+ "> debe tener en el documento xml una etiqueta hija: "
                                    + "\n<" + elemento.getElement() +">, como se especifico en el DTD." );
                        break;
              }
           }//end for
       }

    }    
            
    //Manejador "ATTLIST"
    public void startElement(String tagName, String attName, String attValue, String defaultValue) throws XmlParseException{ 
        XmlNode node = new XmlNode();          
        for (XmlNode nodo : this.list) {
          if(nodo.getElement().getTagName().equals(tagName)){
              node = nodo;
              break;
          }        
        } 
                
        //Destinatario id ID #REQUIRED>
        //Texto idioma CDATA #REQUIRED>
        XmlAttribute attrib = new XmlAttribute();
        
        for(XmlAttribute att : node.getElement().getListAttribute()){                                
            if(att.getName().equals(attName)){
                attrib = att;//Guardo el objeto encontrado
                break;                    
            }
        }//end for

        if(attValue.equals("ID")){ 
            if(attrib.getName().isEmpty())
                throw new XmlParseException("DTD Error: la etiqueta <"+tagName+"> debe tener un atributo "
                          + "\n \"" + attName +"\", tal como se especifico en el DTD." );
            
            if(defaultValue.equals("#REQUIRED"))
                if(!attrib.getValue().matches("[a-zA-Z0-9]+"))
                        throw new XmlParseException("DTD Error: la etiqueta <"+tagName+ "> debe tener un atributo "
                              + "\"" + attName +"\", con su identificador unico,\n no puede estar vacia"
                                + ", tal como se especifico en el DTD." );            
        }
        
        if(attValue.equals("CDATA")){
            if(attrib.getName().isEmpty())
                throw new XmlParseException("DTD Error: la etiqueta <"+tagName+"> debe tener un atributo "
                          + "\n \"" + attName +"\", tal como se especifico en el DTD." );
            
            if(defaultValue.equals("#REQUIRED"))
                if(!attrib.getValue().matches("[a-zA-Z0-9]+"))
                        throw new XmlParseException("DTD Error: la etiqueta <"+tagName+ "> debe tener un atributo "
                              + "\"" + attName +"\", con su identificador unico,\n no puede estar vacia"
                                + ", tal como se especifico en el DTD." );  
        } 
        
    }
    
    //Manejador "ATTLIST"
    public void startElement(String tagName, String attName, List<String> attValues, String defaultValue) throws XmlParseException{ 
        XmlNode node = new XmlNode();          
        for (XmlNode nodo : this.list) {
          if(nodo.getElement().getTagName().equals(tagName)){
              node = nodo;
              break;
          }        
        } 
                
        //<!ATTLIST Mensaje prioridad (normal | urgente) normal>
        XmlAttribute attrib = new XmlAttribute();
        
        for(XmlAttribute att : node.getElement().getListAttribute()){                                
            if(att.getName().equals(attName)){
                attrib = att;//Guardo el objeto encontrado
                break;                    
            }
        }//end for
        
        int count = 0;
        String valor = attrib.getValue();        
        
        //Buscamos que el valor del atributo coincida con algunos de la lista        
        for (int i = 0; i < attValues.size(); i++) {
            String value = attValues.get(i);
            if(valor.equals(value)) {
                count++;
                break;
            }            
        }
        
        if(count < 1)
            throw new XmlParseException("DTD Error: la etiqueta <"+tagName+"> debe tener uno de los siguientes\n valores en el atributo"
                          + " \"" + attName +"\", tal como se especifico en el DTD.\n" 
                    + " Valores: " + attValues.toString());  
    }
    
    /**
     * Metodo encargado de transformar el arbol en una lista de Nodos
     * @param root
     * @return 
     */
    public final List<XmlNode> toList(XmlNode root) {
        List<XmlNode> lista = new ArrayList<XmlNode>();
        preOrder(root, lista);
        return lista;
    }
    
    /**
     * Metodo recursivo que recorre el arbol de nodos
     * @param element
     * @param list 
     */
    private void preOrder(XmlNode element, List<XmlNode> list) {
        list.add(element);
        for (XmlNode data : element.getChildrenNode()) {
            preOrder(data, list);
        }
    }
    
}