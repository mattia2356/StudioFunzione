import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class App {
    public static void main(String[] args) throws Exception {
        /*
         * FUNZIONE--> (-x^3+6x^2-11x+6=0)
         */
        Grafico g = new Grafico();
          // per utilizzare una window Swing è necessario gestire un nuovo thread
          SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                    g.visualizza();
               }
          });
     }
}