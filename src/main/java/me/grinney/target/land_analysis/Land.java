package me.grinney.target.land_analysis;

import java.util.*;

/**
 * Created by geoffrey on 8/4/18.
 *
 * Represents a rectangular plot of land containing fertile land and rectangular plots of barren land.
 */
public class Land {
    private final Condition[][] grid;

    /**
     * Creates a plot of fertile land with the given dimensions
     * @param width width of plot
     * @param length length of plot
     */
    public Land(int width, int length) {
        if(length <= 0 || width <= 0) {
            throw new IllegalArgumentException("Land dimensions must be positive");
        }

        grid = new Condition[width][length];

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                grid[i][j] = Condition.FERTILE;
            }
        }
    }

    /**
     * Adds a rectangular barren plot to this land
     * @param left coordinate of leftmost boundary
     * @param bottom coordinate of bottommost boundary
     * @param right coordinate of rightmost boundary
     * @param top coordinate of topmost boundary
     */
    public void addBarrenPlot(int left, int bottom, int right, int top) {
        validateAddBarrenPlotParams(left, bottom, right, top);

        for (int i = left; i <= right; i++) {
            for(int j = bottom; j <= top; j++) {
                grid[i][j] = Condition.BARREN;
            }
        }
    }

    private void validateAddBarrenPlotParams(int left, int bottom, int right, int top) {
        int width = grid.length;
        int length = grid[0].length;

        if(left < 0 || left >= width
                || bottom < 0 || bottom >= length
                || right < 0 || right >= width
                || top < 0 || top >= length) {
            throw new IllegalArgumentException("Arguments must lie within the dimensions of the land: [0,"
                    + width + ") [0," + length + ")");
        }
        if(left > right || top < bottom) {
            throw new IllegalArgumentException("left must be less than right and up must be less than down");
        }
    }

    /**
     * Computes the areas of the fertile sections of the land.
     * @return The areas of the fertile sections from smallest to largest
     */
    public List<Integer> computeFertileAreas() {
        Condition[][] tally = copyGrid();
        int width = tally.length;
        int length = tally[0].length;

        List<Integer> areas = new ArrayList<Integer>();

        for (int i = 0; i < width; i++) {
            for(int j = 0; j < length; j++) {
                int area = fillAndComputeAreaFromPoint(tally, new Coordinate(i, j));
                if(area > 0) {
                    areas.add(area);
                }
            }
        }
        Collections.sort(areas);
        return areas;
    }

    private int fillAndComputeAreaFromPoint(Condition[][] tally, Coordinate coord) {
        if(tally[coord.getX()][coord.getY()] == Condition.BARREN) {
            return 0;
        }

        int area = 0;

        Stack<Coordinate> remaining = new Stack<>();
        remaining.push(coord);

        while(!remaining.isEmpty()) {

            Coordinate curr = remaining.pop();

            if(tally[curr.getX()][curr.getY()] != Condition.BARREN) {
                // above curr
                addRemainingCoordinate(tally, remaining, new Coordinate(curr.getX(), curr.getY() + 1));

                // below curr
                addRemainingCoordinate(tally, remaining, new Coordinate(curr.getX(), curr.getY() - 1));

                // to the left of curr
                addRemainingCoordinate(tally, remaining, new Coordinate(curr.getX() - 1, curr.getY()));

                // to the right of curr
                addRemainingCoordinate(tally, remaining, new Coordinate(curr.getX() + 1, curr.getY()));

                area++;
            }

            tally[curr.getX()][curr.getY()] = Condition.BARREN;
        }

        return area;
    }

    private void addRemainingCoordinate(Condition[][] tally, Stack<Coordinate> remaining, Coordinate coord) {
        if(isFertile(tally, coord)) {
            remaining.add(coord);
        }
    }

    private boolean isFertile(Condition[][] tally, Coordinate coord) {
        return  !(coord.getX() < 0 || coord.getX() >= tally.length
                    || coord.getY() < 0 || coord.getY() >= tally[0].length
                    || tally[coord.getX()][coord.getY()] == Condition.BARREN);
    }



    private class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x &&
                    y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private Condition[][] copyGrid() {
        int width = grid.length;
        int length = grid[0].length;

        Condition[][] copy = new Condition[width][length];

        for (int i = 0; i < width; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, length);
        }
        return copy;
    }

    /**
     * Prints an ASCII visualization of the 2D array of Conditions representing a plot of land.
     * This is useful in debugging.
     * @param land the 2D array of Conditions to visualize
     */
    public static void printAsciiArt(Condition[][] land) {
        for(int i = land[0].length - 1; i >= 0; i--) {
            for(int j = 0; j < land.length; j++) {
                if(land[j][i] == Condition.FERTILE) {
                    System.out.print('\'');
                } else if (land[j][i] == Condition.BARREN) {
                    System.out.print('#');
                } else {
                    throw new IllegalStateException("Shouldn't happen. Land is either fertile or barren.");
                }
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    private enum Condition {
        FERTILE,
        BARREN
    }
}
