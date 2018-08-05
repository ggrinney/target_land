package me.grinney.target.land_analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by geoffrey on 8/4/18.
 *
 * Represents a rectangular plot of land containing fertile land and rectangular plots of barren land.
 */
public class Land {
    private final Condition[][] grid;

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

    public List<Integer> computeFertileAreas() {
        Condition[][] tally = copyGrid();
        int width = tally.length;
        int length = tally[0].length;

        List<Integer> areas = new ArrayList<Integer>();

        for (int i = 0; i <= width; i++) {
            for(int j = 0; j <= length; j++) {
                int area = fillAndComputeAreaFromPoint(tally, i, j);
                if(area > 0) {
                    System.out.println(area);
                    areas.add(area);
                }
            }
        }
        Collections.sort(areas);
        return areas;
    }

    private int fillAndComputeAreaFromPoint(Condition[][] tally, int x, int y) {
        if(x < 0 || x >= tally.length || y < 0 || y >=tally[0].length) {
            return 0;
        }
        if(tally[x][y] == Condition.BARREN) {
            return 0;
        }
        tally[x][y] = Condition.BARREN;
        return 1 + fillAndComputeAreaFromPoint(tally, x + 1, y)
                + fillAndComputeAreaFromPoint(tally, x - 1, y)
                + fillAndComputeAreaFromPoint(tally, x , y + 1)
                + fillAndComputeAreaFromPoint(tally, x , y - 1);
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

    public void printAsciiArt() {
        for(int i = grid[0].length - 1; i >= 0; i--) {
            for(int j = 0; j < grid.length; j++) {
                if(grid[j][i] == Condition.FERTILE) {
                    System.out.print('\'');
                } else if (grid[j][i] == Condition.BARREN) {
                    System.out.print('#');
                } else {
                    throw new IllegalStateException("Shouldn't happen. Land is either fertile or barren.");
                }
            }
            System.out.print('\n');
        }
    }

    private enum Condition {
        FERTILE,
        BARREN
    }
}
