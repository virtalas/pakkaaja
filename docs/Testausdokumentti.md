# Testausdokumentti

## Testatut asiat

Luokkien metodien toimintaa on testattu JUnit-yksikkötesteillä.

Suorituskykytestit testaavat pakkausalgoritmien nopeutta ja pakkaustehokkuutta
erilaisilla syötteillä.

## Testisyötteet

Testisyötteet löytyvät kansiosta "test/resources/".

WizardOfOz.txt on keskikokoinen, englanninkielinen kirja tekstitiedostona.

Dracula.txt on keskikokoinen englanninkielinen kirja, jonka koko on
kolminkertainen Wizard of Oz -tiedostoon verrattuna.

jpn.txt on japaninkielinen kirja, jonka koko vastaa Draculaa. Tiedoston koodaus on UTF-8,
ja yksittäiset merkit vievät kuusi tavua yhden tavun sijaan. Pakkausalgoritmit
lukevat kuitenkin syötettä tavu kerrallaan, joten tällä on jonkin verran merkitystä
pakkaustehokkuudessa. Merkkien toistuessa toistuvat samat kuusi tavua, kun taas
englanninkielisessä tekstissä merkin toistuessa toistuu yksi sama tavu.

10.txt on suuri tekstitiedosto, joka koostuu englanninkielisistä näytelmistä ja kirjoista, ja on kooltaan 10Mt.

Testisyötteet ladattiin sivulta http://www.gutenberg.org.

## Testien ajaminen

Yksikkötestit voi ajaa projektin juuresta komennolla

    gradle test jacocoTestReport

Samalla generoidaan jacoco testikattavuusraportti jota voi tarkastella "testCoverage/index.html".

Suorituskykytestit voi ajaa komennolla

    gradle run -Parguments="['test']"

Testit kertovat kymmenen ajokerran keskiarvot. Ajokertojen määrän voi antaa toisena parametrinä

    gradle run -Parguments="['test', '100']"

## Tulokset

Tulokset ovat tietokonekohtaisia.
Niiden laskemisessa on käytetty metodia `System.currentTimeMillis()` ja tuloksiin vaikutti
vähintään tietokoneella sillä hetkellä käynnissä olleet muut prosessit.
Jokainen pakkaus ja purku suoritettiin 100 kertaa peräkkäin
ja otettiin tulokseksi yhden tapauksen keskimääräinen aika. Kuitenkin ajokertojen välillä
oli joskus suuriakin eroja, joten tulokset eivät ole erityisen tarkkoja.
Kaikki taulukoissa esitetyt tulokset on otettu samalta
suorituskykytestien ajokerralta. Pakkaustehokkuus ilmaisee pakatun tiedoston koon verrattuna alkuperäiseen, eli
(pakatun tiedoston koko / alkuperäinen koko).

Gzip:n pakkaus ja purku suoritettiin käsin perusasetuksilla (compression level 6), ja otettiin keskiarvo kymmenestä kerrasta.
Suoritusaika mitattiin time-komennolla ja valittiin real-aika, joka vastannee eniten javaohjelman suoritusajan mittausta.

### Pakkaus

Algoritmi | Syöte | Pakkaamaton koko (tavuissa) | Aika (ms) | Pakkaustehokkuus
--- | --- | --- | --- | ---
Huffman | Wizard of Oz | 232776   | 26   | 57%
Huffman | Dracula      | 698328   | 53   | 56%
Huffman | 雲形紋章      | 698328   | 56   | 60%
Huffman | 10Mt tekstiä | 10000275 |  785 | 58%
LZW     | Wizard of Oz | 232776   | 139   | 48%
LZW     | Dracula      | 698328   | 343  | 50%
LZW     | 雲形紋章      | 698328   | 332  | 49%
LZW     | 10Mt tekstiä | 10000275 | 4719 | 53%
gzip    | 10Mt tekstiä | 10000275 | 776 | 38%

Huffmanin koodaus suoriutui pakkauksesta huomattavasti nopeammin verrattuna LZW-algoritmiin,
mutta pakkaustehokkuus oli LZW:n tapauksessa parempi. Tämä on luonnollinen
vaihtokauppa laskenta-ajan ja käytetyn tilan välillä.

Syötteen pituuden kasvaessa kolminkertaiseksi pakkausaika kasvoi 2,03-kertaiseksi Huffmanin koodauksella
ja 2,47-kertaiseksi LZW-algoritmillä.
Pakkaustehokkuus pysyi lähes samana.

Huffmanin koodaus ei pakannut japania yhtä hyvin kuin englantia, mutta LZW-algoritmin
tapauksessa pakkaustehokkuus oli samaa luokkaa.

Gzip oli huomattavasti molempia algoritmejä nopeampi ja pakkasi tiedoston tehokkaammin.

Kaavioissa on kuvattu Huffmanin koodauksen ja LZW-algoritmin pakkaustestitulokset.

![Huffman pakkaus](/dokumentaatio/graphs/huffman_pakkaus.png?raw=true)

Huffmanin koodauksen käyrä muistuttaa aikavaativuutta O(n), sillä aakkoston koko on vakio (arvot 0-255)
ja muuten koodaus lukee tiedostoa vaatimuksella O(n).

![LZW pakkaus](/dokumentaatio/graphs/lzw_pakkaus.png?raw=true)

LZW-algoritmin käyrä muistuttaa aikavaativuutta O(n), sillä jokainen tavu luetaan vain kerran ja sanakirjan
muodostus vaatii joka kerta samat operaatiot.

Seuraavissa kaavioissa on kuvattu pakkaustehokkuudet syötteen koon suhteen.

![Huffman teho](/dokumentaatio/graphs/huffman_teho.png?raw=true)

Huffmanin koodaus vaikuttaa pakkaavan suurin piirtein yhtä hyvin riippumatta syötteen koosta.
Pienet vaihtelut johtuvat todennäköisesti syötteiden eroista, sillä ne eroavat mahdollisesti pituuden lisäksi
myös sisällöltään ja käytetyiltä sanoiltaa.

![LZW teho](/dokumentaatio/graphs/lzw_teho.png?raw=true)

Myös LZW:n pakkaustehokkuus on lähes riippumaton syötteen koosta.
Käyrässä on kuitenkin havaittavissa pieni nousu, joka implikoi LZW:n pakkaustehokkuuden huonontuvan syötteen koon kasvaessa.
Yksi selitys tälle voisi olla sanakirjan maksimikoon saavuttaminen, joka on tässä ohjelmassa asetettu olevan 2^12.

### Purkaminen

Algoritmi | Syöte | Pakattu koko (tavuissa) | Aika (ms)
--- | --- | --- | ---
Huffman | Wizard of Oz | 133311   | 18
Huffman | Dracula      | 396939   | 45
Huffman | 雲形紋章      | 424273   | 48
Huffman | 10Mt tekstiä | 10000275 | 655
LZW     | Wizard of Oz | 232776   | 105
LZW     | Dracula      | 396939   | 335
LZW     | 雲形紋章      | 347262   | 319
LZW     | 10Mt tekstiä | 5367975 | 4436
gzip    | 10Mt tekstiä | 3787214 | 130

Purkamisessa Huffman oli nopeampi kuin LZW samoin kuin pakkaamisessa.
Saman algoritmin pakkaus- ja purkuaikoja verratessa ne olivat samat molemmilla virhemarginaalin ollessa melko suuri.

Gzip oli jälleen huomattavasti Huffmania ja LZW:tä nopeampi.

Kaavioissa on kuvattu Huffmanin koodauksen ja LZW-algoritmin purkutestitulokset.

![Huffman purku](/dokumentaatio/graphs/huffman_purku.png?raw=true)

![LZW purku](/dokumentaatio/graphs/lzw_purku.png?raw=true)

Molemmissa tapauksissa käyrät vastaavat pakkaamisen aikavaativuutta O(n), sillä algoritmit toimivat lähes samalla
periaatteella myös purkusuunnassa.

### Johtopäätökset

Syötteen pituuden kasvaessa kolminkertaiseksi purkamisaika kasvoi 2,22-kertaiseksi Huffmanin koodauksella ja 2,85-kertaiseksi LZW-algoritmillä.
Tämä vastaa suurinpiirtein odotettua aikavaativuutta.

Japaninkielinen teksti pakkautui Huffmanin koodauksella samankokoista Draculaa huonommin, johtuen oletettavasti merkkien
monitavuisesta esitysmuodosta. Pakkausta voisi mahdollisesti tehostaa muuttamalla algoritmiä
lukemaan yhden merkin kerrallaan yhden tavun sijaan. Pakkaustehokkuudesta huolimatta
pakkaus- ja purkuajat vastasivat englanninkielistä tekstiä.
LZW-algoritmillä teksti pakkautui huomattavasti paremmin. Koska LZW rakentaa pakatessaan
sanakirjaa tavuyhdistelmistä, kuudenkin tavun merkit päätyvät sanakirjaan, jonka jälkeen
pakkaaminen on jälleen tehokasta.

Gzip oli huomattavasti molempia algoritmejä nopeampi ja pakkasi tiedoston tehokkaammin.
Algoritmejä voisi siis optimoida vielä valtavasti. Gzip käyttää DEFLATE-algoritmiä, joka sekin
käyttää LZ77:n ja Huffmanin koodauksen yhdistelmää.
