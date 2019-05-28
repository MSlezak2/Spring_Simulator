import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;



public class SpringApp extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
                                        //^implementacja potrzebnych interfejsów
    private double szer, wys;
    private SimEngine SE;
    private SimTask ST;
    private Timer T;
    private Image buf;
    private Graphics bufG;
    private boolean mouseDragging;          //ZMIENNA KONTROLUJĄCA PRZECIĄGANIE MYSZY

    private JTextField poleMasa, poleK, poleC, poleGx, poleGy, poleL0;
    private JButton ustawianie;             //ELEMENTY GRAFICZNEGO INTERFEJSU



    public SpringApp(double w, double s) {
        super("Symulacja oscylatora");
        SE = new SimEngine(1000, 800, 1, 20.0, 0.0, -7.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1);
        ST = new SimTask(SE, this, 0.001);
        T = new Timer();
        T.scheduleAtFixedRate(ST, 0, (long) (ST.getKrok() * 1000));

        szer = s;
        wys = w;
        setSize((int) szer, (int) wys);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        init();
        System.out.println("Jestem za initem");
    }

    public void paint(Graphics g) {
        buf = createImage((int) szer-500, (int) wys);
        bufG = buf.getGraphics();
        paintComponent(bufG);
        g.drawImage(buf, 0, 0,(int)szer-500,(int)wys, null);           //MOŻE DODAĆ width i heigth?

    }

    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, (int) szer-500, (int) wys);
        bufG.drawLine(-1 * ((int) SE.r0.x) + 300, -1 * ((int) SE.r0.y) + 150, -7 * ((int) SE.rMasy.x) + 300, -7 * ((int) SE.rMasy.y) + 150);
        g.setColor(Color.RED);
        bufG.fillOval(-7 * ((int) SE.rMasy.x) + 285, -7 * ((int) SE.rMasy.y) + 135, 30, 30);
        g.setColor(Color.BLACK);
        g.drawString("Masa                        ", (int)szer-700, 100);
        g.drawString("Wsp. sprężystości           ", (int)szer-700, 160);
        g.drawString("Wsp. tłumienia              ", (int)szer-700, 220);
        g.drawString("Pocz. dł. sprężyny          ", (int)szer-700, 280);
        g.drawString("Przysp. grawitacyjne - wsp.x", (int)szer-700, 360);
        g.drawString("Przysp. grawitacyjne - wsp.y", (int)szer-700, 420);

    }

    //PRZECIĄŻONE METODY NIEZBĘDNE DO IMPLEMENTACJI POTRZEBNYCH INTERFEJSÓW:
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();          //ODCZYTANIE POŁOŻENIA KURSORA
        int y = e.getY();
        double r = Math.sqrt((Math.pow(-7 * (SE.rMasy.x) + 300-Math.abs(x),2))+Math.pow(-7 * ((int) SE.rMasy.y) + 150-Math.abs(y),2));
        if (r < 15) {           //SPRAWDZENIE CZY KURSOR ZNAJDUJE SIĘ WEWNĄTRZ "MASY"
            try {
                this.wait();    //ZRESETOWANIE SYMULACJI
            } catch (InterruptedException e1) {
                //e1.printStackTrace();
            } catch (IllegalMonitorStateException e2){

            }
            SE.reset();
            mouseDragging=true;
        }
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(mouseDragging){          //SPRAWDZENIE CZY NASTĄPIŁO PRZECIĄGANIE KURSORA

            mouseDragging=false;
            try {
                this.notify();
            } catch (IllegalMonitorStateException e3){

            }

            e.consume();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(mouseDragging){        //SPRAWDZENIE CZY NASTĘPUJE PRZECIĄGANIE MYSZY
            int x=e.getX();
            int y=e.getY();
            SE.rMasy.x=(x-300)/(-7);    //USTAWIENIE POZYCJI MASY
            SE.rMasy.y=(y-150)/(-7);
            repaint();
        }
        e.consume();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void init(){
        System.out.println("Jestem w inicie");
        mouseDragging=false;
        addMouseListener(this);
        addMouseMotionListener(this);

        JPanel jp=new JPanel();
        getContentPane().add(jp);

        //INICJOWANIE ELEMENTÓW GRAFICZNEGO INTERFEJSU
        ustawianie = new JButton("Ustawianie/Reset");
        ustawianie.setSize(250, 45);
        ustawianie.setLocation((int)szer-450 ,0 );
        ustawianie.addActionListener(this);

        poleMasa = new JTextField(""+SE.masa);
        poleMasa.setSize(120,50 );
        poleMasa.setLocation((int)szer-500,50 );

        poleK = new JTextField(""+SE.k);
        poleK.setSize(120,50 );
        poleK.setLocation((int)szer-500,110 );

        poleC = new JTextField(""+SE.C);
        poleC.setSize(120,50 );
        poleC.setLocation((int)szer-500,170);

        poleL0 = new JTextField(""+SE.l0);
        poleL0.setSize(120,50 );
        poleL0.setLocation((int)szer-500,230 );

        poleGx = new JTextField(""+SE.g.x);
        poleGx.setSize(120,50 );
        poleGx.setLocation((int)szer-500,290 );

        poleGy = new JTextField(""+SE.g.y);
        poleGy.setSize(120,50 );
        poleGy.setLocation((int)szer-500,350 );



        jp.add(ustawianie);
        jp.add(poleMasa);
        jp.add(poleK);
        jp.add(poleC);
        jp.add(poleL0);
        jp.add(poleGx);
        jp.add(poleGy);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();

        if(source==ustawianie){             //SPRAWDZANIE CZY PRZYCISK ZOSTAŁ WCIŚNIĘTY
            try {
                this.wait();                //JEŚLI TAK - ZRESETOWANIE SYMULACJI I ZMIANA PARAMETRÓW
            } catch (InterruptedException e1) {
                //e1.printStackTrace();
            } catch (IllegalMonitorStateException e2){

            }
            SE.reset();

            String myString = poleMasa.getText();
            SE.masa= Integer.parseInt(myString);

            myString = poleK.getText();
            SE.k= Double.parseDouble(myString);

            myString = poleC.getText();
            SE.C= Double.parseDouble(myString);

            myString = poleGx.getText();
            SE.g.x= Double.parseDouble(myString);

            myString = poleL0.getText();
            SE.l0= Double.parseDouble(myString);

            repaint();
        }
    }

    public static void main(String[] args) {

        SpringApp sApp = new SpringApp(728, 1366);

    }

}