# 🤖 Robots in Java

<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/JavaFX-007396?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" />
</div>

<div align="center">
  <img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">
</div>

> **📚 Academic Project Notice**  
> This project was developed during our **third year** of the Computer Engineering program at the University of Buenos Aires. _This repository **does not** reflect our current programming level or professional skills. It is kept here as an academic and knowledge record._

---

## 🤖 About

**Robots in Java** is a unique adaptation of the classic **Chase/Robots** game, developed for the **Algorithms and Programming 3 (Programming Paradigms)** course at FIUBA. This turn-based strategy game challenges players to escape from pursuing robots across infinite levels of increasing difficulty.

### 🎯 Game Objective
- **🏃 Escape** from robots hunting you down
- **🧠 Strategize** your moves to survive each turn
- **📈 Progress** through infinite levels of increasing difficulty
- **💥 Destroy** robots by making them collide

### 🔧 Technical Features
- **JavaFX GUI** with mouse and keyboard support
- **Polymorphic design** for different robot types and game elements
- **Maven build system** for dependency management
- **Layered architecture** separating logic from presentation
- **Configurable grid** size for custom gameplay

---

## 📋 Game Rules

### 🎮 Player Actions
Each turn, you can:
- **Move** to any adjacent cell (8 directions: horizontal, vertical, diagonal)
- **Teleport randomly** to any cell (risky - might land on robots!)
- **Safe teleport** to chosen cell (limited uses, +1 per level)
- **Stay still** and let robots come to you

### 🤖 Robot Types
- **1x Robots** 🐌 — Move one cell toward player per turn
- **2x Robots** ⚡ — Move two cells toward player per turn

### 💥 Combat Mechanics
- **Robot collision** → Both robots destroyed, cell becomes **🔥 burning**
- **Player touches burning cell** → Game over
- **Robot touches burning cell** → Robot destroyed
- **All robots destroyed** → Advance to next level
- **Robot reaches player** → Game over

---

## 🕹️ Controls

### ⌨️ Keyboard Controls
```
Movement:
Q - ↖️ Up-Left     W - ⬆️ Up      E - ↗️ Up-Right
A - ⬅️ Left       S - 🛑 Stay     D - ➡️ Right  
Z - ↙️ Down-Left  X - ⬇️ Down     C - ↘️ Down-Right

Special Actions:
T - 📏 Resize grid
O - 🎲 Random teleport
P - 🎯 Safe teleport (click desired cell)
```

### 🖱️ Mouse Support
Full mouse interaction available for movement and teleportation.

---

## 🛠️ Setup & Installation

### Prerequisites
- **Java JDK 11+**
- **Maven 3.6+**
- **JavaFX** (included in dependencies)

### Compilation
```bash
mvn compile
```

### Run the Game
```bash
mvn javafx:run
```

### Run Tests
```bash
mvn test
```

---

## 🏗️ Architecture

### 📐 Design Patterns
- **MVC Architecture** — Clear separation between logic and GUI
- **Polymorphism** — Different robot behaviors and cell types
- **Observer Pattern** — Game state updates to UI components

### 📁 Project Structure
```
src/
├── main/java/
│   ├── logic/          # Game logic layer
│   │   ├── robots/     # Robot implementations
│   │   ├── cells/      # Cell types (normal, burning)
│   │   └── game/       # Core game mechanics
│   └── gui/            # JavaFX presentation layer
└── test/java/          # Unit tests
```

---

### 📖 Documentation
- **Class diagrams** and sequence diagrams available in repository
- **Theoretical answers** in accompanying documentation files
- **Design decisions** explained in project documentation

---

## ⚙️ Configuration

### Grid Customization
Modify window resolution before starting:
```java
// In App class
private static final int ALTO_VENTANA = 800; // Adjust as needed
```

### Gameplay Settings
- **Grid size** configurable through in-game interface
- **Robot count** scales with difficulty level
- **Teleport uses** increment each level

---

## 🔧 Development Tools

### Code Formatting
```bash
mvn fmt:format
```

### Build & Package
```bash
mvn package
```

---

<div align="center">
  <img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif">
  
  **Built with ☕ Java & 🎨 JavaFX at Universidad de Buenos Aires**
</div>
