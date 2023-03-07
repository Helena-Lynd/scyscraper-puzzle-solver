import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.*;

/**
 * Represents a single configuration in the skyscraper puzzle.
 *
 * @author Helena Lynd
 */
public class SkyscraperConfig implements Configuration {
    /** empty cell value */
    public final static int EMPTY = 0;

    /** empty cell value display */
    public final static char EMPTY_CELL = '.';
    public static int size;
    public static int[] lookNS;
    public static int[] lookSN;
    public static int[] lookEW;
    public static int[] lookWE;
    public int[][] grid;
    public int row;
    public int column;
    /**
     * Constructor
     *
     * @param filename the filename
     *  <p>
     *  Read the board file.  It is organized as follows:
     *  DIM     # square DIMension of board (1-9)
     *  lookNS   # DIM values (1-DIM) left to right
     *  lookEW   # DIM values (1-DIM) top to bottom
     *  lookSN   # DIM values (1-DIM) left to right
     *  lookWE   # DIM values (1-DIM) top to bottom
     *  row 1 values    # 0 for empty, (1-DIM) otherwise
     *  row 2 values    # 0 for empty, (1-DIM) otherwise
     *  ...
     *
     *  @throws FileNotFoundException if file not found
     */
    SkyscraperConfig(String filename) throws FileNotFoundException {
        Scanner f = new Scanner(new File(filename));

        size = f.nextInt();
        lookNS = new int[size];
        for (int i = 0; i < size; i++){
            lookNS[i] = f.nextInt();
        }
        lookEW = new int[size];
        for (int i = 0; i < size; i++){
            lookEW[i] = f.nextInt();
        }
        lookSN = new int[size];
        for (int i = 0; i < size; i++){
            lookSN[i] = f.nextInt();
        }
        lookWE = new int[size];
        for (int i = 0; i < size; i++){
            lookWE[i] = f.nextInt();
        }
        this.grid = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                this.grid[i][j] = f.nextInt();
            }
        }
        this.row = 0;
        this.column = -1;
        // close the input file
        f.close();
    }

    /**
     * Copy constructor
     *
     * @param other SkyscraperConfig instance
     * @param height height
     */
    public SkyscraperConfig(SkyscraperConfig other, int height) {
        this.grid = new int[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                this.grid[i][j] = other.grid[i][j];
            }
        }
        this.row = other.row;
        this.column = other.column;
        this.grid[this.row][this.column] = height;
    }

    /**
     * Determines if the end of the board has been reached or not
     *
     * @return Boolean, true if at end of board, false otherwise
     */
    @Override
    public boolean isGoal() {
        if (this.row == size - 1 && this.column == size - 1){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Increments the cursor and produces a collection of all possible values for the position in the grid that the
     * updated cursor points to
     *
     * @returns Collection of Configurations
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        if (this.column == size - 1){
            this.column = 0;
            this.row++;
        } else {
            this.column++;
        }
        List<Configuration> successors = new LinkedList<>();
        if (this.grid[this.row][this.column] == EMPTY) {
            for (int i = 1; i <= size; i++) {
                successors.add(new SkyscraperConfig(this, i));
            }
        } else {
            successors.add(new SkyscraperConfig(this, grid[this.row][this.column]));
        }
        return successors;
    }

    /**
     * isValid() - checks if current config is valid
     *
     * @returns true if config is valid, false otherwise
     */
    @Override
    public boolean isValid() {
        //check no duplicates in rows
        int newVal;
        newVal = this.grid[this.row][this.column];
        for (int i = this.column - 1; i >= 0; i--) {
            if (this.grid[this.row][i] == newVal) {
                return false;
            }
        }

        //check no duplicates in columns
        if (this.row != 0) {
            newVal = this.grid[this.row][this.column];
            for (int i = this.row - 1; i >= 0; i--) {
                if (this.grid[i][this.column] == newVal) {
                    return false;
                }
            }
        }

        //check looking values

        //check WE
        if (this.column == 0) {
            if (lookWE[row] == 1) {
                if (this.grid[this.row][this.column] != size) {   //if you can only see 1 building, it must be the max height
                    return false;
                }
            } else if (((size + 1) - lookWE[row]) < this.grid[this.row][this.column]){
                return false;
            }
        }

        if (this.column != 0 && this.column != size - 1) {
            if (lookWE[row] != size) {
                int buildingsNeeded = lookWE[row];
                int tallestBuilding = 0;
                int buildingsSeen = 0;
                for (int i = 0; i <= this.column; i++) {
                    if (this.grid[this.row][i] > tallestBuilding) {
                        buildingsSeen += 1;
                        tallestBuilding = this.grid[this.row][i];
                    }
                }
                if (buildingsSeen > buildingsNeeded) {
                    return false;
                }
            }
        }

        if (this.column == size - 1) {
            if (lookWE[row] != 1 && lookWE[row] != size) {
                int buildingsNeeded = lookWE[row];
                int tallestBuilding = 0;
                int buildingsSeen = 0;
                for (int i = 0; i <= this.column; i++) {
                    if (this.grid[this.row][i] > tallestBuilding) {
                        buildingsSeen += 1;
                        tallestBuilding = this.grid[this.row][i];
                    }
                }
                if (buildingsSeen != buildingsNeeded) {
                    return false;
                }
            }
        }
        if (lookWE[row] == size) {
            //whole row should be an incrementing line, starting with 1
            if (this.grid[this.row][this.column] != this.column + 1) {
                return false;
            }
        }

        //check NS
        if (this.row == 0){
            if (lookNS[this.column] == 1){
                if (this.grid[this.row][this.column] != size){
                    return false;
                }
            } else if (((size + 1) - lookNS[column]) < this.grid[this.row][this.column]){
                return false;
            }
        }

        if (this.row != 0 && this.row != size - 1) {
            if (lookNS[column] != size) {
                int buildingsNeeded = lookNS[column];
                int tallestBuilding = 0;
                int buildingsSeen = 0;
                for (int i = 0; i <= this.row; i++) {
                    if (this.grid[i][this.column] > tallestBuilding) {
                        buildingsSeen += 1;
                        tallestBuilding = this.grid[i][this.column];
                    }
                }
                if (buildingsSeen > buildingsNeeded) {
                    return false;
                }
            }
        }

        if (this.row == size - 1) {
            if (lookNS[column] != 1 && lookNS[column] != size) {
                int buildingsNeeded = lookNS[column];
                int tallestBuilding = 0;
                int buildingsSeen = 0;
                for (int i = 0; i <= this.row; i++) {
                    if (this.grid[i][this.column] > tallestBuilding) {
                        buildingsSeen += 1;
                        tallestBuilding = this.grid[i][this.column];
                    }
                }
                if (buildingsSeen != buildingsNeeded) {
                    return false;
                }
            }
        }

        if (lookNS[column] == size) {
            //whole column should be an incrementing line, starting with 1
            if (this.grid[this.row][this.column] != this.row + 1) {
                return false;
            }
        }


        //check SN
        if (this.row == size - 1) {
            if (lookSN[column] != 1 && lookNS[column] != size) {
                int buildingsNeeded = lookSN[column];
                int tallestBuilding = 0;
                int buildingsSeen = 0;
                for (int i = this.row; i >= 0; i--) {
                    if (this.grid[i][this.column] > tallestBuilding) {
                        buildingsSeen += 1;
                        tallestBuilding = this.grid[i][this.column];
                    }
                }
                if (buildingsSeen != buildingsNeeded) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * toString() method
     *
     * @return String representing configuration board & grid w/ look values.
     * The format of the output for the problem-solving initial config is:
     *
     *   1 2 4 2
     *   --------
     * 1|. . . .|3
     * 2|. . . .|3
     * 3|. . . .|1
     * 3|. . . .|2
     *   --------
     *   4 2 1 2
     */
    @Override
    public String toString() {
        String totalString = "\n   ";
        for (int i = 0; i < size; i++){
            totalString = totalString.concat(lookNS[i] + " ");
        }
        totalString = totalString.concat("\n  ");
        for (int i = 0; i < size; i++){
            totalString = totalString.concat("--");
        }
        totalString = totalString.concat("-\n");
        for (int i = 0; i < size; i++){
            totalString = totalString.concat(lookWE[i] + "| ");
            for (int j = 0; j < size; j++) {
                if (this.grid[i][j] == EMPTY){
                    totalString = totalString.concat((EMPTY_CELL + " "));
                } else {
                    totalString = totalString.concat(this.grid[i][j] + " ");
                }
            }
            totalString = totalString.concat("|" + lookEW[i] + "\n");
        }
        totalString = totalString.concat("  ");
        for (int i = 0; i < size; i++){
            totalString = totalString.concat("--");
        }
        totalString = totalString.concat("-\n   ");
        for (int i = 0; i < size; i++){
            totalString = totalString.concat(lookSN[i] + " ");
        }
        totalString = totalString.concat("\n");
        return totalString;
    }
}
