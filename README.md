# Testowanie aplikacji JAVA 2017-2018
## Projekt 2 (Maven, JUnit oraz atrapy) 

[![Build Status](https://travis-ci.com/ronek22/JavaMongoTesting.svg?branch=master)](https://travis-ci.com/ronek22/JavaMongoTesting)
[![Maintainability](https://api.codeclimate.com/v1/badges/74c90aa5b44ed1e3e9e7/maintainability)](https://codeclimate.com/github/ronek22/JavaMongoTesting/maintainability)
[![BCH compliance](https://bettercodehub.com/edge/badge/ronek22/JavaMongoTesting?branch=master)](https://bettercodehub.com/)

-----------------------
**Projekt 4** (20 pkt)

Rozważmy grę w kółko i krzyżyk (lub czwórki czy statki) z poprzedniego projektu. Teraz dodajmy do tej gry bazę **MongoDB** z użyciem **Jongo** (patrz przykład wykorzystany w atrapach) Dopiszmy do
niej odpowiednie wymagania:
- Dodaj opcję umożliwiającą zapisanie posunięcia z numerem kolejki, pozycjami na planszy/mapie oraz ewentualnie symbol gracza (w przypadku gier).
- Zapisuj każdy ruch w bazie danych i zapewnij to, że utworzenie nowej sesji spowoduje usunięcie
starszych danych.

Pod ocenę będą brane pod uwagę następujące elementy:
- (0.5 pkt) Kompilacja i uruchomienie bezbłędne projektu + konfiguracja TravisCi.
- (3 pkt) Uwzględnienie powyższych wymagań.
- (5 pkt) Przypadki testowe (uwzględniające wyjątki).
- (4 pkt) Przetestowanie przy użyciu ręcznie stworzonych atrap (co najmniej 8 testów, różnych od pozostałych)
- (3 pkt) Przetestowanie przy użyciu Mockito (co najmniej 8 testów, różnych od pozostałych).
- (3 pkt) Przetestowanie przy użyciu EasyMock (co najmniej 8 testów, różnych od pozostałych).
- (0.5 pkt) Pokrycie kodu (w przypadku ręcznie stworzonych atrap).
- (1 pkt) Styl kodu.

Ponadto, jako punkty dodatkowe będą brane następujące elementy: 
- (1 pkt) Użycie różnych rodzaji atrap.
- (1 pkt) Wynik z portalu BetterCodeHub.
- (2 pkt) Inne technologie dotyczące atrap, nie pokazywane na zajęciach (co najmniej po 5 testów każda z nich).
- (1 pkt) Integracja repozytorium z dowolnym serwisem.
- (1 pkt) Użycie JUnit5.

Ponadto pod ocenę jest brane również: (Brak tych elementów: -1 pkt za podpunkt od obowiązkowej
punktacji zadania!)
- Historia projektu w repozytorium.
- Różnorodne asercje (co najmniej 5 różnych).
- Struktura projektu.

--------------------------------------------

