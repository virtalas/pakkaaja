# Testausdokumentti

## Testatut asiat

Luokkien metodien toimintaa on testattu JUnit-yksikkötesteillä.

Suorituskykytestit testaavat pakkausalgoritmien nopeutta ja pakkaustehokkuutta.

## Testisyötteet

Testisyötteet löytyvät kansiosta "test/resources/".

WizardOfOz.txt on keskikokoinen, englanninkielinen kirja tekstitiedostona.

Dracula.txt on iso englanninkielinen kirja. Sitä on muokattu niin, että sen koko on täsmälleen
kolminkertainen Wizard of Oz -tiedostoon verrattuna.

Testisyötteet ladattiin sivulta http://www.gutenberg.org.

## Testien ajaminen

Yksikkötestit voi ajaa projektin juuresta komennolla

    gradle test jacocoTestReport

Samalla generoidaan jacoco testikattavuusraportti jota voi tarkastella "testCoverage/index.html".

Suorituskykytestit voi ajaa komennolla

    gradle run -Parguments="['test']"

## Tulokset

Tulokset ovat tietokonekohtaisia.
Niiden laskemisessa on käytetty metodia `System.currentTimeMillis()` ja tuloksiin vaikutti tietokoneella
sillä hetkellä käynnissä olleet muut prosessit. Jokainen pakkaus ja purku suoritettiin 100 kertaa peräkkäin
ja otettiin tulokseksi yhden tapauksen keskimääräinen aika. Kaikki tulokset on otettu samalta
suorituskykytestien ajokerralta. Pakkaustehokkuus ilmaisee tulosta (pakatun tiedoston koko / alkuperäinen koko).

### Pakkaus

Algoritmi | Syöte | Pakkaamaton koko (tavuissa) | Aika (ms) | Pakkaustehokkuus
--- | --- | --- | --- | ---
Huffman | Wizard of Oz | 232776 | 26 | 57%
Huffman | Dracula      | 698328 | 63 | 56%
LZW     | Wizard of Oz | 232776 | 132 | 48%
LZW     | Dracula      | 698328 | 365 | 50%

Huffmanin koodaus suoriutui pakkauksesta huomattavasti nopeammin verrattuna LZW-algoritmiin,
mutta pakkaustehokkuus oli LZW:n tapauksessa parempi. Tämä on luonnollinen
vaihtokauppa laskenta-ajan ja käytetyn tilan välillä.

Syötteen pituuden kasvaessa kolminkertaiseksi pakkausaika kasvoi 2,42-kertaiseksi Huffmanin koodauksella
ja 2,77-kertaiseksi LZW-algoritmillä.

### Purkaminen

Algoritmi | Syöte | Pakattu koko (tavuissa) | Aika (ms)
--- | --- | --- | ---
Huffman | Wizard of Oz | 133311 | 23
Huffman | Dracula      | 396939 | 51
LZW     | Wizard of Oz | 232776 | 129
LZW     | Dracula      | 396939 | 350

Purkamisessa Huffman oli nopeampi kuin LZW samoin kuin pakkaamisessa.
Saman algoritmin pakkaus- ja purkuaikoja verratessa ne olivat samat molemmilla virhemarginaalin ollessa melko suuri.

Syötteen pituuden kasvaessa kolminkertaiseksi purkamisaika kasvoi 2,22-kertaiseksi Huffmanin koodauksella
ja 2,85-kertaiseksi LZW-algoritmillä.
