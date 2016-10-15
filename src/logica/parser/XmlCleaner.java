package logica.parser;

/**
 *
 * @author Guerino
 */
public class XmlCleaner {
    /**
     * Metodo que limpia de comenterios el archivo xml en memoria
     * @param xmlStr
     * @return 
     */
    public static String clearComments(String xmlStr){     
        //Borra los comentarios del documento para que al procesar el xml 
        //el parser no tenga que cargar ningun tipo de comentario
	return xmlStr.replaceAll("<!-- (\\s*(\\w+)\\s*|(\\s*(\\w+))+)* -->", "").trim(); 
        //return xmlStr.replaceAll("<!-- (.*) -->", "").trim(); 
    }
    
    public static String clearWhiteSpaces(String xmlStr){     
        //Borra los espacios en blanco del documento
	return xmlStr.replaceAll("(\\s){2,}", "").trim();        
    }
    
    public static String clearLineNew(String xmlStr){     
        //Borra los espacios en blanco del documento
	return xmlStr.replaceAll("\n", "").trim();        
    }
    
    public static String clearTab(String xmlStr){     
        //Borra los espacios en blanco del documento
	return xmlStr.replaceAll("\t", "").trim();        
    }
}
