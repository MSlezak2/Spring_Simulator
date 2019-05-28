import java.util.TimerTask;
import java.util.Timer;
import java.util.Calendar;
import java.util.Date;



public class SimTask extends TimerTask {

    private SimEngine SE;
    private SpringApp SA;
    private double krok;  //getter ;)
    int i=0;

    public SimTask(SimEngine se,SpringApp sa, double k){
        SE=se;
        SA=sa;
        if(k>0) krok=k;
        else System.out.println("Błędna wartość kroku czasowego");
    }

    public void setKrok(double k){
        if(k>0) krok=k;
        else System.out.println("Błędna wartość kroku czasowego");
    }

    public double getKrok(){
        return krok;
    }

    public void run(){
        SE.sim(krok);
        SA.repaint();
    }

}
