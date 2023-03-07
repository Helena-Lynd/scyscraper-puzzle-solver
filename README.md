# skyscraper-puzzle-solver
A Java program that solves a given skyscraper puzzle using a breadth-first search algorithm.

![ProgramResults](https://github.com/Helena-Lynd/skyscraper-puzzle-solver/blob/main/scyscraper-output.png?raw=true)

## Description<br>
A skyscraper puzzle is a grid-based number puzzle similar to soduku. The rules are:
- Numbers inside the grid represent the size of the skyscrapers, with the smallest height bring represented by 1 and increasing up to the number of rows.
- A skyscraper can only be seen if it is not behind a larger skyscraper. 
- Each number can only be used once per row and column. Numbers line the side of the grid that state how many skyscrapers should be seen.<br>
![SkyScraper Puzzle Example](https://www.conceptispuzzles.com/picture/11/3846.jpg)<br>
This program uses a breadth-first search algorithm to solve any skyscraper puzzle.

## Getting Started<br>
### Dependencies
- Java 18+
- IntelliJ IDE
### Installing
- Download the source files provided to your directory of choice
```
git clone git@github.com:Helena-Lynd/skyscraper-puzzle-solver.git
```
### Executing
- Open the project in IntelliJ
- Play any of the given run configurations
## Modifying
- If you would like to solve a different skyscraper puzzle, replace the contents of one of the files in the "data/" directory with the puzzle of your choice. Be sure to follow the format of the given puzzle files, the program will not be able to solve a puzzle without the proper syntax.
## Common Errors
"Imports could not be resolved"
- Ensure that your JDK is configured for the project
- Right-click on the "src" folder and <i>Mark as Sources Root</i>
## Authors<br>
Helena Lynd
