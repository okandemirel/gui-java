import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import javax.imageio.ImageIO;

public class Board extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	NumberFormat formatter = new DecimalFormat("#0.00");

    public static int deg = 0;
    public static int vel = 50;
    public static double wind = 0.1;
    public static boolean on = false, win = false;
    public static int level = 1;
    public static int score = 0;
    public static int highscore = 0;

    public double dx = 30;
    public double dy = 415;

    int hedgeDistance = 350;
    int alienDistance = 700;

    public static double vx = vel * Math.cos(Math.toRadians(deg));
    public static double vy = vel * Math.sin(Math.toRadians(deg));

    public final double G = 1;

    Random random = new Random();


    public static void setVel(int v) {
        vel = v;
        vx = vel * Math.cos(Math.toRadians(deg));
        vy = vel * Math.sin(Math.toRadians(deg));
    }
    public static void setDeg(int d) {
        deg = d;
        vx = vel * Math.cos(Math.toRadians(deg));
        vy = vel * Math.sin(Math.toRadians(deg));
    }

    void reset() {

        dx = 30;
        dy = 415;

        vx = vel * Math.cos(Math.toRadians(deg));
        vy = vel * Math.sin(Math.toRadians(deg));
    }

    BufferedImage bg =  null;

    BufferedImage pipe = null;
    BufferedImage pipeup = null;
    Rectangle2D.Double pipeB;

    BufferedImage alien = null;
    Rectangle2D.Double alienB;

    BufferedImage bullet = null;
    Rectangle2D.Double bulletB;

    BufferedImage cannon = null;
    BufferedImage cannon2 = null;


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            bg = ImageIO.read(new File("res/bg.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(bg, null, 0, 0);


        try {
            pipe = ImageIO.read(new File("res/Pipe 2.gif"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        try {
            pipeup = ImageIO.read(new File("res/Pipe.gif"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < level+1; i++) {
            g2.drawImage(pipe, null, hedgeDistance, 475 - (i+1)*pipe.getHeight());
        }

        g2.drawImage(pipeup, null, hedgeDistance-4, 475 - (level+1)*pipe.getHeight() - pipeup.getHeight());
        pipeB = new Rectangle2D.Double(hedgeDistance - 4, 475 - (level+1)*pipe.getHeight() - pipeup.getHeight(), pipeup.getWidth(), (level+1)*pipe.getHeight() + pipeup.getHeight());


        try {
            alien = ImageIO.read(new File("res/alien.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(alien, null, alienDistance, 480 - alien.getHeight());
        alienB = new Rectangle2D.Double(alienDistance, 480-alien.getHeight(), alien.getWidth(), alien.getHeight());

        try {
            bullet = ImageIO.read(new File("res/Bullet Bill - Black.gif"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        AffineTransform atb = g2.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(Math.atan(-vy/vx), dx + bullet.getWidth()/2, dy + bullet.getHeight()/2);
        g2.transform(at);
        g2.drawImage(bullet, null, (int)dx, (int)dy);
        g2.setTransform(atb);

        bulletB = new Rectangle2D.Double(dx, dy, bullet.getWidth(), bullet.getHeight());

        try {
            cannon = ImageIO.read(new File("res/Cannon.gif"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage( cannon, null, 30, 475 - cannon.getHeight() );

        try {
            cannon2 = ImageIO.read(new File("res/Cannon 2.gif"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        AffineTransform backup = g2.getTransform();
        AffineTransform trans = new AffineTransform();
        trans.rotate( -Math.toRadians(deg), 30 + cannon.getWidth()/2, 475 - cannon.getHeight() + 8);

        g2.transform( trans );
        g2.drawImage( cannon2, null, 30, 475 - cannon.getHeight() - cannon2.getHeight()/2 );

        g2.setTransform( backup );

        if(bulletB.intersects(pipeB)){
            on = false;

            Control.jsDeg.setEnabled(true);
            Control.jsVel.setEnabled(true);

            if(score > 0) {
                score -= 20;
                Control.jlScore.setText("Score: " + score);
            }

        }

        if(bulletB.intersects(alienB)) {
            on = false;
            win = true;

            Control.jsDeg.setEnabled(false);
            Control.jsVel.setEnabled(false);

        }

        if(win) {
            if(level < 3) {
                level++;
                Control.jlLvl.setText("Level: " + level);;
                hedgeDistance += (random.nextInt(200) - 100);
                alienDistance += (random.nextInt(200) - 100);
                wind += -0.5 + random.nextDouble();
                Control.jlWind.setText("Wind:   " + formatter.format(Math.abs(Board.wind)));
                if(wind < 0) Control.jlWicon.setText("←");
                else Control.jlWicon.setText("→");
                score += level * 100;
                Control.jlScore.setText("Score: " + score);
                if (score > highscore) {
                    highscore = score;
                    Control.jlHighScore.setText("High Score: " + highscore);
                }
                win = false;
            }
            else {
                level = 1;
                Control.jlLvl.setText("Level: " + level);;
                hedgeDistance = 350;
                alienDistance = 700;
                wind = 0.1;
                Control.jlWind.setText("Wind:   " + formatter.format(Math.abs(Board.wind)));
                if(wind < 0) Control.jlWicon.setText("←");
                else Control.jlWicon.setText("→");
                score = 0;
                Control.jlScore.setText("Score: " + score);
                win = false;
            }
        }

    }

    public void actionPerformed(ActionEvent e) {

        Control.jlTimer.setText("Time: " + Control.z(Control.min) + ":" + Control.z(Control.sec));

        if(on && dy <= 443) {
            dx += vx / 5;
            dy -= vy / 5;

            vx -= wind;
            vy -= 0.5 * G;

            Control.jsDeg.setEnabled(false);
            Control.jsVel.setEnabled(false);

            Control.sec ++;
            if(Control.sec == 60) {
                Control.sec %= 60;
                Control.min++;
            }
        }
        else {
            on = false;
            Control.sec = 0;
            Control.min = 0;
            Control.jsDeg.setEnabled(true);
            Control.jsVel.setEnabled(true);

            reset();
        }

        repaint();
    }



}
