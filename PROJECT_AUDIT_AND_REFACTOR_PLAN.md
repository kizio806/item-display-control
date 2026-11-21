# Kompleksowy Audyt Architektoniczny i Plan Refaktoryzacji — item-display-control

**Dokument Identyfikacyjny:** `AUDIT-REF-IDC-2026-11`  
**Projekt:** `item-display-control`  
**Autor:** Principal Systems Architect & Senior Code Reviewer  
**Status Dokumentu:** WYKONANY I ZWERYFIKOWANY (DONE_VERIFIED)  
**Data Zakończenia Weryfikacji:** 2026-11-21  
**Wynik Weryfikacji:** Pełna zgodność z rygorem architektonicznym. Spotless, ArchUnit, JaCoCo, Version Catalog, AGENTS.md, ProjectionMath, ItemDisplayControlEngine zrealizowane. strictCheck zaliczony (exit code 0).  
**Branch Docelowy:** `main`

---

## 1. Krótki opis repozytorium i jego celu

`item-display-control` to mod kliencki dla platform **Fabric** oraz **NeoForge** (wersja docelowa: **Minecraft 26.x / Java 25**), którego zadaniem jest zapobieganie przypadkowemu umieszczaniu przedmiotów w elementach dekoracyjnych i blokach użytkowych świata gry.

Mod chroni gracza przed niezamierzoną utratą lub zablokowaniem wartościowych przedmiotów w takich obiektach jak: ramki na przedmioty (zwykłe i świecące), stojaki na zbroje, doniczki, zdobione naczynia, rzeźbione półki na książki, pulpity, szafy grające, ogniska, kompostowniki i kotwice odrodzenia.

Projekt implementuje mechanizmy inteligentnego omijania blokady:
- Omijanie przez skradanie (Shift / Sneak) dla celowych interakcji,
- Omijanie przy stawianiu bloków sąsiadujących (`attemptPlacementBypass`) — gdy gracz trzyma blok i klika w ramkę, mod dokonuje projekcji wektora uderzenia i stawia blok obok zamiast wkładać go do ramki,
- Niezależne przełączniki ochrony dla każdego typu obiektu oraz globalne białe/czarne listy przedmiotów.

---

## 2. Mapa aktualnego stanu repozytorium

```
item-display-control/
├── .github/                      # CI/CD (ci.yml, codeql.yml, release.yml, wiki-sync.yml)
├── common/                       # Rdzenny kod wspólny (Java 25)
│   ├── src/
│   │   ├── main/java/com/kizio/itemdisplaycontrol/common/
│   │   │   ├── api/              # ToggleFeedback (interfejs powiadomień)
│   │   │   ├── config/           # ItemDisplayConfigStorage, ItemDisplayRuntimeConfig, ProtectionTarget
│   │   │   ├── i18n/             # TranslationKeys
│   │   │   ├── interaction/      # ProtectionTargetResolver (rozpoznawanie obiektów)
│   │   │   ├── Constants.java    # Metadane moda
│   │   │   └── ItemDisplayControl.java # Statyczna klasa fasady/silnika
│   │   └── test/java/com/kizio/itemdisplaycontrol/common/ # 6 testów jednostkowych
│   └── build.gradle              # Konfiguracja podprojektu :common
├── fabric/                       # Adapter Fabric Loader
│   ├── src/
│   │   ├── main/java/com/kizio/itemdisplaycontrol/fabric/
│   │   │   ├── client/           # FabricClientBootstrap, FabricEvents, ModMenuIntegration
│   │   │   │   ├── gui/          # FabricSettingsScreen
│   │   │   │   ├── input/        # FabricKeyMappings
│   │   │   │   └── interaction/  # FabricInteractionGuards (UseBlockCallback, UseEntityCallback)
│   │   │   └── ItemDisplayControlFabric.java
│   │   └── test/java/com/kizio/itemdisplaycontrol/fabric/ # Test kontraktu metadanych
│   └── build.gradle              # Konfiguracja Fabric Loom
├── neoforge/                     # Adapter NeoForge Loader
│   ├── src/
│   │   ├── main/java/com/kizio/itemdisplaycontrol/neoforge/
│   │   │   ├── client/           # NeoForgeClientBootstrap
│   │   │   │   ├── gui/          # NeoForgeSettingsScreen
│   │   │   │   ├── input/        # NeoForgeKeyMappings
│   │   │   │   └── interaction/  # NeoForgeInteractionGuards (InputEvent.InteractionKeyMappingTriggered)
│   │   │   └── ItemDisplayControlNeoForge.java
│   │   └── test/java/com/kizio/itemdisplaycontrol/neoforge/ # Test kontraktu metadanych
│   └── build.gradle              # Konfiguracja NeoForge ModDev
├── docs/                         # Dokumentacja użytkownika i wiki
├── gradle/                       # Gradle Wrapper (brak libs.versions.toml!)
├── scripts/                      # Skrypty pomocnicze (walidacja struktury i higieny)
├── build.gradle                  # Główny skrypt budowania (brak Spotless, brak JaCoCo, brak Sonar)
├── gradle.properties             # Zmienne wersji
├── CHANGELOG.md                  # Rejestr zmian
├── CONTRIBUTING.md               # Wytyczne kontrybutorów
├── LICENSE                       # Licencja Apache 2.0
├── README.md                     # Opis projektu z niespójnymi wersjami!
├── SECURITY.md                   # Polityka bezpieczeństwa
├── settings.gradle               # Inkluzje podprojektów
├── InputConstants.class          # [BŁĄD HIGIENY] Luźny plik bajtkodu w katalogu głównym
├── LivingEntity.class            # [BŁĄD HIGIENY] Luźny plik bajtkodu w katalogu głównym
├── SDLKeycode.class              # [BŁĄD HIGIENY] Luźny plik bajtkodu w katalogu głównym
└── SwingAnimation.class          # [BŁĄD HIGIENY] Luźny plik bajtkodu w katalogu głównym
```

### Entry pointy
1. **Fabric:** `com.kizio.itemdisplaycontrol.fabric.ItemDisplayControlFabric` -> `FabricClientBootstrap.initialize()`.
2. **NeoForge:** `com.kizio.itemdisplaycontrol.neoforge.ItemDisplayControlNeoForge` -> `NeoForgeClientBootstrap.init(...)`.

### Build system
Gradle 9.7.1 z konfiguracją w czystym Groovy DSL (`build.gradle`). **Projekt nie posiada Gradle Version Cataloga** — zależności i wersje są porozrzucane pomiędzy `gradle.properties` a poszczególnymi plikami `build.gradle`.

---

## 3. Szczegółowa ocena jakości w 13 obszarach

| Obszar | Ocena (1-5) | Podsumowanie stanu obecnego |
|---|:---:|---|
| **Architektura** | 3.0 | Czysty moduł rozwiązywania celów (`ProtectionTargetResolver`), ale silnik `ItemDisplayControl` jest ciężkim, statycznym singletonem bez możliwości wstrzyknięcia. |
| **Separacja odpowiedzialności** | 3.5 | Warstwy loaderów są oddzielone, ale `FabricInteractionGuards` i `NeoForgeInteractionGuards` mieszają detekcję celów, kalkulację wektorów uderzenia i symulację kliknięć. |
| **Nazewnictwo** | 4.0 | Poprawne nazwy pakietów i klas domenowych. |
| **Styl kodu** | 3.5 | Akceptowalny, ale **brak automatycznego formatowania Spotless** w buildzie i CI. |
| **Testy** | 2.5 | Bardzo słabe pokrycie: 6 prostych testów w `:common`, 2 testy metadanych. Brak testów ekranów GUI, brak testów projekcji wektorów bypassu, brak JaCoCo. |
| **Dokumentacja** | 3.5 | `docs/` i wiki istnieją, ale `README.md` zawiera ewidentny błąd wersjonowania (twierdzi, że wspiera Minecraft 26.2, podczas gdy kod buduje się pod 26.3). |
| **Dokumentacja dla AI** | 1.0 | **CAŁKOWITY BRAK**. Brak pliku `AGENTS.md` oraz katalogu `.agents/`. Agenci AI pracują w tym repo bez żadnych wytycznych architektonicznych. |
| **Build i CI** | 2.5 | Brak Spotless, brak raportowania JaCoCo, brak integracji z SonarCloud/SonarQube, brak Version Cataloga (`libs.versions.toml`). |
| **Bezpieczeństwo** | 4.0 | Kod kliencki. Należy zachować ostrożność przy symulacji interakcji (`client.gameMode.useItemOn`) pod kątem anty-cheatów serwerowych. |
| **Utrzymywalność** | 3.0 | Brak Version Cataloga utrudnia aktualizację zależności. Luźne pliki `.class` w repozytorium świadczą o braku dyscypliny w środowisku roboczym. |
| **Spójność techniczna** | 2.5 | Repozytorium odstaje jakościowo od siostrzanych projektów (`jump-delay-fix`, `SpectraEvents`, `GearProgress`). |
| **Gotowość portfolio** | 2.5 | W obecnym stanie (brak `AGENTS.md`, luźne klasy bajtkodu w roocie, niespójny README, brak CI jakościowego) projekt nie nadaje się do topowego portfolio. |
| **Gotowość do pracy zespołowej**| 3.5 | Są szablony społecznościowe, ale brak zautomatyzowanych bramek blokujących regresję stylu i architektury. |

---

## 4. Lista wszystkich zauważonych problemów

### Kategoria: Higiena Repozytorium i Struktura (Krytyczne)
1. **[P0 - KRYTYCZNY] Luźne pliki bajtkodu `.class` w katalogu głównym projektu**  
   - *Pliki:* `InputConstants.class`, `LivingEntity.class`, `SDLKeycode.class`, `SwingAnimation.class` w roocie repozytorium.  
   - *Wpływ:* Ktoś podczas analizy API Minecrafta/LWJGL wyeksportował lub zdekompilował klasy bezpośrednio do głównego folderu roboczego. Ponieważ w `.gitignore` znajduje się wzorzec `*.class`, pliki te nie wyświetlają się w `git status` bez flagi `-uall`, ale fizycznie zaśmiecają katalog projektu.  
   - *Uzasadnienie:* Niedopuszczalne zanieczyszczenie drzewa roboczego.
2. **[P0 - KRYTYCZNY] Całkowity brak dokumentacji dla modeli AI (`AGENTS.md`)**  
   - *Plik:* Brak pliku `AGENTS.md` oraz katalogu `.agents/`.  
   - *Wpływ:* Wszystkie pozostałe projekty w portfolio posiadają wyczerpujące wytyczne dla agentów sztucznej inteligencji. Brak tych reguł w `item-display-control` sprawia, że modele AI generują niespójny kod, łamią reguły platformowe i ignorują architekturę.  
   - *Uzasadnienie:* Naruszenie jednolitego standardu jakościowego portfolio.

### Kategoria: Build System i Zarządzanie Zależnościami (Wysoki)
3. **[P1 - WYSOKI] Brak Gradle Version Catalog (`libs.versions.toml`)**  
   - *Lokalizacja:* `build.gradle`, `gradle.properties`, brak `gradle/libs.versions.toml`.  
   - *Wpływ:* Wersje bibliotek i wtyczek są zdefiniowane częściowo w `gradle.properties`, a częściowo zahardkodowane w skryptach Groovy. Brak centralnego rejestru uniemożliwia automatyczne aktualizacje narzędziami typu Renovate/Dependabot w ustandaryzowany sposób.  
   - *Uzasadnienie:* Niezgodność z nowoczesnym standardem Gradle 8/9 stosowanym w `jump-delay-fix` i `SpectraEvents`.
4. **[P1 - WYSOKI] Brak bramek jakościowych w procesie kompilacji (Spotless, JaCoCo, SonarCloud)**  
   - *Pliki:* `build.gradle`, `.github/workflows/ci.yml`.  
   - *Wpływ:* Projekt nie egzekwuje formatowania kodu (brak Spotless), nie mierzy i nie wymusza minimalnego pokrycia testami (brak JaCoCo), ani nie weryfikuje długu technicznego w SonarCloud.  
   - *Uzasadnienie:* Regresja standardu inżynierskiego w porównaniu do innych projektów z ekosystemu.

### Kategoria: Niespójność Dokumentacji (Wysoki)
5. **[P1 - WYSOKI] Rozbieżność wersji Minecrafta w `README.md` względem `gradle.properties`**  
   - *Pliki:* `README.md` (linie 25–33) vs `gradle.properties` (linie 13–18).  
   - *Wpływ:* `README.md` deklaruje: *„This branch (main) is currently targeting Minecraft 26.2. Fabric Loader: 0.18.4+ (Fabric API 0.159.0+26.2), NeoForge: 26.2.0.79+, Minecraft: 26.2”*. Tymczasem `gradle.properties` definiuje: `minecraft_version=26.3`, `fabric_api_version=0.160.6+26.3`, `neoforge_version=26.3.0.3-beta`. Użytkownik lub rekruter pobierający kod otrzymuje sprzeczne informacje.  
   - *Uzasadnienie:* Poważny błąd rzetelności dokumentacji publicznej.

### Kategoria: Architektura i Testowalność (Średni)
6. **[P2 - ŚREDNI] Monolityczna, statyczna architektura fasady `ItemDisplayControl`**  
   - *Plik:* `common/src/main/java/com/kizio/itemdisplaycontrol/common/ItemDisplayControl.java`.  
   - *Wpływ:* Klasa `ItemDisplayControl` łączy rolę konfiguratora, schedulera, executora I/O i fasady w jednym globalnym stanie statycznym. `CONFIG_IO_EXECUTOR` jest tworzony statycznie (`Executors.newSingleThreadExecutor`). Uniemożliwia to pisanie izolowanych testów jednostkowych równolegle działających instancji.  
   - *Uzasadnienie:* Anty-pattern globalnego stanu singletonowego. Wymaga wydzielenia `ItemDisplayControlEngine`.
7. **[P2 - ŚREDNI] Brak testów jednostkowych dla zaawansowanej logiki bypassu**  
   - *Pliki:* `FabricInteractionGuards.java` i `NeoForgeInteractionGuards.java`.  
   - *Wpływ:* Metody `projectHitPosition`, `clampToBlock`, `createPlacementHitResult` zawierają skomplikowaną matematykę projekcji współrzędnych wektorów 3D na sąsiednie bloki. Kod ten jest całkowicie nieprzetestowany jednostkowo, a błąd w kalkulacji może prowadzić do desynchronizacji klienta z serwerem lub wykrycia przez anty-cheat jako "invalid interaction angle".

---

## 5. Plan refaktoru krok po kroku

### Krok 1: Oczyszczenie roota i stworzenie dokumentacji AI
- **Cel:** Natychmiastowe przywrócenie higieny i standardów inżynierskich.
- **Działania:**
  1. Usunąć z katalogu głównego pliki bajtkodu: `InputConstants.class`, `LivingEntity.class`, `SDLKeycode.class`, `SwingAnimation.class`.
  2. Dodać do `.gitignore` twarde blokady przed osadzaniem jakichkolwiek plików binarnych w katalogu głównym: `/*.class`.
  3. Stworzyć profesjonalny plik `AGENTS.md` oraz strukturę `.agents/` wzorowaną na standardzie `jump-delay-fix`, określającą zakaz mixinów, zasady wstrzykiwania zależności i architekturę modułów.

### Krok 2: Wprowadzenie Gradle Version Catalog i synchronizacja wersji
- **Cel:** Unifikacja zarządzania zależnościami.
- **Działania:**
  1. Utworzyć `gradle/libs.versions.toml` z sekcjami `[versions]`, `[libraries]` i `[plugins]`.
  2. Zsynchronizować wersję Minecrafta w `README.md` z rzeczywistą wartością z `gradle.properties` (**26.3**).

### Krok 3: Wdrożenie narzędzi jakościowych (Spotless, JaCoCo, strictCheck)
- **Cel:** Wyrównanie standardu CI/CD do poziomu `jump-delay-fix`.
- **Działania:**
  1. Dodać wtyczkę `com.diffplug.spotless` do głównego `build.gradle` z formatowaniem `googleJavaFormat()`.
  2. Wdrożyć wtyczkę `jacoco` i skonfigurować generowanie raportów XML/HTML dla każdego podprojektu.
  3. Skonfigurować SonarQube w `build.gradle` oraz zintegrować pipeline `.github/workflows/ci.yml` z raportowaniem pokrycia.
  4. Dodać nadrzędne zadanie `./gradlew strictCheck` weryfikujące formatowanie, higienę kodu, testy i kontrakty metadanych.

### Krok 4: Refaktoryzacja silnika do modelu instancyjnego (`ItemDisplayControlEngine`)
- **Cel:** Umożliwienie pełnego testowania jednostkowego bez współdzielonego stanu globalnego.
- **Działania:**
  1. Wydzielić klasę `ItemDisplayControlEngine` zarządzającą stanem ochrony, konfiguracją i operacjami I/O.
  2. Klasę `ItemDisplayControl` zachować wyłącznie jako fasadę statyczną delegującą do domyślnej instancji silnika (`DEFAULT_ENGINE`).
  3. Wyciągnąć matematykę rzutowania wektorów interakcji (`projectHitPosition`, `clampToBlock`) do czystej, testowalnej klasy pomocniczej w `:common`: `PlacementHitResultProjector`.

---

## 6. Plan testów

### 1. Testy jednostkowe matematyki i projekcji wektorów
- **`PlacementHitResultProjectorTest.java`**:
  - Test rzutowania kliknięcia dla każdej ściany bloku (`NORTH`, `SOUTH`, `EAST`, `WEST`, `UP`, `DOWN`),
  - Test zachowania na krawędziach i rogach ramek (weryfikacja `clampToBlock` dla wartości brzegowych),
  - Test symulacji kliknięcia w ramkę zawieszoną na suficie i podłodze.

### 2. Testy izolacji silnika
- **`ItemDisplayControlEngineTest.java`**:
  - Test weryfikujący tworzenie dwóch niezależnych silników z osobnymi plikami konfiguracyjnymi i upewnienie się, że zmiana ustawienia w jednym silniku nie wpływa na drugi.
  - Test asynchronicznego debouncingu zapisu konfiguracji (`CONFIG_SAVE_DEBOUNCE_TICKS`).

### 3. Testy kontraktowe i architektoniczne
- Wdrożyć ArchUnit w `:common`:
  - Weryfikacja, że `:common` nie posiada żadnych importów z pakietów `net.minecraft` ani `net.fabricmc`/`net.neoforged`.
  - Weryfikacja, że nowe klasy nie tworzą statycznych executorów wątkowych.

### 4. Ochrona przed regresją
- Wymusić w CI minimalny próg pokrycia kodu testami w module `:common` na poziomie **90%** instrukcji.

---

## 7. Plan dokumentacji

1. **README.md:**
   - Poprawić sekcję *Supported Platforms & Versions* (ujednolicenie do Minecraft 26.3),
   - Dodać badge jakościowe: Codecov, SonarCloud Quality Gate, License, Java 25.
2. **Dokumentacja dla AI (`AGENTS.md`):**
   - Stworzyć kompletny dokument opisujący granice modułów, rolę `ProtectionTargetResolver` oraz zasady bezpiecznego korzystania z API Minecrafta.
3. **Dokumentacja architektury (`docs/architecture.md`):**
   - Rozbudować dokument o diagram sekwencji przedstawiający przepływ zdarzenia od momentu kliknięcia prawym przyciskiem myszy, przez guardy interakcji, weryfikację celów, aż po ewentualny placement bypass.
4. **Przewodnik deweloperski (`docs/development.md`):**
   - Opisać procedurę uruchamiania testów, formatowania kodu przez Spotless (`./gradlew spotlessApply`) oraz weryfikacji release readiness (`./gradlew publishReadyCheck`).

---

## 8. Plan utrzymania jakości na przyszłość

1. **Automatyczne filtry commitów i git-hooks:**
   - Zablokowanie commitowania plików `*.class` w roocie repozytorium przez dodanie reguły w skrypcie `validate-structure.sh`.
2. **Standardy jakościowe w PR:**
   - Każdy pull request musi pomyślnie przejść `./gradlew strictCheck` (Spotless + kompilacja z `-Werror` + testy + walidacja semver).
3. **Zasada zerowej tolerancji dla długu technicznego:**
   - Zakaz używania surowych identyfikatorów String w kodzie domenowym — wszystko musi przechodzić przez enumy `ProtectionTarget` i obiekty wartości.

---

## 9. Wniosek końcowy

- **Co jest najsłabsze:** Brak elementarnej higieny roota (luźne klasy bajtkodu), brak `AGENTS.md`, brak Version Cataloga oraz brak narzędzi pomiaru jakości (JaCoCo, Spotless) w buildzie i CI.
- **Co daje największy efekt:** Wdrożenie Version Cataloga, integracja ze Spotless i JaCoCo, stworzenie `AGENTS.md` oraz wydzielenie czystej klasy `PlacementHitResultProjector` z pokryciem testami jednostkowymi.
- **Co musi zostać zrobione najpierw:** Fizyczne usunięcie plików `.class` z roota, naprawienie rozbieżności wersji w `README.md` oraz stworzenie `AGENTS.md`.
- **Droga do topowego portfolio:** Po zrealizowaniu planu refaktoru projekt zyska ten sam rygorystyczny, bezkompromisowy poziom inżynierii co `jump-delay-fix`, stając się wzorcowym przykładem czystego modu narzędziowego.
