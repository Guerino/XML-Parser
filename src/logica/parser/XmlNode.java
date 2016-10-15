package logica.parser;

import java.util.ArrayList;

/**
 *
 * @author Guerino
 */
public class XmlNode {
    private XmlElement element;//nombre, atributos,texto    
    //Arbol
    private XmlNode parentNode;//0..1
    private ArrayList<XmlNode> childrenNode;//0..*
    
    //Navegacion en ambos sentidos
    private XmlNode previusSiblings;//0..*
    private XmlNode nextSiblings;//0..* 
    
    //Constructor    
    public XmlNode() {
        this.element = null;
        this.childrenNode = new ArrayList<XmlNode>();
        this.parentNode = null;        
        this.previusSiblings = null;
        this.nextSiblings = null;
    }

    public XmlNode(XmlElement element, XmlNode parentNode, ArrayList<XmlNode> childrenNode) {
        this.element = element;
        this.parentNode = parentNode;
        this.childrenNode = childrenNode;
    }    
    
    //Metodo de seteo    
    public XmlElement getElement() {
        return element;
    }

    public void setElement(XmlElement element) {
        this.element = element;
    }
    
    public XmlNode getNextSiblings() {
        return nextSiblings;
    }

    public void setNextSiblings(XmlNode nextSiblings) {
        this.nextSiblings = nextSiblings;
    }

    public XmlNode getPreviusSiblings() {
        return previusSiblings;
    }

    public void setPreviusSiblings(XmlNode previusSiblings) {
        this.previusSiblings = previusSiblings;
    }

    public ArrayList<XmlNode> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(ArrayList<XmlNode> childrenNode) {
        for(XmlNode child : childrenNode) {
           child.parentNode = this;
        }
        this.childrenNode = childrenNode;
    }

    public XmlNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(XmlNode parentNode) {
        this.parentNode = parentNode;
    }   
    
    public int getNumberOfChildren() {
        return getChildrenNode().size();
    }

    public boolean hasChildren() {
        return (getNumberOfChildren() > 0);
    }
    
    public boolean addChild(XmlNode child) { 
        //Seteamos el nodo padre
        child.parentNode = this;
        return childrenNode.add(child);
    }
    
    public void addChildAt(int index, XmlNode child) throws IndexOutOfBoundsException {
        child.parentNode = this;
        childrenNode.add(index, child);
    }
    
    public void removeChildren() {
        childrenNode = new ArrayList<XmlNode>();//Seteo a null       
    }

    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        childrenNode.remove(index);
    }

    public XmlNode getChildAt(int index) throws IndexOutOfBoundsException {
        return childrenNode.get(index);
    }

    @Override
    public String toString() {
        return element.toString();
    }
    
    
    
}