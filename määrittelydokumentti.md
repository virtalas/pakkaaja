# Määrittelydokumentti

## Aihe

Aiheena on tekstitiedoston pakkaus käyttäen pääasiassa Huffmanin koodausta. Mahdollisia ominaisuuksia ovat myös pakkaus käyttäen Lempel-ziv-algoritmia ja pakkaus käyttäen molempia algoritmeja. Kaikissa tapauksissa myös pakatun tiedoston purkaminen kuuluu ohjelmaan.

## Algoritmit ja tietorakenteet

Huffmanin koodausta varten tiedoston merkit luetaan hajautustaulun avulla frekvenssilistaan, joka järjestetään esimerkiksi quicksort-algoritmillä merkkien yleisyyden mukaan. Listasta muodostetaan Huffman-puu, joka toteutetaan binääripuulla. Lempel-ziv-algoritmin sanakirjaa ja toimintaa varten tarvitaan ainakin listoja.

## Syöte ja ohjelman toiminta

Ohjelmalle annetaan syötteenä lähde- ja kohdetiedostojen nimet, sekä tieto mitä pakkausalgoritmia halutaan käyttää, ja onko kyseessä pakkaus vai purkaminen.

Pakatessa ohjelma lukee tiedoston ja muodostaa frekvenssilistan merkeistä. Listasta muodostetaan Huffman-puu, joka kirjoitetaan pakatun tiedoston alkuun. Tiedoston sisältö pakataan kohdetiedostoon puun perusteella.

Purkaminen tapahtuu lukemalla Huffman-puu tiedoston alusta ja muuttamalla tiedoston koodit puun perusteella oikeiksi merkeiksi, jotka kirjoitetaan kohdetiedostoon.

Mahdollisesti molempia algoritmeja käytettäessä ajetaan ensin Lempel-ziv-algoritmi, jonka tulos pakataan Huffmanin koodauksella. Kohdetiedostoon tallennetaan lisäksi sanakirja ja Huffman-puu. Kohdetiedosto voi indikoida käytetyn pakkausalgoritmin esimerkiksi tiedostopäätteellä.

## Tavoitteelliset aika- ja tilavaativuudet

Huffmanin koodaus, pakkaaminen:

1. Lähdetiedoston lukeminen ja frekvenssilistan muodostus käyttäen hajautustaulua O(n)
2. Listan järjestäminen quicksort-algoritmilla O(n log n)
3. Huffman-puun muodostaminen O(n log n)
4. Kohdetiedoston kirjoitus O(n)

Huffmanin koodaus, purkaminen:

1. Lähdetiedoston lukeminen O(n)
2. Huffman-puun lukeminen O(log n) jokaista luettua koodia kohden eli O(n log n)
3. Kohdetiedoston kirjoitus O(n)

Sekä pakkauksen että purkamisen tavoitteellinen aikavaativuus voisi siten olla O(n log n). Jos oletamme merkistön koon vakioksi, aikavaativuus voisi olla O(n). Tilavaativuus koostuu Huffman-puun koosta O(k) ja syötteen koosta O(n).

Vaikka n on syötteen pituus, kuitenkin tiedoston lukemista/kirjoittamista lukuunottamatta se on samaa kokoluokkaa aakkoston kanssa eli vakio käsiteltäessä esimerkiksi englanninkielistä tekstiä.

Pakatun tiedoston tavoitteellinen koko on 40-60% alkuperäisestä.

## Lähteet

https://en.wikipedia.org/wiki/Huffman_coding
https://en.wikipedia.org/wiki/Lempel–Ziv–Welch
