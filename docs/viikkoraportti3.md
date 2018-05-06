Aloitin korjaamalla testikattavuusraporttien generoinnin, joka onnistui määrittelemällä build.gradle:ssa raporteille html-kohdetiedoston.
Jatkoin ohjelmaa toteuttamalla huffman puun tallentamisen pakatun tiedoston alkuun. Toteutin sen nyt niin, että ensin tallennetaan puun rakenne
käyttäen yhden bitin per solmu/lehti. Sen perään tallennetaan tavu kerrallaan lehtien arvot eli merkkien koodit.

Sen jälkeen toteutin pakatun tiedoston purkamisen. Huomasin että pakkaus ja purkaminen toimii yhden sanan mittaisella syötteellä, 
mutta ei pidemmällä syötteellä. Tässä vaiheessa kuitenkin päivitin koodin dokumentaation ja tein puuttuvat testit, jotka
toistaiseksi testaavat vain pienellä syötteellä, joten pidemmän syötteen ongelma ei vielä löytynyt.

Löysin ja korjasin ongelman, joka johtui tavuja purkavan rekursiometodin logiikasta. Lisäsin koodin 0 eli ASCII:n NUL-merkin
end of file -merkiksi, jotta tiedostoa purkaessa perään ei tule ekstramerkkejä. Sen voisi myös toteuttaa esimerkiksi lisäämällä viimeisen tavun
loppuun kuinka monta bittiä vielä on oikeaa dataa, mutta tyydyn ainakin toistaiseksi käyttämään yhden merkistön merkin EOF-merkiksi.
Aikaa meni 12h. Seuraavaksi aloitan omien tietorakenteiden toteutusta.
