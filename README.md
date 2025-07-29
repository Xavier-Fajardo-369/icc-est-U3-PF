# Proyecto Final: Solver de Laberintos con Algoritmos y MVC

---

## Carátula Institucional

**Universidad:** Universidad Politecnica Saleciana  
**Carrera:** Ingenieria en ciencias de la computacion  
**Materia:** Estructura de Datos / Algoritmos  
**Proyecto:** Solver de Laberintos  
**Integrantes:**
- Integrante 1: [Nombre integrante 1]
- Integrante 2: Adriano Rodas
- Integrante 3: [Nombre integrante 3]
- Integrante 4: [Nombre integrante 4]  
  **Fecha:** [29/07/2025]

---

## Descripción del Problema

Este proyecto implementa un solucionador de laberintos que permite al usuario definir un laberinto mediante una interfaz gráfica y aplicar diferentes algoritmos para encontrar caminos desde un punto de inicio hasta un punto final. La aplicación está diseñada bajo el patrón MVC para mantener la separación clara entre vista, controlador y modelo, y utiliza algoritmos clásicos y avanzados de búsqueda y recorrido para comparar su eficiencia y resultados.

---

## 1. Marco Teórico y Algoritmos Implementados
*Espacio para que el Integrante 2 complete*

Aquí se describen los algoritmos implementados para la resolución del laberinto:

- **MazeSolverRecursivo**: Explicación breve del algoritmo, recorrido en 2 direcciones (derecha y abajo).
- **MazeSolverRecursivoCompleto**: Recorrido en las 4 direcciones, buscando todos los caminos posibles.
- **MazeSolverRecursivoCompletoBT**: Backtracking para encontrar el camino más corto.
- **MazeSolverDFS**: Búsqueda en profundidad usando pila.
- **MazeSolverBFS**: Búsqueda en anchura usando cola, encuentra el camino más corto en grafos no ponderados.

Además, se explica la forma de medir la eficiencia (tiempo de ejecución en nanosegundos) y la longitud del camino encontrado.

---

## 2. Comparación entre Algoritmos
*Espacio para que el Integrante 2 complete*

En esta sección se compara el desempeño de los algoritmos en diferentes laberintos (tamaño, complejidad), incluyendo:

- Tiempo de ejecución
- Longitud del camino encontrado
- Número de pasos o celdas recorridas
- Casos donde un algoritmo es más eficiente o falla

poner tablas o gráficos comparativos.

---

## 3. Código Comentado

Ejemplo comentado de uno de los algoritmos (por ejemplo BFS o Backtracking), explicando las partes importantes del código, la lógica, y las decisiones de implementación.

## 3. Código Comentado

A continuación se presenta el código comentado del algoritmo **BFS (Breadth-First Search)** utilizado para resolver el laberinto. Este algoritmo explora el laberinto nivel por nivel, garantizando encontrar el camino más corto en un grafo no ponderado.

```java
package Solver.solverImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import Models.Cell;
import Solver.MazeSolver;

public class MazeSolverBFS implements MazeSolver {
    private int steps = 0; // Contador para el número de celdas recorridas

    @Override
    public List<Cell> solve(Cell[][] maze, Cell start, Cell end) {
        // Mapa para rastrear de dónde viene cada celda (para reconstruir el camino)
        Map<Cell, Cell> cameFrom = new HashMap<>();

        // Cola para el recorrido BFS
        Queue<Cell> queue = new LinkedList<>();

        // Conjunto para registrar celdas ya visitadas y evitar ciclos
        Set<Cell> visited = new HashSet<>();

        // Lista donde almacenaremos el camino final encontrado
        List<Cell> path = new ArrayList<>();

        // Inicializamos la búsqueda añadiendo la celda de inicio
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            steps++; // Incrementamos el contador al procesar una celda

            // Si llegamos a la celda final, terminamos la búsqueda
            if (current.equals(end)) break;

            // Recorremos los vecinos adyacentes (arriba, abajo, izquierda, derecha)
            for (Cell neighbor : getNeighbors(current, maze)) {
                // Ignoramos celdas no caminables o ya visitadas
                if (!neighbor.isWalkable() || visited.contains(neighbor)) continue;

                // Añadimos la celda vecina a la cola y marcamos como visitada
                queue.offer(neighbor);
                visited.add(neighbor);

                // Guardamos de dónde llegamos a esa celda
                cameFrom.put(neighbor, current);
            }
        }

        // Reconstruimos el camino desde el final hacia el inicio usando el mapa cameFrom
        Cell current = end;
        while (current != null && cameFrom.containsKey(current)) {
            path.add(0, current); // Insertamos al inicio para ordenar el camino
            current = cameFrom.get(current);
        }

        // Si el inicio y fin son la misma celda, agregamos el inicio como camino
        if (start.equals(end)) path.add(start);

        return path; // Retornamos la lista de celdas que forman el camino encontrado
    }

    // Método auxiliar para obtener los vecinos válidos de una celda (arriba, abajo, izquierda, derecha)
    private List<Cell> getNeighbors(Cell cell, Cell[][] maze) {
        List<Cell> neighbors = new ArrayList<>();
        int r = cell.getRow();
        int c = cell.getCol();

        // Definimos desplazamientos para filas y columnas de los 4 vecinos
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Comprobamos que cada vecino esté dentro del rango del laberinto y lo agregamos
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (nr >= 0 && nr < maze.length && nc >= 0 && nc < maze[0].length) {
                neighbors.add(maze[nr][nc]);
            }
        }

        return neighbors;
    }

    @Override
    public String getName() {
        return "BFS";
    }

    @Override
    public int getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return getName();
    }
}

```

## 4. Interfaz Gráfica y Vista
*Espacio para que el Integrante 1 complete*

Descripción de la interfaz, los botones, la forma en que el usuario interactúa con el laberinto, validaciones, mejoras visuales y capturas de pantalla.

---

## 5. Controlador y Coordinación General
*Espacio para que el Integrante 3 complete*

Explicación del flujo general del programa, cómo interactúan las vistas con los modelos y controladores, eventos y manejo de datos.

---

## 6. Persistencia y Gestión de Resultados
*Espacio para que el Integrante 4 complete*

Descripción del sistema de almacenamiento en `results.csv`, prevención de duplicados, actualización de resultados, y presentación en tablas y gráficas.

---

## 7. Diagrama UML
*Espacio para que el Integrante 3 o 4 complete*

Diagrama UML que muestre las clases principales, sus relaciones, y el patrón MVC aplicado.

---

## 8. Conclusiones y Recomendaciones
*Espacio para que el Integrante 4 complete*

Reflexiones finales, dificultades encontradas, sugerencias para mejoras o futuras implementaciones.

---

## 9. Capturas de Pantalla
*Espacio para que el Integrante 1 complete*

Incluir imágenes de la interfaz, la selección de laberinto, resultados y gráficos.

---

## 10. Referencias
- [JFreeChart](https://www.jfree.org/jfreechart/) para gráficas.
- Documentación oficial de Java Swing.
- Artículos y tutoriales sobre algoritmos de búsqueda y backtracking.

---

# ¡Gracias por revisar el proyecto!

---

