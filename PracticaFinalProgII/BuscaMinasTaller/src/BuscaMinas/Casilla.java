//Pavel Ernesto García Lorenzo
//https://youtu.be/4ltigFRbUY0  enlace del video

package BuscaMinas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;


/**
 *Esta clase Casilla que hereda de JButton es la clase donde se guarda el numero de minas en el cuadrante ,
 * si la casilla tiene mina o si esta seleccionada,asi como un codigo unico para cada casilla
 */
public class Casilla extends JButton implements Serializable {

    private boolean seleccionada = false;
    private boolean mina = false;
    private int minasEnZona;
    private int codigo;

    private final ActionListener funcionCasilla = (ActionEvent e) -> {
        //gracias al actionevent mediante el get source hallamos cual de los
        //botones ha sido seleccionado
        JButton a = ((JButton) e.getSource());
        //obtenemos el codigo perteneciente al boton
        String codBtnSeleccionado = a.getActionCommand();
        int casillaSeleccionada = Integer.parseInt(codBtnSeleccionado);
        BuscaMinasMain.updCasillaSeleccionada(casillaSeleccionada);
    };
    
    
    //METODOS CONSTRUCTORES
    public Casilla(int cod) {
        //a cada boton se le asigna un codigo para luego acceder mas facilmente a ellos
        this.codigo=cod;
        this.setActionCommand("" + cod);
        this.addActionListener(funcionCasilla);
        if (this.seleccionada == true) {
            this.setBackground(Color.white);
        } else {
            this.setBackground(Color.lightGray);
            this.setText("");
        }

    }
    
    public Casilla(CasillaSer cas){
        this.codigo=cas.getCodigo();
        this.seleccionada=cas.isSeleccionada();
        this.mina=cas.isMina();
        this.minasEnZona=cas.getMinasEnZona();
        
        this.setActionCommand("" + codigo);
        this.addActionListener(funcionCasilla);
        if (this.seleccionada == true) {
            this.setBackground(Color.white);
        } else {
            this.setBackground(Color.lightGray);
            this.setText("");
        }
        
        
    }

   
    public void destapar() {
        this.seleccionada = true;
        if(isMina()){
            this.setBackground(Color.GRAY);
        }else{
            this.setBackground(Color.white);
        }
        
    }
    
    //getters and setters de la clase Casilla

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    public int getMinasEnZona() {
        return minasEnZona;
    }

    public void setMinasEnZona(int minasEnZona) {
        this.minasEnZona = minasEnZona;
    }

}

/* clase auxiliar que es una version de la clase casilla la cual es serializable y por lo tanto
   podemos leer y escribir en un fichero
*/

class CasillaSer implements Serializable {
    private boolean seleccionada = false;
    private boolean mina = false;
    private int minasEnZona;
    private int codigo;
    
    public CasillaSer(Casilla cas){
        this.seleccionada = cas.isSeleccionada();
        this.mina = cas.isMina();
        this.minasEnZona = cas.getMinasEnZona();
        this.codigo = cas.getCodigo();
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public int getMinasEnZona() {
        return minasEnZona;
    }

    public void setMinasEnZona(int minasEnZona) {
        this.minasEnZona = minasEnZona;
    }
    
    

}
