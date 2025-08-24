## Note: This project was developed during our third year of the Computer Engineering program at the University of Buenos Aires.
_This repository **does not** reflect our current programming level or professional skills. It is kept here as an academic and knowledge record._

------------------------------------------------------------------------------------

**README** available in English and Spanish | **README** disponible en Inglés y en Español

------------------------------------------------------------------------------------

# Robots

2024c1

El objetivo de este trabajo práctico es implementar el videojuego Robots, que es una variente de un juego clásico conocido como Chase. Este es el primer trabajo práctico de la materia algoritmos y programación 3 (paradigmas de programación).

### Integrantes del grupo

1. SASSONE IRRAZABAL, Camilo - 111135
2. COLINA, Andrés Eduardo - 110680

### ACLARACIONES y CONTROLES:
Modificar a gusto la resolución vertical de la ventana antes de inciar el juego.
Cambiar la constante ALTO_VENTANA en la clase App. 
* Teclas:
- Q: Diagonal arriba-izq
- E: Diagonal arriba-der
- W: Arriba
- A: Izquierda
- D: derecha
- X: abajo
- S: No-moverse
- Z: Diagonal abajo-izq
- C: Diagonal abajo-der

- T: Redimensión
- O: Tp aleatorio
- P: Tp seguro (clickear casilla deseada)

### Reglas de juego


- Robots es un juego por turnos en el que el jugador debe escapar de unos robots que lo persiguen.
- El juego termina cuando el jugador es alcanzado por un robot.
- El juego se desarrolla en una sucesión infinita de niveles de dificultad incremental.
- Cada nivel se desarrolla en una grilla bidimensional de N filas y M columnas (valores configurables).
- El jugador comienza en el centro de la grilla.
- En cada nivel hay una cantidad de robots que comienzan en posiciones aleatorias.
- El jugador dispone de una cantidad limitada de usos de teletransportación segura. Al comenzar cada nivel se le otorga un uso adicional.
- En cada turno, el jugador puede:
    - Moverse a una celda vecina, en cualquiera de las 8 direcciones posibles (movimiento horizontal, vertical y diagonal).
    - Teletransportarse a una celda aleatoria (que puede o no estar ocupada por un robot).
    - Si tiene usos disponibles de teletransportación segura, teletransportarse a una celda a elección.
- Luego de cada movimiento del jugador, cada uno de los robots efectúan su movimiento.
- Hay dos tipos de robots:
    - 1x: siempre se mueve hacia el jugador, una celda por turno.
    - 2x: siempre se mueve hacia el jugador, dos celdas por turno.
- Si alguno de los robots llega a la celda en la que está el jugador, el juego termina.
- Si al finalizar el turno, dos o más robots se encuentran en la misma celda, los robots son destruidos y esa celda queda incendiada hasta que termina el nivel.
- Si el jugador toca una celda incendiada, termina el juego.
- Si un robot toca una celda incendiada, el robot es destruido.
- Si al finalizar el turno, todos los robots están destruidos, el jugador pasa al siguiente nivel.


### Interfaz gráfica

- Se debe implementar una interfaz gráfica para el juego, con JavaFX.
- Debe ser posible jugar con el mouse o con el teclado.
- Se puede usar cualquier set de imágenes para representar los elementos del juego, por ejemplo las de Gnome Robots.
- Se debe permitir configurar el tamaño de la grilla (cantidad de filas y columnas).
- Luego de perder una partida, se debe permitir comenzar de nuevo.


### Requerimientos adicionales

- El proyecto debe utilizar Maven.
- Deben estar bien separadas las clases relacionadas con la lógica y la interfaz gráfica, en 2 capas de abstracción.
- Se debe utilizar polimorfismo para implementar los comportamientos de los distintos robots y las celdas incendiadas.
- Agregar un diagrama de clases y/o secuencia en formato png o pdf. El diagrama debe incluir las clases principales de la capa lógica.



------------------------------------------------------------------------------------

## Nota: Este proyecto fue desarrollado durante nuestro tercer año del programa de Ingeniería en Informática en la Universidad de Buenos Aires.
_Este repositorio **no** refleja nuestro nivel actual de programación ni nuestro set de skills profesionales. Se guarda aquí como un registro académico y de conocimiento.__

------------------------------------------------------------------------------------
