public class Punto {
    private double x;
    private double y;

    //costruttori
    public Punto(){

    }

    public Punto(double x, double y){
        setX(getX());
        setY(getY());
    }

    //setter e getter
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
