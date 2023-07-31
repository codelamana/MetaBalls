package grids;

import grids.concreteoffsets.Sines;
import grids.concreteoffsets.Sombrero;
import processing.core.PApplet;
import processing.core.PVector;

public class PerlinGrid extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{PerlinGrid.class.getName()}, new PerlinGrid());
    }

    Grid g;

    @Override
    public void settings() {
        size(800, 600, P2D);
        fullScreen();
        noiseDetail(1, 0.1f);
    }

    @Override
    public void setup() {

        g = new Grid(width, height, 10, 100);
        //g.offset(new Sombrero());
        g.offset(new Sines());
    }


    @Override
    public void draw() {
        background(0);
        g.drawHorizontalLines(this);
        g.drawVerticalLines(this);
    }
}
