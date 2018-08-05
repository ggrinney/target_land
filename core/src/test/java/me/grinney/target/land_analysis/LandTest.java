package me.grinney.target.land_analysis;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.List;

public class LandTest {

    @Test
    public void testConstructorHappyPath() {
        new Land(400, 600);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorNegativeDimension() {
        new Land(-1, 50);
    }

    @Test
    public void testAddBarrenPlot() {
        Land land = new Land(80, 20);
        land.addBarrenPlot(1,1, 20, 2);
        land.addBarrenPlot(0, 19, 79,19);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void testAddBarrenPlotNegativeDimension() {
        Land land = new Land(400, 600);
        land.addBarrenPlot(-5,0,0,0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void testAddBarrenPlotExceedDimension() {
        Land land = new Land(400, 600);
        land.addBarrenPlot(0,0,400,0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void testAddBarrenPlotBackwardDimension() {
        Land land = new Land(400, 600);
        land.addBarrenPlot(25,0,24,0);
    }

    @Test
    void testComputeFertileAreas() {
        Land land = new Land(4, 6);
        land.addBarrenPlot(0, 1, 3, 4);
        List<Integer> areas = land.computeFertileAreas();
        assertEquals((int) areas.get(0), 4);
        assertEquals((int) areas.get(1), 4);

        land = new Land(400, 600);
        land.addBarrenPlot(0, 292, 399, 307);
        areas = land.computeFertileAreas();
        assertEquals((int) areas.get(0), 116800);
        assertEquals((int) areas.get(1), 116800);

        land = new Land(400, 600);
        land.addBarrenPlot(0, 292, 399, 307);
        land.addBarrenPlot(0, 0, 0, 0);
        areas = land.computeFertileAreas();
        assertEquals((int) areas.get(0), 116799);
        assertEquals((int) areas.get(1), 116800);

        land = new Land(400, 600);
        land.addBarrenPlot(48, 192, 351, 207);
        land.addBarrenPlot(48, 392, 351, 407);
        land.addBarrenPlot(120, 52, 135, 547);
        land.addBarrenPlot(260, 52, 275, 547);
        areas = land.computeFertileAreas();
        assertEquals((int) areas.get(0), 22816);
        assertEquals((int) areas.get(1), 192608);
    }

}
