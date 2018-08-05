package me.grinney.target.land_analysis;

/**
 * Created by geoffrey on 8/4/18.
 */
public class Main {
    private static String INVALID_ARGS_STRING = "Arguments must be two positive integers representing the length and width of the land";

    public static void main(String[] args) {
        Land land = createLand(args);

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
