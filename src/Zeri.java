import java.util.Arrays;

public class Zeri {
    //parametri
    private Zero[] zeri;

    //Costruttori
    public Zeri(){}

    public Zeri(Zero zeri[]) {
        this.zeri = Arrays.copyOf(zeri, zeri.length);   //shallow copy
        /* for (int i = 0; i < zeri.length; i++) {      //Deep Copy
            add(zeri[i].getX(), zeri[i].getTipo());
        } */
    }

    //Metodo Add
    public void add(double x, String tipo){
        // aumentare la dim di zeri di un elemento alla volta
        if (zeri == null) {

            zeri = new Zero[1];

        }else

            zeri = Arrays.copyOf(zeri, zeri.length+1);
            zeri[zeri.length-1] = new Zero(x, tipo);    //new zero effettua la deep copy
    }

    //Metodo toString
    public String toString(){
        String s="";
        for (Zero zero : zeri) {

            s+=zero.toString() + "\n";
        
        }
        return s;
    }
}  