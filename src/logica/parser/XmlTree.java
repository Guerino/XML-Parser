package logica.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Arbol de Nodos Elementos
 * @author Guerino
 */
public class XmlTree {
    private XmlNode root; 
    
    public XmlTree() {
        this.root = null;
   }
    
    public XmlTree(XmlNode root) {
        this.root = root;        
    }

    public XmlNode getRoot() {
        return root;
    }

    public void setRoot(XmlNode root) {
        this.root = root;
    }
    
    /**
     * Devuelve el tamaño del arbol
     * @return 
     */
    public int size() {
        int numberOfNodes = 0;

        if(root != null) {
            numberOfNodes = size(root) + 1; //1 para la raiz
        }

        return numberOfNodes;
    }
    
    private int size(XmlNode node) {
        int numberOfNodes = node.getNumberOfChildren();

        for(XmlNode child : node.getChildrenNode()) {
            numberOfNodes += size(child);
        }
        
        return numberOfNodes;
    }
    
    public List<XmlNode> getPathToNode(XmlNode node) {
        XmlNode currentNode = node;
        List<XmlNode> reversePath = new ArrayList<XmlNode>();
        reversePath.add(node);
        while (!(this.root.equals(currentNode)) ) {
            currentNode = currentNode.getParentNode();
            reversePath.add(currentNode);
        }
        Collections.reverse(reversePath); // now the list is root -> node
        return reversePath;              
    } 
    
    public void insertChild(XmlNode child){
        root.addChild(child);
    }
    
    
    private ArrayList<XmlNode> insertNode(XmlNode element, ArrayList<XmlNode> list) {
        list.add(element);
        for (XmlNode data : element.getChildrenNode())
             insertNode(data, list);
        
        return list;
    }
        
    
}
