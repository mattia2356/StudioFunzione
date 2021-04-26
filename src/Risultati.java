import java.awt.*;

public class Risultati {
    public static void plot(Graphics g1, String f, double[] radiciX, double[] min, double[] max, double[] flesso, int x,int y) {
        g1.drawString("La funzione è: " + f, x, y);
        g1.drawString("Le radici sono a: " + toString(radiciX), x, y + 30);
        g1.drawString("I minimi sono a: " + toString(min), x, y + 45);
        g1.drawString("I massimi sono a: " + toString(max), x, y + 60);
        g1.drawString("I flessi sono a: " + toString(flesso), x, y + 75);
    }
    public static void plot(Graphics2D g1, String f, String string, int x, int y) {
        g1.drawString("La funzione è: " + f, x, y);
        g1.drawString("Le radici sono: " + string, x, y + 30);
    }
    public static String toString(double[] vett) {
        String s = "";
        for (int i = 0; i < vett.length; i++) {
            if (vett[i] != 0) {
                s += "x --> " + String.format("%4.3f", vett[i]) + " | ";
            }
        }
        s += System.lineSeparator();
        return s;
    }
}