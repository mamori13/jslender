		JSLENDER
-----------------------------------------

FUTTAT�S:

A j�t�kot lehet futtatni IDE-b�l vagy termin�lb�l.
Futtat�s ut�n a program megk�rdezi, hogy saj�t p�ly�t haszn�lunk-e (igen/nem), ezut�n k�l�n ablakban elindul a j�t�k.

IDE: A main class-t kell futtatni.

Termin�l: El�sz�r be kell �ll�tani az el�r�si k�nyvt�rat az al�bbi parancsok egyik�vel:
	cd "C:\ ... "
	cd /d "D:\ ... "

Teh�t az el�r�si �tvonalat id�z�jelek k�z� kell tenni. Ha nem a C lemezr�l ind�tjuk, akkor a "/d" kapcsol�t is haszn�lnunk kell.
Ezut�n a .jar f�jl futtat�sa az al�bbi paranccsal lehets�ges:
	java -jar jslender.jar

-----------------------------------------

A J�T�KR�L:

A j�t�k c�lja, hogy az erd�ben m�szk�lva �sszegy�jts�k mind a 8 pap�rt, miel�tt Slenderman elkap minket. A pap�rok az k�vetkez� t�rgyakon lehetnek: nagy fa, szikla, aut�, teheraut�, hord�, a h�z fala.

A j�t�kost a nyilakkal lehet �r�ny�tani. A pap�rokat space-szel lehet felvenni, ha a pap�ron vagy ha a pap�r mellett �llunk.
A j�t�kos csak 3 mez�nyi t�vols�gra l�t el. Az enn�l messzebb l�v� pap�rokat vagy a t�volabb l�v� Slendermant nem l�tja.

Slenderman mindig a j�t�kos l�p�se ut�n l�p. A megszerzett pap�rokt�l f�gg�en teleport�l a p�ly�n. Min�l t�bb pap�rt tal�ltunk m�r meg, ann�l k�zelebb j�n.

A j�t�knak v�ge van, ha az �sszes pap�rt felvett�k, vagy ha Slenderman elkapott minket.

-----------------------------------------

SAJ�T P�LYA FELT�LT�SE:

Saj�t p�ly�t a "custom_map.csv" f�jl seg�ts�g�vel tudunk felt�lteni. A f�jl eredetileg az alap p�lya adatait tartalmazza. Egy t�rgyat az al�bbi m�don lehet felt�lteni:
	kisfa 0 4

Teh�t az els� oszlopba a t�rgy nev�t �rjuk, ami lehet: kisfa, nagyfa, szikla, auto, teherauto, hordo, haz. A m�sodik oszlopba az x koordin�ta (0-14 k�z�tti eg�sz sz�m) m�g a harmadik oszlopba az y koordin�ta (szint�n 0-14 k�z�tti sz�m) ker�l.
Az x �s y koordin�t�k a t�rgy "bal fels� sark�nak" elhelyez�s�t �rja le. A t�rgyak m�ret�t lasd a feladatle�r�sban.
A h�z falain nem lehet v�ltoztatni, csak a h�z poz�ci�j�n.

Warning! Egy mez�n csak egy t�rgy lehet �s egy p�ly�n pontosan egy h�zat helyzhet�nk el. Ha ezeket a kit�teleket megszegj�k, akkor a program figyelmeztet �s az alap p�ly�t ind�tja el.