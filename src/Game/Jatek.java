package Game;

import Game.Exceptions.*;
import Game.GUI.JatekPanel;
import targyak.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A játék háttérbeli működését írja le.
 */
public class Jatek {

    public List<Pozicio> akadalyok = new ArrayList<>();
    public List<Papir> papirok;
    public int talaltPapirok = 0;

    public Jatekos jatekos = new Jatekos();
    public Slender slender = new Slender(JatekPanel.SZELESSEG, JatekPanel.MAGASSAG);

    public Haz haz = new Haz(8, 3);
    List<Pozicio> hazAjtok = new ArrayList<>();
    int[] falX = new int[25]; //a falmezok x koordinatai
    int[] falY = new int[25]; //a falmezok y koordinatait tarolja

    public List<KisFa> kisfak = new ArrayList<>();
    public List<NagyFa> nagyfak = new ArrayList<>();
    public List<Szikla> sziklak = new ArrayList<>();
    public List<Auto> autok = new ArrayList<>();
    public List<Teherauto> teherautok = new ArrayList<>();
    public List<Hordo> hordok = new ArrayList<>();

    List<Integer> kisfaKoo = new ArrayList<>(); //koordinatakat tarol, felvaltva x es y koordinatakat
    List<Integer> nagyfaKoo = new ArrayList<>();
    List<Integer> sziklaKoo = new ArrayList<>();
    List<Integer> autoKoo = new ArrayList<>();
    List<Integer> teherautoKoo = new ArrayList<>();
    List<Integer> hordoKoo = new ArrayList<>();

    List<Pozicio> lehetPapir = new ArrayList<>();
    int targySzam = 0;  //osszesen ennyi db kulonbozo targy van a palyan (amin papir is lehet)
    Map<Integer, Integer> targyak = new HashMap<>();  //<targy sorszama, lehetseges papirmezok szama>
    Set<Integer> papirHelye = new HashSet<>();
    List<Integer> papirHelyeList;

    //a slenderLep() metodushoz:
    int tavolsag; //Manhattan-tavolsag ket koordinata kozott: |x0-x1| + |y0-y1|
    int slenderX, slenderY;
    int count = 0;
    int szamlalo1 = 0, szamlalo2 = 0, szamlalo3 = 0;
    boolean seged = false;
    int valsz; //valoszinuseg
    int kotTav; //kotelezo tavolsag egyes esetekben

    Random random = new Random(System.currentTimeMillis());
    boolean sajatPalya = false;

    /**
     *<p>A saját pálya létrehozását intézi. Inicálja a "Koo", azaz a koordinátákat tároló listákat,
     * ami szerint majd a tárgyak el lesznek rendezve.</p>
     * <p>Először beolvassa a "custom_map.csv" fájlt és aszerint dolgozik. Ha nem találja ezt a fájlt, hibát dob és
     * az eredeti pálya kerül megjelenítésre. Ha a tárgyak megadása nem megfelelően történt a fájlban,
     * szintén hibát érzékel és az eredeti pályát tölti be.</p>
     */
    public void sajatPalya() {
        BufferedReader br;
        String sor, fileTargyak = "";

        try {
            br = new BufferedReader(new FileReader("custom_map.csv"));
            sor = br.readLine();

            while (sor != null) {
                fileTargyak += sor + "\n";
                sor = br.readLine();
            }
            br.close();
        } catch (IOException io) {
            System.err.println("Hiba történt: " + io.getMessage());
            System.err.println("Az eredeti pálya fog betöltődni.");
        }

        String[] sorok = fileTargyak.split("\n");
        boolean haz = false;

        try {
            for (String s : sorok) {
                String[] targySor = s.split(";");

                if (targySor[0].equals("haz") && haz) {
                    throw new EgyHazException();
                }
                if (targySor[0].equals("kisfa")) {
                    kisfaKoo.add(Integer.parseInt(targySor[1]));
                    kisfaKoo.add(Integer.parseInt(targySor[2]));
                }
                if (targySor[0].equals("nagyfa")) {
                    nagyfaKoo.add(Integer.parseInt(targySor[1]));
                    nagyfaKoo.add(Integer.parseInt(targySor[2]));
                }
                if (targySor[0].equals("szikla")) {
                    sziklaKoo.add(Integer.parseInt(targySor[1]));
                    sziklaKoo.add(Integer.parseInt(targySor[2]));
                }
                if (targySor[0].equals("auto")) {
                    autoKoo.add(Integer.parseInt(targySor[1]));
                    autoKoo.add(Integer.parseInt(targySor[2]));
                }
                if (targySor[0].equals("teherauto")) {
                    teherautoKoo.add(Integer.parseInt(targySor[1]));
                    teherautoKoo.add(Integer.parseInt(targySor[2]));
                }
                if (targySor[0].equals("hordo")) {
                    hordoKoo.add(Integer.parseInt(targySor[1]));
                    hordoKoo.add(Integer.parseInt(targySor[2]));
                }
                if (targySor[0].equals("haz")) {
                    haz = true;
                    int x = 8;
                    int y = 3;
                    int[] segedX = {x, x + 1, x + 2, x + 3, x + 4, x + 5, x, x + 2, x + 5, x, x + 5, x, x + 2, x + 4, x + 5, x, x + 2, x + 5, x + 2, x + 5, x, x + 1, x + 2, x + 3, x + 5};
                    int[] segedY = {y, y, y, y, y, y, y + 1, y + 1, y + 1, y + 2, y + 2, y + 3, y + 3, y + 3, y + 3, y + 4, y + 4, y + 4, y + 5, y + 5, y + 6, y + 6, y + 6, y + 6, y + 6};
                    for (int i = 0; i < falX.length; i++) {
                        falX[i] = segedX[i];
                        falY[i] = segedY[i];
                    }
                    hazAjtok.add(new Pozicio(x, y + 5));
                    hazAjtok.add(new Pozicio(x + 4, y + 6));
                }
            }
        } catch (EgyHazException e) {
            System.err.println("Csak egy ház lehet a pályán!\nAz eredeti pálya fog betöltődni.");
        } catch (NumberFormatException e) {
            System.err.println("Helytelenül adtad meg a koordinátákat!\n" + e.getMessage());
            System.err.println("Az eredeti pálya fog betöltődni.");
        }
        sajatPalya = true;

    }

    /**
     * <p>Egyesével elrendezi a különböző tárgyakat a pályán.</p>
     * <p>Az akadályokat egy másik listában is eltárolja kétféleképpen: Egyszer csak az x és az y koordinátáit tárolja
     * el az aktuális akadálymezőnek. Másik alkalommal meg egy sorszámot is hozzáad (i változó). Egy sorszám csak
     * egy tárgyhoz tartozik. Ez a tárgyak megkülönböztetéséhez szükséges.<br>
     * <p>Ha saját pályát adtunk meg, akkor egy plusz ellenőrzést is csinál. Ha két tárgyat akarunk ugyanarra a mezőre rakni,
     * vagy a ház ajtaja elé akadályt tettünk, akkor hibát dob és az eredeti pálya kerül megjelenítésre.</p>
     * <p>A metódus végén eltároljuk azt is, hogy összesen hány tárgy van a pályán, amin lehet papír.</p>
     */
    public void targyakElrendez() {
        int j, ii, jj, i = 1;

        if (!sajatPalya) {
            kisfaKoo = Arrays.asList(0, 4, 2, 10, 3, 1, 4, 9, 7, 5, 11, 0, 12, 12, 12, 13, 13, 1, 14, 5);
            nagyfaKoo = Arrays.asList(0, 13, 2, 5, 6, 9, 13, 10);
            sziklaKoo = Arrays.asList(0, 1, 0, 7);
            autoKoo = Arrays.asList(5, 0, 2, 12);
            teherautoKoo = Arrays.asList(7, 12);
            hordoKoo = Arrays.asList(5, 3);
            int[] segedX = {8, 9, 10, 11, 12, 13, 8, 10, 13, 8, 13, 8, 10, 12, 13, 8, 10, 13, 10, 13, 8, 9, 10, 11, 13};
            int[] segedY = {3, 3, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 6, 7, 7, 7, 8, 8, 9, 9, 9, 9, 9};
            for (int x = 0; x < falX.length; x++) {
                falX[x] = segedX[x];
                falY[x] = segedY[x];
            }
        }

        for (j = 0; j < kisfaKoo.size(); j += 2) {
            kisfak.add(new KisFa(kisfaKoo.get(j), kisfaKoo.get(j + 1)));
        }
        for (j = 0; j < nagyfaKoo.size(); i++, j += 2) {
            nagyfak.add(new NagyFa(nagyfaKoo.get(j), nagyfaKoo.get(j + 1)));
            for (ii = nagyfaKoo.get(j); ii < nagyfaKoo.get(j) + 2; ii++) {
                for (jj = nagyfaKoo.get(j + 1); jj < nagyfaKoo.get(j + 1) + 2; jj++) {
                    lehetPapir.add(new Pozicio(i, ii, jj));
                }
            }
        }
        for (j = 0; j < sziklaKoo.size(); i++, j += 2) {
            sziklak.add(new Szikla(sziklaKoo.get(j), sziklaKoo.get(j + 1)));
            for (ii = sziklaKoo.get(j); ii < sziklaKoo.get(j) + 3; ii++) {
                for (jj = sziklaKoo.get(j + 1); jj < sziklaKoo.get(j + 1) + 3; jj++) {
                    akadalyok.add(new Pozicio(i, ii, jj));
                    akadalyok.add(new Pozicio(ii, jj));
                }
            }
        }
        for (j = 0; j < autoKoo.size(); i++, j += 2) {
            autok.add(new Auto(autoKoo.get(j), autoKoo.get(j + 1)));
            for (ii = autoKoo.get(j); ii < autoKoo.get(j) + 3; ii++) {
                for (jj = autoKoo.get(j + 1); jj < autoKoo.get(j + 1) + 2; jj++) {
                    akadalyok.add(new Pozicio(i, ii, jj));
                    akadalyok.add(new Pozicio(ii, jj));
                }
            }
        }
        for (j = 0; j < teherautoKoo.size(); i++, j += 2) {
            teherautok.add(new Teherauto(teherautoKoo.get(j), teherautoKoo.get(j + 1)));
            for (ii = teherautoKoo.get(j); ii < teherautoKoo.get(j) + 5; ii++) {
                for (jj = teherautoKoo.get(j + 1); jj < teherautoKoo.get(j + 1) + 3; jj++) {
                    akadalyok.add(new Pozicio(i, ii, jj));
                    akadalyok.add(new Pozicio(ii, jj));
                }
            }
        }
        for (j = 0; j < hordoKoo.size(); i++, j += 2) {
            hordok.add(new Hordo(hordoKoo.get(j), hordoKoo.get(j + 1)));
            for (ii = hordoKoo.get(j); ii < hordoKoo.get(j) + 2; ii++) {
                for (jj = hordoKoo.get(j + 1); jj < hordoKoo.get(j + 1) + 4; jj++) {
                    akadalyok.add(new Pozicio(i, ii, jj));
                    akadalyok.add(new Pozicio(ii, jj));
                }
            }
        }
        i++;
        for (j = 0; j < falX.length; j++) {
            akadalyok.add(new Pozicio(i, falX[j], falY[j]));
            akadalyok.add(new Pozicio(falX[j], falY[j]));
        }

        if (sajatPalya) {
            List<Pozicio> akadalySeged = new ArrayList<>();
            try {
                for (j = 1; j < akadalyok.size(); j += 2) {
                    if (akadalySeged.contains(akadalyok.get(j))) {
                        throw new TobbTargyException();
                    } else akadalySeged.add(akadalyok.get(j));
                    if (akadalySeged.contains(hazAjtok.get(0)) || akadalySeged.contains(hazAjtok.get(1))) {
                        throw new HazElottException();
                    }
                }
            } catch (TobbTargyException | HazElottException e) {
                System.err.println("Több tárgy egymáson vagy a ház ajtaja elé raktál egy akadályt.\nAz eredeti pálya fog betöltődni.");
                kisfak.clear();
                nagyfak.clear();
                autok.clear();
                teherautok.clear();
                sziklak.clear();
                hordok.clear();
                akadalyok.clear();
                sajatPalya = false;
                targyakElrendez();
            }
        }
        targySzam = i;
    }

    /**
     * <p>A papírokat véletlenszerűen elrendezi a tárgyakon.</p>
     * <p>Első körben megvizsgálunk minden akadálymezőt. Ha a vizsgált akadálymezőt mind a 4 irányból akadálymezők
     * veszik körbe, akkor oda nem rakhatunk papírt, hiszen azt a játékos nem tudná elérni. Azt is megvizsgáljuk,
     * hogyha az akadálymező a pálya szélén van, akkor se korlátozza  őt több akadálymező, különben oda sem
     * genrálhatunk papírt. A végén kapunk egy listát, ami a lehetséges papírmezők pozícióit tárolja (lehetPapir list).</p>
     * <p>Ezután egyesével végigmegyünk az előbb generált listán, és a tárgy sorszáma segítségével megszámoljuk,
     * hogy hány darab valid papírmező van az egyes tárgyakon.</p>
     * <p>Majd kiosztjuk mind a 8 papírt a tárgyak között. Hogy egy tárgyon ne legyen több papír, biztosításképp
     * minden papír generálásakor létrehozunk egy újabb listát, ami már nem tartalmazza az olyan sorszámú valid
     * papírmezőket, ahova már raktunk lapot.</p>
     */
    public void papirokElrendez() {
        papirok = new ArrayList<>();

        int db, targy;
        int szamlalo = 1;
        int aktualis = 0;

        for (int i = 0; i < akadalyok.size(); i += 2) {
            Pozicio p1 = new Pozicio(akadalyok.get(i).x - 46, akadalyok.get(i).y);
            Pozicio p2 = new Pozicio(akadalyok.get(i).x + 46, akadalyok.get(i).y);
            Pozicio p3 = new Pozicio(akadalyok.get(i).x, akadalyok.get(i).y - 46);
            Pozicio p4 = new Pozicio(akadalyok.get(i).x, akadalyok.get(i).y + 46);

            if (akadalyok.contains(p1) && akadalyok.contains(p2) && akadalyok.contains(p3) && akadalyok.contains(p4)) {
            } else if (p1.x <= 0 && akadalyok.contains(p3) && akadalyok.contains(p4) && akadalyok.contains(p2)) {
            } else if (p2.x >= JatekPanel.SZELESSEG && akadalyok.contains(p1) && akadalyok.contains(p3) && akadalyok.contains(p4)) {
            } else if (p3.y <= 0 && akadalyok.contains(p1) && akadalyok.contains(p2) && akadalyok.contains(p4)) {
            } else if (p4.y >= JatekPanel.MAGASSAG && akadalyok.contains(p1) && akadalyok.contains(p2) && akadalyok.contains(p3)) {
            } else lehetPapir.add(akadalyok.get(i));
        }

        for (Pozicio value : lehetPapir) {
            targy = value.targySzam;
            if (targyak.containsKey(targy)) {
                targyak.put(targy, targyak.get(targy) + 1);
            } else {
                targyak.put(targy, 1);
                papirHelye.add(targy);
            }
        }

        while (aktualis < 8) {
            papirHelyeList = new ArrayList<>(papirHelye);
            Collections.shuffle(papirHelyeList);
            targy = papirHelyeList.get(0);

            if (targyak.containsKey(targy)) {
                db = random.nextInt(targyak.get(targy) + 1);

                if (db > 0) {
                    for (Pozicio pozicio : lehetPapir) {
                        if (pozicio.targySzam == targy) {
                            if (szamlalo == db) {
                                papirok.add(new Papir(pozicio.x, pozicio.y));
                                papirHelye.remove(targy);
                                aktualis++;
                                szamlalo = 1;
                                break;
                            } else szamlalo++;
                        }
                    }
                }
            }
        }
    }

    /**
     * A játékos ennek a metódusnak a segítségével tudja felvenni azt a papírt, amin épp áll (csak nagyfa esetén) vagy
     * a papírt, ami mellette lévő tárgyon van.
     */
    public void papirFelvesz() {
        Pozicio p1 = new Pozicio(jatekos.getX() - JatekPanel.MEZO_MERET, jatekos.getY());
        Pozicio p2 = new Pozicio(jatekos.getX() + JatekPanel.MEZO_MERET, jatekos.getY());
        Pozicio p3 = new Pozicio(jatekos.getX(), jatekos.getY() - JatekPanel.MEZO_MERET);
        Pozicio p4 = new Pozicio(jatekos.getX(), jatekos.getY() + JatekPanel.MEZO_MERET);

        for (Papir p : papirok) {
            if (p.getX() == p1.x && p.getY() == p1.y || p.getX() == p2.x && p.getY() == p2.y ||
                    p.getX() == p3.x && p.getY() == p3.y || p.getX() == p4.x && p.getY() == p4.y ||
                    p.getX() == jatekos.getX() && p.getY() == jatekos.getY()) {
                talaltPapirok++;
                papirok.remove(p);
                break;
            }
        }
    }

    /**
     * <p>Slenderman teleportálását írja le a feladatban megadottak alapján.<br>
     * Slenderman csak akkor jelenik meg a játékban, ha a játékos felvette az első papírját. Ilyenkor ellenőrzi
     * azt is, hogy hány megtalált papír van és Slender aszerint lép. </p>
     * <p>Ha a játékosnak 1 db papírja van, akkor Slender egy 5 mező sugarú "körön" kívül tartózkodik.
     * A távolságot valójában Manhattan-távolságként értelmezzük. Slenderman minden 5. lépésével egy random mezőre teleportál
     * a pályán, de ilyenkor is figyelünk, hogy ne kaphasssa el a játékost.</p>
     * <p>Ha ennél több papírja van a játékosnak, akkor Slenderman közelebb jön és a meghatározott sugarú "körön"
     * belül lépked. Hogyha távolság=1 3 egymás utáni lépésen keresztül, akkor a következő lépés alkalmával Slenderman
     * a meghatározott valószínűség alapján kapja el a játékost.<br>
     * Minden 5. lépéskor Slenderman egy random mezőre teleportál, de mostmár ilyenkor is elkaphatja a játékost.</p>
     */
    public void slenderLep() {
        if (talaltPapirok > 0) {

            if (talaltPapirok < 2) {
                if (count < 5) {
                    do {
                        slenderX = random.nextInt(JatekPanel.MEZO_SZAM) * JatekPanel.MEZO_MERET;
                        slenderY = random.nextInt(JatekPanel.MEZO_SZAM) * JatekPanel.MEZO_MERET;
                        tavolsag = (Math.abs(jatekos.getX() - slenderX) + Math.abs(jatekos.getY() - slenderY)) / JatekPanel.MEZO_MERET;
                    } while (tavolsag < 5);
                    slender.setX(slenderX);
                    slender.setY(slenderY);
                    count++;
                }
                //"Slenderman a teleportálásokkal akár a játékos pozíciójára is teleportálhat, kivéve, ha kevesebb mint 2 papírja van."
                if (count == 5) {
                    do {
                        slender.teleportX();
                        slender.teleportY();
                        tavolsag = (jatekos.getX() - slender.getX()) + (jatekos.getY() - slender.getY());
                    } while (tavolsag == 0);
                    count = 0;
                }
            } else {
                if (talaltPapirok < 4) {
                    seged = szamlalo1 >= 3;
                    kotTav = 5; // a játékos pozíciójától legfeljebb 5 távolságra teleportál Slenderman.
                    valsz = random.nextInt(3); //valsz lehet: 0,1,2; ha valsz = 1 --> 33%
                } else if (talaltPapirok < 6) {
                    if (tavolsag == 1) szamlalo2++;
                    seged = szamlalo2 >= 3;
                    kotTav = 4; //a játékos pozíciójától legfeljebb 4 távolságra teleportál Slenderman.
                    valsz = random.nextInt(2);  //valsz lehet: 0,1; ha valsz = 1 --> 50%
                } else {
                    if (tavolsag == 1) szamlalo3++;
                    seged = szamlalo3 >= 3;
                    kotTav = 3; //a játékos pozíciójától legfeljebb 3 távolságra teleportál Slenderman.
                    valsz = random.nextInt(3) + 1;  //valsz lehet: 1,2,3; ha valsz = 1 vagy 3 --> 66%
                }

                if (count < 5) {
                    do {
                        slenderX = random.nextInt(JatekPanel.MEZO_SZAM) * JatekPanel.MEZO_MERET;
                        slenderY = random.nextInt(JatekPanel.MEZO_SZAM) * JatekPanel.MEZO_MERET;
                        tavolsag = (Math.abs(jatekos.getX() - slenderX) + Math.abs(jatekos.getY() - slenderY)) / JatekPanel.MEZO_MERET;
                    } while (tavolsag > kotTav);
                    slender.setX(slenderX);
                    slender.setY(slenderY);
                    count++;
                    if (seged) {
                        if (valsz == 1 || valsz == 3) {
                            halalosTeleport();
                        }
                    }
                }
                if (count == 5) {
                    slender.teleportX();
                    slender.teleportY();
                    count = 0;
                }
            }
        }
    }

    /**
     * Slendermant a játékos pozíciójára teleportálja.
     */
    public void halalosTeleport() {
        slender.setX(jatekos.getX());
        slender.setY(jatekos.getY());
    }

}
