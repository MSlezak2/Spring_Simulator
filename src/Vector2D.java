import java.lang.Math;

public class Vector2D {

    public double x,y;

    public Vector2D() {						//domyślny konstruktor
        x=1;
        y=1;
    }

    public Vector2D(double x1, double y1) {
        x=x1;
        y=y1;
    }

    public Vector2D suma(Vector2D AB){
        Vector2D AC = new Vector2D();
        AC.x=AB.x+x;
        AC.y=AB.y+y;
        return AC;
    }

    public Vector2D roznica(Vector2D AB){
        Vector2D AC = new Vector2D();
        AC.x=x-AB.x;
        AC.y=y-AB.y;
        return AC;
    }

    public Vector2D powieksz(double k){
        Vector2D AC = new Vector2D();
        AC.x=k*x;
        AC.y=k*y;
        return AC;
    }

    public double dlugosc(){
        double r=Math.sqrt(Math.pow(x,2)+Math.pow(y,2));

        return r;
    }

    public Vector2D normalizuj(){
        Vector2D AC = new Vector2D();
        double r=Math.sqrt((Math.pow(x,2))+(Math.pow(y,2)));
        AC.x=x/r;
        AC.y=y/r;
        return AC;
    }

    public void show(){
        System.out.println("Wsp. x: "+x+"\nWsp. y: "+y+"\n");
    }

}

