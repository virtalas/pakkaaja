Aloitin korjaamalla testikattavuusraporttien generoinnin, joka onnistui määrittelemällä build.gradle:ssa raporteille html-kohdetiedoston.
Jatkoin ohjelmaa toteuttamalla huffman puun tallentamisen pakatun tiedoston alkuun. Toteutin sen nyt niin, että ensin tallennetaan puun rakenne
käyttäen yhden bitin per solmu/lehti. Sen perään tallennetaan tavu kerrallaan lehtien arvot eli merkkien koodit.

Sen jälkeen toteutin pakatun tiedoston purkamisen. Huomasin että pakkaus ja purkaminen toimii yhden sanan mittaisella syötteellä, 
mutta ei pidemmällä syötteellä. Tässä vaiheessa kuitenkin päivitin koodin dokumentaation ja tein puuttuvat testit, jotka
toistaiseksi testaavat vain pienellä syötteellä, joten pidemmän syötteen ongelma ei vielä löytynyt.
