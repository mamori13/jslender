package Game.GUI;

import Game.Jatek;
import Game.Pozicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Létrehoz egy JPanel-t és megjeleníti a játékhoz szükséges GUI részeket.
 *
 */
public class JatekPanel extends JPanel implements ActionListener {

    public static final int SZELESSEG = 690;
    public static final int MAGASSAG = 690;
    public static final int MEZO_MERET = 46;
    public static final int MEZO_SZAM = SZELESSEG / MEZO_MERET;
    static final int DELAY = 200;
    int tavolsag;

    Jatek jatek = new Jatek();
    Scanner sc = new Scanner(System.in);

    Image hatter;
    boolean running = false;
    Timer timer;
    Random random;

    boolean slenderLephet = false;

    JatekPanel() {
        random = new Random(System.currentTimeMillis());
        this.setPreferredSize(new Dimension(SZELESSEG, MAGASSAG));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        hatter = new ImageIcon("images/fu.png").getImage();

        startJatek();
    }

    /**
     * Új játék kezdése. Elrendezi a tárgyakat és papírokat generál (mindig más mezőre).
     */
    public void startJatek() {
        running = true;
        System.out.println("Saját pályát használsz? (igen/nem)");
        String valasz = sc.nextLine();
        if (valasz.toLowerCase(Locale.ROOT).equals("igen")){
            jatek.sajatPalya();
        }
        jatek.targyakElrendez();
        jatek.papirokElrendez();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Megjeleníti a grafikus elemeket a pályán.<br>
     * A játékos minden lépésével frissül.<br>
     * <p>Slendermant és a papírokat, csak akkor jeleníti meg, ha bizonyos távolságon belül vannak a játékos pozíciójától nézve.</p>
     *
     * @param g Ezt a képet jelenítjük meg.
     */
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        int i;
        g2D.drawImage(hatter, 0, 0, null);

        g2D.drawImage(jatek.haz.getKep(), jatek.haz.getX(), jatek.haz.getY(), null);
        g2D.drawImage(jatek.jatekos.getKep(), jatek.jatekos.getX(), jatek.jatekos.getY(), null);

        for (i = 0; i < jatek.sziklak.size(); i++) {
            g2D.drawImage(jatek.sziklak.get(i).getKep(), jatek.sziklak.get(i).getX(), jatek.sziklak.get(i).getY(), null);
        }
        for (i = 0; i < jatek.autok.size(); i++) {
            g2D.drawImage(jatek.autok.get(i).getKep(), jatek.autok.get(i).getX(), jatek.autok.get(i).getY(), null);
        }
        for (i = 0; i < jatek.teherautok.size(); i++) {
            g2D.drawImage(jatek.teherautok.get(i).getKep(), jatek.teherautok.get(i).getX(), jatek.teherautok.get(i).getY(), null);
        }
        for (i = 0; i < jatek.hordok.size(); i++) {
            g2D.drawImage(jatek.hordok.get(i).getKep(), jatek.hordok.get(i).getX(), jatek.hordok.get(i).getY(), null);
        }

        //A játékba Slenderman az első papír felvételekor érkezik meg
        if (jatek.talaltPapirok > 0) {
            tavolsag = (Math.abs(jatek.jatekos.getX() - jatek.slender.getX()) + Math.abs(jatek.jatekos.getY() - jatek.slender.getY())) / JatekPanel.MEZO_MERET;
            if (tavolsag <= 3) g2D.drawImage(jatek.slender.getKep(), jatek.slender.getX(), jatek.slender.getY(), null);
        }

        for (i = 0; i < jatek.kisfak.size(); i++) {
            g2D.drawImage(jatek.kisfak.get(i).getKep(), jatek.kisfak.get(i).getX(), jatek.kisfak.get(i).getY(), null);
        }
        for (i = 0; i < jatek.nagyfak.size(); i++) {
            g2D.drawImage(jatek.nagyfak.get(i).getKep(), jatek.nagyfak.get(i).getX(), jatek.nagyfak.get(i).getY(), null);
        }

        for (i = 0; i < jatek.papirok.size(); i++) {
            tavolsag = (Math.abs(jatek.jatekos.getX() - jatek.papirok.get(i).getX()) + Math.abs(jatek.jatekos.getY() - jatek.papirok.get(i).getY())) / JatekPanel.MEZO_MERET;
            if (tavolsag <= 2) {
                g2D.drawImage(jatek.papirok.get(i).getKep(), jatek.papirok.get(i).getX(), jatek.papirok.get(i).getY(), null);
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.PLAIN, 20));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Talált papírok: " + jatek.talaltPapirok + "/8", ((SZELESSEG - metrics1.stringWidth("Talált papírok: " + jatek.talaltPapirok + "/8")) / 2) + 1, 20);
        g.drawString("Talált papírok: " + jatek.talaltPapirok + "/8", ((SZELESSEG - metrics1.stringWidth("Talált papírok: " + jatek.talaltPapirok + "/8")) / 2) - 1, 20);
        g.drawString("Talált papírok: " + jatek.talaltPapirok + "/8", ((SZELESSEG - metrics1.stringWidth("Talált papírok: " + jatek.talaltPapirok + "/8")) / 2), 20 + 1);
        g.drawString("Talált papírok: " + jatek.talaltPapirok + "/8", ((SZELESSEG - metrics1.stringWidth("Talált papírok: " + jatek.talaltPapirok + "/8")) / 2), 20 - 1);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.PLAIN, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Talált papírok: " + jatek.talaltPapirok + "/8", ((SZELESSEG - metrics.stringWidth("Talált papírok: " + jatek.talaltPapirok + "/8")) / 2), 20);

        if (!running) jatekVege(g);
    }

    /**
     * Leellenőrzi, hogy a játékos léphet-e egy bizonyos mezőre.
     * @return Igaz, ha léphet és hamis, ha akadályba ütközött.
     */
    public boolean checkCollisions() {
        if (jatek.akadalyok.contains(new Pozicio(jatek.jatekos.getX(), jatek.jatekos.getY()))) {
            return true;
        }
        return false;
    }

    /**
     * Leellenőrzi, hogy a játékos és Slenderman pozíciója megegyezik-e.
     * Ha igen, akkor Slenderman elkapta a játékost és így véget ér a játék.
     */
    public void elkap() {
        if (jatek.jatekos.getX() == jatek.slender.getX() && jatek.jatekos.getY() == jatek.slender.getY()) {
            running = false;
            timer.stop();
        }
    }

    /**
     * Ha a játéknak úgy lett vége, hogy a játékost elkapta Slenderman, akkor a "Game Over" szöveget,
     * egyébként meg "Nyertél!" feliratot jeleníti meg a képernyő közepén.
     * @param g Ezt a képet (jelen esetben szöveget) jeleníti meg.
     */
    public void jatekVege(Graphics g) {
        if (jatek.talaltPapirok < 8) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.BOLD, 80));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Game Over", ((SZELESSEG - metrics1.stringWidth("Game Over")) / 2) + 1, MAGASSAG / 2);
            g.drawString("Game Over", ((SZELESSEG - metrics1.stringWidth("Game Over")) / 2) - 1, MAGASSAG / 2);
            g.drawString("Game Over", (SZELESSEG - metrics1.stringWidth("Game Over")) / 2, (MAGASSAG / 2) + 1);
            g.drawString("Game Over", (SZELESSEG - metrics1.stringWidth("Game Over")) / 2, (MAGASSAG / 2) - 1);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Helvetica", Font.BOLD, 80));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Game Over", (SZELESSEG - metrics2.stringWidth("Game Over")) / 2, MAGASSAG / 2);
        } else if (jatek.talaltPapirok == 8) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Helvetica", Font.BOLD, 79));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Nyertél!", ((SZELESSEG - metrics1.stringWidth("Nyertél!")) / 2) + 2, MAGASSAG / 2);
            g.drawString("Nyertél!", ((SZELESSEG - metrics1.stringWidth("Nyertél!")) / 2) - 2, MAGASSAG / 2);
            g.drawString("Nyertél!", (SZELESSEG - metrics1.stringWidth("Nyertél!")) / 2, (MAGASSAG / 2) + 2);
            g.drawString("Nyertél!", (SZELESSEG - metrics1.stringWidth("Nyertél!")) / 2, (MAGASSAG / 2) - 2);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.BOLD, 79));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Nyertél!", (SZELESSEG - metrics2.stringWidth("Nyertél!")) / 2, MAGASSAG / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /**
     * <p>A billentyű-lenyomásokat érzékeli.</p>
     * <p>A nyilakkal a játékos mozgását lehet irányítani. Minden nyíl-gombnyomásra leellenőrzi, hogy a játékos belefutott-e
     * Slenderman-be. Ha a játékos akadályba ütközik, akkor nem engedi rálépni és így Slender
     * sem teleportál új helyre még.</p>
     * <p>A space billentyűvel lehet felvenni a papírokat. A papírokat akkor lehet elérni, ha a játékos a lap mellett áll, vagy ha
     * magán a papíron áll (nagy fa esetében).</p>
     * <p>Ha a játékos megszerezte mind a 8 papírt, vagy ha a játékos Slenderman-be ütközött,
     * akkor automatikusan meghívja a jatekVege() metódust.</p>
     */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (jatek.jatekos.getX() - MEZO_MERET == jatek.slender.getX() && jatek.jatekos.getY() == jatek.slender.getY()) {
                        jatek.jatekos.setX(jatek.jatekos.getX() - MEZO_MERET);
                        running = false;
                        timer.stop();
                    }
                    if (jatek.jatekos.getX() == 0) slenderLephet = false;
                    jatek.jatekos.setX(jatek.jatekos.getX() - MEZO_MERET);
                    if (checkCollisions() || !running) {
                        jatek.jatekos.setX(jatek.jatekos.getX() + MEZO_MERET);
                        slenderLephet = false;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (jatek.jatekos.getX() + MEZO_MERET == jatek.slender.getX() && jatek.jatekos.getY() == jatek.slender.getY()) {
                        jatek.jatekos.setX(jatek.jatekos.getX() + MEZO_MERET);
                        running = false;
                        timer.stop();
                    }
                    if (jatek.jatekos.getX() == SZELESSEG - MEZO_MERET) slenderLephet = false;
                    jatek.jatekos.setX(jatek.jatekos.getX() + MEZO_MERET);
                    if (checkCollisions() || !running) {
                        jatek.jatekos.setX(jatek.jatekos.getX() - MEZO_MERET);
                        slenderLephet = false;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (jatek.jatekos.getX() == jatek.slender.getX() && jatek.jatekos.getY() - MEZO_MERET == jatek.slender.getY()) {
                        jatek.jatekos.setY(jatek.jatekos.getY() - MEZO_MERET);
                        running = false;
                        timer.stop();
                    }
                    if (jatek.jatekos.getY() == 0) slenderLephet = false;
                    jatek.jatekos.setY(jatek.jatekos.getY() - MEZO_MERET);
                    if (checkCollisions() || !running) {
                        jatek.jatekos.setY(jatek.jatekos.getY() + MEZO_MERET);
                        slenderLephet = false;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (jatek.jatekos.getX() == jatek.slender.getX() && jatek.jatekos.getY() + MEZO_MERET == jatek.slender.getY()) {
                        jatek.jatekos.setY(jatek.jatekos.getY() + MEZO_MERET);
                        running = false;
                        timer.stop();
                    }
                    if (jatek.jatekos.getY() == MAGASSAG - MEZO_MERET) slenderLephet = false;
                    jatek.jatekos.setY(jatek.jatekos.getY() + MEZO_MERET);
                    if (checkCollisions() || !running) {
                        jatek.jatekos.setY(jatek.jatekos.getY() - MEZO_MERET);
                        slenderLephet = false;
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    jatek.papirFelvesz();
                    slenderLephet = false;
                    break;
                default:
                    slenderLephet = false;
            }
            if (jatek.talaltPapirok == 8) running = false;
            if (slenderLephet) jatek.slenderLep();
            repaint();
            elkap();
            slenderLephet = true;
        }
    }
}
