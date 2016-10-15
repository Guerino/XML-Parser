package logica.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Guerino
 */
public class XmlDTDValidator {
    private String strFileDTD;//Nombre del archivo DTD
    private String strXmlDTD;//Contenido del dtd
    private XmlNode rootNode;//Nodo raiz del arbol generado
    private String strTagRootName;

    //Constructor de la clase
    public XmlDTDValidator(XmlNode rootNode, String fileDTD, String tagRoot) {
        this.rootNode = rootNode;
        this.strFileDTD = fileDTD;
        this.strTagRootName = tagRoot;
        this.strXmlDTD = new String();
    }
    
    private String readFileDTD(){
         StringBuilder read = new StringBuilder();//Buffer de lectura 
         try {
            //Leer el archivo
            BufferedReader xml_bf = new BufferedReader(new InputStreamReader(new FileInputStream(this.strFileDTD),"UTF-8"));
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
            
            //Cerramos el buffer de lectura
            xml_bf.close();            
        } catch (FileNotFoundException ex) {
             JOptionPane.showMessageDialog(null,  "DTD Error: No se encuentra el archivo " + this.strFileDTD, "Error al abrir el archivo", JOptionPane.ERROR_MESSAGE);
        }catch (IOException ex) { 
            JOptionPane.showMessageDialog(null,  ex.getMessage(), "Error al abrir el archivo", JOptionPane.ERROR_MESSAGE);
        }        
        
        return read.toString();
    }
    
    /**
     * Metodo encargado de validar los caracteres de apertura 
     * y cierre de cada etiqueta xml
     */
    private void tagValidator() throws XmlParseException{
        int cntLine = 1;
        int status = -1;
        String strTag = new String(); 
        LinkedList<String> pila = new LinkedList<String>();  
        
        //1 - Validamos los caracteres de apertura y cierre
        for (int i = 0; i < this.strXmlDTD.length(); i++) {
            status = -1;
            char c = this.strXmlDTD.charAt(i);
            strTag += String.valueOf(c);            
            
            if(c == '<') status = 1;
            if(c == '<' && this.strXmlDTD.charAt(i+1) == '/') status = 2;
            if(c == '>')  status = 3;         
            
            if(c == '\n') cntLine++;
            else
                switch(status){
                    case 1: pila.push(String.valueOf(c)); break;
                    case 2: pila.push((String.valueOf(c) + this.strXmlDTD.charAt(i+1))); break;                                     
                    case 3:
                         if(pila.isEmpty()){
                           throw new XmlParseException
                                    ("DTD Error: falta el caracter de inicio de etiqueta '<'\nLinea: " + (cntLine-1) + "\nEtiqueta: " + strTag.trim() 
                                        + "\nArchivo: " + this.strFileDTD ); 
                          }else
                            if(pila.size() > 1){
                               //si, se paso de largo <  <
                               strTag = strTag.substring(0, strTag.lastIndexOf('<')).trim();
                               throw new XmlParseException
                                    ("DTD Error: falta el caracter de cierre de etiqueta '>'\nLinea: " + (cntLine-1) + "\nEtiqueta: " + strTag
                                        + "\nArchivo: " + this.strFileDTD );   
                           }else{
                                //Removemos los elementos de la pila
                                pila.pop();
                                strTag = "";
                            } break;                       
                        
               }//end switch   
        }//end for 
        
    }
    
    /**
     * Metodo arrancador del validador de xml contra una DTD
     * @throws XmlParseException 
     */
    public void validate() throws XmlParseException {
        this.strXmlDTD = this.readFileDTD();
        //validamos las etiquetas
        this.tagValidator();        
        //Si no limpio el archivo, no se alcanza el final del mismo y queda en
        //un bucle al infinito
        strXmlDTD = XmlCleaner.clearTab(strXmlDTD);
        strXmlDTD = XmlCleaner.clearComments(strXmlDTD);
        strXmlDTD = XmlCleaner.clearWhiteSpaces(strXmlDTD);       
        strXmlDTD = XmlCleaner.clearLineNew(strXmlDTD);
                               
        Pattern pattern = Pattern.compile("<[^>]+>");
        String patron = "(\\w+)([+*?])?|#(\\w+)";
        
        XmlDTDHandler  dtdHandler = new XmlDTDHandler(this.rootNode, this.strTagRootName); 
        
        while (strXmlDTD.length() > 0){
            String strTag = new String();            
            Matcher matcher = pattern.matcher(strXmlDTD);
            //busco las etiquetas
            if (matcher.find()){
                 strTag = matcher.group(0);//Obtengo la etiqueta <XXX>
                 String elementTag = strTag.substring(1, strTag.length() - 1);//Le quito los caracteres < y > 
                 //Busco la posicion en donde comienza la etiqueta
                 int pos = strXmlDTD.indexOf(elementTag);
                 //Corto desde la posicion+1 en donde se encuentra la etiqueta actual hasta el final del string
                 strXmlDTD = strXmlDTD.substring(pos + elementTag.length() + 1);  
                                  
                 if(elementTag.startsWith("!ELEMENT")){  
                       elementTag = elementTag.substring(elementTag.indexOf(" "),elementTag.length()).trim(); 
                       //Etiqueta raiz o simple la etiqueta con #PCDATA
                       String tagRootName = elementTag.substring(0,elementTag.indexOf(" ")).trim();
                       elementTag = elementTag.substring(elementTag.indexOf("(")+1,elementTag.indexOf(")")).trim(); 
                       ArrayList<XmlDTDElement> elementos = new ArrayList<XmlDTDElement>();                    
                       
                       //Si se encuentra una coma quiere decir que hay etiquetas hijas
                       if(elementTag.indexOf(",") > 0){//<!ELEMENT nota (remitente, destinatario, titulo, mensaje?)>                           
                            //System.out.println(elementTag);
                            //Separo por comas
                            StringTokenizer st = new StringTokenizer(elementTag, ",");
                            // recorre elementos separados
                            while (st.hasMoreTokens()) {
                                //Leo cada etiqueta
                                String elemento = st.nextToken().trim();
                                //System.out.println(elemento);                                
                                Pattern patToken = Pattern.compile(patron);
                                Matcher mat = patToken.matcher(elemento);
                                mat.find();
                                //System.out.println("elemento:");
                                if (mat.group(2) != null)
                                        elementos.add(new XmlDTDElement(mat.group(1), mat.group(2).toCharArray()[0]));
                                else {
                                    if (mat.group(3) != null)
                                        elementos.add(new XmlDTDElement(mat.group(3), '1'));
                                    else
                                        elementos.add(new XmlDTDElement(mat.group(1), '1'));
                                }
                            }//end while
                            
                            //Manejador de etiquetas  tagRoot     tag hijos  
                            dtdHandler.startElement(tagRootName, elementos);
                            
                       }else{
                                //Si no tiene mas de una etiqueta
                               // System.out.println(tagRootName + " " + elementTag);                                 
                                Pattern patToken = Pattern.compile(patron);
                                Matcher mat = patToken.matcher(elementTag);
                                mat.find();                                
                                //tagRootName Etiqueta raiz                                
                                //#PCDATA mat.group(3)                                
                                //Cardinalidad mat.group(2)
                                //Tag hijo mat.group(1)                               
                                
                                if (mat.group(2) != null)
                                    //etiqueta hija y su cardinalidad
                                    elementos.add(new XmlDTDElement(mat.group(1), mat.group(2).toCharArray()[0]));                                       
                                else {
                                    if (mat.group(3) != null)//#PCDATA
                                         elementos.add( new XmlDTDElement(mat.group(3), '1'));    
                                    else                                    
                                         elementos.add(new XmlDTDElement(mat.group(1), '1'));
                                }
                                
                             //Manejador de etiquetas
                             dtdHandler.startElement(tagRootName, elementos);
                       }                                     
                 }
                
                 if(elementTag.startsWith("!ATTLIST")){
                      String tagName="",attributeName="",attValue="",defaultValue="";
                      ArrayList<String> valores = new ArrayList<String>();
                      //Obtengo todo lo que haiga despues de !ATTLIST
                      elementTag = elementTag.substring(elementTag.indexOf(" "),elementTag.length()).trim();  
                      //Nombre de la Etiqueta
                      tagName = elementTag.substring(0,elementTag.indexOf(" ")).trim(); 
                      //Ahora corto desde el nombre del atributo hasta el final
                      elementTag = elementTag.substring(elementTag.indexOf(" "),elementTag.length()).trim();
                      //System.out.println(elementTag);     
                      //Nombre del atributo
                      attributeName = elementTag.substring(0,elementTag.indexOf(" ")).trim(); 
                      // System.out.println(attributeName);  
                       
                      if(elementTag.indexOf("(") > 0){
                          //Ahora desde el primer ( hasta el final
                          elementTag = elementTag.substring(elementTag.indexOf("("),elementTag.length()).trim();
                          //Si hay un OR quiere decir que hay mas de una opcion para el atributo
                          if(elementTag.indexOf("|") > 0){
                                 
                               String optionsValue = elementTag.substring(elementTag.indexOf("(")+1,elementTag.indexOf(")")).trim(); 
                               StringTokenizer st = new StringTokenizer(optionsValue, "|");
                                // recorre elementos separados
                                while (st.hasMoreTokens()) {
                                    //Leo cada etiqueta
                                    attValue = st.nextToken().trim();
                                    valores.add(attValue);
                                }
                          }
                          defaultValue = elementTag.substring(elementTag.indexOf(")")+1,elementTag.length()).trim(); 
                          
                      //Para el caso en que no tenga lista de opciones de valores    
                      }else{
                          //Me quedo con lo demas
                          elementTag = elementTag.substring(elementTag.indexOf(" "),elementTag.length()).trim(); 
                          //Valor del atributo
                          attValue = elementTag.substring(0,elementTag.indexOf(" ")).trim();
                          //Valor por defecto
                          defaultValue = elementTag.substring(elementTag.indexOf(" ")+1,elementTag.length()).trim();                      
                      }
                      
//                      System.out.println("tagName: "+tagName);
//                      System.out.println("attributeName:" + attributeName);
//                      System.out.println("attValue: "+attValue);
//                      if(!valores.isEmpty())
//                        System.out.println(valores.toString());
//                      System.out.println("defaultValue: "+defaultValue);    
                                
                    //Manejador de atributos
                    if(!valores.isEmpty())                       
                        dtdHandler.startElement(tagName, attributeName, valores, defaultValue);
                    else
                        dtdHandler.startElement(tagName, attributeName, attValue, defaultValue);
                 }                 
           }//end of if (matcher.find())
        }//end of while
    }    
    
}