# 🗺️ SE115 Maps – Fastest Route Finder

This is a Java console application that finds the **fastest route between two cities** using **Dijkstra's algorithm**. The project was developed as part of the SE115 course and follows object-oriented design principles.

---

## 📌 Overview

- 🧠 **Dijkstra’s Algorithm** implementation from scratch  
- 🧱 Clean **object-oriented structure**  
- 🛠️ Takes input from a file and outputs the **shortest path** to a file  
- 🧾 **Handles errors** such as missing files and invalid inputs  

---

## 📂 File Structure

- `City.java` – Represents a city
- `CountryMap.java` – Stores city and route data
- `WayFinder.java` – Calculates the shortest route
- `Se115Maps.java` – Main class, handles file I/O and execution

---

## ⚙️ How to Compile and Run

> Make sure Java is installed on your machine.

### 📦 Clone the repository

```bash
git clone https://github.com/episkeys/SE115-Maps.git
cd SE115-Maps
```
### 🛠️ Compile the source files
```bash
javac *.java
```
### ▶️  Run the program from the terminal
```bash
java Se115Maps input.txt output.txt
```
- `input.txt`: Input file containing cities and routes
- `output.txt`: Output file where the shortest path result will be written

---

## 📥 Sample Input (input.txt)
```txt
3         // number of cities
X Y Z     // city names
3         // number of routes
X Y 6     // route from X to Y takes 6 min
X Z 10    // route from X to Z takes 10 min
Y Z 2     // route from Y to Z takes 2 min
X Z       // find fastest route from X to Z

```
## 📥 Sample Output (output.txt)
```txt
Fastest Way: X -> Y -> Z
Total Time: 8 min

```
---
## ⚙️ Implementation Details

- *Java 8+*
- *Command-Line Interface (CLI)*
- *File I/O*
- *Exception Handling*
- *Object-Oriented Programming (OOP)*
- *Dijkstra’s Algorithm*

