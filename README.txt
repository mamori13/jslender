		JSLENDER
-----------------------------------------

FUTTATÁS:

A játékot lehet futtatni IDE-bõl vagy terminálból.
Futtatás után a program megkérdezi, hogy saját pályát használunk-e (igen/nem), ezután külön ablakban elindul a játék.

IDE: A main class-t kell futtatni.

Terminál: Elõször be kell állítani az elérési könyvtárat az alábbi parancsok egyikével:
	cd "C:\ ... "
	cd /d "D:\ ... "

Tehát az elérési útvonalat idézõjelek közé kell tenni. Ha nem a C lemezrõl indítjuk, akkor a "/d" kapcsolót is használnunk kell.
Ezután a .jar fájl futtatása az alábbi paranccsal lehetséges:
	java -jar jslender.jar

-----------------------------------------

A JÁTÉKRÓL:

A játék célja, hogy az erdõben mászkálva összegyûjtsük mind a 8 papírt, mielõtt Slenderman elkap minket. A papírok az következõ tárgyakon lehetnek: nagy fa, szikla, autó, teherautó, hordó, a ház fala.

A játékost a nyilakkal lehet írányítani. A papírokat space-szel lehet felvenni, ha a papíron vagy ha a papír mellett állunk.
A játékos csak 3 mezõnyi távolságra lát el. Az ennél messzebb lévõ papírokat vagy a távolabb lévõ Slendermant nem látja.

Slenderman mindig a játékos lépése után lép. A megszerzett papíroktól függõen teleportál a pályán. Minél több papírt találtunk már meg, annál közelebb jön.

A játéknak vége van, ha az összes papírt felvettük, vagy ha Slenderman elkapott minket.

-----------------------------------------

SAJÁT PÁLYA FELTÖLTÉSE:

Saját pályát a "custom_map.csv" fájl segítségével tudunk feltölteni. A fájl eredetileg az alap pálya adatait tartalmazza. Egy tárgyat az alábbi módon lehet feltölteni:
	kisfa 0 4

Tehát az elsõ oszlopba a tárgy nevét írjuk, ami lehet: kisfa, nagyfa, szikla, auto, teherauto, hordo, haz. A második oszlopba az x koordináta (0-14 közötti egész szám) míg a harmadik oszlopba az y koordináta (szintén 0-14 közötti szám) kerül.
Az x és y koordináták a tárgy "bal felsõ sarkának" elhelyezését írja le. A tárgyak méretét lasd a feladatleírásban.
A ház falain nem lehet változtatni, csak a ház pozícióján.

Warning! Egy mezõn csak egy tárgy lehet és egy pályán pontosan egy házat helyzhetünk el. Ha ezeket a kitételeket megszegjük, akkor a program figyelmeztet és az alap pályát indítja el.