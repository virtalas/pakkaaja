Jatkoin Lempel-Ziv-Welch-algoritmin toteutusta ja sain sen toimimaan sekä pakkaus- että purkusuunnassa.
Koodia tähän kului huomattavasti vähemmän kuin Huffmanin koodaukseen mutta lopputulos on hieman sekavampi ja
vaatii ehkä vielä vähän refaktorointia. Vaikka koodia kului vähemmän niin aikaa algoritmin ymmärtämiseen ja toteuttamiseen
tuntui kuluvan enemmänkin. Lempel-Ziv-Welch lisäsi myös hajautustaulun uudeksi toteutettavaksi tietorakenteeksi.

Aiemmin NetBeans ei tunnistanut projektia gradle-projektina, mutta sain ongelman nyt ratkaistua. Ratkaisuksi
päätyi lopulta gradle init -komennon uudelleenajaminen. Ehkä ongelmana oli netbeans-projekti ja erilainen projektin rakenne.
Lisäksi siistin projektin repoa ja poistin kaiken tarpeettoman.

Aloitin suorituskykytestit, jotka testaavat algoritmejä toistaiseksi yhdellä englanninkielisellä kirjalla. Helpotin
komentoriviargumenttien antamista ohjelmalle gradlea käyttäessä. Lisäksi päivitin dokumentteja. Lopuksi kirjoitin
vertaispalautteen. Aikaa meni 11h.
