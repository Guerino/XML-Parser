package logica.parser;

/**
 *
 * @author Guerino
 */
public class XmlDTDElement {
    private String element;//Nombre de la etiqueta
    /**
     * Cardinalidad 
     * "*" cero o muchos
     * "+" uno o muchos
     * "?" cero o uno
     */
    private char cardinality;

    public XmlDTDElement() {
    }

    public XmlDTDElement(String element, char cardinality) {
        this.element = element;
        this.cardinality = cardinality;
    }

    public char getCardinality() {
        return cardinality;
    }

    public void setCardinality(char cardinality) {
        this.cardinality = cardinality;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }   
    
    
    /**
     * Obtiene la cardinalidad de una etiqueta
     * @param tag
     * @return 
     */
    
    private char getCardinality(String tag){
        char cardinal='1';
        tag = tag.trim();        
        //Obtengo el ultimo caracter de la etiqueta
        String card = String.valueOf(tag.charAt(tag.length()-1));
               
        if(card.equals("*")){
            System.out.println(""); 
            cardinal = '*';
        }else
            if(card.equals("+")){
                cardinal = '+';
            }else
                if(card.equals("?")){
                    cardinal = '?';
                }else
                    cardinal = '1';
        
        return cardinal;
    }

    @Override
    public String toString() {
        return "XmlDTDElement{" + "element=" + element + ", cardinality=" + cardinality + '}';
    }
    
    
}
