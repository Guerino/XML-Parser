/*
 * @author Güerino
 */

package logica.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;


public class XmlParser {        
    private String file_name; //Nombre del archivo xml a leer   
    private String txtXml;//para el getXMl() y el validador
    private ArrayList<String> strXmlList;
    private boolean isRoot;
    private boolean isValidDocument;

    /**
     * Constructor de la clase XmlParser
     * @param file: ruta al archivo xml 
     */
    public XmlParser(String file) { 
        this.isRoot = true;
        this.isValidDocument = false;
        this.file_name = file;
        this.txtXml = new String(); 
        this.strXmlList = new ArrayList<String>();     
    }

    public boolean isIsValidDocument() {
        return isValidDocument;
    }    
    
    public String getXml() {
        return txtXml;
    }
    
    private String getEncoding(){
        String strEncoding = new String();
        try {
                FileReader xml_file = new FileReader(this.file_name);
                //Leer el archivo
                BufferedReader xml_bf = new BufferedReader(xml_file);
                //Leer la cabecera
                String sCadena = xml_bf.readLine();
                int index = sCadena.indexOf("encoding=");
                if(index > 0){
                String str = sCadena.substring(index, sCadena.length());                
                    //Delimitadores para los atributos, osea para los pares nombre=valor
                    StringTokenizer tokens=new StringTokenizer(str, " =\"");
                    //Avanzamos un token para obtener el tipo de codificacion del fichero xml       
                    for (int i = 0; i < 1; i++) tokens.nextToken(); 
                    //Guardamos el tipo de codificacion
                    strEncoding = tokens.nextToken();  
                    //Cerramos el buffer de lectura
                    xml_bf.close();                                   
                } else{
                    System.out.println("No se ha definido el tipo de codificacion a utilizar en el documento.");
                    System.out.println("Usando UTF-8 por defecto");
                    
                    strEncoding = "UTF-8"; 
                }                             
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null,  "No se encuentra el archivo " + this.file_name, "Error al abrir el archivo", JOptionPane.ERROR_MESSAGE);                 
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,  ex.getMessage(), "Error al abrir el archivo", JOptionPane.ERROR_MESSAGE);               
            }
        
        return strEncoding;
    }
    
    /**
     * Metodo iniciador del parser
     * @return true
     */
    public XmlTree parse(){   
        XmlTree xmlTree = new XmlTree();        
        //Si se valido correctamente generamos el arbol XML
        this.readXML(this.getEncoding());
        
        String xml = XmlCleaner.clearTab(this.txtXml);
        xml = XmlCleaner.clearWhiteSpaces(xml);
        xml = XmlCleaner.clearComments(xml);
        xml = XmlCleaner.clearLineNew(xml);
        
        //Valida que el documento este bien formado
        XmlValidator validator = new XmlValidator(xml);
        
        try {            
            //Validamos el xml
            validator.validate();           
            
            //Metodo que analiza cada caracter y genera el arbol XML
            XmlNode rootNode = parserXML();
                        
            //<!DOCTYPE Edit_Mensaje SYSTEM "Edit_Mensaje.dtd">
            if(xml.indexOf("!DOCTYPE") != -1){               
                int start = xml.indexOf("\"",xml.indexOf("!DOCTYPE"));//ok
                int end = xml.indexOf("\">",xml.indexOf("!DOCTYPE"));//ok
                //Extraemos el nombre del archivo
                String fileDTD = xml.substring(start+1,end);                
                
                //Obtengo la ruta del archivo DTD que debe estar en el mismo directorio
                //que el archivo xml
                String strPathDTD = this.file_name.substring(0, file_name.lastIndexOf("\\")); 
                strPathDTD += "\\" + fileDTD;//Concateno la ruta + el nombre del archivo DTD

                //Extraemos el nombre de la etiqueta raiz
                int startTagRoot = xml.indexOf(" ", xml.indexOf("!DOCTYPE")); 
                String tagRootName = xml.substring(startTagRoot,xml.indexOf("SYSTEM")).trim();
                //System.out.println(xml.substring(startTagRoot,xml.indexOf("SYSTEM")));
                //Valida el arbol xml generado con el DTD especificado
                XmlDTDValidator dtdValidator = new XmlDTDValidator(rootNode, strPathDTD, tagRootName);
                //Le pasamos el arbol generado y el nombre del archivo .dtd
                dtdValidator.validate(); 
                //Si todo salio bien, este atributo se pone a true
                isValidDocument = true;
            }          
            //Seteamos el nodo raiz
            xmlTree.setRoot(rootNode);           
            
        } catch (XmlParseException e){
            String  regexpFile= "([\\w ]+\\.xml)";
            Pattern patFile = Pattern.compile(regexpFile);
            Matcher matFile = patFile.matcher(this.file_name); 

            matFile.find();                    
            xmlTree.setRoot(null);
            xmlTree = null;
            this.txtXml = "";
            //Informamos el error
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error - " + matFile.group(1), JOptionPane.ERROR_MESSAGE);
        }  
        
        return xmlTree;
    }    
	
    /**
     * Metodo encargado de leer el archivo xml y pasarle el contenido
     * al metodo parserXML
     * @param encoding 
     */
    private void readXML(String encoding){
        String[] strXML = null;
        try {
            //Leer el archivo
            BufferedReader xml_bf = new BufferedReader(new InputStreamReader(new FileInputStream(this.file_name),encoding));
            StringBuilder read = new StringBuilder();//Buffer de lectura 

            int i= -1;
            do{
                i = xml_bf.read();
                if (i != -1) {
                    //Convierto a char el entero devuelto
                    char c = (char)i;
                    read.append(c);                    
                   // System.out.println("Caracter: " + c);
                }                
            }while( i != -1);
      
            //Para el JTextArea
            txtXml = read.toString().trim();
			
	    //Limpiamos comentarios y otras cositas mas, queda el texto limpio para parsear      
            String str = XmlCleaner.clearComments(read.toString());
            str = XmlCleaner.clearLineNew(str);
            str = XmlCleaner.clearWhiteSpaces(str);            
            str = XmlCleaner.clearTab(str);			
                        
            //Separamos las etiquetas y demas texto en linea
            strXML = this.processXML(str);
            //ArrayList<String> con cada etiqueta separada
            this.strXmlList.addAll(Arrays.asList(strXML));
            
            //Cerramos el buffer de lectura
            xml_bf.close();            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,  "No se encuentra el archivo " + this.file_name, "Error al abrir el archivo", JOptionPane.ERROR_MESSAGE);           
        }catch (IOException ex) {            
            JOptionPane.showMessageDialog(null,  ex.getMessage(), "Error al abrir el archivo", JOptionPane.ERROR_MESSAGE);
        }        
   }
    
   /**
     * Metodo encargado de procesar linea por linea
     * el archivo xml y transformarlo en un array de elementos xml
     * @param strXML
     * @return String[]
     */ 
   private String[] processXML(String strXML){
       ArrayList<String> list = new ArrayList<String>();
       //Recorro el String caracter por caracter
       for (int i = 0; i < strXML.length(); i++) {
            char c = strXML.charAt(i);
            String str = String.valueOf(c);
            if (c == '<') {
                    str +=  separateTag(i, strXML);                
                    //Agregamos la etiqueta a la lista
                    list.add(str.trim());
                    //System.out.println("processXML=>Tag agregada: " +  str);//de 10!                             
            }else 
                if ( c == '>') {
                    str = "";
                    str += separateData(i, strXML);
                    str = str.trim();
                    //de 10!
                    if (!str.isEmpty() && String.valueOf(c).trim().length() != 0) {
                        //Agregamos el contenido a la lista
                        list.add(str.trim());
                        //System.out.println("processXML=>Texto agregado: " +  str);
                    }                    
                }
       }       
    
       return list.toArray(new String[list.size()]);
   }
   
   /**
    * Metodo que se encarga de separar las etiquetas xml
    * @param index
    * @param strXML
    * @return String
    */
   private  String separateTag(int index, String strXML) {
        //System.out.println("separateTag=>Index: " +index);
        String str = new String();
        for ( index += 1; index < strXML.length(); index++) {
            char c = strXML.charAt(index);
            str += String.valueOf(c);
            if (c == '>')  break;
        }
        return str;     
    }
   
    /**
     * Metodo que se encarga de separar el texto contenido
     * entre las etiquetas xml
     * @param index
     * @param strXML
     * @return String
     */
    private String separateData(int index, String strXML) {
        String str = new String();
        for (index += 1; index < strXML.length(); index++) {
            char c = strXML.charAt(index);
            if (c != '<') {
                str += String.valueOf(c);
            } else 
                break;            
        }        
        return str;
    }    
    
   /**
     * Metodo que se encarga generar el arbol en memoria   
     * @return XmlNode root
     */ 
   private XmlNode parserXML() throws XmlParseException{       
      //Pila de nodos 
      ArrayList<XmlNode> pila = new ArrayList<XmlNode>();
      XmlNode root = new XmlNode();
      
      String  openTag = "<(\\w+)>|<(\\w+)([\\s(\\w+)=\"(\\w+)\"]+)>";
      Pattern patopenTag = Pattern.compile(openTag);
      
      String  oneLineTag = "<(\\w+)([\\s(\\w+)=\"(\\w+)\"]+)/>";
      Pattern patOneLineTag = Pattern.compile(oneLineTag);
          
      String  closeTag = "</(\\w+)>";
      Pattern patcloseTag = Pattern.compile(closeTag);      
     
      //Recorremos el arrayList
      for (int i = 0; i< this.strXmlList.size(); i++){       
              String element = this.strXmlList.get(i);
             // System.out.println("Elemento: " + element);
              //Para la busqueda de patrones de etiquetas
              Matcher matOpenTag = patopenTag.matcher(element);
              Matcher matCloseTag = patcloseTag.matcher(element);
              Matcher matOneLineTag = patOneLineTag.matcher(element);

              XmlNode node = new XmlNode();
              XmlText text = new XmlText();
              XmlAttribute attribute = new XmlAttribute();
              XmlElement elementNode = new XmlElement();   

              //Si es una etiqueta de cierre la borramos de la pila
              if(matCloseTag.find()){
                  //Obtenemos su texto
                  String texto = this.strXmlList.get(i-1).trim();

                  if(!texto.isEmpty()  ){
                      if(!texto.startsWith("</")){//Detecto dos Tag de cierre seguidos
                             text.setText(texto);                            
                             if(pila.size() > 1)// 0 es la raiz
                                pila.get(pila.size()-1).getElement().setXmlText(text);
                     }
                  }                      
                 //Borramos el ultimo elemento de la pila 
                 pila.remove(pila.size()-1);
              }else                      
              //Etiquetas de apertura con y sin atributos
              if(matOpenTag.find()){                         
                     if(matOpenTag.group(1) == null){ 
                           //Con atributos                               
                           elementNode.setTagName(matOpenTag.group(2));//Nombre de la etiqueta
                           node.setElement(elementNode);
                           //System.out.println(matOpenTag.group(3));   
                           //Lista de Atributos
                           List<XmlAttribute> atrBuilder = attribute.splitAttribute(matOpenTag.group(3));                              
                           elementNode.setListAttribute(atrBuilder);
                      }else{                             
                            //Sin atributos
                            elementNode.setTagName(matOpenTag.group(1));                           
                            node.setElement(elementNode);                                
                      }

                      //Agregamos el nodo recien creado a la pila
                      pila.add(node);

                      if(isRoot){                       
                            if(matOpenTag.group(1) == null){ 
                                //Con atributos
                                elementNode.setTagName(matOpenTag.group(2));                       
                                //Lista de Atributos
                                List<XmlAttribute> atrBuilder = attribute.splitAttribute(matOpenTag.group(3));
                                
                                elementNode.setListAttribute(atrBuilder);
                                node.setElement(elementNode);

                            }else{                             
                                //Sin atributos
                                elementNode.setTagName(matOpenTag.group(1));     
                                node.setElement(elementNode);
                              //  System.out.println("root: " + matOpenTag.group(1));                                
                            }                        
                            //Establecemos el nodo raiz
                            root = node;
                            isRoot = false;                        
                      }else{    
                            //Obtengo el objeto anterior de la pila                                                                               
                            XmlNode nodeBefore = pila.get(pila.size()-2);//<xxx>
                           //  System.out.println("nodeBefore: " + nodeBefore);

                            //obtengo su nodo hijo
                            XmlNode nodeChild =  pila.get(pila.size()-1);//<xxx><xxx>
                           //  System.out.println("nodeChild: " + nodeChild);

                            //y luego le agrego su hijo
                            nodeBefore.addChild(nodeChild);                                                              
                     }
                  }//end Etiquetas de apertura con y sin atributos
                   else
                      //Etiquetas autocontenidas con atributos 
                      if(matOneLineTag.find() ){//<string id="9014" value="xxxx"/>
                              elementNode.setTagName(matOneLineTag.group(1));//Nombre de la etiqueta                           
                              node.setElement(elementNode);                       
                              
                              //Lista de Atributos
                              List<XmlAttribute> atrBuilder = attribute.splitAttribute(matOneLineTag.group(2));                              
                              elementNode.setListAttribute(atrBuilder);                            

                              //Lo agregamos a la pila
                              pila.add(node);

                              //Obtengo el objeto anterior de la pila  
                              XmlNode nodeBefore = pila.get(pila.size()-2);                          
                              //Agregamos este nodo como hijo del nodo anterior
                              nodeBefore.addChild(node);

                              //Lo borramos haca ya que no posee etiqueta de cierre
                              pila.remove(pila.size()-1);
                       }
      }//end for
          
      if(!pila.isEmpty())
            throw new XmlParseException("XML Error - Documento mal anidado");       
      
      //Devuelvo el nodo raiz
      return root;
   }
   
}