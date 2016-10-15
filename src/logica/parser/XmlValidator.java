package logica.parser;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Guerino
 */
public class XmlValidator {
    private String strXml;

    public XmlValidator() {
       this.strXml = new String();
    }

    public XmlValidator(String strXml) {
        this.strXml = strXml;
    }

    public void setStringXml(String strXml) {
        this.strXml = strXml;    
    }
    
    public void validate() throws XmlParseException{
        //1 - Validamos los caracteres de apertura y cierre 
        this.tagValidator();
        
        //2 - Validamos el encabezado
        this.headValidator();
        
        //3 - Validamos el correcto anidamiento y los atributos
        //4 - Validamos que solo tenga un elemento raiz
        this.nestedValidator();        
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
        for (int i = 0; i < this.strXml.length(); i++) {
            status = -1;
            char c = this.strXml.charAt(i);
            strTag += String.valueOf(c);            
            
            if(c == '<') status = 1;
            if(c == '<' && this.strXml.charAt(i+1) == '/') status = 2;
            if(c == '>')  status = 3;         
            
           // if(c == '\n') cntLine++;
            //else
                switch(status){
                    case 1: pila.push(String.valueOf(c)); break;
                    case 2: pila.push((String.valueOf(c) + this.strXml.charAt(i+1))); break;                                     
                    case 3:
                         if(pila.isEmpty()){
                           throw new XmlParseException /*Linea: " + (cntLine-1) + */
                                    ("XML Error: falta el caracter de inicio de etiqueta '<'\nEtiqueta: " + strTag.trim()); 
                          }else
                            if(pila.size() > 1){
                               //si, se paso de largo <  <
                               strTag = strTag.substring(0, strTag.lastIndexOf('<')).trim();
                               throw new XmlParseException
                                    ("XML Error: falta el caracter de cierre de etiqueta '>'\nEtiqueta: " + strTag);   
                           }else{
                                //Removemos los elementos de la pila
                                pila.pop();
                                strTag = "";
                            } break;                       
                        
               }//end switch   
        }//end for 
        
    }
    
    /**
     * Metodo encargado de validar el encabezado xml
     * @param xmlStr
     * @param line
     * @throws XmlParseException 
     */
    private void headValidator() throws XmlParseException{
        //<?xml version="1.0" encoding="ISO-8859-1"?> 
        String xmlStr = this.strXml.substring(0, this.strXml.indexOf(">")+1);
        
        xmlStr = xmlStr.trim();        
        if(!xmlStr.startsWith("<?xml")){
            throw new XmlParseException("XML Error: faltan caracteres de inicio de encabezado" + " \nEtiqueta: " + xmlStr.trim() );   
        }else{
            
            if(!xmlStr.endsWith("?>")){
                throw new XmlParseException("XML Error: faltan caracteres de cierre de encabezado" + " \nEtiqueta: " + xmlStr.trim() );
            }
        }
        
        //me quedo solo con los atributos
        xmlStr = xmlStr.substring(xmlStr.indexOf(" "), xmlStr.indexOf("?>")).trim();
        String[] strAttr = xmlStr.split(" ");
        //Recorremos cada elemento del vector
        for(String str:  strAttr){        
            int pos = str.indexOf("=");
            if(pos != -1){
                //Nombre del atributo
                String text = str.substring(0, pos).trim();                           
                if(!text.matches("[a-zA-Z]+")) //solo letras 
                    throw new XmlParseException
                        ("XML Error: caracter(s) invalido(s) en un nombre de atributo del encabezado" + " \nEtiqueta: " + xmlStr.trim() ); 
                
                //Validamos comillas
                String strQuote = str.substring(pos+1, str.length()).trim();
                //System.out.println(strQuote);                
                
                if(!strQuote.startsWith("\""))
                     throw new XmlParseException
                        ("XML Error: faltan comilla de apertura en un valor de atributo del encabezado" + " \nEtiqueta: " + xmlStr.trim() ); 
               
                if(!strQuote.endsWith("\""))
                    throw new XmlParseException
                        ("XML Error: faltan comilla de cierre en un valor de atributo del encabezado"+ " \nEtiqueta: " + xmlStr.trim() );      
                
            }else{
               throw new XmlParseException
                       ("XML Error: faltan  un caracter '=' en el encabezado" + " \nEtiqueta: " + xmlStr.trim() ); 
            }            
        }//end for
    }
    
    /**
     * Validador de atributos
     * @param xmlStr
     * @param line
     * @throws XmlParseException 
     */
    private void attributeValidator(String xmlStr) throws XmlParseException{
        //me quedo solo con los atributos
         String strTag = xmlStr;//Nombre del tag
         
         if(xmlStr.endsWith("/"))
                //Tag autocontenidos <xxx />
               xmlStr = xmlStr.substring(xmlStr.indexOf(" "), xmlStr.length()-1).trim();
            else
               xmlStr = xmlStr.substring(xmlStr.indexOf(" "), xmlStr.length()).trim();        

            //Separo los atributos por un espacio
            String[] strAttr = xmlStr.split(" ");     

            //Recorremos cada elemento del vector
            for(String str:  strAttr){        
                int pos = str.indexOf("=");
                if(pos != -1){
                    //Nombre del atributo
                    String text = str.substring(0, pos).trim();                           
                    if(!text.matches("[a-zA-Z0-9]+")) //letras o letras con numeros
                        throw new XmlParseException
                            ("XML Error: caracter(s) invalido(s) en un nombre de atributo" + " \nEtiqueta: <" + strTag.trim() +">"); 

                    //Validamos comillas
                    String strQuote = str.substring(pos+1, str.length()).trim();

                    if(!strQuote.startsWith("\""))
                         throw new XmlParseException
                            ("XML Error: faltan comilla de apertura en un valor de atributo" + " \nEtiqueta: <" + strTag.trim() +">"); 

                    if(!strQuote.endsWith("\""))
                        throw new XmlParseException
                            ("XML Error: faltan comilla de cierre en un valor de atributo"  + " \nEtiqueta: <" + strTag.trim() +">");      

                 }else{
                   throw new XmlParseException
                           ("XML Error: faltan un caracter '=' en un valor de atributo" + " \nEtiqueta: <" + strTag.trim() +">"); 
                }
            }//end for        
    
    }
    
    /**
     * Metodo encargado de validar el correcto anidamiento
     * y que solo exista un elemento raiz
     */
    private void nestedValidator() throws XmlParseException{
        String xml = this.strXml;
        boolean rootStart = false;
        boolean rootEnd = false;
        boolean secondRoot = false;
        
        String strTag = new String();
        String strRoot = new String();
        String tagNode = new String();        

        LinkedList<String> pila = new LinkedList<String>();   
        //3- Validamos el correcto anidamiento y los atributos
        xml = xml.substring(xml.indexOf("?>")+2, xml.length());
        
        if(xml.indexOf("<!DOCTYPE") != -1 ){
            String strDTD = xml.substring(xml.indexOf("<!DOCTYPE")+1, xml.indexOf(">"));
            //Validamos el encabezado DTD
            this.dtdValidator(strDTD);
            //Nos quedamos solo con el xml sin el DOCTYPE
            xml = xml.substring(xml.indexOf(">")+1, xml.length());            
        }
        
        Pattern pattern = Pattern.compile("<[^>]+>");
        while (xml.length() > 0){
            Matcher matcher = pattern.matcher(xml);
            //busco las etiquetas
            if (matcher.find()){
                 strTag = matcher.group(0);//Obtengo la etiqueta <XXX>                 
                 tagNode = strTag.substring(1, strTag.length() - 1);//Le quito los caracteres < y > 
                 
                 if(!rootStart){
                    //Guardo el nombre de la primer etiqueta encontrada 
                    strRoot = tagNode;
                    rootStart = true;
                 }
                
                 //Busco la posicion en donde comienza la etiqueta
                 int pos = xml.indexOf(tagNode);
                 //Corto desde la posicion+1 en donde se encuentra la etiqueta actual hasta el final del string
                 xml = xml.substring(pos + tagNode.length() + 1);
                
                 if(!tagNode.startsWith("/")){//etiqueta de apertura o autocontenidas
                     //Sin son etiquetas autocontenidas solo valido sus atributos
                     if(tagNode.endsWith("/")){//No se agregan a la pila
                         this.attributeValidator(tagNode);
                         
                     }else                     
                         if(tagNode.indexOf(" ") != -1){
                            this.attributeValidator(tagNode);                        
                           
                            //Almaceno el nombre de la etiqueta en la pila
                            pila.push(tagNode.substring(0, tagNode.indexOf(" ")).trim());                        

                         }else{                            
                               pila.push(tagNode); //Apilar
                         }
                     
                 }else{  
                     //para el caso en el que viene un tag de cierre y no esta el de apertura en la pila
                     if(pila.isEmpty()){
                          throw new XmlParseException
                            ("XML Error: etiqueta de cierre sin etiqueta de apertura." + "\nEtiqueta: <" + tagNode +">");
                     }else{                     
                        //le quito la barra de inicio / a las etiquetas de cierre
                        tagNode = tagNode.substring(1).trim();
                        //Si la etiqueta leida es igual a la de la pila, la borro
                        if(pila.peek().equals(tagNode)){
                            //Se detecto una segunda etiqueta raiz
                            if(rootEnd){
                                secondRoot = true;
                            }
                            
                            //Desapilo
                            String str = pila.pop();
                            //Validamos que solo exista una etiqueta raiz
                            //Si la etiqueta removida es igual a la raiz
                            if(str.equals(strRoot)){                               
                               rootEnd = true;
                            }      
                        }else
                           throw new XmlParseException
                                ("XML Error: no coinciden los nombres de incio y cierre de etiqueta" 
                                   + "\nEtiqueta: <" + pila.peek() +"> </" + tagNode + ">"); 
                     }
                 }                
            }
        }//end while  
        
        
        if(rootEnd && secondRoot)
            throw new XmlParseException
                    ("XML Error: no puede haber mas de un elemento raiz." + "\nEtiqueta: <" + tagNode +">"); 
        
        if(!pila.isEmpty())
            throw new XmlParseException
                    ("XML Error: etiqueta mal anidada." + "\nEtiqueta: <" + tagNode +">");  
    }
    
    /**
     * Metodo encargado de validar la etiqueta de especificacion
     * del documento DTD
     * @param strTag
     * @throws XmlParseException 
     */
    private void dtdValidator(String strTag) throws XmlParseException{
        //<!DOCTYPE nota SYSTEM "NotaInterna.dtd">
            if(strTag.indexOf("SYSTEM") == -1)
                throw new XmlParseException
                         ("XML Error: falta la especificacion SYSTEM" + " \nEtiqueta: <" + strTag.trim() +">");
            
            //Validamos que este el nombre de la etiqueta raiz
            String strTagRoot = strTag.substring(strTag.indexOf(" ")+1, strTag.indexOf("SYSTEM"));            
            if(strTagRoot.isEmpty())
                throw new XmlParseException
                         ("XML Error: falta el nombre de la etiqueta raiz" + " \nEtiqueta: <" + strTag.trim() +">");
            
            //Validamos que se encuentre el nombre del archivo DTD
            String strFileDTD = strTag.substring(strTag.lastIndexOf("SYSTEM")+6, strTag.length()).trim();            
            
            if(strFileDTD.isEmpty()){
                throw new XmlParseException
                         ("XML Error: falta el nombre del archivo DTD" + " \nEtiqueta: <" + strTag.trim() +">");
            }else{
                if(!strFileDTD.startsWith("\""))
                    throw new XmlParseException
                         ("XML Error: falta comillas de apertura en el nombre del archivo DTD" + " \nEtiqueta: <" + strTag.trim() +">");
                
                if(!strFileDTD.endsWith("\""))
                    throw new XmlParseException
                         ("XML Error: falta comillas de apertura en el nombre del archivo DTD" + " \nEtiqueta: <" + strTag.trim() +">");
                
                //Le quito las comillas
                strFileDTD = strFileDTD.substring(1,strFileDTD.length()-1).trim();
                if(strFileDTD.indexOf(".") == -1)
                    throw new XmlParseException
                         ("XML Error: falta la extension en el nombre del archivo DTD" + " \nEtiqueta: <" + strTag.trim() +">");
                
                //Valido la extension
                strFileDTD = strFileDTD.substring(strFileDTD.indexOf(".")+1,strFileDTD.length()).trim();
                //System.out.println(strFileDTD);
                if(!strFileDTD.contains("dtd"))
                    throw new XmlParseException
                         ("XML Error: la extension en el nombre de archivo DTD no es valida" + " \nEtiqueta: <" + strTag.trim() +">");
            }
    }
    
}