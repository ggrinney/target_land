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


}
