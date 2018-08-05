package me.grinney.target.land_analysis;

/**
 * Created by geoffrey on 8/4/18.
 */
public class Land {
    private final Condition[][] grid;

    public Land(int length, int width) {
        if(length <= 0 || width <= 0) {
            throw new IllegalArgumentException("Land dimensions must be positive");
        }
        grid = new Condition[length][width];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                grid[i][j] = Condition.FERTILE;
            }
        }
    }

    public void addBarrenPlot(int left, int up, int right, int down) {
        if(left > right || up > down) {
            throw new IllegalArgumentException("left must be less than right and up must be less than down");
        }
        for (int i = left; i <= right; i++) {
            for(int j = up; j <= down; j++) {
                grid[i][j] = Condition.BARREN;
            }
        }
    }

    public void drawAsciiArt() {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == Condition.FERTILE) {
                    System.out.print('\'');
                } else if (grid[i][j] == Condition.BARREN) {
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
