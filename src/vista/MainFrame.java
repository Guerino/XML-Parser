/*
 * MainFrame.java
 *
 * Created on 26-dic-2011, 22:31:43
 */
package vista;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import logica.JComboBoxRenderer;
import javax.swing.tree.DefaultTreeModel;
import logica.HiloParser;
import logica.JTreeXmlRender;
import logica.parser.XmlNode;
import logica.parser.XmlTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import logica.HiloGuardarArchivo;
/**
 *
 * @author Guerino
 */
public class MainFrame extends javax.swing.JFrame {
    private HiloParser hiloParse;
    private String strFileName;
    private String frameTitle;
    /** Creates new form MainFrame */
    public MainFrame() {
        super();    
        initComponents();
        this.setIconImage (new ImageIcon(getClass().getResource("/resources/parser.png")).getImage());
        this.setLocationRelativeTo(null);
        this.jTreeXML.setModel(null);  
        this.hiloParse = null;
        this.strFileName = new String();
        
        frameTitle = "XML Parser 1.0 | Sistemas.frc.utn.edu.ar - 2012";
        this.setTitle(frameTitle);
        
                 
        this.jButtonValidar.setEnabled(false);
        this.jButtonGuardar.setEnabled(false);
       
        //menu del TextArea
        this.popUpMenuJTextArea();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jPopupMenuTextArea = new javax.swing.JPopupMenu();
        jMenuItemTitle = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemCut = new javax.swing.JMenuItem();
        jMenuItemCopy = new javax.swing.JMenuItem();
        jMenuItemPaste = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonOpenFile = new javax.swing.JButton();
        jButtonGuardar = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jCbxPathFile = new javax.swing.JComboBox();
        jButtonValidar = new javax.swing.JButton();
        jLblInfoBar = new javax.swing.JLabel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeXML = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaXML = new javax.swing.JTextArea();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jMenuItemTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItemTitle.setText("XML Parser");
        jMenuItemTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuItemTitle.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jMenuItemTitle.setIconTextGap(8);
        jMenuItemTitle.setRequestFocusEnabled(false);
        jMenuItemTitle.setVerifyInputWhenFocusTarget(false);
        jPopupMenuTextArea.add(jMenuItemTitle);
        jPopupMenuTextArea.add(jSeparator1);

        jMenuItemCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Cut-icon.png"))); // NOI18N
        jMenuItemCut.setMnemonic('C');
        jMenuItemCut.setText("Cortar");
        jMenuItemCut.setActionCommand("");
        jMenuItemCut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuItemCut.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenuItemCut.setIconTextGap(8);
        jPopupMenuTextArea.add(jMenuItemCut);

        jMenuItemCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Copy-icon.png"))); // NOI18N
        jMenuItemCopy.setMnemonic('O');
        jMenuItemCopy.setText("Copiar");
        jMenuItemCopy.setActionCommand("");
        jMenuItemCopy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuItemCopy.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenuItemCopy.setIconTextGap(8);
        jPopupMenuTextArea.add(jMenuItemCopy);

        jMenuItemPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/paste-icon.png"))); // NOI18N
        jMenuItemPaste.setMnemonic('P');
        jMenuItemPaste.setText("Pegar");
        jMenuItemPaste.setActionCommand("");
        jMenuItemPaste.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuItemPaste.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenuItemPaste.setIconTextGap(8);
        jPopupMenuTextArea.add(jMenuItemPaste);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setDoubleBuffered(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(32954, 40));
        jToolBar1.setMinimumSize(new java.awt.Dimension(154, 26));

        jButtonOpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/001_01.png"))); // NOI18N
        jButtonOpenFile.setText("Abrir archivo...");
        jButtonOpenFile.setFocusable(false);
        jButtonOpenFile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonOpenFile.setMaximumSize(new java.awt.Dimension(120, 32));
        jButtonOpenFile.setMinimumSize(new java.awt.Dimension(120, 32));
        jButtonOpenFile.setPreferredSize(new java.awt.Dimension(120, 32));
        jButtonOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenFileActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonOpenFile);

        jButtonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/save.png"))); // NOI18N
        jButtonGuardar.setText("Guardar");
        jButtonGuardar.setFocusable(false);
        jButtonGuardar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonGuardar.setPreferredSize(new java.awt.Dimension(80, 32));
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonGuardar);

        jButtonExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/exit.png"))); // NOI18N
        jButtonExit.setText("Salir");
        jButtonExit.setFocusable(false);
        jButtonExit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonExit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonExit.setMaximumSize(new java.awt.Dimension(55, 32));
        jButtonExit.setMinimumSize(new java.awt.Dimension(55, 32));
        jButtonExit.setPreferredSize(new java.awt.Dimension(70, 32));
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonExit);
        jToolBar1.add(jSeparator2);

        jCbxPathFile.setFont(new java.awt.Font("Tahoma", 0, 12));
        jCbxPathFile.setMaximumRowCount(30);
        jCbxPathFile.setDoubleBuffered(true);
        jCbxPathFile.setPreferredSize(new java.awt.Dimension(28, 21));
        jToolBar1.add(jCbxPathFile);

        jButtonValidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/validate.png"))); // NOI18N
        jButtonValidar.setText("Validar");
        jButtonValidar.setFocusable(false);
        jButtonValidar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonValidar.setMaximumSize(new java.awt.Dimension(80, 32));
        jButtonValidar.setMinimumSize(new java.awt.Dimension(80, 32));
        jButtonValidar.setPreferredSize(new java.awt.Dimension(80, 32));
        jButtonValidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValidarActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonValidar);

        jLblInfoBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jSplitPane3.setDividerLocation(300);
        jSplitPane3.setDividerSize(6);

        jScrollPane1.setViewportView(jTreeXML);

        jSplitPane3.setLeftComponent(jScrollPane1);

        jTextAreaXML.setColumns(20);
        jTextAreaXML.setRows(5);
        jTextAreaXML.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextAreaXMLMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTextAreaXML);

        jSplitPane3.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLblInfoBar, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLblInfoBar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenFileActionPerformed
       //Seleccionamos el archivo XML    
       FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos XML (*.xml)", "xml");
       this.jFileChooser.setMultiSelectionEnabled(false);
       this.jFileChooser.setFileFilter(filter);
       
       int seleccion = this.jFileChooser.showOpenDialog(this);
       
       if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = this.jFileChooser.getSelectedFile();
            this.strFileName = fichero.getAbsolutePath();
            
            JComboBoxRenderer rc = new JComboBoxRenderer();  
            this.jCbxPathFile.setRenderer(rc);
            this.jCbxPathFile.addItem(strFileName);
            this.jCbxPathFile.setSelectedIndex(this.jCbxPathFile.getItemCount()-1);
            this.jButtonValidar.setEnabled(true);
            
       }
       
       this.jFileChooser.removeChoosableFileFilter(filter);
        
    }//GEN-LAST:event_jButtonOpenFileActionPerformed

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_jButtonExitActionPerformed

    private void jButtonValidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValidarActionPerformed
        //Mostramos el nombre del archivo
        String  strPath = (String)this.jCbxPathFile.getSelectedItem();
        //Archivo que actualmente se esta editando
        this.strFileName = strPath;
        String  regexpFile= "([\\w ]+\\.xml)";
        Pattern patFile = Pattern.compile(regexpFile);
        Matcher matFile = patFile.matcher(strPath); 
      
        matFile.find();        
        this.setTitle(this.frameTitle + " - [" + matFile.group(1) + "]" );              
            
        //Implementado la ejecucion del parser en un hilo        
        hiloParse = new HiloParser(this,(String)this.jCbxPathFile.getSelectedItem(), this.jTextAreaXML, this.jLblInfoBar);     
        hiloParse.start(); 
               
        //////////////////////////////////////////////////////////////////////////////////////////////////
        this.jTextAreaXML.setFont(new Font("Monospaced",Font.PLAIN,12));
        this.jTextAreaXML.setForeground(Color.BLUE);
        this.jTextAreaXML.setBackground(Color.white);         
        //Posicionamos el cursor al principio del documento
        this.jTextAreaXML.setCaretPosition(this.jTextAreaXML.getDocument().getStartPosition().getOffset());
        jButtonGuardar.setEnabled(false); 

    }//GEN-LAST:event_jButtonValidarActionPerformed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        //Hilo de escritura del archivo a disco
        HiloGuardarArchivo hilo = new HiloGuardarArchivo(this.jLblInfoBar, this.jButtonGuardar, this.jTextAreaXML.getText(), this.strFileName);
        //Iniciamos el hilo
        hilo.start();
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jTextAreaXMLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextAreaXMLMouseClicked
        //Menu contextual para copiar y pegar
        if (evt.getButton() == MouseEvent.BUTTON3) 
                   this.jPopupMenuTextArea.show(this.jTextAreaXML, evt.getX(), evt.getY());
              
    }//GEN-LAST:event_jTextAreaXMLMouseClicked

    public void inicializarArbol(XmlTree tree){       
        //Verificamos que el arbol no este vacio        
        if( tree.size() > 0){            
            DefaultMutableTreeNode root = new DefaultMutableTreeNode( tree.getRoot().toString());
            XmlNode rnode =  tree.getRoot();

            //Iteramos por cada nodo en la lista
            for (XmlNode xnode: rnode.getChildrenNode()) {                              
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(xnode.toString());                                
                //Nodos padres
                root.add(newNode);
                //nodos hijos
                if(xnode.getNumberOfChildren() > 0){
                    this.addChildNode(xnode, newNode);
                }
            }

            DefaultTreeModel model = new DefaultTreeModel (root);             
            //Seteamos el JTree            
            this.jTreeXML.setModel(model);            
            this.jTreeXML.setCellRenderer(new JTreeXmlRender());
            this.jTreeXML.expandRow(0);        
        }
    }
    
    private void addChildNode(XmlNode node, DefaultMutableTreeNode parent){
        //Tomamos los hijos del arraylist
        for (XmlNode cnode: node.getChildrenNode()) {
           DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(cnode.toString());
           //Nodos padres e hijos a la vez
           parent.add(newNode); 
           //El proceso de insercion es recursivo
           addChildNode(cnode, newNode);
        }  
    }
    
    public void habilitarCtrls(boolean estado){
        this.jButtonOpenFile.setEnabled(estado);
        this.jButtonValidar.setEnabled(estado);
        this.jButtonExit.setEnabled(estado);
        this.jCbxPathFile.setEnabled(estado);
    }

    public void setjButtonGuardarEnabled(boolean state) {
        this.jButtonGuardar.setEnabled(state);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
             //Para que tenga el estilo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    
    public final void popUpMenuJTextArea() {
        //Si se edita el documento se habilita el boton de guardar
         this.jTextAreaXML.getDocument().addDocumentListener( new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    jButtonGuardar.setEnabled(true);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    jButtonGuardar.setEnabled(true);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                   jButtonGuardar.setEnabled(true);
                }
            } );
         
        this.jMenuItemCut.addActionListener(new Cut());
        this.jMenuItemCopy.addActionListener(new Copy());
        this.jMenuItemPaste.addActionListener(new Paste());  
        //Aceleradores de teclado para los menus
        this.jMenuItemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        this.jMenuItemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        this.jMenuItemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
    }
    
    class Copy implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        String selection = jTextAreaXML.getSelectedText();
        if (selection == null)   return;
        StringSelection clipString = new StringSelection(selection);
        clipbd.setContents(clipString, clipString);
        }
    }

   class Cut implements ActionListener {
        @Override
    public void actionPerformed(ActionEvent e) {
      String selection = jTextAreaXML.getSelectedText();
      if (selection == null)  return;
      StringSelection clipString = new StringSelection(selection);
      clipbd.setContents(clipString, clipString);
      jTextAreaXML.replaceRange("", jTextAreaXML.getSelectionStart(), jTextAreaXML.getSelectionEnd());
    }
   }

   class Paste implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      Transferable clipData = clipbd.getContents(this);
      try {
        String clipString = (String) clipData.getTransferData(DataFlavor.stringFlavor);
        jTextAreaXML.replaceRange(clipString, jTextAreaXML.getSelectionStart(), jTextAreaXML.getSelectionEnd());
      } catch (Exception ex) {
        System.err.println("Not String flavor");
      }
     }
    }
   
    private Clipboard clipbd = getToolkit().getSystemClipboard();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonOpenFile;
    private javax.swing.JButton jButtonValidar;
    private javax.swing.JComboBox jCbxPathFile;
    private javax.swing.JFileChooser jFileChooser;
    private javax.swing.JLabel jLblInfoBar;
    private javax.swing.JMenuItem jMenuItemCopy;
    private javax.swing.JMenuItem jMenuItemCut;
    private javax.swing.JMenuItem jMenuItemPaste;
    private javax.swing.JMenuItem jMenuItemTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenuTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTextArea jTextAreaXML;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTreeXML;
    // End of variables declaration//GEN-END:variables
}
