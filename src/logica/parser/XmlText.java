package logica.parser;


/**
 *
 * @author Guerino
 */
public class XmlText {
    private String text;

    public XmlText() {
        this.text = null;
    }

    public XmlText(String txt) {
        this.text = txt;
    }

    public String getText() {
        return text;
    }

    public void setText(String txt) {
        this.text = txt;
    }

    
    @Override
    public String toString() {
        return "Text{" + "txt=" + text + '}';
    }
    
    
    
}
