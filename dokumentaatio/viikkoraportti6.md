Aloitin korjaamalla koodikatselmoinnissa esille tuodut puutteet ja ideat. Suurin muutos oli Main-luokkaan,
jota olin jo aikonut refaktoroida mutta nyt palautteen myötä päädyin sen vihdoin tekemään.
Siirsin siitä pois huffman-logiikka ja jouduin muuttamaan vähän huffmanin ja i/o:n luokkia.
Luokassa riittää vielä paranneltavaa, mm. pakkauksen onnistumisen/epäonnistumisen kertominen käyttäjälle paremmin.

Refaktoroin ja kommentoin viimeksi toteuttamiani LZW-luokkia. Seuraavaksi implementoin oman hajautustaulurakenteen ja
linkitetyn listan jota se käyttää. Jätin tarkoituksella niistä pois yleisiä toimintoja kuten poista etc, sillä
niitä ei tarvitse LZW:n kontekstissa. Näiden toteutus ja hashmapin korvaaminen aiheutti jonkin verran debuggausta.
Huomasin myös että LZW:n pakkaus- ja purkujat pitenivät kun vaihdoin Javan HashMapin omaani.

Ignoresin Benchmark-luokan testikattavuudesta, koska siellä ei ole mitään varsinaista testattavaa.
Lopuksi tein vertaisarvioinnin. Seuraavaksi parantelen dokumenttejä ja lisään vielä sekä huffmania että lzw:tä
käyttävän pakkaus/purkamisen. Aikaa meni 8h.
