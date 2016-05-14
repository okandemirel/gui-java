import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        Control jpControl = new Control();
        Board jpBoard = new Board();
        Timer timer = new Timer(20, jpBoard);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(960, 650);
        jf.setVisible(true);
        jf.setLayout(new BorderLayout());

        jf.add(jpControl, BorderLayout.SOUTH);
        jf.add(jpBoard, BorderLayout.CENTER);
        timer.start();

    }
}
