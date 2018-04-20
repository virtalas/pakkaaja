# Toteutusdokumentti

## Ohjelman yleisrakenne

### main.java.pakkaajaMain

Main-luokassa tulkitaan käyttäjän antamat parametrit ja käynnistetään niiden mukainen pakkaus/purku.

### main.java.huffman

Paketti sisältää kaikki Huffmanin koodaukseen liittyvät luokat.
Tähän kuuluu tietorakenteet `HuffmanLeaf` ja `HuffmanInternalNode` Huffmanin puuta varten, sekä niiden yläluokka `HuffmanTree`.
Algoritmin toteuttavat luokat `HuffmanCoder` ja `HuffmanDecoder`.

### main.java.io

Sisältää tiedoston lukemisen ja kirjoittamisen luokat.
Testauksen helpottamista varten `FileInput` ja `FileOutput` käyttävät `InStream`/`OutStream` rajapinnan toteuttavia luokkia
`InputByteStream` ja `OutputByteStream`. ByteStream-luokissa I/O bufferoidaan `BufferedOutputStream`/`BufferedInputStream`:llä.

### main.java.structures

Sisältää työssä toteutetut tietorakenteet poislukien Huffmanin puu.

### Testipaketit

Testit on jaettu ohjelman pakettien mukaan vastaavalla tavalla ja sisältää JUnit-yksikkötestit.
I/O:ta varten main.java.io:ssa on `MockInStream` ja `MockOutStream` joiden syöte/kirjoitus on helppo asettaa ja tarkistaa.

## Saavutetut aika- ja tilavaativuudet

## Puutteet ja parannusehdotukset

## Lähteet
