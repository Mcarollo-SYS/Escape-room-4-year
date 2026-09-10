## 2. Repository: `EscapeRoom1` (Videogioco Java 2D)

### 📌 About (Descrizione breve box GitHub)
> 2D top-down puzzle game in Java featuring MVC architecture, multithreaded Producer-Consumer pattern, custom tile engine, and collision detection[cite: 4].

---

### 📄 File `README.md`

```markdown
# 🧩 2D Escape Room Game — Java Engine & MVC

Videogioco 2D top-down in stile puzzle/escape sviluppato in Java puro, focalizzato sull'applicazione dei principi di Object-Oriented Programming (OOP), architettura MVC e programmazione concorrente multithread.

---

## ⚙️ Architettura Software & Design Pattern

* **MVC Pattern:** Netta separazione tra dominio dati (`model`), rendering e interfacce grafiche (`view`) e gestione dell'input/eventi (`controller`).
* **Multithreading (Producer-Consumer):** Gestione concorrente e thread-safe dei dati tramite la classe `Buffer` condivisa tra `Produttore` e `Consumatore`.
* **2D Tile Engine:** Motore di rendering basato su mappa a griglia gestito dalla classe `TileManager`[cite: 4].
* **Physics & Collisions:** Motore di rilevamento collisioni bounding-box (`CollisionChecker`) tra l'entità giocatore (`Player`) e la mappa/oggetti (`Entity`, `Tile`).
* **Multi-channel Audio:** Sistema audio dedicato per la gestione separata di musica d'ambiente ed effetti sonori (`Music`, `SoundWalk`).

---

## 🛠️ Tech Stack

* **Linguaggio:** Java[cite: 4]
* **GUI & Graphics:** Java Swing / AWT Graphics, Game Loop a thread singolo per il rendering
* **Concurrency:** Java Threads, synchronized buffers, lock/wait mechanics
* **Diagrammi:** UML Class Diagram (Generato con ObjectAid/UCLS)

---

## 📁 Organizzazione dei Package

* `model`: Entità di gioco (`Player`, `Entity`), buffer di memoria e thread concorrenti (`Produttore`, `Consumatore`, `Buffer`).
* `view`: Componenti grafici (`GamePanel`, `GameWindow`, `UI`), gestione delle `Tile` e oggetti interattivi (`InternalObject`).
* `controller`: Gestori di input da tastiera (`KeyHandler`), mouse (`MouseControl`) e controllo collisioni (`CollisionChecker`).
* `main`: Entry point dell'applicazione (`Main`), game loop ed engine audio (`Music`, `Sound`).
