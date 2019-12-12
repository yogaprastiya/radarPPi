package radarppi;

/**
 *
 * @author Yoga Prastiya Wibawa
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Surface extends JPanel implements ActionListener {

    private final int DELAY = 150;
    private Timer timer;
    double deg = 0;
    double deg0 = 0;
    double rad = 0;
    double pi = 3.141592654;

    public Surface() {

        initTimer();
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer() {

        return timer;
    }

    private void PlotTarget(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.BLACK);

        int w = getWidth();
        int h = getHeight();

        deg = deg0;
        rad = deg * pi / 180;
        double I = (Math.random() * 500) / (100);
        int idx = (int) I;

        //line sweeper
        double rad1 = rad - (2.5 * pi / 180);
        double rad2 = rad + (2.5 * pi / 180);
        int lx = (int) (600 * Math.cos(rad1));
        int ly = (int) (600 * Math.sin(rad1));
        int lx1 = (int) (600 * Math.cos(rad2));
        int ly1 = (int) (600 * Math.sin(rad2));

        lx = lx + 300;
        ly = ly + 300;
        lx1 = lx1 + 300;
        ly1 = ly1 + 300;

        g2d.drawLine(lx, ly, 300, 300);
        g2d.drawLine(lx1, ly1, 300, 300);
        
        int y =0;
        int x =0;

        for (int i = 0; i < idx; i++) {

            double r = Math.random() * 400 * (i + 1) / idx % w;

            if (r >= 50) {

                double X = (r * Math.cos(rad - (Math.random() / 50) + (Math.random() / 50)));
                double Y = (r * Math.sin(rad - (Math.random() / 50) + (Math.random() / 50)));

                y = (int) (Y + 300);
                x = (int) (X + 300);

                g2d.fillOval(x, y, 8, 8);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                String X1 = String.valueOf(X);
                String Y1 = String.valueOf(Y);

                String paket;
                //paket = "$" + X1 + "," + Y1 + "," + deg0 + "," + rad+"," + r;
                paket = "$" + X1 + "," + Y1;
                //System.out.println(paket);
            }
            deg0 = deg + 2.5;

            if (deg == 360) {
                deg = 0;
            }

        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void paintComponent(Graphics g) {
        Image gambar = new ImageIcon(getClass().getResource("peta.png")).getImage();

        super.paintComponent(g);
        g.drawImage(gambar, 0, 0, getWidth(), getHeight(), this);
        PlotTarget(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

public class TargetPlotter extends JFrame {

    public TargetPlotter() {

        initUI();
    }

    private void initUI() {

        final Surface surface = new Surface();
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });

        setTitle("Radar Plot Position Indicator");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        System.out.println("radar ON");

        EventQueue.invokeLater(() -> {
            TargetPlotter ex = new TargetPlotter();
            ex.setVisible(true);
        });
    }
}
