package Game.GUI;

import javax.swing.JFrame;

/**
 * Létrehoz egy JFrame-t, amihez később hozzáadja a JatekPanel-t is, ami megjeleníti a játékot.
 */
public class JatekFrame extends JFrame {

    public JatekFrame() {
        this.add(new JatekPanel());

        this.setTitle("JSlender");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


}
