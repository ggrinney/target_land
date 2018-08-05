package me.grinney.target.land_analysis;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by geoffrey on 8/4/18.
 *
 * Computes areas of of fertile land in a rectangular plot containing rectangular sections of barren land.
 *
 * Takes two parameters, the with and length of the plot respectively.
 *
 * Barren sections are entered through STDIN. They consist in double quotes of four integers separated by single spaces
 * with no additional spaces.
 *
 * The areas of fertile land are returned through STOUT from smallest to largest separated by single spaces
 */
public class Main {
    private static final String INVALID_ARGS_STRING = "Arguments must be two positive integers representing the length and width of the land";

    private static final Pattern rectangle = Pattern.compile("\"(\\d+ \\d+ \\d+ \\d+)\"");

    public static void main(String[] args) {
        Land land = createLand(args);

        Scanner s = new Scanner(System.in);

        String coordString;

        while ((coordString = s.findWithinHorizon(rectangle, 0)) != null) {
            String[] coords = coordString.substring(1, coordString.length() - 1).split(" ");
            land.addBarrenPlot(Integer.parseInt(coords[0]),
                    Integer.parseInt(coords[1]),
                    Integer.parseInt(coords[2]),
                    Integer.parseInt(coords[3]));
        }

        boolean first = true;
        for(Integer integer : land.computeFertileAreas()) {
            if(!first) {
                System.out.print(' ');
            } else {
                first = false;
            }
            System.out.print(integer);
        }
        System.out.print('\n');
    }

    private static Land createLand(String[] args) {
        if (args.length != 2) {
            failInitialization();
        }
        try {
            int width = Integer.parseInt(args[0]);
            int length = Integer.parseInt(args[1]);

            if (width < 0 || length < 0) {
                failInitialization();
            }
            return new Land(width, length);
        } catch (NumberFormatException nfe) {
            failInitialization();
        }
        throw new IllegalStateException("Shouldn't happen");
    }

    private static void failInitialization() {
        System.out.println(INVALID_ARGS_STRING);
        System.exit(1);
    }
}
