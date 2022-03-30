//Pavel Ernesto García Lorenzo
//https://youtu.be/4ltigFRbUY0  enlace del video
package BuscaMinas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Pavel Garcia
 */
public class BuscaMinasMain extends JFrame implements MouseListener, ClipboardOwner {

    static boolean jugando = true;
    static boolean victoria = false;
    static BuscaMinasMain ventana;
    static Tablero tablero;
    static JPanel jplTablero;
    private static JMenuBar jmbarMenu;
    private static JMenu jmnuPartida;
    private static JMenuItem jmItemGuardar;
    private static JMenuItem jmItemAbrir;
    private static JMenuItem jmItemRestart;
    static Casilla iconosImagenes[];

    public BuscaMinasMain() {
        this.setTitle("Taller Evaluable 2");
        this.setLayout(new BorderLayout());
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    public static void main(String[] args) {
        ventana = new BuscaMinasMain();
        BuscaMinasMain.initComponents();
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // Inicialització de totes les components
    private static void initComponents() {
        //inicializamos el MenuBar y lo mostramos en pantalla
        inicializarMenu();
        //inicializamos el tablero y lo mostramos en pantalla
        inicializarTablero();
        //inicializarMenuDificultad();

    }

    // metdo que actualiza los datos la Casilla seleccionada
    public static void updCasillaSeleccionada(int n) {
        if (jugando == true) {

            if (tablero.casillaPorCodigo(n).isMina()) {
                jugando = false;
                jplTablero.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                tablero.casillaPorCodigo(n).setBackground(Color.gray);
                tablero.casillaPorCodigo(n).setText("X");

                tablero.casillaPorCodigo(n).setSeleccionada(true);
                tablero.derrota();

                Resultado res = new Resultado(false);

            } else {

                tablero.destaparCeldas(n);
                if (tablero.victoria()) {
                    jugando = false;
                    jplTablero.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
                    Resultado res = new Resultado(true);
                    
                }

            }
        }

    }

    public static void inicializarMenu() {
        //declaramos los componenetes del menu principal

        jmbarMenu = new JMenuBar();
        jmnuPartida = new JMenu();
        jmItemGuardar = new JMenuItem();
        jmItemAbrir = new JMenuItem();
        jmItemRestart = new JMenuItem();
        ventana.getContentPane().add(jmbarMenu);

        //configuramos el boton de guardar
        jmItemGuardar.setText("Guardar");
        jmItemGuardar.addActionListener((ActionEvent evt) -> {
            try {
                tablero.guardarTablero();
            } catch (IOException ex) {
                Logger.getLogger(BuscaMinasMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //configuramos el boton de abrir
        jmItemAbrir.setText("Abrir");
        jmItemAbrir.addActionListener((ActionEvent evt) -> {
            try {
                tablero.abrirTablero();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(BuscaMinasMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jmItemRestart.setText("Reiniciar");
        jmItemRestart.addActionListener((ActionEvent evt) -> {
            tablero.cambiar(new Tablero(10));
            jugando = true;
        });
        //añadimos los botones del menu en la barra del menu y lo establecemos
        jmnuPartida.setText("Partida");
        jmnuPartida.add(jmItemGuardar);
        jmnuPartida.add(jmItemAbrir);
        jmnuPartida.add(jmItemRestart);
        jmbarMenu.add(jmnuPartida);
        ventana.setJMenuBar(jmbarMenu);

    }

    public static void inicializarTablero() {
        tablero = new Tablero(10);
        int filas = 9;
        int columnas = 9;
        jplTablero = new JPanel();
        jplTablero.setLayout(new GridLayout(columnas, filas));
        jplTablero.setPreferredSize(new Dimension(400, 450));
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                jplTablero.add(tablero.casillaPosicion(i, j));
            }

        }
        jplTablero.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
        jplTablero.setBackground(Color.white);
        ventana.getContentPane().add(jplTablero, BorderLayout.CENTER);
    }

    public static void inicializarMenuDificultad() {
        JPanel jplTab = new JPanel();
        jplTab.setLayout(new GridLayout(3, 1));
        jplTab.setPreferredSize(new Dimension(100, 450));
        //MODO FACIL
        JButton btn = new JButton();
        btn.setText("FACIL");
        btn.setBackground(Color.white);
        btn.addActionListener((ActionEvent e) -> {
            tablero.cambiar(new Tablero(10));
        });
        //btn.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
        jplTab.add(btn);
        //MODO MEDIO
        JButton btn2 = new JButton();
        btn2.setText("INTERMEDIO");
        btn2.setBackground(Color.white);
        btn2.addActionListener((ActionEvent e) -> {
            tablero.cambiar(new Tablero(15));
        });
        //btn2.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
        jplTab.add(btn2);
        //MODO INTERMEDIO
        JButton btn3 = new JButton();
        btn3.setText("AVANZADO");
        btn3.setBackground(Color.white);
        btn3.addActionListener((ActionEvent e) -> {
            tablero.cambiar(new Tablero(20));
        });
        //btn3.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
        jplTab.add(btn3);
        //jplTab.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
        ventana.getContentPane().add(jplTab, BorderLayout.WEST);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
