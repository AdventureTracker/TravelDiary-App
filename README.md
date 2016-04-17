TravelDiary App
=========

[![Apiary Documentation](https://img.shields.io/badge/Apiary-Documented-blue.svg)](http://docs.traveldiaryapi.apiary.io/)

[Back-end Documentation](https://github.com/MTAA-FIIT/TravelDiary-Api/blob/master/README.md)

Repozitar mobilnej aplikacie pre zadanie z predmetu MTAA na FIIT STU.

## Application description

Aplikacia sluzi na vytvaranie cestovatelskych dennikov, ktore sa naslednej posielaju na server, ktory nazbierane data analyzuje a vykresluje.

### Simple use case

1. Po spusteni aplikacie, sa ktontroluje pripojenie k internetu a robi sa kontrolne volanie na API aby sa overilo jeho funkcnost.
   * Ak je pouzivatel prihlaseny, aplikacia stiane ciselniky a aktualizuje lokalny cache
   * Ak pouzivatel nie je prihlaseny, vypyta sa meno a heslo, vytvori sa volanie na API. V projekte vyuzivame OAuth autorizaciu.

2. Po uspesnom prihlaseni pouzivatel vidi svoje vylety. V pripade ze je online tak sa stiahnu zo servra a aktualizuje sa cache alebo sa nacita z localnej DB.

3. Pouzivatel vytvori novy vylet
   * Vie nastavit zdielanie
   * Datum prichodu a odchodu
   * Hlavna destinacia
   * Popis
   * Nazov vyletu

4. Po vytvoreni vyletu moze pridavat konretne zaznamy:
   * Moze upravit cas a datum, ale je defaultne predvoleny na aktualny cas
   * Automaticky sa zisti lokacia
   * Moze prilozit fotografie
   * Pridava kratny popis

5. Vie vykonavat vsetky CRUD operacie na vyletmi a ich zaznamami

## Technical stuff

Aplikacia je pisana pre platformu Android. Lokalna databaza je realizovana pomocou SQLite databazi (napisali sme vlastne ORM mapovanie nech je sranda). Aplikacia umoznuje komunikovat s REST alebo WebSocket API serverom. Snazili sme sa o absolutnu abstrakciu od pouziteho protokolu. Spravanie pripojenia je preto definovane v dvoch objektoch:
1. com.fiit.traveldiary.app.api.provider.RestProvider - definuje spravanie pre pripojenie pomocou HTTP REST navrhoveho vzoru
2. com.fiit.traveldiary.app.api.provider.WebSocketProvider - definuje spravanie pre pripojenie pomocou WebSocket

ApiProviders patria do jedneho rozhrania a objekty na pracu s API (com.fiit.traveldiary.app.api.*) dokazu pracovat nezavysle na typu spojenia.

Na ukladanie informacii o pouzivatelovi sme pouzili kniznicu [SecurePreferencies](https://github.com/scottyab/secure-preferences) pretoze cryptovanie je cool a mal by to robit kazdy. Z toho dovodu je komunikacia s API vylucne len cez HTTPS.

Este raz pre istotu spomenieme ze dokumentacia k plne funkcnemu API (za predpokladu ze funguje server, trosicku nam to hapruje) je vygenerovana cez Apiary a mozete ju najst tu: [Apiary documentation](http://docs.traveldiaryapi.apiary.io/)

## Project status

Aplikacie je momentalne v stadiu vyvoja a preto nie su spristupneje jej vsetky casti.

 - [X] Objekty na pracu s REST API
 - [] Objekty na pracu s WebSocket
 - [] Datove entity
 - [X] Zadefinovane aktivity
 - [X] Objekty na pracu s lokalnym cache (praca so SQLite)
 - [] Synhronizacia
 - [] Auth

## Credentials

 - [Jakub Dubec](mailto:xdubec@stuba.sk)
 - [Barbora Celesova](mailto:xcelesova@stuba.sk)