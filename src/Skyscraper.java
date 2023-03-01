import java.util.Optional;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Tha main Skyscraper class is run as:
 *  $java Skyscraper [filename] [debug]
 *       [filename]: The name of the board file
 *       [debug]: true or false for debug output
 *
 *  @author RIT CS
 */
public class Skyscraper {
    /** 
     * The main program.
     * @param args command line arguments
     * @throws FileNotFoundException if file not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            System.err.println("Usage: java Skyscraper file debug");
        } else {
            // pass scanner object to constructor to read initial board
            String fileName = args[0];
            SkyscraperConfig initConfig = new SkyscraperConfig(fileName);

            boolean debug = args[1].equals("true");
            System.out.println("File: " + fileName);
            System.out.println("Debug: " + debug);
            System.out.println("Initial config:");
            System.out.println(initConfig);

            // create the backtracker with the debug flag
            Backtracker bt = new Backtracker(debug);

            // start the clock
            double start = System.currentTimeMillis();

            // solve the puzzle
            Optional<Configuration> solution = bt.solve(initConfig);

            // compute the elapsed time
            double elapsed = (System.currentTimeMillis() - start) / 1000.0;

            // display the solution, if one exists
            if (solution.isPresent()) {
                System.out.println("Solution:\n" + solution.get());
            } else {
                System.out.println("No solution");
            }

            System.out.println("Elapsed time: " + elapsed + " seconds.");
        }
    }
} 
