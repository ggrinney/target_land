package me.grinney.target.land_analysis;

/**
 * Created by geoffrey on 8/4/18.
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

    public void drawAsciiArt() {
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
