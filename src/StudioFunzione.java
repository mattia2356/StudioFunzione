import java.awt.*;
import java.util.function.*;

public class StudioFunzione extends PianoCartesiano {
    // private double f;
    // private double f1;
    private double a, b, h, x;
    private String fx;
    private static final long serialVersionUID = 1L;
    private Graphics2D g1;
    private int nCoords;
    private double datiXY[][];
    private double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
    private double radici[];
    private int contaradici;
    private Zeri z = new Zeri();
    private Punto [] punto;
/*     private double minimi[];
    private double massimi[]; */
    private int contamin;
    private int contamax;
    private double flessi[];
    private int contaflessi;
    private double epsilon;

    // Le funzioni per la 1°,2°,3° derivata
    Function<Double, Double> f;// = x -> -Math.pow(x, 3) + 6*Math.pow(x, 2) - 11 * x+6;//simbolo della funzione
                               // lambda
    Function<Double, Double> f1;// = x -> -3*Math.pow(x, 2) + 12*x - 11;
    Function<Double, Double> f2;// = x -> -6*x + 12;
    Function<Double, Double> f3;// = x -> -6.0;

    // Vettore unico
    private double[][] vett;

    public StudioFunzione() {
        a = 0;
        b = 0;
        h = 0;
        radici = new double[100];
        punto  = new Punto[100];
        flessi = new double[100];

        vett = new double[100][4];
    }

    public StudioFunzione(double a, double b, double h) {
        this.a = a;
        this.b = b;
        this.h = h;
        nCoords = (int) Math.floor(Math.abs(a - b) / h) + 1;
        radici = new double[100];
        flessi = new double[100];

        vett = new double[100][4];
    }

    public StudioFunzione(String fx, double a, double b, double h, double e, Function<Double, Double> f,
            Function<Double, Double> f1, Function<Double, Double> f2, Function<Double, Double> f3) {
        this.fx = fx;
        setA(a);
        setB(b);
        setH(h);
        this.epsilon = e;
        this.f = f;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;

        nCoords = (int) Math.ceil(Math.abs(a - b) / h) + 1;
        datiXY = new double[nCoords][2];
        radici = new double[100];
        flessi = new double[100];
        minY = Double.MAX_VALUE;
        maxY = Double.MIN_VALUE;

        vett = new double[100][4];
    }

    // metodi set e get
    public void setA(double a) {
        this.a = a;
    }

    public double getA() {
        return a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getB() {
        return b;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getH() {
        return h;
    }

    public Graphics2D getG1() {
        return g1;
    }
    // Calcolo di calcFunzDerivata.
    /*
     * private double calcFunzDerivata(double x) { return -3 * Math.pow(x, 2) + 12 *
     * x - 11; }
     * 
     * private double calcFunzDerivata2(double x) { return -6 * Math.pow(x, 1) + 12;
     * }
     * 
     * private double calcFunzDerivata3(double x) { return -6; }
     */
    // metodi per calcolo delle radici
    /*
     * public double calcRadice(double a, double b) { double x = a; double x1 = 0;
     * parametroLimite = 0.001; do { x1 = x; x = x - f(x) / calcFunzDerivata(x); }
     * while (Math.abs(x - x1) >= parametroLimite); return x; }
     * 
     * public double calcRadiceDerivata2(double a, double b) { double x = a; double
     * x1 = 0; parametroLimite = 0.001; do { x1 = x; x = x - calcFunzDerivata(x) /
     * calcFunzDerivata2(x); } while (Math.abs(x - x1) >= parametroLimite); return
     * x; }
     * 
     * public double calcRadiceDerivata3(double a, double b) { double x = a; double
     * x1 = 0; parametroLimite = 0.001; do { x1 = x; x = x - calcFunzDerivata2(x) /
     * calcFunzDerivata3(x); } while (Math.abs(x - x1) >= parametroLimite); return
     * x; }
     */
    // metodo per calcolo min e max locale tra le radici
    /*
     * public void minMaxLocale() { double min = 0; double max = 0; double temp,
     * temp1; for (double n = radici[0]; n < radici[1]; n += 0.001) { temp = n -
     * 0.001; temp1 = n + 0.001; if (calcFunzDerivata(temp) < 0 &&
     * calcFunzDerivata(temp1) > 0) { min = n; } else if (calcFunzDerivata(temp) > 0
     * && calcFunzDerivata(temp1) < 0) { max = n; } } minLocale = min; maxLocale =
     * max; }
     * 
     * // metodo per la ricerca dei flessi public void cercaFlessi() { // in questo
     * caso 1 int conta = 0; for (double i = a; i < b; i += 0.01) { if
     * (calcFunzDerivata(i - 0.001) < calcFunzDerivata(i) && calcFunzDerivata(i) >
     * calcFunzDerivata(i + 0.001)) { conta++; } } flessi = new double[conta]; int n
     * = 0; for (double i = a; i < b; i += 0.01) { if (calcFunzDerivata(i - 0.001) <
     * calcFunzDerivata(i) && calcFunzDerivata(i) > calcFunzDerivata(i + 0.001)) {
     * flessi[n] = i; n++; } } }
     */

    // metodo per la ricerca radici
    public double ricercaRad(double a, double b, Function<Double, Double> f, Function<Double, Double> f1) {
        double x = a;
        double x1 = 0;

        do {
            x1 = x;
            x = x - f.apply(x) / f1.apply(x);
        } while (Math.abs(x - x1) >= 0.001);
        return x;
    }

    // metodo scansioneFx
    public StudioFunzione scansioneFx() {
        datiXY = new double[nCoords][2];
        double x = a, y = 0;
        int i = 0;
        double yprec = f(a);
        double xprec = a;
        contaradici = 0;
        contamax = 0;
        contamin = 0;
        contaflessi = 0;

        while (x < b) {
            y = f.apply(x);
            // per calcolo radici
            if (y * yprec < 0) {
                z.add(ricercaRad(a, b, f, f1), "Radici");
            // per calcolo del massimo e del minimo
            if (f1.apply(xprec) < 0 && f1.apply(x) > 0) {

                z.add(ricercaRad(a, b, f, f1), "Max:");

            } else if (f1.apply(xprec) > 0 && f1.apply(x) < 0) {

                z.add(ricercaRad(a, b, f, f1), "Min:");

            }
            // per calcolo dei flessi
            if ((f2.apply(xprec) * f2.apply(x)) < 0) {

                z.add(ricercaRad(a, b, f, f1),"Flessi:");
            }

            yprec = y;
            xprec = x;
            datiXY[i][0] = x;
            datiXY[i][1] = y;
            if (y > maxY)
                maxY = y;
            if (y < minY)
                minY = y;
            x += h;
            i++;
        }
        if (a > 0) {

            setxOrigine(0);

        } else {

            setxOrigine(a);

        }
        setyOrigine(Math.abs((maxY - minY)) / 2.0);
        if ((b - a) > (maxY - minY)) {

            setMaxY(b - a);

        } else {

            setMaxY(maxY - minY);

        }
    }
    return this;
}

    // metodo toString
    /*
     * public String toString() { String s = ""; for (int i = 0; i < radici.length;
     * i++) { if (radici[i] != 0) { s += "x --> " + String.format("%4.3f",
     * radici[i]) + " | "; } } s += System.lineSeparator(); for (int i = 0; i <
     * contamax; i++) { if (radici[i] != 0) { s += "I massimi: " +
     * String.format("%4.3f", massimi[i]) + " | "; } } s += System.lineSeparator();
     * for (int i = 0; i < contamin; i++) { if (radici[i] != 0) { s += "I minimi: "
     * + String.format("%4.3f", minimi[i]) + " | "; } } s += System.lineSeparator();
     * for (int i = 0; i < contaflessi; i++) { if (radici[i] != 0) { s += "Flessi: "
     * + String.format("%4.3f", flessi[i]) + " | "; } } s += System.lineSeparator();
     * return s; }
     */
    public String toString() {
        String s = "";
        for (int j = 0; j < contaradici; j++) {
            if (vett[j][0] != 0) {
                s += "x --> " + String.format("%4.3f", vett[j][0]) ;
            }
        }
        s+=" || ";
        for (int j = 0; j < contamax; j++) {
            if (vett[j][1] != 0) {
                s += "I massimi --> " + String.format("%4.3f", vett[j][1]) ;
            }
        }
        s+=" || ";
        for (int j = 0; j < contamin; j++) {
            if (vett[j][2] != 0) {
                s += "I minimi--> " + String.format("%4.3f", vett[j][2]) ;
            }
        }
        s+=" || ";
        for (int j = 0; j < contaflessi; j++) {
            if (vett[j][0] != 0) {
                s += "I flessi --> " + String.format("%4.3f", vett[j][3]);
            }
        }
        s += System.lineSeparator();
        return s;
    }

    public void assi() {
        super.assi(g1);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g1 = (Graphics2D) g;
        Risultati.plot(g1, this.fx, toString(), 120, 60);
        super.assi(g1);
        //errore ambiguo
        super.plotFx(punto, g1);
/*         int x1 = 60;
        int y = 30;
        g1.setFont(new Font("Courier", Font.PLAIN, 12)); 
            for (String line : z.toString().split("\n"))
                g1.drawString(line, x1, y += g1.getFontMetrics().getHeight()); */
 
    }

    /*
     * protected void paintComponent1(Graphics g) { super.paintComponent(g); g1 =
     * (Graphics2D) g; super.assi(g1); super.plotFx(datiXY, g1);
     * 
     * // Stampa delle radici String s = ""; int n = 0; for (int i = 0; i <
     * contaradici; i++) { s = "-La " + i + " (" +
     * String.format(java.util.Locale.US, "%.4f", radici[i]) + " ; 0.0)";
     * g1.drawString(s, 10, 25 + n * 10); n++; } // Stampa del massimo e del minimo
     * locale for (int i = 0; i < contamin; i++) { s = "-Il " + i +
     * " minimo locale: (" + String.format(java.util.Locale.US, "%.4f", minimi[i]) +
     * " ; 0.0)"; g1.drawString(s, 10, 25 + n * 10); n++; } for (int i = 0; i <
     * contamax; i++) { s = "-Il " + i + " massimo locale: (" +
     * String.format(java.util.Locale.US, "%.4f", massimi[i]) + " ; 0.0) ";
     * g1.drawString(s, 10, 25 + n * 10); n++; } // Stampa flessi s = ""; for (int i
     * = 0; i < contaflessi; i++) { s += "-Flessi: (" +
     * String.format(java.util.Locale.US, "%.4f", flessi[i]) + " ; " +
     * String.format(java.util.Locale.US, "%.4f", f(flessi[i])) + ")";
     * g1.drawString(s, 10, 25 + n * 10); }
     * 
     * }
     */
    private double f(double x) {
        // return Math.pow(Math.sin(x),2)+Math.cos(x);
        return -Math.pow(x, 3) + 6 * Math.pow(x, 2) - 11 * x + 6;
    }

    public void plotFx() {
        super.assi(g1);
        super.plotFx(punto, g1);
    }

}