# Toteutusdokumentti

## Ohjelman yleisrakenne

### main.java.pakkaajaMain

Main-luokassa tulkitaan käyttäjän antamat parametrit ja käynnistetään niiden mukainen pakkaus/purku.
Paketti sisältää myös apuluokan `MathUtils` ja algoritmien suorituskykytestausluokan `Benchmark`.

### main.java.huffman

Paketti sisältää kaikki Huffmanin koodaukseen liittyvät luokat.
Tähän kuuluu tietorakenteet `HuffmanLeaf` ja `HuffmanInternalNode` Huffmanin puuta varten, sekä niiden yläluokka `HuffmanTree`.
Rakenteessa on oleellista merkin arvon ja sen esiintymismäärän säilyttäminen puurakenteessa.
Algoritmin toteuttavat luokat `HuffmanCoder` ja `HuffmanDecoder`.

### main.java.io

Sisältää tiedoston lukemisen ja kirjoittamisen luokat.
Testauksen helpottamista varten `FileInput` ja `FileOutput` käyttävät `InStream`/`OutStream` rajapinnan toteuttavia luokkia
`InputByteStream` ja `OutputByteStream`. Luokat tarjoavat mahdollisuudet lukea/kirjoittaa myös yksittäisiä bittejä tai muun määrän bittejä kerrallaan.
ByteStream-luokissa I/O bufferoidaan `BufferedOutputStream`/`BufferedInputStream`:llä.

### main.java.structures

Sisältää työssä toteutetut tietorakenteet poislukien Huffmanin puu. `HashDictionary` on hajautustaulu Integer-String-pareille
LZW-algoritmin toteutusta varten. Se käyttää aputietorakenteenaan `LinkedDictionaryEntry`-luokkaa, joka on linkitetty lista.
`MinHeap` toteuttaa Huffmanin koodauksen vaatiman prioriteettijonon minimikekona.

### Testipaketit

Testit on jaettu ohjelman pakettien mukaan vastaavalla tavalla ja sisältää JUnit-yksikkötestit.
I/O:ta varten main.java.io:ssa on `MockInStream` ja `MockOutStream` joiden syöte/kirjoitus on helppo asettaa ja tarkistaa.

## Saavutetut aika- ja tilavaativuudet

### Huffmanin koodaus

Pakkaamisen toteuttava metodi on seuraavanlainen.

```java
public void compress(FileInput in, FileOutput out) {
    generateByteFrequencies(in);             // 1
    // ...
    HuffmanTree root = buildTree();          // 2
    buildCodeList(root, new StringBuffer()); // 3
    // ...
    writeCompressedContent(in, out);         // 4
}
```

Riveittäin algoritmin toiminta on

1. Luetaan läpi tiedosto ja generoidaan frekvenssilista merkeistä. O(n).
2. Rakennetaan Huffman-puu Huffmanin algoritmillä. O(n log n) jossa n on merkistön koko, O(1) merkistön koon ollessa vakio.
3. Rakennetaan lista merkeistä ja niiden huffman-koodeista. Riippuu myös vain merkistön koosta.
4. Kirjoitetaan pakattu tiedosto. O(n).

Kokonaan operaation aikavaativuus on O(n). Tilavaativuus on riippuvainen vain merkistön koosta k.
Jokainen merkki on kahdessa listassa O(k) sekä puussa. Puussa voi olla korkeintaan O(k^2) solmua.

Purkaminen tapahtuu vastaavasti

```java
public void decompress(FileInput in, FileOutput out) {
    HuffmanTree root = readHuffmanTree(in);  // 1
    writeDecompressedContent(in, out, root); // 2
    // ...
}
```

1. Puurakenne luetaan ja luodaan, O(k^2).
2. Purettu tiedosto kirjoitetaan, O(n).

Joten aikavaativuus on O(n) ja tilavaativuus O(k^2).

### Lempel-Ziv-Welch

Pakkaaminen tapahtuu seuraavasti

```java
public void compress(FileInput in, FileOutput out) {
    initDictionary();                          // 1
    compressFileByBuildingDictionary(in, out); // 2
    // ...
}
```

1. Merkistön kaikki merkit alustetaan taulukkoon, O(k).
2. Tiedosto luetaan ja kirjoitetaan rakentamalla samalla sanakirjaa. Jokainen tavu luetaan vain kerran ja sanakirjan muodostuksessa on vakiomäärä operaatioita, O(n).

Aikavaativuus on siten O(n) ja tilavaativuus O(k), sillä sanakirjan suurin mahdollinen koko määritellään ohjelmassa, eli oikeastaan O(1).

Purkaminen tapahtuu vastaavalla tavalla ja vaativuuksilla. Aikavaativuudeksi tulee O(n) ja tilavaativuudeksi sama O(1).

## Puutteet ja parannusehdotukset

Ohjelmassa on paljon parannettavaa. Käyttökokemusta voisi parantaa paremmilla ohjeilla ja toiminnalla.
Ohjelma voisi nimetä pakatun tiedoston käytetyn algoritmin mukaan niin, että käyttäjän ei tarvitsisi tietää millä algoritmillä
tiedosto on pakattu. UTF-8 tukea voisi myös parantaa esimerkiksi käsittelemällä merkkejä tavujen sijaan.
Pakkaustehokkuutta voisi parantaa optimoimalla molempien pakkausalgoritmien käytön samalle tiedostolle.

## Lähteet

TKT20001 Tietorakenteet ja algoritmit, Jyrki Kivinen, syksy 2017

https://en.wikipedia.org/wiki/Huffman_coding

https://en.wikipedia.org/wiki/Lempel–Ziv–Welch

https://www.youtube.com/watch?v=at9tjpxcBh8
