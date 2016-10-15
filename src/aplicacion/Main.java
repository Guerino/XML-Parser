/* 
 * Implementar un parser XML, al estilo de un parser DOM. La aplicación debe ser capaz de las siguientes funcionalidades:

1-Permitir que se seleccione un documento desde una carpeta, usando elementos como objetos de la clase JFileChooser (de Swing). =>HECHO
 * 
2-A partir de un documento XML, verificar que el mismo esté bien formado, y en ese caso armar en memoria 
 * un árbol de caminos múltiples (o sea, un árbol n-ario [no binario]) con la estructura del documento, 
 * emulando lo que hace un parser DOM. =>HECHO
 * 
3-Controlar la validez del documento contra una DTD, si es que el mismo indica alguna. //HECHO
 * 
4-Si todo está bien, la aplicación debe mostrar la estructura del documento adecuadamente en la interfaz visual. 
 * Lo mejor sería que esa estructura se muestre como un árbol, y que se pueda interactuar con ese árbol al estilo 
 * de lo que hace un navegador web cuando despliega un documento XML: cerrar o abrir una rama, colorear cada 
 * elemento en forma distinta según el tipo, etc. =>HECHO
 * 
5-Analizar la forma de permitir que se puedan introducir cambios en el árbol visualizado, y hacer que
 * esos cambios "regresen" al documento en disco y lo actualicen. =>HECHO

•OBVIAMENTE, NO PUEDEN usar para este trabajo ninguna de las clases que ya vienen provistas 
 * en las APIs de Java para XML: nada de DOM ni nada de SAX que venga predefinido en Java.
 * TODA la programación debe ser realizada por los alumnos.
*/

//Falta validar contra un DTD - Hecho
//Falta grabar en disco los cambios realizados - Hecho
//Falta validar que tenga un solo nodo raiz - Hecho
//Falta el encabezado puede no terminar en \n - Hecho
//Falta agregar comportamiento al XmlAttribute - Hecho

/*
    A "Well Formed" XML document has correct XML syntax.

    The syntax rules were described in the previous chapters:

    1 - XML documents must have a root element - HECHO
    2 - XML elements must have a closing tag - HECHO
    3 - XML tags are case sensitive - HECHO
    4 - XML elements must be properly nested - HECHO
    5 - XML attribute values must be quoted - HECHO
 * 
 */
package aplicacion;

import javax.swing.UIManager;
import vista.MainFrame;

/**
 *
 * @author Güerino
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){      
               
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
}
