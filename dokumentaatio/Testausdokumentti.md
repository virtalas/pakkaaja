# Testausdokumentti

## Testatut asiat

Luokkien metodien toimintaa on testattu JUnit-yksikkötesteillä.

Suorituskykytestit testaavat pakkausalgoritmien nopeutta ja pakkaustehokkuutta
erilaisilla syötteillä.

## Testisyötteet

Testisyötteet löytyvät kansiosta "test/resources/".

WizardOfOz.txt on keskikokoinen, englanninkielinen kirja tekstitiedostona.

Dracula.txt on iso englanninkielinen kirja, jonka koko on
kolminkertainen Wizard of Oz -tiedostoon verrattuna.

jpn.txt on japaninkielinen kirja, jonka koko vastaa Draculaa. Tiedoston koodaus on UTF-8,
ja yksittäiset merkit vievät kuusi tavua yhden tavun sijaan. Pakkausalgoritmit
lukevat kuitenkin syötettä tavu kerrallaan, joten tällä on jonkin verran merkitystä
pakkaustehokkuudessa. Merkkien toistuessa toistuvat samat kuusi tavua, kun taas
englanninkielisessä tekstissä merkin toistuessa toistuu yksi sama tavu.

Testisyötteet ladattiin sivulta http://www.gutenberg.org.

## Testien ajaminen

Yksikkötestit voi ajaa projektin juuresta komennolla

    gradle test jacocoTestReport

Samalla generoidaan jacoco testikattavuusraportti jota voi tarkastella "testCoverage/index.html".

Suorituskykytestit voi ajaa komennolla

    gradle run -Parguments="['test']"

## Tulokset

Tulokset ovat tietokonekohtaisia.
Niiden laskemisessa on käytetty metodia `System.currentTimeMillis()` ja tuloksiin vaikutti
vähintään tietokoneella sillä hetkellä käynnissä olleet muut prosessit.
Jokainen pakkaus ja purku suoritettiin 100 kertaa peräkkäin
ja otettiin tulokseksi yhden tapauksen keskimääräinen aika. Kuitenkin ajokertojen välillä
oli joskus suuriakin eroja, joten tulokset eivät ole erityisen tarkkoja.
Kaikki taulukoissa esitetyt tulokset on otettu samalta
suorituskykytestien ajokerralta. Pakkaustehokkuus ilmaisee tulosta (pakatun tiedoston koko / alkuperäinen koko).

### Pakkaus

Algoritmi | Syöte | Pakkaamaton koko (tavuissa) | Aika (ms) | Pakkaustehokkuus
--- | --- | --- | --- | ---
Huffman | Wizard of Oz | 232776 | 22 | 57%
Huffman | Dracula      | 698328 | 42 | 56%
Huffman | 雲形紋章      | 698328 | 43 | 60%
LZW     | Wizard of Oz | 232776 | 94 | 48%
LZW     | Dracula      | 698328 | 269 | 50%
LZW     | 雲形紋章      | 698328 | 277 | 49%

Huffmanin koodaus suoriutui pakkauksesta huomattavasti nopeammin verrattuna LZW-algoritmiin,
mutta pakkaustehokkuus oli LZW:n tapauksessa parempi. Tämä on luonnollinen
vaihtokauppa laskenta-ajan ja käytetyn tilan välillä.

Syötteen pituuden kasvaessa kolminkertaiseksi pakkausaika kasvoi 2,42-kertaiseksi Huffmanin koodauksella
ja 2,77-kertaiseksi LZW-algoritmillä. Pakkaustehokkuus pysyi suurinpiirtein samana.

Huffmanin koodaus ei pakannut japania yhtä hyvin kuin englantia, mutta LZW-algoritmin
tapauksessa pakkaustehokkuus oli samaa luokkaa.

### Purkaminen

Algoritmi | Syöte | Pakattu koko (tavuissa) | Aika (ms)
--- | --- | --- | ---
Huffman | Wizard of Oz | 133311 | 15
Huffman | Dracula      | 396939 | 36
Huffman | 雲形紋章      | 424273 | 37
LZW     | Wizard of Oz | 232776 | 102
LZW     | Dracula      | 396939 | 313
LZW     | 雲形紋章      | 347262 | 308

Purkamisessa Huffman oli nopeampi kuin LZW samoin kuin pakkaamisessa.
Saman algoritmin pakkaus- ja purkuaikoja verratessa ne olivat samat molemmilla virhemarginaalin ollessa melko suuri.

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
