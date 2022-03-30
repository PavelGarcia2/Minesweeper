//Pavel Ernesto García Lorenzo
//https://youtu.be/4ltigFRbUY0  enlace del video
package BuscaMinas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *Esta clase que hereda de JFrame muestra una ventana  emergente que nos dice que hemos ganado o perdido
 * y nos da la opcion de seguir jugando o salir
 */
public class Resultado extends JFrame {

    private JTextField jtexto;
    private JPanel jpBotones;
    private JButton btnCerrar;
    private JButton btnReiniciar;

    public Resultado(boolean victoria) {
        new JFrame();
        this.setLayout(new BorderLayout());
        this.setSize(200, 200);
        this.setLocationRelativeTo(null);
        jtexto = new JTextField();

        if (victoria) {
            jtexto.setText("VICTORIA");
            jtexto.setForeground(Color.green);
        } else {
            jtexto.setText("DERROTA");

            jtexto.setForeground(Color.red);
        }
        jtexto.setFont(new Font("Dialog", Font.BOLD, 36));
        jtexto.setEditable(false);

        jpBotones = new JPanel();
        btnCerrar = new JButton();
        btnCerrar.setText("TERMINAR");
        btnCerrar.setBackground(Color.white);
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }

        });

        btnReiniciar = new JButton();
        btnReiniciar.setText("JUGAR");
        btnReiniciar.setBackground(Color.white);
        btnReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscaMinasMain.jugando=true;
                BuscaMinasMain.tablero.cambiar(new Tablero(10));
                setVisible(false);
                dispose();
            }

        });

        //jpBotones.add(jtexto);
        jpBotones.add(btnReiniciar);
        jpBotones.add(btnCerrar);
        
        this.getContentPane().add(jtexto, BorderLayout.CENTER);
        this.getContentPane().add(jpBotones, BorderLayout.SOUTH);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
