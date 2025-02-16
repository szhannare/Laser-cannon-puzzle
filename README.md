[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/XbZw8B6J)
# LASER-CANNON PUZZLE

You start from the upper left corner of the board, your goal is to reach the lower right corner.

In every turn you can move one square horizontally or vertically.

Above and on the left side of the board you can see little triangles. These triangles mark cannons which when active, fire lasers on the start of every turn, thus you must not stand in the column or row of an active cannon.
In the first round the filled in cannons are inactive, thus the others active. In the next round these states will be reversed, the now inactive ones will be active and the active ones inactive.

You also must not step on the filled in squares on the board.

Good luck and have fun!

Download the game:
-
execute the following command from a bash opened in the folder you want to download the game to:
___
    git clone https://github.com/INBPM0420L/homework-project-2024-szhannare.git
    cd homework-project-2024-szhannare
___

Play the game:
- 
execute the following command from a bash opened in the project's folder:
___
    mvn clean compile exec:java  
___
    mvn package
    cd target
    java -jar laser-cannon-puzzle-game-1.0.jar
___
*A possible sequence of keys to get to the goal: ↓ ↓ ↓ → → → ↓ ↓ ← ← ← ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ → → → ↓ → → ↑ → ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ← ↑ ↑ → ↑ ↑ ↑ → → → → → → → → ↓ ↓ ↓ ← ↓ ↓ → ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓*

Play the game on console:  
-
execute the following command from a bash opened in the project's folder:
___
    mvn clean compile exec:java -Dexec.mainClass="laserpuzzle.play.Console"
___
*A possible sequence of keys to get to the goal: s s s d d d s s a a a s s s s s s s s d d d s d d w d w w w w w w w w a w w d w w w d d d d d d d d s s s a s s d s s s s s s s s s*

Get a solution for the game:
-
execute the following commands from a bash opened in the project's folder:
___
    set MAVEN_OPTS=-Xmx{size}m
    mvn clean compile exec:java -Dexec.mainClass="laserpuzzle.solver.Solver"
___
*You need to increase the {size} over 5120, as 5 GB of RAM wasn't enough for the solver to find a solution, my resources didn't allow me to increase it further, but you are free to experiment.*
___
If you would like to make sure the logic of the game is good, you can try this command:  
___
    mvn clean compile exec:java -Dexec.mainClass="laserpuzzle.solver.SolverSmaller"
___
*For some reason the step counter doesn't increase after 2, while with the console game that uses the same logic the number of the steps is increasing.*  

It plays on a smaller board that uses the same logic, just the size, the number of cannons and the location of the blocked squares is changed.  

If you'd like to play this version of the console, execute this:
___
    mvn clean compile exec:java -Dexec.mainClass="laserpuzzle.play.ConsoleSmaller"
___
Press these keys to complete it: s s s d d d s s d d  

Read the API documentation:
-
execute the following commands from a bash opened in the project's folder:

on Windows:
___
    mvn clean site  
    cd target  
    cd site   
    start index.html  
___
on Linux:  
___
    mvn clean site  
    cd target  
    cd site  
-  open in Firefox:  

        firefox index.html  
- open in Chromium:  

        chromium index.html  
___# Laser-cannon-puzzle
