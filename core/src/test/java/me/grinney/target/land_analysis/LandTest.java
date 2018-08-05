package me.grinney.target.land_analysis;

import org.testng.annotations.Test;

public class LandTest {

    @Test
    public void testConstructorHappyPath() {
        new Land(400, 600);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorInvalidArgs() {
        new Land(-1, 50);
    }

    @Test
    public void testAddBarrenPlot() {
        Land land = new Land(80, 20);
        land.addBarrenPlot(1,1, 20, 2);
        land.addBarrenPlot(0, 19, 79,19);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void testAddBarrenPlotNegativeDimensions() {
        Land land = new Land(400, 600);
        land.addBarrenPlot(-5,0,0,0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void testAddBarrenPlotExceedDimensions() {
        Land land = new Land(400, 600);
        land.addBarrenPlot(0,0,400,0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void testAddBarrenPlotBackwardDimensions() {
        Land land = new Land(400, 600);
        land.addBarrenPlot(25,0,24,0);
    }


}
