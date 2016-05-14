import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Control extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int min = 0;
    public static int sec = 0;

    JLabel jlDeg = new JLabel("Angle:    " + Board.deg);
    public static JSlider jsDeg = new JSlider(0, 90, Board.deg);

    JLabel jlVel = new JLabel("Velocity:    " + Board.vel);
    public static JSlider jsVel = new JSlider(1, 100, Board.vel);

    public static JLabel jlWind = new JLabel("Wind:   " + Math.abs(Board.wind));
    public static JLabel jlWicon = new JLabel("←");


    JButton jsStart = new JButton("start");
    JButton jsreStart = new JButton("restart");

    public static JLabel jlTimer = new JLabel("Time:    " + z(min) + ":" + z(sec));
    public static JLabel jlLvl = new JLabel("Level:    " + Board.level);
    public static JLabel jlScore = new JLabel("Score:    " + Board.score);
    public static JLabel jlHighScore = new JLabel("High Score:    " + Board.highscore);



    Control() {

        setLayout(new GridLayout(2, 6));

        jlDeg.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlDeg);
        jlVel.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlVel);
        jlWind.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlWind);
        jlTimer.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlTimer);
        jlLvl.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlLvl);
        jlScore.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlScore);

        add(jsDeg);

        jsDeg.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(!Board.on) Board.setDeg(jsDeg.getValue());
                jlDeg.setText("Angle: " + Board.deg);
            }
        });



        add(jsVel);

        jsVel.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(!Board.on) Board.setVel(jsVel.getValue());
                jlVel.setText("Velocity: " + Board.vel);
            }
        });

        jlWicon.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlWicon);

        add(jsStart);
        jsStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Board.on = true;
            }
        });

        add(jsreStart);
        jsreStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Board.on = false;
            }
        });

        jlHighScore.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlHighScore);




    }

    public static String z(int i) {
        String s;
        if(i < 10) s = "0" + i;
        else s = "" + i;
        return s;
    }

}
