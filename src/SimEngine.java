public class SimEngine {

    public int masa;
    public double k, C, l0;
    public Vector2D rMasy, vMasy, r0, g;

    //gettery i settery
    public void setMasa(int x){
        if(x>0) masa=x;
        else masa=1;
    }

    public void setK(int x){
        if(x>=0) k=x;
        else k=1;
    }

    public void setC(int x){
        if(x>=0 && x<=1) C=x;
        else C=0;
    }

    public void setl0(int x){
        if(x>0) l0=x;
        else l0=1;
    }

    public void setRMasy(int x, int y){
        rMasy=new Vector2D(x,y);
    }

    public void setVMasy(int x, int y){
        vMasy=new Vector2D(x,y);
    }

    public void setR0(int x, int y){
        r0=new Vector2D(x,y);
    }

    public void setG(int x, int y){
        g=new Vector2D(x,y);
    }

    public int getMasa(int x){
        return masa;
    }

    public double getK(int x){
        return k;
    }

    public double getC(int x){
        return C;
    }

    public double getl0(int x){
        return l0;
    }

    public Vector2D getRMasy(int x, int y){
        return rMasy;
    }

    public Vector2D getVMasy(int x, int y){
        return vMasy;
    }

    public Vector2D getR0(int x, int y){
        return r0;
    }

    public Vector2D getG(int x, int y){
        return g;
    }

    //konstruktor
    public SimEngine(int m1, double k1, double C1, double l1, double rMasyX, double rMasyY, double vMasyX, double vMasyY, double r0x, double r0y, double gX, double gY) {
        if(m1>0) masa=m1;
        else masa=1;

        if(k1>=0) k=k1;
        else k=1;

        if(C1>=0 && C1<=1) C=C1;
        else C=0;

        if(l1>0) l0=l1;
        else l0=1;

        rMasy=new Vector2D(rMasyX,rMasyY);
        vMasy=new Vector2D(vMasyX,vMasyY);
        r0=new Vector2D(r0x,r0y);
        g=new Vector2D(gX,gY);

    }

    public void sim(double krok){

        Vector2D L0=new Vector2D(0,-1);
        L0=L0.powieksz(l0);
        Vector2D r=new Vector2D();
        Vector2D a=new Vector2D();

        r=rMasy.roznica(L0);
        r=r.roznica(r0);

        a=r.powieksz(-1*k);
        a=a.suma(vMasy.powieksz(-1*C));
        a=a.suma(g.powieksz(masa));
        a=a.powieksz(1.0/masa);

        vMasy=vMasy.suma(a.powieksz(krok));

        r=r.suma(vMasy.powieksz(krok));
        r=r.suma(a.powieksz(0.5*krok*krok));

        rMasy=r.suma(L0.suma(r0));
    }

    public void reset(){
        setVMasy(0 , 0);
    }

}
