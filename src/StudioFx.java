import java.awt.*;
import java.util.Vector;

public class StudioFx extends PianoCartesiano{
    //** ATTRIBUTI **//
    //* GRAFICA *//
    private static final long serialVersionUID = 1L;
    Graphics2D g1;
    private double minY = Double.MAX_VALUE;
    private double maxY = Double.MIN_VALUE;

    //* PARAMETRI ANALISI FUNZIONE *//
    private double Piniziale;
    private double Pfinale;
    private double Intervallo;

    //* PARAMETRI FUNZIONE *//
    private double a;
    private double b;
    private double c;
    private double d;
    private Grado gradoFx;
    private double exp = 1;
    private int nCoords;

    //* MATRICI DATI FINALI *//
    private double datiXY[][];
    private double root[][];
    private double minMax[][];
    private double flex[][];


    //* COTRUTTORE *//
    public StudioFx(double[] value, double[] parametri) throws Exception{
        if (parametri.length == 3){
            this.Piniziale = parametri[0];
            this.Pfinale = parametri[1];
            this.Intervallo = parametri[2];
        }else { throw new IllegalArgumentException("Formato parametri non corretto"); }

        switch (value.length){
            case 2: {
                this.a = value[0];
                this.b = value[1];
                this.gradoFx = Grado.PRIMO;
            }
            case 3: {
                this.a = value[0];
                this.b = value[1];
                this.c = value[2];
                this.gradoFx = Grado.SECONDO;
            }
            case 4: {
                this.a = value[0];
                this.b = value[1];
                this.c = value[2];
                this.d = value[3];
                this.gradoFx = Grado.TERZO;
            }
            default: { throw new Exception(); }
        }
    }

    //* FUNZIONI MATEMATICHE *//
    //Funzione f di X
    private double f(double x){
        switch (gradoFx){
            case PRIMO: { return a * Math.pow(x, exp) + b; }
            case SECONDO: { return a * Math.pow(x, exp+1) + b * x + c; }
            case TERZO: { return  a * Math.pow(x, exp+2) + b * Math.pow(x, exp+1) + c * x + d; }
            default: throw new ArithmeticException();
        }
    }
    //Derivata Prima di f
    private double F(double x){
        switch (gradoFx){
            case PRIMO: { return 1 * a * Math.pow(x, 1);}
            case SECONDO: { return 2 * a * Math.pow(x, 2-1) + b; }
            case TERZO: { return 3 * a * Math.pow(x, 3-1) + 2 * b * Math.pow(x, 2-1) + c;}
            default: throw new ArithmeticException();
        }
    }
    //Derivata Seconda di f
    private double F2(double x){
        switch (gradoFx){
            case PRIMO: { return a;}
            case SECONDO: { return 2 * a * x; }
            case TERZO: { return 3 * a * x + b;}
            default: throw new ArithmeticException();
        }
    }

    //* COSTRUTTORE FUNZIONE *//
    public void scanFx() throws Exception {
        nCoords = (int)Math.floor(Math.abs(Piniziale - Pfinale)/ Intervallo)+1;
        datiXY = new double[nCoords][2];
        double x= Piniziale;
        double y = 0;

        int i=0;

        //NullPointerException
        try{
            while (x <= Pfinale){
                y = f(x);

                datiXY[i][0]=x;
                datiXY[i][1]=y;

                if (y > maxY) { maxY = y; }
                if (y < minY) { minY = y; }

                x+= Intervallo;
                i++;
            }
        }catch (NullPointerException ex){
            System.err.println(ex.getMessage());
        }

        if(Piniziale >0){
            setxOrigine(0);
        }
        else{
            setxOrigine(Piniziale);
            setyOrigine(Math.abs((maxY-minY)) / 2.0);}
        if ((Pfinale - Piniziale) > (maxY-minY)){
            setMaxY(Pfinale - Piniziale);}
        else{
            setMaxY(maxY - minY);}

        Root();
        MinMax();
        Flex();
    }


    //* SEGNO FUNZIONE *//
    private static int sign(double x) {
        if (x < 0.0){
            return -1;
        }else if (x > 0.0){
            return 1;
        }else{ return 0;}
    }
    //* ZERI DELLA FUNZIONE *//
    private void Root(){
        //vettore dinamico
        Vector<double[]> root = new Vector();

        double x = Piniziale;
        double ox = x;
        double y = f(x);
        double oy = y;
        int s = sign(y);
        int os = s;

        //try-catch
        try{
            for (; x <= Pfinale ; x += Intervallo) {
                s = sign(y = f(x));
                if (s == 0) {
                    System.out.println(x);
                } else if (s != os) {
                    double dx = x - ox;
                    double dy = y - oy;
                    double cx = x - dx * (y / dy);
                    // [x, y]
                    root.addElement(new double[]{
                            Double.parseDouble(String.format("%.2f", cx).replace(",",".")),
                            Double.parseDouble(String.format("%.2f", f(cx)).replace(",","."))
                    });
                }
                ox = x; oy = y; os = s;
            }
        }catch (NullPointerException ex){
            System.err.println(ex.getMessage());
        }

        this.root = root.toArray(new double[root.size()][2]);
    }
    //* MINIMI E MASIMI DELLA FUNZIONE *//
    private void MinMax() throws NullPointerException {
        //vettore dinamico
        Vector<double[]> minMax = new Vector();

        double x = Piniziale;
        double ox = x;
        double y = f(x);
        double oy = y;
        int s = sign(y);
        int os = s;

        try{
            //FOR senza indice, si usa la x come indice inizializzata sopra
            for (; x <= Pfinale ; x += Intervallo) {
                s = sign(y = F(x));
                if (s == 0) {
                    System.out.println(x);
                } else if (s != os) {
                    double dx = x - ox;
                    double dy = y - oy;
                    double cx = x - dx * (y / dy);
                    // [x, y]
                    minMax.addElement(new double[]{
                        //parseDouble si usa per convertirlo in un numero double in stringa con 2 decimali dopo virgola e rinconvertirlo in double
                            Double.parseDouble(String.format("%.2f", cx).replace(",",".")),
                            Double.parseDouble(String.format("%.2f", f(cx)).replace(",","."))
                    });
                }
                ox = x; oy = y; os = s;
            }
        }catch (NullPointerException ex){
            System.err.println(ex.getMessage());
        }

        this.minMax = minMax.toArray(new double[minMax.size()][2]);
    }
    //calcolo derivata
    private double derivata(double x){
        return -3*Math.pow(x, 2) + 12*x - 11;
    }
    //calcolo radici
/*     private double[] trovoRadici(){
        double x2=0;
        double x1=a;
        int ind=0;
        for (int i = 0; i < 3; i++) {
            if(i!=0){
                x1+=0.8;
            }
            do
            {
                x2=x1;
                x1= x1-f(x1)/f(x1);
            }
            while (Math.abs(x1-x2) >= 0.001);
            datiXY[ind]=Math.round(x1);
            ind++;
        }
        return datiXY;
    } */
    
    //* FLESSI DELLA FUNZIONE *//
    private void Flex() throws NullPointerException {
        //Vettore dinamico
        Vector<double[]> flex = new Vector();

        double x = Piniziale;
        double ox = x;
        double y = f(x);
        double oy = y;
        int s = sign(y);
        int os = s;

        try{
            for (; x <= Pfinale ; x += Intervallo) {
                s = sign(y = F2(x));
                if (s == 0) {
                    System.out.println(x);
                } else if (s != os) {
                    double dx = x - ox;
                    double dy = y - oy;
                    double cx = x - dx * (y / dy);
                    // [x, y]
                    //parseDouble si usa per convertirlo in un numero double in stringa con 2 decimali dopo virgola e rinconvertirlo in double
                    flex.addElement(new double[]{
                            Double.parseDouble(String.format("%.2f", cx).replace(",",".")),
                            Double.parseDouble(String.format("%.2f", f(cx)).replace(",","."))
                    });
                }
                ox = x; oy = y; os = s;
            }
        }catch (NullPointerException ex){
            System.err.println(ex.getMessage());
        }

        this.flex = flex.toArray(new double[flex.size()][2]);
    }

    //* OUT DATI *//
    public double[][] getFlex() { return flex; }
    public double[][] getRoot() { return root; }
    public double[][] getMinMax() { return minMax; }


    //** PARTE DI GRAFICA **//
    private void assi(){
        super.assi(g1);
    }
    
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g1=(Graphics2D)g;
        super.assi(g1);
        super.plotFx(datiXY,g1, Color.BLUE);

        try{
            super.plotPoint(root, Color.RED, "root");
            super.plotPoint(minMax, Color.ORANGE, "mM");
            super.plotPoint(flex, Color.MAGENTA, "flex");
        }catch (NullPointerException ex){
            System.out.println("Alcune matrici sono vuote -> " + ex.getMessage());
        }
    }
    private void plotFx(){
        super.assi(g1);
        super.plotFx(datiXY,g1, Color.BLUE);

        try{
            super.plotPoint(root, Color.RED, "root");
            super.plotPoint(minMax, Color.ORANGE, "mM");
            super.plotPoint(flex, Color.PINK, "flex");
        }catch (NullPointerException ex){
            System.out.println("Alcune matrici sono vuote -> " + ex.getMessage());
        }
    }
}
