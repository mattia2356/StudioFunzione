public class Zero {
    private double x;
    private String tipo;    //"radice","min","max","flesso"

    public Zero(){}

    public Zero(double x, String tipo) {
        this.x = x;
        this.tipo = tipo;
    }

    //getter & setter
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public String toString() {
        return String.format("%8s x:%9.3f ", tipo, x);
        /* String s ="";
        s ="X: "+x+"| Tipo: "+tipo;
        return s; */
    }
}
