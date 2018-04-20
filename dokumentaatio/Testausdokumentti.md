# Testausdokumentti

## Testatut asiat

Luokkien metodien toimintaa on testattu JUnit-yksikkötesteillä.

Suorituskykytestit testaavat pakkausalgoritmien nopeutta ja pakkaustehokkuutta.

## Testisyötteet

Testisyötteet löytyvät kansiosta "test/resources/". WizardOfOz.txt on keskikokoinen, englanninkielinen kirja
ASCII-tekstitiedostona.

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

Algoritmi | Syöte | Syötteen koko (tavuissa) | Aika (ms) | Pakkaustehokkuus
--- | --- | --- | --- | ---
Huffman | Wizard of Oz | 232776 | 21 | 57%
LZW | Wizard of Oz | 232776 | 78 | 48%

Huffmanin koodaus suoriutui pakkauksesta huomattavasti nopeammin verrattuna LZW-algoritmiin,
mutta pakkaustehokkuus oli LZW:n tapauksessa parempi. Tämä on luonnollinen
vaihtokauppa laskenta-ajan ja käytetyn tilan välillä.

### Purkaminen

Algoritmi | Syöte | Syötteen koko (tavuissa) | Aika (ms)
--- | --- | --- | ---
Huffman | Wizard of Oz | 232776 | 19
LZW | Wizard of Oz | 232776 | 92

Purkamisessa Huffman oli nopeampi kuin LZW samoin kuin pakkaamisessa.
Saman algoritmin pakkaus- ja purkuaikoja verratessa ne olivat samat Huffmanin koodauksella virhemarginaalin ollessa melko suuri.
Sen sijaan LZW:n tapauksessa purkamiseen menee enemmän aikaa kuin pakkaamiseen.
