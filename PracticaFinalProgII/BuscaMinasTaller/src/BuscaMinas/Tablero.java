//Pavel Ernesto García Lorenzo
//https://youtu.be/4ltigFRbUY0  enlace del video
package BuscaMinas;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/* esta Clase Tablero es la que maneja el funcionamiento del tablero como la logica de la 
  partida,y sus casillas.
 */
public class Tablero {

    private Casilla tablero[][];
    private final static int filas = 9;
    private final static int columnas = 9;
    private int numeroMinas;

//METODOS CONSTRUCTORES
    public Tablero(int n) {
        tablero = new Casilla[filas][columnas];
        this.numeroMinas = n;
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = new Casilla(contador);
                contador++;
            }
        }

        ubicarMinas(n);
        calcularCercaniaMinas();

    }

    public Tablero(TableroSer tabS) {
        tablero = new Casilla[filas][columnas];
        this.numeroMinas = tabS.getNumeroMinas();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = new Casilla(tabS.casillaPosicion(i, j));
            }
        }
    }

//METODOS FUNCIONALES
    //metodo que devuelve la casilla que esta en la posicion i j
    public Casilla casillaPosicion(int i, int j) {
        return tablero[i][j];
    }

    //metodo que devuelve la casilla n
    public Casilla casillaPorCodigo(int n) {
        int i = (int) Math.floor((n) / columnas);
        int j = ((n) % columnas);
        return tablero[i][j];
    }

    //metodo que coloca nMn minas en el tablero aleatoriamente
    public void ubicarMinas(int nMn) {
        int minas[] = new int[nMn];
        //ecogemos nMn numeros aleatorios sin repeticion
        // Metemos en una lista los números del 1 al 40.
        java.util.List<Integer> numbers = new ArrayList<>((filas * columnas) + 1);
        for (int i = 1; i < (filas * columnas) + 1; i++) {
            numbers.add(i);
        }
        // Instanciamos la clase Random
        Random random = new Random();
        //cogemos los mnm posiciones de la mina
        int i = 0;
        while (i < nMn) {
            // Elegimos un índice al azar, entre 0 y el número de casillas que quedan por sacar
            int randomIndex = random.nextInt(numbers.size());
            minas[i] = numbers.get(randomIndex) - 1;
            // Y eliminamos la posicion del la mina (la borramos de la lista)
            i++;
            numbers.remove(randomIndex);
        }
        //colocamos las minas
        for (int j = 0; j < nMn; j++) {
            casillaPorCodigo(minas[j]).setMina(true);
        }
    }

    //metodo que al perder destapa todas las celdas del tablero
    public void derrota() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j].destapar();
                if (casillaPosicion(i, j).getMinasEnZona() == 0) {
                    casillaPosicion(i, j).setText("");

                } else if (casillaPosicion(i, j).isMina()) {
                    casillaPosicion(i, j).setText("X");
                } else {
                    casillaPosicion(i, j).setText("" + casillaPosicion(i, j).getMinasEnZona());
                    switch (casillaPosicion(i, j).getMinasEnZona()) {
                        case 1:
                            casillaPosicion(i, j).setForeground(Color.blue);
                            break;
                        case 2:
                            casillaPosicion(i, j).setForeground(Color.green);
                            break;
                        case 3:
                            casillaPosicion(i, j).setForeground(Color.red);
                            break;
                        default:
                            break;
                    }
                }

            }
        }
    }

    //metodo para destapar celdas
    public void destaparCeldas(int n) {
        int i1 = (int) Math.floor((n) / columnas);
        int j1 = ((n) % columnas);

        if (casillaPorCodigo(n).getMinasEnZona() == 0) {
            for (int i = i1 - 1; i < i1 + 2; i++) {
                for (int j = j1 - 1; j < j1 + 2; j++) {
                    if (i >= 0 && i <= filas - 1 && j >= 0 && j <= columnas - 1) {
                        if (casillaPosicion(i, j).getMinasEnZona() >= 0 && !casillaPosicion(i, j).isMina() && !casillaPosicion(i, j).isSeleccionada()) {
                            if (!(i == i1 - 1 && j == j1 - 1) && !(i == j1 + 2 && j == i1 + 2) && !(i == i1 + 2 && j == j1 - 1) && !(i == i1 - 1 && j == j1 + 2)) {
                                int a = columnas * (i) + (j);
                                casillaPorCodigo(a).destapar();
                                if (casillaPorCodigo(a).getMinasEnZona() == 0) {
                                    casillaPorCodigo(a).setText("");
                                    destaparCeldas(a);
                                } else {
                                    casillaPorCodigo(a).setText("" + casillaPorCodigo(a).getMinasEnZona());
                                    switch (casillaPorCodigo(a).getMinasEnZona()) {
                                        case 1:
                                            casillaPorCodigo(a).setForeground(Color.blue);
                                            break;
                                        case 2:
                                            casillaPorCodigo(a).setForeground(Color.green);
                                            break;
                                        case 3:
                                            casillaPorCodigo(a).setForeground(Color.red);
                                            break;
                                        default:
                                            break;
                                    }
                                }

                            }

                        }

                    }

                }
            }
        } else {
            if (casillaPorCodigo(n).getMinasEnZona() == 0) {
                casillaPorCodigo(n).setText("");
                casillaPorCodigo(n).destapar();
            } else {
                casillaPorCodigo(n).setText("" + casillaPorCodigo(n).getMinasEnZona());
                switch (casillaPorCodigo(n).getMinasEnZona()) {
                    case 1:
                        casillaPorCodigo(n).setForeground(Color.blue);
                        break;
                    case 2:
                        casillaPorCodigo(n).setForeground(Color.green);
                        break;
                    case 3:
                        casillaPorCodigo(n).setForeground(Color.red);
                        break;
                    default:
                        break;
                }
                casillaPorCodigo(n).destapar();

            }

        }

    }

    //metodo para saber si se ha ganado la partida
    public boolean victoria() {
        int casillasDest = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tablero[i][j].isSeleccionada() && !tablero[i][j].isMina()) {
                    casillasDest++;
                }
            }
        }
        if (casillasDest == (filas * columnas) - numeroMinas) {
            return true;
        } else {
            return false;

        }
    }

    //metodo que convierte tablero a tableroSer
    public TableroSer toTableroSer() {
        return new TableroSer(this);
    }

    //metodod que convierte un objeto tablero en otro
    public void cambiar(Tablero tab) {
        BuscaMinasMain.jplTablero.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillaPosicion(i, j).setBackground(Color.lightGray);
                casillaPosicion(i, j).setText("");
                //establecemos si hay mina
                casillaPosicion(i, j).setMina(tab.casillaPosicion(i, j).isMina());
                //colocamos las minas en ZOna
                casillaPosicion(i, j).setMinasEnZona(tab.casillaPosicion(i, j).getMinasEnZona());
                //miramos si esta seleccionada o no
                if (tab.casillaPosicion(i, j).isSeleccionada()) {
                    if (tab.casillaPosicion(i, j).isMina()) {
                        casillaPosicion(i, j).setSeleccionada(true);
                        casillaPosicion(i, j).setText("X");
                        casillaPosicion(i, j).destapar();
                        BuscaMinasMain.jugando = false;
                        BuscaMinasMain.jplTablero.setBorder(BorderFactory.createLineBorder(Color.red, 4));
                        Resultado res = new Resultado(false);
                    } else {
                        casillaPosicion(i, j).setSeleccionada(true);
                        if (casillaPosicion(i, j).getMinasEnZona() == 0) {
                            casillaPosicion(i, j).setText("");
                        } else {
                            casillaPosicion(i, j).setText(casillaPosicion(i, j).getMinasEnZona() + "");
                        }

                        casillaPosicion(i, j).destapar();
                    }
                } else {
                    casillaPosicion(i, j).setSeleccionada(false);
                    casillaPosicion(i, j).setBackground(Color.lightGray);
                }

            }

        }

    }

    //metodo que comprueba cuantas minas tiene cada casilla del tablero alrededor
    public void calcularCercaniaMinas() {
        for (int i1 = 0; i1 < filas; i1++) {
            for (int j1 = 0; j1 < columnas; j1++) {
                int suma = 0;
                for (int i = i1 - 1; i < i1 + 2; i++) {
                    for (int j = j1 - 1; j < j1 + 2; j++) {
                        if (i >= 0 && i <= filas - 1 && j >= 0 && j <= columnas - 1) {
                            if (tablero[i][j].isMina()) {
                                suma++;
                            }
                        }

                    }
                }
                tablero[i1][j1].setMinasEnZona(suma);

            }

        }
    }

    //metodo que permite guardar un objeto tablero utilizando la clase auxiliar
    //TableroSer
    public void guardarTablero() throws FileNotFoundException, IOException {
        JFileChooser fc = new JFileChooser();

        int seleccion = fc.showSaveDialog(BuscaMinasMain.ventana);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File path = fc.getSelectedFile();

            ObjectOutputStream fo = new ObjectOutputStream(new FileOutputStream(path));
            fo.writeObject(this.toTableroSer());
            fo.close();

        }

    }

    //metodo que permite abrir un objeto tablero utilizando la clase auxiliar
    //TableroSer
    public void abrirTablero() throws IOException, ClassNotFoundException {
        JFileChooser fc = new JFileChooser();

        int seleccion = fc.showOpenDialog(BuscaMinasMain.ventana);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fc.getSelectedFile();
            try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(fichero))) {
                TableroSer tab = (TableroSer) entrada.readObject();
                this.cambiar(tab.toTablero());

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public int getNumeroMinas() {
        return numeroMinas;
    }

    public void setNumeroMinas(int numeroMinas) {
        this.numeroMinas = numeroMinas;
    }

}

/* clase que permite guardar objetos tablero como serializables*/
class TableroSer implements Serializable {

    private CasillaSer tablero[][];
    private static int filas = 9;
    private static int columnas = 9;
    private int numeroMinas;

    public TableroSer(Tablero tab) {
        tablero = new CasillaSer[filas][columnas];
        numeroMinas = tab.getNumeroMinas();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = new CasillaSer(tab.casillaPosicion(i, j));
            }
        }
    }

    //metodo por el cual obtenemos cualquier casillaSer de un objeto TableroSer
    public CasillaSer casillaPosicion(int i, int j) {
        return tablero[i][j];
    }

    //metodo que convierte un objeto Tablero Serializable a un objeto tablero
    public Tablero toTablero() {
        return new Tablero(this);

    }

    public int getNumeroMinas() {
        return numeroMinas;
    }

    public void setNumeroMinas(int numeroMinas) {
        this.numeroMinas = numeroMinas;
    }

}
